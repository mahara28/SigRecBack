package com.mc.back.sigrecette.controller.pub;

import com.mc.back.sigrecette.model.tool.AuthRequest;
import com.mc.back.sigrecette.security.JwtSecurityUtil;
import com.mc.back.sigrecette.service.IAdmUserService;
import com.mc.back.sigrecette.service.ICommonService;
import com.mc.back.sigrecette.service.ISendWsService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.servlet.http.HttpServletRequest;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.servlet.view.RedirectView;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/public")
@CrossOrigin(origins = "*")
public class PublicController {

    private static final Logger logger = LogManager.getLogger(PublicController.class);

    @Autowired
    private IAdmUserService admUserService;

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
                    admUserService.authenticateUserWs(authenticationRequest, sendWsService.ipAddressFormWeb(exchange),exchange)
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




}