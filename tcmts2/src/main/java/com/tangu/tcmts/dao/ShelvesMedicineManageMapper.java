package com.tangu.tcmts.dao;

import com.tangu.tcmts.po.ShelvesMedicineDTO;
import com.tangu.tcmts.po.ShelvesMedicineManage;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * Created by Administrator on 2018/1/11.
 */

@Mapper
public interface ShelvesMedicineManageMapper {

    List<ShelvesMedicineDTO> listShelvesMedicineDTOByCondition(ShelvesMedicineManage shelvesMedicineManage);
}
