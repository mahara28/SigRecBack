package com.mc.back.sigrecette.controller.priv;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.mc.back.sigrecette.service.IAdmFoncProfileService;
import com.mc.back.sigrecette.service.IAdmFoncService;
import com.mc.back.sigrecette.service.IAdmProfileService;
import com.mc.back.sigrecette.service.ICommonService;
import com.mc.back.sigrecette.service.ISendWsService;

import jakarta.servlet.http.HttpServletRequest;

@RestController
@CrossOrigin("*")
@RequestMapping("/admFoncProfil")
public class AdmFoncProfilController {
	
	private static final Logger logger = LogManager.getLogger(AdmFoncProfilController.class);

    @Autowired
    private IAdmFoncService admFoncService;

    @Autowired
    private ISendWsService sendWsService;

    @Autowired
    private ICommonService commonService;

    @Autowired
    private IAdmFoncProfileService admFoncProfileService;
	
	@GetMapping(value = "/menu", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getMenusWs(HttpServletRequest request, @RequestParam("idProfil") Long idProfil) {
        try {
            
                
                return sendWsService.sendResult(request, admFoncProfileService.findAdmFoncProfileByIdWs(idProfil));
            
        } catch (Exception argEx) {
            logger.error("Error AdmProfileController in method getMenusWs :: {}", String.valueOf(argEx));
            return sendWsService.sendResultException(request);
        }
    }
}
