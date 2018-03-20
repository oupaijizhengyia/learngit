/**
 * FileName: WarningServiceImpl
 * Author: yeyang
 * Date: 2018/1/31 10:01
 */
package com.tangu.tcmts.service.imp;

import com.tangu.tcmts.dao.WaringMapper;
import com.tangu.tcmts.po.WarningDO;
import com.tangu.tcmts.po.WarningDTO;
import com.tangu.tcmts.service.WarningService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class WarningServiceImpl implements WarningService{
    @Autowired
    private WaringMapper waringMapper;
    @Override
    public List<WarningDO> listWarning(WarningDTO warningDTO) {
        return waringMapper.listWarning(warningDTO);
    }

    @Override
    public int addWarning(WarningDO warningDO) {
        return waringMapper.addWarning(warningDO);
    }
}
