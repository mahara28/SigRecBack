package com.mc.back.sigrecette.controller.priv;

import com.mc.back.sigrecette.model.AdmUserProfil;
import com.mc.back.sigrecette.service.IAdmUserProfilService;
import com.mc.back.sigrecette.service.ICommonService;
import com.mc.back.sigrecette.service.ISendWsService;
import com.mc.back.sigrecette.tools.ConstanteWs;
import com.mc.back.sigrecette.tools.model.SearchObject;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.servlet.http.HttpServletRequest;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/admUserProfil")
@CrossOrigin("*")
public class AdmUserProfilController {

    private static final Logger logger = LogManager.getLogger(AdmUserProfilController.class);

    @Autowired
    private IAdmUserProfilService admUserProfilService;

    @Autowired
    private ISendWsService sendWsService;

    @Autowired
    private ICommonService commonService;

    @Operation(summary = "Recuperation liste de donnees sans filtre de recherche")
    @ApiResponses(value = { @ApiResponse(responseCode = ConstanteWs._CODE_WS_SUCCESS, description = "Success"),
            @ApiResponse(responseCode = ConstanteWs._CODE_WS_ERROR_ALIAS_PARAM, description = "Un ou plus param�tre(s) est null", content = @Content),
            @ApiResponse(responseCode = ConstanteWs._CODE_WS_ERROR_IN_METHOD, description = "Erreur du methode", content = @Content),
            @ApiResponse(responseCode = ConstanteWs._CODE_WS_ERROR, description = "Erreur du service", content = @Content) })
    @GetMapping(value = "/", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getListAdmUserProfilWs(HttpServletRequest request) {
        try {
            return sendWsService.sendResult(request, admUserProfilService.getListAdmUserProfilWs());
        } catch (Exception argEx) {
            logger.error("Error AdmUserProfilController in method getListAdmUserProfilWs :: {}", argEx.toString());
            return sendWsService.sendResultException(request);
        }
    }

    @Operation(summary = "Recuperation liste de donnees avec filtre de recherche")
    @ApiResponses(value = { @ApiResponse(responseCode = ConstanteWs._CODE_WS_SUCCESS, description = "Success"),
            @ApiResponse(responseCode = ConstanteWs._CODE_WS_ERROR_ALIAS_PARAM, description = "Un ou plus param�tre(s) est null", content = @Content),
            @ApiResponse(responseCode = ConstanteWs._CODE_WS_ERROR_IN_METHOD, description = "Erreur du methode", content = @Content),
            @ApiResponse(responseCode = ConstanteWs._CODE_WS_ERROR, description = "Erreur du service", content = @Content) })
    @PostMapping(value = "/data", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getDataAdmUserProfilWs(HttpServletRequest request, @RequestBody SearchObject obj) {
        try {
            return sendWsService.sendResult(request,
                    commonService.getListPaginator(obj, new AdmUserProfil(), null));
        } catch (Exception argEx) {
            logger.error("Error AdmUserProfilController in method getDataAdmUserProfilWs :: {}", argEx.toString());
            return sendWsService.sendResultException(request);
        }
    }

    @Operation(summary = "Recuperation un objet d'apres son id")
    @ApiResponses(value = { @ApiResponse(responseCode = ConstanteWs._CODE_WS_SUCCESS, description = "Success"),
            @ApiResponse(responseCode = ConstanteWs._CODE_WS_ERROR_ALIAS_PARAM, description = "Un ou plus param�tre(s) est null", content = @Content),
            @ApiResponse(responseCode = ConstanteWs._CODE_WS_ERROR_IN_METHOD, description = "Erreur du methode", content = @Content),
            @ApiResponse(responseCode = ConstanteWs._CODE_WS_ERROR, description = "Erreur du service", content = @Content) })
    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getAdmUserProfilByIdWs(HttpServletRequest request,
                                                    @PathVariable(name = "id") Long id) {
        try {
            return sendWsService.sendResult(request, admUserProfilService.findAdmUserProfilByIdWs(id));
        } catch (Exception argEx) {
            logger.error("Error AdmUserProfilController in method getAdmUserProfilByIdWs :: {}", argEx.toString());
            return sendWsService.sendResultException(request);
        }
    }
}
