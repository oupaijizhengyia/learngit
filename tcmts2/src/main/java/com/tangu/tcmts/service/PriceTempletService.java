package com.tangu.tcmts.service;

import java.util.List;

import com.tangu.tcmts.po.MedicinePriceHistoryDTO;
import com.tangu.tcmts.po.PriceTempletDTO;
import com.tangu.tcmts.po.PriceTempletDetailDTO;

public interface PriceTempletService {
  
  // 获取价格模板
  Object getPriceTempletList();
  
  // 获取模板细节
  Object getPriceTempletDetail(PriceTempletDetailDTO record);

  // 默认模板价格列表
  Object getPriceTempletListDropDown();
  
  // 新增修改模板名
  Object savePriceTempletName(PriceTempletDTO record);
  
  // 删除模板
  Object delPriceTemplet(PriceTempletDTO record);
  
  // 药品调价单个
  Object changeSingeMedPrice(PriceTempletDetailDTO record);
  
  // 药品调价批量
  Object changeListMedPrice(List<PriceTempletDetailDTO> record);
  
  // 载入药品
  Object loadMedicines(PriceTempletDetailDTO record);
  
  List<PriceTempletDetailDTO> listPriceByTemIdsOrMedIds(PriceTempletDetailDTO record);
  PriceTempletDetailDTO getPriceByTemIdAndMedId(PriceTempletDetailDTO record);
  //调价记录
  Object listHistory(MedicinePriceHistoryDTO medicinePriceHistoryDTO);

  //调价记录详情
  Object listPriceTempleByTemidAndMedid(
      PriceTempletDetailDTO priceTempletDetailDTO);

  Object deleteByTempletDetailId(PriceTempletDetailDTO priceTempletDetailDTO);
}
