package com.tangu.tcmts.service;

import com.tangu.tcmts.po.WarningDO;
import com.tangu.tcmts.po.WarningDTO;

import java.util.List;

public interface WarningService {
    List<WarningDO> listWarning(WarningDTO warningDTO);
    int addWarning(WarningDO warningDO);
}
