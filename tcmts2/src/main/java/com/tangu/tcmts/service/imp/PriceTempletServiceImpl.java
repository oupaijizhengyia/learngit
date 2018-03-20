package com.tangu.tcmts.service.imp;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tangu.common.util.DateUtil;
import com.tangu.common.util.entity.ResponseModel;
import com.tangu.security.JwtUserTool;
import com.tangu.tcmts.dao.MedicinePriceHistoryMapper;
import com.tangu.tcmts.dao.PriceTempletDetailMapper;
import com.tangu.tcmts.dao.PriceTempletMapper;
import com.tangu.tcmts.dao.SettleCompanyMapper;
import com.tangu.tcmts.po.MedicinePriceHistoryDO;
import com.tangu.tcmts.po.MedicinePriceHistoryDTO;
import com.tangu.tcmts.po.PriceTempletDTO;
import com.tangu.tcmts.po.PriceTempletDetailDTO;
import com.tangu.tcmts.service.PriceTempletService;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class PriceTempletServiceImpl implements PriceTempletService{
  
  @Autowired
  PriceTempletMapper priceTempletMapper; 
  
  @Autowired
  PriceTempletDetailMapper priceTempletDetailMapper;
  
  @Autowired
  MedicinePriceHistoryMapper medicinePriceHistoryMapper;
  
  @Autowired
  SettleCompanyMapper settleCompanyMapper;

  @Override
  public Object getPriceTempletList() {
    return new ResponseModel(priceTempletMapper.getPriceTempletList());
  }

  @Override
  public Object getPriceTempletListDropDown() {
    return new ResponseModel(priceTempletMapper.getPriceTempletListDropDown());
  }
  
  @Override
  public Object getPriceTempletDetail(PriceTempletDetailDTO record){
    log.debug("getPriceTempletDetail templetId:{}",
        record.getTempletId());
    List<PriceTempletDetailDTO> lp = priceTempletDetailMapper.selectByTemIdAndMedId(record);
    return record.getMedicineId() == null ?
      new ResponseModel(priceTempletDetailMapper.selectByTemIdAndMedId(record))
    : getSinglePriceTempletDetail(lp);
  }
  
  public Object getSinglePriceTempletDetail(List<PriceTempletDetailDTO> lp){
    return lp.size()==0 ?
        new ResponseModel(new PriceTempletDetailDTO())
       :new ResponseModel(lp.get(0));
  }
  
  @Override
  @Transactional
  public Object savePriceTempletName(PriceTempletDTO record) {
    System.out.println(record.toString());
    List<PriceTempletDTO> lp = priceTempletMapper.getPriceTempletList();
//    System.out.println(lp.size());
    boolean isNotReplace = lp.stream().noneMatch(p -> p.getTempletName().equals(record.getTempletName()));
    log.info("TempletName isNotReplace: {}", isNotReplace);
    int i = 0;
    if(isNotReplace){
      i = record.getTempletId() == null ? createPriceTemplet(record)
          : priceTempletMapper.updateByPrimaryKeySelective(record);
    }
    return i > 0 ? new ResponseModel(record, "操作成功")
        : new ResponseModel().attr(ResponseModel.KEY_ERROR, "操作失败,该价格模板名称已存在");
  }
  
  public int createPriceTemplet(PriceTempletDTO record){
    record.setCreatorId(JwtUserTool.getId());
    return priceTempletMapper.insertSelective(record);
  }

  @Override
  @Transactional
  public Object delPriceTemplet(PriceTempletDTO record) {
    Set<Integer> set = settleCompanyMapper.getPriceTypeId();
    if(set.contains(record.getTempletId())){
      return new ResponseModel().attr(ResponseModel.KEY_ERROR, "结算方使用中,不能删除");
    }
    //删除价格模板
    int i = priceTempletMapper.deleteByPrimaryKey(record.getTempletId());
    //删除模板对应药品
    priceTempletDetailMapper.deleteByTempletId(record);
    return i > 0 ? new ResponseModel("删除成功")
        : new ResponseModel(ResponseModel.KEY_ERROR, "删除失败");
  }

  /**
   *    a.零售批发价不同  修改最新一条
   *    b.生效时间不同      修改上一条  插入新一条
   */
  @Override
  @Transactional
  public Object changeSingeMedPrice(PriceTempletDetailDTO record) {
    record.setModUser(JwtUserTool.getId());
    PriceTempletDetailDTO ptDTO = priceTempletDetailMapper.selectById(record);
//    int i = !record.getNowRetailPrice().equals(ptDTO.getRetailPrice()) 
//        || !record.getNowTradePrice().equals(ptDTO.getTradePrice()) ?
//        priceTempletDetailMapper.updateByIdSelective(record)
//        : changeSingePriceTemplet(record, ptDTO);
    //a.生效时间不同      修改上一条  插入新一条
    //b.零售批发价不同  修改最新一条
    int j = !equalsDateByByPattern(record.getPriceStart(), ptDTO.getPriceStart())?
        changeSingePriceTemplet(record, ptDTO):
          updateSingePriceTemplet(record, ptDTO);
        
    return j > 0 ? new ResponseModel(priceTempletDetailMapper.selectByTemIdAndMedId(ptDTO).get(0),"调价成功")
        : new ResponseModel(ResponseModel.KEY_ERROR, "调价失败");
  }

  private int updateSingePriceTemplet(
      PriceTempletDetailDTO record, PriceTempletDetailDTO ptDTO){
    int i = 0;
    if(!record.getNowRetailPrice().equals(ptDTO.getRetailPrice()) 
    || !record.getNowTradePrice().equals(ptDTO.getTradePrice()) ){
      //a.零售批发价不同  修改最新一条
      i = priceTempletDetailMapper.updateByIdSelective(record);
    }
    return i;
  }
  
  private int changeSingePriceTemplet(
      PriceTempletDetailDTO record, PriceTempletDetailDTO ptDTO){
    log.info("PriceStart:{} , oldPriceStart:{}, 有效时间是否相等:{}", record.getPriceStart(),
      ptDTO.getPriceStart(), equalsDateByByPattern(record.getPriceStart(), ptDTO.getPriceStart()));
    log.info("--------------生效时间不同---------------");
    PriceTempletDetailDTO pt = getMedicineLastPriceDetailDTO(record);
    record.setTempletId(ptDTO.getTempletId());
    record.setMedicineId(ptDTO.getMedicineId());
    record.setModUser(JwtUserTool.getId());
    record.setModifyTime(new Date());
    int i = priceTempletDetailMapper.updateByIdSelective(pt);
    priceTempletDetailMapper.insertSelective(record);
    //调价时插入调价记录历史表
    return i;
  }

  @Override
  @Transactional
  public Object changeListMedPrice(List<PriceTempletDetailDTO> record) {
    /**
     *    a.零售批发价不同  修改最新一条
     *    b.生效时间不同      修改上一条  插入新一条
     */
    //a.零售批发价不同  修改最新一条
    List<PriceTempletDetailDTO> newOneRecord = new ArrayList<PriceTempletDetailDTO>();
    //b.生效时间不同      修改上一条  插入新一条
    List<PriceTempletDetailDTO> oldUpdateRecord = new ArrayList<PriceTempletDetailDTO>();
    List<PriceTempletDetailDTO> oldInsertRecord = new ArrayList<PriceTempletDetailDTO>();
    record.stream().forEach(p -> {
      p.setModUser(JwtUserTool.getId());
      if(!p.getNowRetailPrice().equals(p.getRetailPrice()) 
          || !p.getNowTradePrice().equals(p.getTradePrice())){
        // a.零售批发价不同  修改最新一条
        newOneRecord.add(p);
      }else if(!equalsDateByByPattern(p.getOldPriceStart(),p.getPriceStart())){
        // b.生效时间不同      修改上一条  插入新一条
        PriceTempletDetailDTO pt = getMedicineLastPriceDetailDTO(p);
        pt.setTempletId(p.getTempletId());
        oldUpdateRecord.add(pt);
        oldInsertRecord.add(p);
      }
    });
    // 批量更新最后一条
    int newOneUpdateCount = newOneRecord.size() == 0 ? 0 : priceTempletDetailMapper.updateListById(newOneRecord);
    log.info("--------------newOneUpdateCount {}", newOneUpdateCount);
    int oldUpdateCount = oldUpdateRecord.size() == 0 ? 0 : priceTempletDetailMapper.updateListById(oldUpdateRecord);
    log.info("--------------oldUpdateCount {}", oldUpdateCount);
    int oldInsertCount = oldInsertRecord.size() == 0 ? 0 : priceTempletDetailMapper.insertList(oldInsertRecord);
    log.info("--------------oldInsertCount {}", oldInsertCount);
    return new ResponseModel("更新成功");
  }

  @Override
  public Object loadMedicines(PriceTempletDetailDTO record) {
    List<PriceTempletDetailDTO> pl= priceTempletDetailMapper.selectByTemIdAndMedId(record);
    pl.stream().forEach(p -> {
      p.setName_medicineName(p.getMedicineName());
      p.setName_templetId(p.getTempletName());
    });
    return new ResponseModel(pl, "载入成功");
  }
  
  public boolean equalsDateByByPattern(Date aDate, Date bDate){
    String datePattern = DateUtil.getDatePattern();
    return DateUtil.getDateTime(datePattern, aDate).equals(DateUtil.getDateTime(datePattern, bDate));
  }
  
  @Override
  public List<PriceTempletDetailDTO> listPriceByTemIdsOrMedIds(PriceTempletDetailDTO record) {
	  return priceTempletDetailMapper.listPriceByTemIdsOrMedIds(record);
  }
  
  @Override
  public PriceTempletDetailDTO getPriceByTemIdAndMedId(PriceTempletDetailDTO record) {
	  return priceTempletDetailMapper.getPriceByTemIdAndMedId(record);
  }
  
  public PriceTempletDetailDTO getMedicineLastPriceDetailDTO(PriceTempletDetailDTO p){
    PriceTempletDetailDTO pt = new PriceTempletDetailDTO();
    pt.setTempletDetailId(p.getTempletDetailId());
    pt.setModUser(JwtUserTool.getId());
    pt.setPriceEnd(p.getPriceStart());
    pt.setMedicineLast(0);
    return pt;
  }

  /**
   * 调价记录查询
   */
  @Override
  public List<MedicinePriceHistoryDO> listHistory(MedicinePriceHistoryDTO medicinePriceHistoryDTO) {
    medicinePriceHistoryDTO.setPriceType(medicinePriceHistoryDTO.getTempletId());
    List<MedicinePriceHistoryDO> list = medicinePriceHistoryMapper.listHistory(medicinePriceHistoryDTO);
    return list;
  }

  @Override
  public Object deleteByTempletDetailId(PriceTempletDetailDTO priceTempletDetailDTO) {
    int medicineLast = priceTempletDetailDTO.getMedicineLast();
    priceTempletDetailMapper.deleteByTempletDetailId(priceTempletDetailDTO);
    if(medicineLast == 1){
      //TODO 更新最新一条
      priceTempletDetailMapper.updatePriceTempletDetailLast(priceTempletDetailDTO);
    }
    int i = priceTempletDetailMapper.selectByTempletDetailId(priceTempletDetailDTO);
    return i > 0 ? new ResponseModel().attr(ResponseModel.KEY_ERROR, "删除失败"):
      new ResponseModel("删除成功");
  }

  @Override
  public Object listPriceTempleByTemidAndMedid(
      PriceTempletDetailDTO priceTempletDetailDTO) {
    return new ResponseModel(priceTempletDetailMapper.listPriceTempleByTemidAndMedid(priceTempletDetailDTO));
  }
  
}
