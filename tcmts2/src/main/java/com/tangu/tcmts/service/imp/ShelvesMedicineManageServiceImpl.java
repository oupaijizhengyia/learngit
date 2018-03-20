package com.tangu.tcmts.service.imp;

import com.alibaba.fastjson.JSONObject;
import com.tangu.tcmts.bean.SysPropertyUtil;
import com.tangu.tcmts.dao.MedicineShelvesMapper;
import com.tangu.tcmts.dao.ShelvesMedicineManageMapper;
import com.tangu.tcmts.po.MedicineShelves;
import com.tangu.tcmts.po.RecipeDO;
import com.tangu.tcmts.po.RecipeDTO;
import com.tangu.tcmts.po.RecipeSerialVO;
import com.tangu.tcmts.po.RecipeVO;
import com.tangu.tcmts.po.ShelvesMedicineDTO;
import com.tangu.tcmts.po.ShelvesMedicineManage;
import com.tangu.tcmts.service.RecipeService;
import com.tangu.tcmts.service.ShelvesMedicineManageService;
import com.tangu.tcmts.util.Constants;
import com.tangu.tcmts.util.excel.ExcelExportVO;
import com.tangu.tcmts.util.excel.ReportUtil;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.List;

/**
 * Created by Administrator on 2018/1/10.
 */

@Service
@Slf4j
public class ShelvesMedicineManageServiceImpl implements ShelvesMedicineManageService {

    @Autowired
    private ShelvesMedicineManageMapper shelvesMedicineManageMapper;

    @Autowired
    private SysPropertyUtil sysPropertyUtil;

    @Autowired
    private RecipeService recipeService;

    @Autowired
    private MedicineShelvesMapper medicineShelvesMapper;

    private static final int DECIMAL_POINT = 3;

    private final String DEFAULT_DECIMAL_POINT = "DEFAULT_DECIMAL_POINT";

    @Override
    public List<ShelvesMedicineDTO> listShelvesMedicineDTOByCondition(ShelvesMedicineManage shelveMedicineManage) {
        List<ShelvesMedicineDTO> shelvesMedicineDTOList = shelvesMedicineManageMapper.listShelvesMedicineDTOByCondition(shelveMedicineManage);
        for(ShelvesMedicineDTO s : shelvesMedicineDTOList){
            if(s.getShelvesType() == null){continue;}
            if(s.getShelvesType() == 1){s.setShelvesTypeName("门诊");}
            else if(s.getShelvesType() == 2){s.setShelvesTypeName("住院");}
            else if(s.getShelvesType() == 3){s.setShelvesTypeName("快递");}
        }
        return shelvesMedicineDTOList;
    }


    @Override
    public void export(Object headList, List<?> list, HttpServletRequest req, HttpServletResponse resp, String xml) throws UnsupportedEncodingException, ServletException, IOException {
        ExcelExportVO para = new ExcelExportVO();
        para.setHeaderList(headList);
        para.xmlString = xml;
        para.setData(list);
        Integer decimalPoint = Integer.parseInt(sysPropertyUtil.getProperty(DEFAULT_DECIMAL_POINT, "3"));
        if (decimalPoint.equals(DECIMAL_POINT)) {
            para.setDecimalPointIndex(decimalPoint);
        }
        req.setCharacterEncoding("UTF-8");
        resp.setContentType("APPLICATION/OCTET-STREAM;CHARSET=UTF-8");
        ReportUtil.export(req, resp, para);
    }

    // ************************************************RecipeStateHandle
    @Data
    class RecipeStateHandle{
      private State state;
      public RecipeStateHandle(Integer recipeState){
        switch (recipeState) {
          case Constants.STATE_PACK_SCAN:
            this.state = new MedicineMatchSfelvesState(recipeState);
            break;
          case Constants.STATE_SHELVES_ALREADY:
            this.state = new MedicineTake(recipeState);
            break;
          default:;
        }
      }

      //
      public JSONObject updateState(JSONObject object) throws Exception{
        return state.updateState(object);
      }


      //验证处方
      public JSONObject confirmRecipe(JSONObject object,JSONObject obj) throws Exception{
        return state.confirmRecipe(object,obj);
      }
    }
    // *************************************************************RecipeStateHandle


    // **************************************************************State
    abstract class State{
      private Integer preState;

      public void setPreState(Integer preState){
        this.preState = preState;
      }

      public Integer getPreState(){
        return preState;
      }

      //更新状态
      public JSONObject updateState(JSONObject object) throws Exception{
        // 获取状态码, 匹配状态
        JSONObject obj = new JSONObject();
        obj.put("SF", "0");
        obj = handle(object, obj);
        return obj;
      }

      //获取处方信息,确认处方状态
      public abstract JSONObject confirmRecipe(JSONObject object,JSONObject obj) throws Exception;

      //获取处方信息
      public abstract Object getRecipe(JSONObject object);

      //更新状态
      public abstract JSONObject handle(
          JSONObject object, JSONObject obj) throws Exception;

      public boolean matchState(Integer state){
        return preState.equals(state);
      }

      public Integer handleSuccess(JSONObject object) throws Exception{
        Integer modUser = object.getInteger("employeeId");
        RecipeDO re = new RecipeDO();
        re.setId(object.getInteger("recipeId"));
        re.setShippingState(Constants.STATE_SHELVES_ALREADY);
        re.setModTime(new Date());
        re.setModUser(modUser);
        re.setScanUser(re.getModUser());
        setProcess(re, modUser);
        re.setShelvesCode(object.getString("shelvesCode"));
        return recipeService.updateRecipeStatus(re);
      }

      public abstract void setProcess(RecipeDO re, Integer modUser);
    }

    // ***********************************************************************State

    //上架 ******************************************************************* 上架
    class MedicineMatchSfelvesState extends State{

      public MedicineMatchSfelvesState(Integer state){
        this.setPreState(state);
      }

        @Override
        public JSONObject confirmRecipe(JSONObject object, JSONObject obj) throws Exception {
            RecipeVO recipeDO = (RecipeVO) getRecipe(object);
//            obj.put("处方信息",recipeDO);
            if(recipeDO != null){
                if(recipeDO.getShippingState() == Constants.STATE_PACK_SCAN){
                    obj = JSONObject.parseObject(JSONObject.toJSONString(recipeDO));
                    obj.put("SF", 1);
                }else if(recipeDO.getShippingState() == Constants.STATE_SHELVES_ALREADY){
                    obj.put("SF","0");
                    obj.put("errMsg","该药品已上架");
                }else if(recipeDO.getShippingState() < Constants.STATE_PACK_SCAN){
                    obj.put("SF","0");
                    obj.put("errMsg","该药品还未打包");
                }else if(recipeDO.getShippingState() > Constants.STATE_SHELVES_ALREADY){
                    obj.put("SF","0");
                    obj.put("errMsg","该药品已发药");
                }
            }else{
              obj.put("SF", 0);
              obj.put("errMsg", "查无此单");
            }
            log.info("上架 confirmRecipe", obj);
            return obj;
        }

        @Override
        public Object getRecipe(JSONObject object) {
            String recipeCode = object.getString("recipeCode");
            RecipeDTO recipeDTO = new RecipeDTO();
            recipeDTO.setSysRecipeCode(recipeCode);
            return recipeService.getRecipeVO(recipeDTO);
        }

      @Override
      public JSONObject handle(JSONObject object, JSONObject obj)
          throws Exception {
          //1.货架匹配
          //  a.门诊部
          //  b.住院部
          //货架类型判断
        Integer belong = object.getInteger("belong");
        String shelvesCode = object.getString("shelvesCode");
        Integer carryId = object.getInteger("carryId");
        //TODO 根据货架号 查出货架信息
        MedicineShelves m = new MedicineShelves();
        m.setShelvesCode(shelvesCode);
        List<MedicineShelves> lms = medicineShelvesMapper.listMedicineShelves(m);
        if(CollectionUtils.isEmpty(lms)){
        	obj.put("SF", "e");
        	obj.put("errMsg", "货架不存在");
        	return obj;
        }
        Integer msBelong = lms.get(0).getShelvesType();
        belong = carryId == Constants.EXPRESS_SELF ? belong : Constants.SHELVES_TYPE_EXPRESS;
        log.info("msBelong{} belong{}", msBelong, belong);
        boolean isMedSelfvesType = belong.equals(msBelong);
        //是否门诊
        boolean isOut = belong == Constants.SHELVES_TYPE_OUT;
        if(isMedSelfvesType){
          //根据发票号，病区 判断货架
          boolean isMedSelfves = belong.equals(Constants.SHELVES_TYPE_EXPRESS)?
               true : matchMedSelfves(isOut, object, shelvesCode);
          if(isMedSelfves){
            //更新上架时间, 修改处方状态, 插入recipe_scan
            try{
              handleSuccess(object);
              obj.put("SF", "1");
              obj.put("feedbackMsg", "上架成功");
            }catch(Exception e){
              obj.put("SF", "0");
              obj.put("errMsg", "上架失败");
            }
          }else{
            obj.put("errMsg", "货架不匹配");
          }
        }else if(belong == Constants.SHELVES_TYPE_EXPRESS){
          obj.put("errMsg", "该药品类型为快递！");
        }else{
          obj.put("errMsg", "该药品为"+(isOut? "门诊":"住院")+"自取！");
        }
        return obj;
      }


      private boolean matchMedSelfves(boolean isOut, JSONObject object, String string) {
          boolean isMatchMedSelfves = false;
          String matchStr = isOut ? object.getString("invoiceCode")
              : object.getString("inpatientArea");
          log.info("matchStr{}  {}", matchStr, string);
          if(isOut){
            if(StringUtils.isBlank(matchStr)){
              isMatchMedSelfves = true;
            }else if(StringUtils.isNotBlank(string)){
              isMatchMedSelfves = matchStr.endsWith(string.substring(string.length()-1, string.length()));
            }
          }else if(StringUtils.isNotBlank(matchStr) && StringUtils.isNotBlank(string) &&
              matchStr.length()>2 && string.length() >2){
            isMatchMedSelfves = matchStr.startsWith(string.substring(0, 3));
          }
          return isMatchMedSelfves;
      }

      @Override
      public void setProcess(RecipeDO re, Integer modUser) {
        re.setShelveUser(modUser);
        re.setShelveTime(new Date());
      }

    }
    //发药确认 *********************************************************** 发药确认
    class MedicineTake extends State{

      public MedicineTake(Integer state){
        this.setPreState(state);
      }

      @Override
      public JSONObject handle(JSONObject object, JSONObject obj)
          throws Exception {
          try{
            handleSuccess(object);
            obj.put("SF","1");
            obj.put("feedbackMsg","发药成功");
          }catch(Exception e){
            obj.put("SF","0");
            obj.put("errMsg","发药失败");
          }
          return obj;
      }

      @Override
      public JSONObject confirmRecipe(JSONObject object,JSONObject obj)throws Exception{
        RecipeSerialVO recipeDO = (RecipeSerialVO) getRecipe(object);
        if(recipeDO ==null){
          obj.put("SF","0");
          obj.put("errMsg","查无此单");
        }else if(recipeDO.getShelvesCode() == null || recipeDO.getShelvesCode() == ""){
              obj.put("SF","0");
              obj.put("errMsg","该药品还未上架");
          }else{
              if(recipeDO.getShippingState() >= Constants.STATE_DELIVERY_BILL){
                  obj.put("SF","0");
                  obj.put("errMsg","该药品已被取走");
              }else{
                  if(recipeDO != null){
                      obj = JSONObject.parseObject(JSONObject.toJSONString(recipeDO));
                      obj.put("SF","1");
                  }
              }
          }
          return obj;
      }

      @Override
      public Object getRecipe(JSONObject object) {
          String recipeSerial = object.getString("recipeSerial");
          RecipeDTO recipeDTO = new RecipeDTO();
          recipeDTO.setRecipeSerial(recipeSerial);
          return recipeService.getRecipeSerialVO(recipeDTO);
      }

        @Transactional
        @Override
        public Integer handleSuccess(JSONObject object) throws Exception{
            Integer modUser = object.getInteger("employeeId");
            RecipeDO re = new RecipeDO();
            re.setId(object.getInteger("recipeId"));
            re.setShippingState(Constants.STATE_DELIVERY_BILL);
            re.setModTime(new Date());
            re.setModUser(modUser);
            re.setScanUser(re.getModUser());
            setProcess(re, modUser);
            return recipeService.updateRecipeStatus(re);
        }


        @Override
        public void setProcess(RecipeDO re, Integer modUser) {
          // TODO Auto-generated method stub
          re.setPackageId(modUser);
          re.setPackageTime(new Date());
        }

    }
    // ****************************************************** 发药确认



    @Override
    public JSONObject medicineChangeState(JSONObject object, JSONObject obj,
        int statePackScan) throws Exception {
      RecipeStateHandle rsh = new RecipeStateHandle(statePackScan);
          obj = rsh.updateState(object);
      return obj;
    }

    @Override
    public JSONObject confirmRecipe(JSONObject object, JSONObject obj, int statePackScan) throws Exception {
        RecipeStateHandle rsh = new RecipeStateHandle(statePackScan);
        obj = rsh.confirmRecipe(object,obj);
        return obj;
    }

}
