package com.mc.back.sigrecette.controller.priv;
import com.mc.back.sigrecette.model.tool.NomenclatureDTO;
import com.mc.back.sigrecette.service.impl.NomenclatureService;
import com.mc.back.sigrecette.tools.model.SendObject;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin("*")
@RequestMapping("/dynamic-nomenclature")
public class DynamicNomenclatureController {

    @Autowired
    private  NomenclatureService nomenclatureservice;
    @PostMapping("/insert")
    public ResponseEntity<?> insert(HttpServletRequest request,
                                    @RequestBody NomenclatureDTO dto) {

        SendObject result = nomenclatureservice.insertDynamic(dto);
        return ResponseEntity.ok(result);
    }
}
