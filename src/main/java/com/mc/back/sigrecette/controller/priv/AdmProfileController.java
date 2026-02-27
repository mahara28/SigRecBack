package com.mc.back.sigrecette.controller.priv;

import com.mc.back.sigrecette.model.AdmProfile;
import com.mc.back.sigrecette.service.IAdmFoncService;
import com.mc.back.sigrecette.service.IAdmProfileService;
import com.mc.back.sigrecette.service.ICommonService;
import com.mc.back.sigrecette.service.ISendWsService;
import com.mc.back.sigrecette.tools.ConstanteWs;
import com.mc.back.sigrecette.tools.model.CriteriaSearch;
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

import java.util.List;

@RestController
@CrossOrigin("*")
@RequestMapping("/admProfil")
public class AdmProfileController {

    private static final Logger logger = LogManager.getLogger(AdmProfileController.class);

    @Autowired
    private IAdmFoncService admFoncService;

    @Autowired
    private ISendWsService sendWsService;

    @Autowired
    private ICommonService commonService;

    @Autowired
    private IAdmProfileService admProfileService;

    @Operation(summary = "Get all users list data with search filter")
    @ApiResponses(value = {@ApiResponse(responseCode = ConstanteWs._CODE_WS_SUCCESS, description = "Success"),
            @ApiResponse(responseCode = ConstanteWs._CODE_WS_ERROR_ALIAS_PARAM, description = "One or many parameter(s) is null", content = @Content),
            @ApiResponse(responseCode = ConstanteWs._CODE_WS_ERROR_IN_METHOD, description = "Method error", content = @Content),
            @ApiResponse(responseCode = ConstanteWs._CODE_WS_ERROR, description = "Service Error", content = @Content)})
    @PostMapping(value = "/data", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getDataAdmProfilWs(HttpServletRequest request, @RequestBody SearchObject obj) {
        try {
            if (obj.getDataSearch() != null && !obj.getDataSearch().isEmpty()) {
                for (CriteriaSearch oneSearchElement : obj.getDataSearch()) {
                    if (oneSearchElement.getValue().toString().contains("'")) {
                        oneSearchElement.setValue(oneSearchElement.getValue().toString().replace("'", "''"));
                    }
                }
            }

            return sendWsService.sendResult(request, commonService.getListPaginator(obj, new AdmProfile(), null));
        } catch (Exception argEx) {
            logger.error("Error AdmProfileController in method getDataAdmProfilWs :: {}", String.valueOf(argEx));
            return sendWsService.sendResultException(request);
        }
    }

    @Operation(summary = "Insert un nouveau objet")
    @ApiResponses(value = {@ApiResponse(responseCode = ConstanteWs._CODE_WS_SUCCESS, description = "Success"),
            @ApiResponse(responseCode = ConstanteWs._CODE_WS_ERROR_ALIAS_PARAM, description = "Un ou plus param�tre(s) est null", content = @Content),
            @ApiResponse(responseCode = ConstanteWs._CODE_WS_ERROR_IN_METHOD, description = "Erreur du methode", content = @Content),
            @ApiResponse(responseCode = ConstanteWs._CODE_WS_ERROR, description = "Erreur du service", content = @Content)})
    @PostMapping(value = "/add", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> pushAdmProfile(HttpServletRequest request, @RequestBody AdmProfile entity) {

        try {
            if (entity != null) {
                return sendWsService.sendResult(request, admProfileService.saveAdmProfileWs(entity));
            } else {

                return ResponseEntity.badRequest().body("Entity is null");
            }
        } catch (Exception argEx) {
            logger.error("Error AdmProfileController in method pushAdmProfile :: {}", String.valueOf(argEx));
            return sendWsService.sendResultException(request);
        }
    }

    @Operation(summary = "Mise a jour l'objet")
    @ApiResponses(value = {@ApiResponse(responseCode = ConstanteWs._CODE_WS_SUCCESS, description = "Success"),
            @ApiResponse(responseCode = ConstanteWs._CODE_WS_ERROR_ALIAS_PARAM, description = "Un ou plus param�tre(s) est null", content = @Content),
            @ApiResponse(responseCode = ConstanteWs._CODE_WS_ERROR_IN_METHOD, description = "Erreur du methode", content = @Content),
            @ApiResponse(responseCode = ConstanteWs._CODE_WS_ERROR, description = "Erreur du service", content = @Content)})
    @PutMapping(value = "/update", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> putAdmProfilWs(HttpServletRequest request, @RequestBody AdmProfile entity) {
        try {
            return sendWsService.sendResult(request, admProfileService.updateAdmProfileWs(entity));
        } catch (Exception argEx) {
            logger.error("Error AdmProfileController in method putAdmProfilWs :: {}", String.valueOf(argEx));
            return sendWsService.sendResultException(request);
        }
    }

    @Operation(summary = "Supprimer l'objet ")
    @ApiResponses(value = {@ApiResponse(responseCode = ConstanteWs._CODE_WS_SUCCESS, description = "Success"),
            @ApiResponse(responseCode = ConstanteWs._CODE_WS_ERROR_ALIAS_PARAM, description = "Un ou plus param�tre(s) est null", content = @Content),
            @ApiResponse(responseCode = ConstanteWs._CODE_WS_ERROR_DELETE_ROW, description = "Impossible de supprimer l'objet car il est reference ", content = @Content),
            @ApiResponse(responseCode = ConstanteWs._CODE_WS_ERROR_IN_METHOD, description = "Erreur du methode", content = @Content),
            @ApiResponse(responseCode = ConstanteWs._CODE_WS_ERROR, description = "Erreur du service", content = @Content)})
    @DeleteMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> deleteAdmProfileByIdWs(HttpServletRequest request, @PathVariable("id") Long id) {
        try {
            return sendWsService.sendResult(request, admProfileService.deleteAdmProfileByIdWs(id));
        } catch (Exception argEx) {
            logger.error("Error AdmProfileController in method deleteAdmProfileByIdWs :: {}", String.valueOf(argEx));
            return sendWsService.sendResultException(request);
        }
    }

    @GetMapping(value = "/menu", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getMenusWs(HttpServletRequest request, @RequestParam("idProfil") List<Long> idProfil) {
        try {
            if (idProfil == null || idProfil.isEmpty()) {


                return sendWsService.sendResult(request, admFoncService.getAllMenus());
            } else {

                return sendWsService.sendResult(request, admFoncService.getMenusForIdProfil(idProfil));
            }
        } catch (Exception argEx) {
            logger.error("Error AdmProfileController in method getMenusWs :: {}", String.valueOf(argEx));
            return sendWsService.sendResultException(request);
        }
    }

    @GetMapping(value = "/menuprofil", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getMenuProfile(HttpServletRequest request, @RequestParam("idProfil") Long idProfil) {
        try {
            return sendWsService.sendResult(request, admFoncService.getAllMenusChecked(idProfil));
        } catch (Exception argEx) {
            logger.error("Error AdmProfileController in method getMenuProfile :: {}", String.valueOf(argEx));
            return sendWsService.sendResultException(request);
        }
    }

    @Operation(summary = "Export data")
    @ApiResponses(value = {@ApiResponse(responseCode = ConstanteWs._CODE_WS_SUCCESS, description = "Success"),
            @ApiResponse(responseCode = ConstanteWs._CODE_WS_ERROR_ALIAS_PARAM, description = "Un ou plus param�tre(s) est null", content = @Content),
            @ApiResponse(responseCode = ConstanteWs._CODE_WS_ERROR_IN_METHOD, description = "Erreur du methode", content = @Content),
            @ApiResponse(responseCode = ConstanteWs._CODE_WS_ERROR, description = "Erreur du service", content = @Content)})
    @PostMapping(value = "/export", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> exportDataWs(HttpServletRequest request, @RequestBody SearchObject obj) {
        try {
            return sendWsService.downloadFile(request, commonService.exportDataWs(obj, new AdmProfile(), null));
        } catch (Exception argEx) {
            logger.error("Error AdmProfileController in method exportDataWs :: {}", String.valueOf(argEx));
            return sendWsService.sendResultException(request);
        }
    }

}
