package com.tangu.tcmts.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.tangu.tcmts.po.MedicineRelationDO;
import com.tangu.tcmts.po.MedicineRelationDTO;

/**
 * @author:huangyifan
 * @Description:中药规格管理Mapper
 * @Date: create in 11：07 2017/11/7
 */
@Mapper
public interface MedicineRelationMapper {
    Integer deleteMedicineRelationById(MedicineRelationDTO medicineRelationDTO);

    Integer updateMedicineRelationById(MedicineRelationDO medicineRelationDO);

    Integer insertMedicineRelation(List<MedicineRelationDO> list);

    List<MedicineRelationDO> listMedicineRelation(MedicineRelationDTO medicineRelationDTO);
    
    List<MedicineRelationDO> getMedicineRelationByName(MedicineRelationDO medicineRelationDO);
    
    List<MedicineRelationDO> listMedicineRelationAll();
    //MedicineRelationDO getMedicineByNativeId(MedicineRelationDO medicineRelationDO);
    List<MedicineRelationDO>  getMnemonicCode();
 }