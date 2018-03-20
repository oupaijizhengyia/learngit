/**
 * FileName: WaringMapper
 * Author: yeyang
 * Date: 2018/1/31 10:03
 */
package com.tangu.tcmts.dao;

import com.tangu.tcmts.po.WarningDO;
import com.tangu.tcmts.po.WarningDTO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface WaringMapper {
    List<WarningDO> listWarning(WarningDTO warningDTO);
    int addWarning(WarningDO warningDO);
}
