package com.mc.back.sigrecette.controller.pub;

import com.mc.back.sigrecette.model.AdmVerificationCode;
import com.mc.back.sigrecette.model.tool.AuthRequest;
import com.mc.back.sigrecette.security.JwtSecurityUtil;
import com.mc.back.sigrecette.service.IAdmUserService;
import com.mc.back.sigrecette.service.IAdmVerificationCodeService;
import com.mc.back.sigrecette.service.ICommonService;
import com.mc.back.sigrecette.service.ISendWsService;
import jakarta.servlet.http.HttpServletRequest;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ServerWebExchange;
import java.util.Map;

@RestController
@RequestMapping("/public")
@CrossOrigin(origins = "*")
public class PublicController {

    private static final Logger logger = LogManager.getLogger(PublicController.class);

    @Autowired
    private IAdmUserService admUserService;
    
    @Autowired
    private IAdmVerificationCodeService admVerificationCodeService;

    @Autowired
    private ICommonService commonService;

    @Autowired
    private ISendWsService sendWsService;

    
    @Autowired
    private JwtSecurityUtil jwtSecurityUtil;




    @RequestMapping(value = "/authenticate", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> authenticateUser(HttpServletRequest exchange, @RequestBody AuthRequest authenticationRequest) {
        try {
            return sendWsService.sendResultPublic(
                    admUserService.authenticateUserWs(authenticationRequest, sendWsService.ipAddressFormWeb(exchange))
            );
        } catch (Exception argEx) {
            logger.error("Error PublicController in method authenticate :: {}", String.valueOf(argEx));
            return sendWsService.sendResultException(exchange);
        }
    }
    
    @RequestMapping(value = "/authenticateUserNo2FA", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> authenticateUserNo2FA(HttpServletRequest exchange, @RequestBody AuthRequest authenticationRequest) {
        try {
            return sendWsService.sendResultPublic(
            		admVerificationCodeService.GenerationCodeVerif(authenticationRequest, sendWsService.ipAddressFormWeb(exchange))
            );
        } catch (Exception argEx) {
            logger.error("Error PublicController in method authenticate :: {}", String.valueOf(argEx));
            return sendWsService.sendResultException(exchange);
        }
    }
    
    @RequestMapping(value = "/verifyCode", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> verifyCode(HttpServletRequest exchange, @RequestBody AdmVerificationCode entity) {
        try {
            return sendWsService.sendResultPublic(
            		admVerificationCodeService.verifyCode(entity, sendWsService.ipAddressFormWeb(exchange))
            );
        } catch (Exception argEx) {
            logger.error("Error PublicController in method authenticate :: {}", String.valueOf(argEx));
            return sendWsService.sendResultException(exchange);
        }
    }
    
    /*@GetMapping(value = "whoAmI", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> whoAmI(ServerWebExchange exchange) {
        try {
            return sendWsService.sendResult(
                    exchange,
                    admUserService.whoAmI(admUserService.getUserIdFromToken(exchange))
            );
        } catch (Exception e) {
            logger.error("Error PublicController in method whoAmI :: {}", String.valueOf(e));
            return sendWsService.sendResultException(exchange);
        }
    }*/
    
    @GetMapping(value = "whoAmI", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> whoAmI(HttpServletRequest request) {
        try {
            return sendWsService.sendResult(
                    request,
                    admUserService.whoAmI(admUserService.getUserIdFromToken(request))
            );
        } catch (Exception e) {
            logger.error("Error PublicController in method whoAmI :: {}", String.valueOf(e));
            return sendWsService.sendResultException(request);
        }
    }

    @GetMapping(value = "/dateNow", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getDateNow() {
        try {
            return sendWsService.sendResultPublic(commonService.getDateSystemNowWs());
        } catch (Exception argEx) {
            logger.error("Error PublicController in method dateNow :: {}", String.valueOf(argEx));
            return sendWsService.sendResultPublic(null);
        }
    }


    @GetMapping(value = "logout", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> logout(ServerWebExchange request, @RequestParam("id") Long id) {
    	
        String authHeader = request.getRequest().getHeaders().getFirst(HttpHeaders.AUTHORIZATION);
        String token = authHeader.substring(7).trim();
       
        try {
        	
            return sendWsService.sendResult(
            		request,
                    admUserService.logout(token)
            );
        } catch (Exception e) {
            logger.error("Error PublicController in method logout :: {}", String.valueOf(e));
            return sendWsService.sendResultException(request);
        }
    }
    
    @PostMapping(value = "/refresh", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> refresh(ServerWebExchange exchange, @RequestBody Map<String,String> payload) {
        try {
            String refreshToken = payload.get("refreshToken");
            return sendWsService.sendResultPublic(admUserService.refreshAccessToken(refreshToken));
        } catch (Exception e) {
            logger.error("Error PublicController in method refresh :: {}", String.valueOf(e));
            return sendWsService.sendResultException(exchange);
        }
    }


}