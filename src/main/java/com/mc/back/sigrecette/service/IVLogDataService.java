package com.mc.back.sigrecette.service;



import com.mc.back.sigrecette.model.view.VAdmLogData;
import com.mc.back.sigrecette.tools.model.SendObject;

import java.util.List;

public interface IVLogDataService {
    List<VAdmLogData> getList();

    VAdmLogData findById(Long id);

    SendObject findVLogDataByIdWs(Long id);
}
