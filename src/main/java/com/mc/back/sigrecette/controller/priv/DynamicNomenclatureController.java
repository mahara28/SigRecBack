package com.mc.back.sigrecette.controller.priv;
import com.mc.back.sigrecette.model.tool.NomenclatureDTO;
import com.mc.back.sigrecette.model.view.VParamNomenColumns;
import com.mc.back.sigrecette.service.ICommonService;
import com.mc.back.sigrecette.service.ISendWsService;
import com.mc.back.sigrecette.service.impl.NomenclatureService;
import com.mc.back.sigrecette.tools.ConstanteWs;
import com.mc.back.sigrecette.tools.model.SearchObject;
import com.mc.back.sigrecette.tools.model.SendObject;

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
@CrossOrigin("*")
@RequestMapping("/dynamic-nomenclature")
public class DynamicNomenclatureController {
	private static final Logger logger = LogManager.getLogger(DynamicNomenclatureController.class);
	@Autowired
    private ISendWsService sendWsService;

    @Autowired
    private ICommonService commonService;
    
    @Autowired
    private  NomenclatureService nomenclatureservice;
    @PostMapping("/insert")
    public ResponseEntity<?> insert(HttpServletRequest request,
                                    @RequestBody NomenclatureDTO dto) {

        SendObject result = nomenclatureservice.insertDynamic(dto);
        return ResponseEntity.ok(result);
    }
    
}
