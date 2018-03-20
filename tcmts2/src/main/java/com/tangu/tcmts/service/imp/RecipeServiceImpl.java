package com.tangu.tcmts.service.imp;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.tangu.tcmts.dao.*;
import com.tangu.tcmts.po.*;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tangu.common.exception.TanguException;
import com.tangu.common.util.Constant;
import com.tangu.common.util.StringUtil;
import com.tangu.common.util.entity.ResponseModel;
import com.tangu.tcmts.bean.SysPropertyUtil;
import com.tangu.tcmts.service.RecipeService;
import com.tangu.tcmts.util.ChineseCharacterUtil;
import com.tangu.tcmts.util.Constants;
import com.tangu.tcmts.util.SystemConstant;
import com.tangu.tcmts.util.excel.ExcelExportVO;
import com.tangu.tcmts.util.excel.ReportUtil;
import com.tangu.tcmts.util.express.BaseExpress;
import com.tangu.tcmts.util.express.BaseExpressInterface;

@Service
public class RecipeServiceImpl implements RecipeService {
    @Autowired
    private RecipeMapper recipeMapper;
    @Autowired
    private RecipeMedicineMapper recipeMedicineMapper;
    @Autowired
    private RecipePatrolMapper recipePatrolMapper;
    @Autowired
    private TransferRecipeMapper transferRecipeMapper;
    @Autowired
    RecipeHistoryMapper recipeHistoryMapper;
    @Autowired
    RecipeScanMapper recipeScanMapper;
    @Autowired
    private RecipientMapper recipientMapper;
    @Autowired
    private SysConfigMapper sysConfigMapper;
    @Autowired
    private MachineMapper machineMapper;
    @Autowired
    private RecipeProccessMapper recipeProccessMapper;
    @Autowired
    private MedicineMapper medicineMapper;
    @Autowired
    private SysPropertyUtil sysPropertyUtil;
    @Autowired
    private EmployeeMapper employeeMapper;
    @Autowired
    private ReportMapper reportMapper;

    private static final int CHINA_POST = 2;

    private static final int DECIMAL_POINT = 3;

    private final String DEFAULT_DECIMAL_POINT= "DEFAULT_DECIMAL_POINT";

    @Override
    public List<Map<String, String>> queryLogistics(RecipeDTO recipeDTO) throws Exception {
        String hospitalCode = null;
        String recipeCode = recipeDTO.getRecipeCode();
        String logisticCode = recipeDTO.getLogisticsCode();
        List<Map<String, String>> list = new ArrayList<Map<String, String>>();

        SysLookup sysLookup = new SysLookup();
        sysLookup.setCode(Constants.TRANSFER_TYPE);
        sysLookup.setValue(recipeDTO.getCarryId().toString());
        SysLookup transferMessage = sysConfigMapper.getExts(sysLookup);

        BaseExpressInterface<BaseExpress> baseExpress = BaseExpress::new;
        BaseExpress express = baseExpress.getExpress(recipeDTO.getCarryId());
        if (express!=null){
            list = express.searchExpress(transferMessage,logisticCode);
        }else {
            list = null ;
        }

        if (recipeDTO.getCarryId() == CHINA_POST) {
            hospitalCode = recipeDTO.getHospitalCode();
            if ("201000".equals(hospitalCode) || "100900".equals(hospitalCode)) {
                logisticCode = null;
            } else {
                recipeCode = null;
            }
           list = express.searchExpress(transferMessage, recipeCode, logisticCode, hospitalCode);
        }
        return list;
    }

    @Override
    public List<RecipeDO> listRecipes(RecipeDTO recipeDTO) {
        List<RecipeDO> list = recipeMapper.listRecipes(recipeDTO);
        for(RecipeDO recipeDO : list){
            recipeDO.setBelongName(recipeDO.getBelong() == 1 ? "门诊" : "住院");
        }
        return list;
    }

    @Override
    public List<RecipeDO> listCheckRecipes(RecipeDTO recipeDTO) {
        return recipeMapper.listCheckRecipes(recipeDTO);
    }

    @Override
    public List<PublicDO> getShippingState() {
        return recipeMapper.getShippingState();
    }

    @Override
    public RecipeDO getRecipeContent(Integer id) {
        return recipeMapper.getRecipeContent(id);
    }

    @Override
    public CountRecipes getRecipeNumbers(RecipeDTO recipeDTO) {
        return recipeMapper.getRecipeNumbers(recipeDTO);
    }

    @Override
    public List<RecipeMedicine> getRecipeMedicineByRecipe(Integer id) {
    	List<RecipeMedicine> list = recipeMedicineMapper.getRecipeMedicineByRecipe(id);
    	for (RecipeMedicine rm : list) {
			if (rm.getUnitType() != null && rm.getUnitType() == 1 && StringUtils.isNotBlank(rm.getStandard())
					&& "公斤".equals(rm.getStandard())) {
				rm.setStandard("g");
			}
		}
        return list;
    }

    @Override
    public List<RecipePatrol> listRecipePatrols(RecipePatrol recipePatrol) {
        return recipePatrolMapper.listRecipePatrols(recipePatrol);
    }

    @Override
    public Integer updatePatrolStatus(Integer id) {
        return recipePatrolMapper.updatePatrolStatus(id);
    }


    @Override
    public Integer updateShippingStateToReject(RecipeDTO recipeDTO) {
        return recipeMapper.updateShippingStateToReject(recipeDTO);
    }

    @Override
    public Integer updateCheckState(List<RecipeDTO> list) {
        Integer result = 0;
        if (recipeMapper.updateCheckState(list) > 0) {
            result = 1;
        }
        return result;
    }

    @Override
    public void export(Object headList, List<?> list, HttpServletRequest req, HttpServletResponse resp, String xml) throws UnsupportedEncodingException, ServletException, IOException {
        ExcelExportVO para = new ExcelExportVO();
        para.xmlString = xml ;
        para.setHeaderList(headList);
        para.setData(list);
        Integer decimalPoint = Integer.parseInt(sysPropertyUtil.getProperty(DEFAULT_DECIMAL_POINT,"3"));
        if (decimalPoint.equals(DECIMAL_POINT)){
            para.setDecimalPointIndex(decimalPoint);
        }
        req.setCharacterEncoding("UTF-8");
        resp.setContentType("APPLICATION/OCTET-STREAM");
        ReportUtil.export(req, resp, para);
    }

    @Override
    public Integer updateRecipePrintState(List<Integer> list) {
        return recipeMapper.updateRecipePrintState(list);
    }

    @Override
    public Integer updateShippingState(List<RecipeDTO> list) {
        Integer result = 0;
        if (recipeMapper.updateShippingState(list) > 0
                && recipeScanMapper.insertCheckState(list) > 0) {
            result = 1;
        }
        return result;
    }

    @Override
    public Integer insertRecipe(RecipeDO recipeDO) {
        Integer transferRecipeId = null;
        if (recipeDO.getRecipeSource() > 0) {
            transferRecipeId = recipeDO.getId();
        }
        maintainRecipient(recipeDO);
        recipeDO.setSysRecipeCode(recipeDO.getBoilType() + getRecipeCode());
        recipeMapper.insertSelective(recipeDO);
        if (recipeDO.getRecipeMedicineList() != null && recipeDO.getRecipeMedicineList().size() > 0) {
            recipeMedicineMapper.insertByBatch(recipeDO);
        }
        //插入流程单
        /**
         * 暂时废弃，归入trigger执行
         * 
        List list = new ArrayList<>();
        RecipeProccess recipeProccess = new RecipeProccess();
        recipeProccess.setRecipeId(recipeDO.getId());
        list.add(recipeProccess);
        recipeMapper.insertProccess(list);
        */
        //更新订单状态
        if (transferRecipeId != null) {
            TransferRecipeDO transferRecipeDO = new TransferRecipeDO();
            transferRecipeDO.setId(transferRecipeId);
            transferRecipeDO.setNativeRecipeId(recipeDO.getId());
            transferRecipeDO.setReceiveTime(new Date());
            transferRecipeDO.setIfReceive(1);
            transferRecipeMapper.updateUseState(transferRecipeDO);
        }
        return recipeDO.getId();
    }

    @Override
    public List<TransferRecipeDO> batchInsertRecipe(List<RecipeDO> list) {
        List<TransferRecipeDO> transferList = new ArrayList<>();
        TransferRecipeDO transferRecipeDO = null;
        List proccessList = new ArrayList<>();
        RecipeProccess recipeProccess = null;
        for (RecipeDO recipeDO : list) {
            transferRecipeDO = new TransferRecipeDO();
            transferRecipeDO.setId(recipeDO.getId());
            maintainRecipient(recipeDO);
            recipeDO.setSysRecipeCode(recipeDO.getBoilType() + getRecipeCode());
            
            recipeMapper.insertSelective(recipeDO);
            transferRecipeDO.setNativeRecipeId(recipeDO.getId());
            transferList.add(transferRecipeDO);
            if (recipeDO.getRecipeMedicineList() != null && recipeDO.getRecipeMedicineList().size() > 0) {
                recipeMedicineMapper.insertByBatch(recipeDO);
            }
            /**
             * 
            recipeProccess = new RecipeProccess();
            recipeProccess.setRecipeId(recipeDO.getId());
            proccessList.add(recipeProccess);
            */
        }
        /**
         * 暂时废弃，归入trigger执行
         * 
        recipeMapper.insertProccess(proccessList);
        */
        transferRecipeMapper.updateUseStateByIds(transferList);
        return transferList;
    }

    public void maintainRecipient(RecipeDO recipe) {
        //仅根据病人姓名搜索收货人
        Recipient recipient = new Recipient();
        recipient.setRecipientName(recipe.getRecipientName());
        List list = recipientMapper.getRecipientList(recipient);
        //补全收货人信息，以便新增或修改收货人
        recipient.setAge(recipe.getAge());
        recipient.setSex(recipe.getSex());
        recipient.setRecipientTel(recipe.getRecipientTel());
        recipient.setInitialCode(ChineseCharacterUtil.convertHanzi2Pinyin(recipient.getRecipientName()));
        recipient.setProvince(recipe.getProvince());
        recipient.setCity(recipe.getCity());
        recipient.setRegion(recipe.getRegion());
        recipient.setRecipientAddress(recipe.getRecipientAddress());
        if (CollectionUtils.isNotEmpty(list)) {
            //修改收货人
            if (recipe.getCarryId().intValue() != Constants.TYPE_THREE) {
                recipe.setRecipientId(((Recipient) list.get(0)).getId());
                return;
            }
            Recipient re = (Recipient) list.get(0);
            recipient.setId(re.getId());
            if (recipe.getRecipientAddress()!=null&&re.getRecipientAddress()!=null&&
            		!recipe.getRecipientAddress().equals(re.getRecipientAddress()) ||
            		(recipe.getConsigneeTel()!=null&&re.getConsigneeTel()!=null&&
            		recipe.getConsigneeTel().equals(re.getConsigneeTel()))) {
                recipientMapper.updateByPrimaryKeySelective(recipient);
            }
        } else {
            //新增收货人
            recipientMapper.insert(recipient);
        }
        recipe.setRecipientId(recipient.getId());
    }

    @Override
    public Integer updateRecipeCheckProccess(RecipeProccess recipeProccess) {
        return recipeMapper.updateRecipeCheckProccess(recipeProccess);
    }

    @Override
    public Object listOperationRecord(RecipeDTO recipeDTO) {
        Map<String, Object> map = new HashMap<String, Object>(16);
        //处方修改记录
        map.put(Constants.RECIPE_HISTORY, recipeHistoryMapper.listRecipeHistory(recipeDTO));
        //处方流程记录
        map.put(Constants.RECIPE_SCAN, recipeScanMapper.listRecipeScan(recipeDTO));
        return map;
    }

    @Transactional
    @Override
    public Integer addRecipe(RecipeDO recipeDO) throws Exception {
        //系统处方号：煎药方式+7+月+日+ 0000(自增)
        recipeDO.setSysRecipeCode(recipeDO.getBoilType() + getRecipeCode());
        recipeDO.setShippingState(Constants.STATE_ONE);
        List proccessList = new ArrayList<>();
        RecipeProccess recipeProccess = null;
        Integer result = recipeMapper.insertSelective(recipeDO);
        if (result > 0) {
            /**
             * 新增处方药品明细
             */
            if (CollectionUtils.isNotEmpty(recipeDO.getRecipeMedicineList())) {
                recipeMedicineMapper.insertByBatch(recipeDO);
            }
            /**
             * 
            recipeProccess = new RecipeProccess();
            recipeProccess.setRecipeId(recipeDO.getId());
            proccessList.add(recipeProccess);
            recipeMapper.insertProccess(proccessList);
             */
            return 1;
        }
        return 0;
    }

    @Transactional
    @Override
    public Integer updateRecipe(RecipeDO recipeDO, String hospitalType) {
    	if ("0".equals(hospitalType)) {
    		if (recipeDO.getRecipeSerial() != null && "".equals(recipeDO.getRecipeSerial())) {
    			recipeDO.setRecipeSerial(null);
    		}
    	}
        Integer result =0;
        if ("0".equals(hospitalType)) {
        	result=recipeMapper.updateHisRecipe(recipeDO);
        }else{
        	result=recipeMapper.updateRecipe(recipeDO);
        }
        if (result > 0) {
        	if ("0".equals(hospitalType)) {//院内
        		if (CollectionUtils.isNotEmpty(recipeDO.getRecipeMedicineList())) {
        			recipeMedicineMapper.updateRecipeMedicine(recipeDO.getRecipeMedicineList());
        		}
        	} else {
        		recipeMedicineMapper.deleteRecipeMedicine(recipeDO);
            	if (recipeDO.getRecipeMedicineList() != null && recipeDO.getRecipeMedicineList().size() > 0) {
            		recipeDO.getRecipeMedicineList().stream().forEach(m -> m.setRecipeId(recipeDO.getId()));
            		recipeMedicineMapper.insertByBatch(recipeDO);
            	}
        	}
            return 1;
        }
        return 0;
    }

    /**
     * 生成系统处方单号
     */
    public String getRecipeCode() {
        Map<String, Object> map = new HashMap<String, Object>(16);
        map.put(Constants.BILL_ID, 0);
        recipeMapper.generateBillIndex(map);
        if ((int) map.get(Constants.BILL_ID) == 0) {
            throw new TanguException("处方单号生成失败,请重试！");
        }
        String recipeCode = (map.get(Constants.BILL_ID)).toString();
        int len = recipeCode.length();
        for (int i = 0; i < (4 - len); i++) {
            recipeCode = "0" + recipeCode;
        }
        SimpleDateFormat sdf = new SimpleDateFormat("yyMMdd");
        String date = sdf.format(new Date());
        return date.substring(1, date.length()) + recipeCode;
    }


	@Override
	public List<BtRelationZd> listBtRelationZdByHospitalId(Integer hospitalId) {
		// TODO Auto-generated method stub
		return recipeMapper.listBtRelationZdByHospitalId(hospitalId);
	}

	@Override
	public RecipeDO checkRecipeStatus(RecipeDO recipe) {
		// TODO Auto-generated method stub
		List<RecipeDO> list = recipeMapper.checkRecipeStatus(recipe);
		if(list==null||list.size()==0)
		{
			return null;
		}
		RecipeDO recipeDo = (RecipeDO)list.get(0);
		return recipeDo;
	}

	@Override
	public Integer updateRecipeStatus(RecipeDO recipe) throws Exception {
		// TODO Auto-generated method stub
		Integer rows = 0;
		//更新状态
		Integer result = recipeMapper.updateRecipeStatus(recipe);
		//更新处方的扫描附表
		recipeMapper.updateProccess(recipe);
		if(result>0){
			RecipeScan scan = new RecipeScan();
			scan.setOperateType(recipe.getShippingState());
			scan.setRecipeId(recipe.getId());
			scan.setMachineId(recipe.getMachineId());
			//开始煎煮需要补充煎药机id，并更新煎药机状态----->正在使用
			if(Objects.equals(scan.getOperateType(), Constants.State.START_DECOCT_SCAN.getNewState())||
				Objects.equals(scan.getOperateType(), Constants.State.START_SECOND_BOIL.getNewState()))
			{
				MachineDO machine = new MachineDO();
				machine.setId(recipe.getMachineId());
				machine.setMachineStatus(1);//1表示煎药机正在使用
				machine.setModUser(recipe.getModUser());
				machineMapper.updateMachineStatus(machine);
			}
			//结束煎煮需要补充煎药机id，并更新煎药机状态----->空闲状态（即正常状态）
			else if(Objects.equals(scan.getOperateType(), Constants.State.END_DECOCT_SCAN.getNewState()))
			{
				
				MachineDO machine = new MachineDO();
				machine.setId(recipe.getMachineId());
				machine.setMachineStatus(0);//0表示煎药机处在空闲状态（即正常状态）
				machineMapper.updateMachineStatus(machine);
			}
			scan.setReceiveScanTime(new Date());
        	scan.setReceiveScanUse(recipe.getScanUser());
        	scan.setQuantity(recipe.getQuantity());
        	recipeMapper.insertRecipeScan(scan);
        	rows= scan.getId().intValue();
        	if(recipe.getAddSubside()!=null&&recipe.getAddSubside()==1&& Objects.equals(recipe.getShippingState(), Constants.State.END_DECOCT_SCAN.getNewState()) &&recipe.getBoilType()==3){
        		RecipeDO re =new RecipeDO();
        		re.setShippingState(Constants.State.SUBSIDE_START.getNewState()); 
        		re.setId(recipe.getId());
        		recipeMapper.updateRecipeStatus(re);
        		RecipeScan scan2 = new RecipeScan();
				scan2.setOperateType(Constants.State.SUBSIDE_START.getNewState());
				scan2.setRecipeId(recipe.getId());
				scan2.setReceiveScanTime(new Date());
            	scan2.setReceiveScanUse(recipe.getScanUser());
            	scan2.setQuantity(recipe.getQuantity());
            	recipeMapper.insertRecipeScan(scan2);
            	rows= scan2.getId().intValue();
        	}
		}
		return rows;
	}

	@Override
	public List<RecipeDO> selectRecipeByCode(RecipeDO recipeDO) {
		// TODO Auto-generated method stub
		return recipeMapper.selectRecipeByCode(recipeDO);
	}

	@Override
    public String generateBillIdIndex() {
		// TODO Auto-generated method stub
		String billId = "";
		int sequenceLength = 4;
		billId = getDate(new Date(), "yyMMdd");
		String index = recipeMapper.generateBillIdIndex(billId);
		if (index.length() < sequenceLength) {
			billId = billId
					+ StringUtil.repeat("0", sequenceLength
							- index.length()) + index;
		} else {
			billId = billId + index;
		}
		return billId;
	}
	private static final String getDate(Date aDate, String datePattern) {
		SimpleDateFormat df = null;
		String returnValue = "";

		if (aDate != null) {
			df = new SimpleDateFormat(datePattern);
			returnValue = df.format(aDate);
		}

		return (returnValue);
	}

	@Override
	public RecipeDO insertRecipePackingPrint(RecipeDO recipeDO) {
		// TODO Auto-generated method stub
		return recipeMapper.insertRecipePackingPrint(recipeDO);
	}

	@Override
	public List<RecipeScan> findRecipeHistory(RecipeDO scan) {
		// TODO Auto-generated method stub
		return recipeScanMapper.findRecipeHistory(scan);
	}

	@Override
	public List<RecipeDO> findRecipeScanList(RecipeDO recipeDO) {
		// TODO Auto-generated method stub
		return recipeMapper.findRecipeScanList(recipeDO);
	}

	@Override
	public Integer addRecipePatrol(RecipePatrol recipePatrol) {
		// TODO Auto-generated method stub
		return recipePatrolMapper.addRecipePatrol(recipePatrol);
	}

	@Override
	public RecipeDO getZnpgList(Integer eid) {
		RecipeDO recipes = new RecipeDO();
		List znpgList = new ArrayList();
		
		Employee employee = new Employee();
		employee.setId(eid);
		List recipeList = null;
		List chaoshiList = new ArrayList();
		List machineList = null;
		machineList = machineMapper.getNotUseMachineList();
		if(machineList!=null&&machineList.size()>0){
			recipeList = recipeMapper.getZnpgRecipeList();
			if(recipeList!=null&&recipeList.size()>0){
				RecipeDO recipe = null;
				//获得系统配置时间
				List<SysConfig> list = sysConfigMapper.listConfigByName(SystemConstant.MAX_SOAK_TIME);
				SysConfig config = (SysConfig) list.get(0);
				//从列表中拆接出浸泡超时时间小于系统配置时间的处方
				for(int i=0;i<recipeList.size();i++){
					recipe = (RecipeDO)recipeList.get(i);
					if(recipe.getSoakTimeLong()>Integer.valueOf(config.getValue()).intValue()){
						chaoshiList.add(recipe);
						recipeList.remove(i);
						i--;
					}
				}
				//排序（浸泡超时时间小于系统配置时间的处方），按照处方是否加急、医院煎药优先级（高中低）以及开单时间排序
				if(recipeList.size()>0){
					ComparatorRecipe1 comparator=new ComparatorRecipe1();
					Collections.sort(recipeList, comparator);
					znpgList.addAll(0, recipeList);
				}
				//排序（浸泡超时时间大于系统配置时间的处方），按照超出时间排序
				if(chaoshiList.size()>0){
					ComparatorRecipe2 comparator=new ComparatorRecipe2();
					Collections.sort(chaoshiList, comparator);
					znpgList.addAll(0, chaoshiList);
				}
				
				if(znpgList.size()>0&&znpgList.size()>machineList.size()){
					znpgList=znpgList.subList(0, machineList.size());
				}
			}
		}else{
			machineList = new ArrayList();
		}
		recipes.setRecipeList(znpgList);
		recipes.setSelectedList(machineList);
		return recipes;
	}

	@Override
	public Integer addCheckScan(RecipeDO recipeDO) {
		return recipeMapper.addCheckScan(recipeDO);
	}

	@Override
	public List<RecipeScan> listStartVolume(RecipeDO scan) {
		return recipeScanMapper.listStartVolume(scan);
	}

	@Override
	public Integer updateConcentrateStatus(RecipeDO recipeDO) {
		int rows = 0;
		try{
			//更新状态
			int count = recipeMapper.updateRecipeStatus(recipeDO);
			//插入扫描表
			RecipeScan scan = new RecipeScan();
			scan.setOperateType(recipeDO.getShippingState());
			scan.setRecipeId(recipeDO.getId());
			scan.setReceiveScanTime(new Date());
        	scan.setReceiveScanUse(recipeDO.getScanUser());
        	scan.setQuantity(recipeDO.getQuantity());
        	if(Objects.equals(recipeDO.getShippingState(), Constants.State.CONCENTRATE_START.getNewState())){
        		scan.setConcentrateVolume(recipeDO.getStartConcentrateVolume());
        	}else if(Objects.equals(recipeDO.getShippingState(), Constants.State.CONCENTRATE_END.getNewState())){
        		scan.setConcentrateVolume(recipeDO.getEndConcentrateVolume());
        	}
        	rows = recipeMapper.insertRecipeScan(scan);
			//更新流程表
			if(Objects.equals(recipeDO.getShippingState(), Constants.State.CONCENTRATE_START.getNewState())){
				recipeProccessMapper.updateProccessConcentrate(recipeDO);
			}else if(Objects.equals(recipeDO.getShippingState(), Constants.State.CONCENTRATE_END.getNewState())){
				recipeProccessMapper.updateProccessEndConcentrate(recipeDO);
			}
		}catch(Exception e)
		{
			e.printStackTrace();
		}
		return rows;
	}

	@Override
	public RecipeDO getRecipeAndXjMedicine(RecipeDO recipeDO) {
		List<RecipeDO> list = recipeMapper.checkRecipeStatus(recipeDO);
		if(list==null||list.size()==0)
		{
			return null;
		}
		RecipeDO recipe = (RecipeDO)list.get(0);
		recipe.setModUser(recipeDO.getScanUser());
		List recipeMedicineList =recipeMapper.getRecipeMedicineForXj(recipe);
		String medicineStr="";
		if(recipe.getShippingState()<Constants.State.SOAK_SCAN.getNewState()){
			recipe.setActionResult(11);
			return recipe;
		}
		if(Objects.equals(recipe.getShippingState(), Constants.State.START_DECOCT_SCAN.getNewState()) || Objects.equals(recipe.getShippingState(), Constants.State.START_AFTER_BOIL.getNewState())){
			if(recipeMedicineList!=null){
				recipe.setSelectedList(recipeMedicineList);
			}
			return recipe;
		}
		if(recipeMedicineList==null||recipeMedicineList.size()==0){
			MedicineDO medicine = new MedicineDO();
			medicine.setSpecialBoilType("先煎");
			List xjMedicineList = medicineMapper.findXjMedicineList(medicine);
			if(Objects.equals(recipe.getShippingState(), Constants.State.START_FRIST_BOIL.getNewState())){
				List listScan= recipeScanMapper.findRecipeScanXj(recipe);
				RecipeScan scan = (RecipeScan) listScan.get(0);
				List  finishXjMedicineList = new ArrayList();
				MedicineDO me = null;
				for(int i=0;i<xjMedicineList.size();i++){
					me = (MedicineDO) xjMedicineList.get(i);
					if(scan.getOperateComment().indexOf(me.getMedicineName())>-1){
						finishXjMedicineList.add(me);
					}
				}
				recipe.setSelectedList(finishXjMedicineList);
				recipe.setActionResult(12);//表示该处方未配方补录，但已开始先煎
			}else{
				recipe.setMedicineList(xjMedicineList);
				recipe.setActionResult(11);//表示该处方未配方补录，返回系统里所以先煎药品
			}
		}else{
			List newRecipeList = new ArrayList();
			MedicineDO me = null;
			for(int i=0;i<recipeMedicineList.size();i++){
				me = (MedicineDO) recipeMedicineList.get(i);
				if("先煎".equals(me.getSpecialBoilType())){
					newRecipeList.add(me);
					if("".equals(medicineStr)){
						medicineStr=me.getMedicineName();
					}else{
						medicineStr = medicineStr+","+me.getMedicineName();
					}
				}
			}
			if(newRecipeList.size()>0){
				recipe.setSelectedList(newRecipeList);
				if(Objects.equals(recipe.getShippingState(), Constants.State.SOAK_SCAN.getNewState())){
					recipeDO.setShippingState(Constants.State.START_FRIST_BOIL.getNewState());
					recipeDO.setId(recipe.getId());
					//更新状态
					int count = recipeMapper.updateRecipeStatus(recipeDO);
					//更新处方的扫描附表
					recipeDO.setFristBoilId(recipe.getModUser());
					recipeDO.setFristBoilTime(new Date());
					recipeMapper.updateProccess(recipeDO);
					RecipeScan scan = new RecipeScan();
					scan.setOperateType(recipeDO.getShippingState());
					scan.setQuantity(recipe.getQuantity());
					scan.setRecipeId(recipeDO.getId());
					scan.setOperateComment(medicineStr);
					scan.setReceiveScanTime(new Date());
					scan.setReceiveScanUse(recipe.getModUser());
					recipeMapper.insertRecipeScan(scan);
				}
				recipe.setActionResult(12);//表示该处方已配方补录，且配方里有先煎药品，返回该处方的先煎药品
			}else{
				recipe.setActionResult(13);//表示该处方已配方补录，但配方里没先煎药品，
			}
		}
		return recipe;
	}
	@Override
    public RecipeDO getRecipeAndHxMedicine(RecipeDO record){
		String medicineStr="";
		if(record.getSelectedList()==null||record.getSelectedList().size()==0){
			MedicineDO medicine = new MedicineDO();
			medicine.setSpecialBoilType("后下");
			List hxMedicineList = medicineMapper.findXjMedicineList(medicine);
			if(Objects.equals(record.getShippingState(), Constants.State.START_AFTER_BOIL.getNewState())){
				List listScan= recipeScanMapper.findRecipeScanXj(record);
				RecipeScan scan = (RecipeScan) listScan.get(0);
				List  finishHxMedicineList = new ArrayList();
				MedicineDO me = null;
				for(int i=0;i<hxMedicineList.size();i++){
					me = (MedicineDO) hxMedicineList.get(i);
					if(scan.getOperateComment().indexOf(me.getMedicineName())>-1){
						finishHxMedicineList.add(me);
					}
				}
				record.setSelectedList(finishHxMedicineList);
				record.setActionResult(12);//表示该处方未配方补录，但已后下先煎
			}else{
				record.setMedicineList(hxMedicineList);
				record.setActionResult(11);//表示该处方未配方补录，返回系统里所以后下药品
			}
		}else{
			List newRecipeList = new ArrayList();
			MedicineDO me = null;
			for(int i=0;i<record.getSelectedList().size();i++){
				me = (MedicineDO) record.getSelectedList().get(i);
				if("后下".equals(me.getSpecialBoilType())){
					newRecipeList.add(me);
					if("".equals(medicineStr)){
						medicineStr=me.getMedicineName();
					}else{
						medicineStr = medicineStr+","+me.getMedicineName();
					}
				}
			}
			if(newRecipeList.size()>0){
				RecipeDO re=new RecipeDO();
				record.setSelectedList(newRecipeList);
				if(Objects.equals(record.getShippingState(), Constants.State.START_DECOCT_SCAN.getNewState())){
					re.setShippingState(Constants.State.START_AFTER_BOIL.getNewState());
					re.setId(record.getId());
					//更新状态
					int count = recipeMapper.updateRecipeStatus(re);
					//更新处方的扫描附表
					re.setAfterBoilId(record.getModUser());
					re.setAfterBoilTime(new Date());
					recipeMapper.updateProccess(re);
					RecipeScan scan = new RecipeScan();
					scan.setQuantity(record.getQuantity());
					scan.setOperateType(re.getShippingState());
					scan.setRecipeId(re.getId());
					scan.setOperateComment(medicineStr);
					scan.setReceiveScanTime(new Date());
					scan.setReceiveScanUse(record.getModUser());
					recipeMapper.insertRecipeScan(scan);
				}
				record.setActionResult(12);//表示该处方已配方补录，且配方里有后下药品，返回该处方的后下药品
			}else{
				record.setActionResult(13);//表示该处方已配方补录，但配方里没后下药品，
			}
		}
		return record;
	}

	@Override
	public RecipeDO saveRecipeAndXjMedicine(RecipeDO recipeDO) {
		List list = recipeMapper.checkRecipeStatus(recipeDO);
		if(list==null||list.size()==0)
		{
			return null;
		}
		RecipeDO recipe = (RecipeDO)list.get(0);
		recipeDO.setShippingState(Constants.State.START_FRIST_BOIL.getNewState());
		recipeDO.setId(recipe.getId());
		//更新状态
		int count = recipeMapper.updateRecipeStatus(recipeDO);
		//更新处方的扫描附表
		recipeDO.setFristBoilId(recipeDO.getScanUser());
		recipeDO.setFristBoilTime(new Date());
		recipeMapper.updateProccess(recipeDO);
		RecipeScan scan = new RecipeScan();
		scan.setOperateType(recipeDO.getShippingState());
		scan.setRecipeId(recipeDO.getId());
		scan.setOperateComment(recipeDO.getRemark());
		scan.setReceiveScanTime(new Date());
		scan.setReceiveScanUse(recipeDO.getScanUser());
		recipeMapper.insertRecipeScan(scan);
		return recipe;
	}
	@Override
    public RecipeDO saveRecipeAndhxMedicine(RecipeDO recipeDO){
		List list = recipeMapper.checkRecipeStatus(recipeDO);
		if(list==null||list.size()==0)
		{
			return null;
		}
		RecipeDO recipe = (RecipeDO)list.get(0);
		recipeDO.setShippingState(Constants.State.START_AFTER_BOIL.getNewState());
		recipeDO.setId(recipe.getId());
		//更新状态
		int count = recipeMapper.updateRecipeStatus(recipeDO);
		//更新处方的扫描附表
		recipeDO.setAfterBoilId(recipeDO.getScanUser());
		recipeDO.setAfterBoilTime(new Date());
		recipeMapper.updateProccess(recipeDO);
		RecipeScan scan = new RecipeScan();
		scan.setOperateType(recipeDO.getShippingState());
		scan.setRecipeId(recipeDO.getId());
		scan.setOperateComment(recipeDO.getRemark());
		scan.setReceiveScanTime(new Date());
		scan.setReceiveScanUse(recipeDO.getScanUser());
		scan.setQuantity(recipe.getQuantity());
		recipeMapper.insertRecipeScan(scan);
		return recipe;
	}
    /*@Override
    public List<PackingType> listPackingType() {
        return recipeMapper.listPackingType();
    }*/

	@Override
	public Integer updateRecipeScan(RecipeScan scan) {
		// TODO Auto-generated method stub
		int count = recipeScanMapper.updateRecipeScan(scan);
		return count;
	}

	@Override
	public RecipeDO getCheckState(RecipeDO recipe) {
		// TODO Auto-generated method stub
		return recipeMapper.getCheckState(recipe);
	}

	@Override
	public List<RecipeMedicine> findRecipeMedicineBySpecialBoilType(RecipeMedicine recipeMedicine) {
		// TODO Auto-generated method stub
		return recipeMedicineMapper.findRecipeMedicineBySpecialBoilType(recipeMedicine);
	}

	@Override
	public int updateByPrimaryKeySelective(RecipeDO recipeDO) {
		return recipeMapper.updateByPrimaryKeySelective(recipeDO);
	}

    @Override
    public int updateByConfirmationExamine(RecipeDO recipeDO) {
        return recipeMapper.updateByConfirmationExamine(recipeDO);
    }

    @Override
	public synchronized  RecipeDO dispatchRecipe(Employee employee) {
		RecipeDO recipe = null;
        recipe = recipeMapper.getExamineRecipe(employee);
		if(recipe == null && employee.getRecipeId() != null){
			recipe = recipeMapper.getRecipeContent(employee.getRecipeId());
        }else if(recipe == null && employee.getRecipeId() == null){
            recipe = recipeMapper.getNotAuthRecipe(employee);
        }
		if(recipe == null){return null;}
		if(recipe.getDispenseId() == null || recipe.getDispenseId() < 1){
			RecipeDO updateR=new RecipeDO();
			updateR.setId(recipe.getId());
			updateR.setDispenseId(employee.getId());
			updateR.setModUser(employee.getId());
			recipeMapper.updateByPrimaryKeySelective(updateR);
		}
		return recipe;
	}

	@Override
	public Integer updateProccessDeliveryTime(RecipeProccess recipeProccess) {
		return recipeProccessMapper.updateProccessDeliveryTime(recipeProccess);
	}

	@Override
	public Integer insertRecipeScan(List<RecipeScan> list) {
		return recipeScanMapper.insertRecipeScan(list);
	}

	@Override
	public Integer updateRecipeMedicines(List<RecipeMedicine> list) {
		return recipeMedicineMapper.updateRecipeMedicines(list);
	}

//	@Override
//	public RecipeDO getExamineRecipe(Employee employee) {
//		return recipeMapper.getExamineRecipe(employee);
//	}

	@Override
	public Integer updatesProccessDeliveryTime(List<RecipeProccess> list) {
		return recipeProccessMapper.updatesProccessDeliveryTime(list);
	}

	@Override
	public List<RecipeScan> countEmployeeAchievement(RecipeScan recipeScan) {
		return recipeScanMapper.countEmployeeAchievement(recipeScan);
	}


	@Transactional
    @Override
    public Object lockingRecipe(Integer id) {
        RecipeDO recipeDO1 = recipeMapper.getRecipeContent(id);
        if(recipeDO1.getShippingState() >= 5){
            return new ResponseModel().attr(ResponseModel.KEY_ERROR, "该处方已完成审方，无法锁定");
        }else{
        	Integer result = 0;
            if(recipeDO1.getDispenseId() < 0){
                if(recipeDO1.getDispenseId() == -999999){
                    result = recipeMapper.unlockRecipeZero(recipeDO1);
                }else{
                    result = recipeMapper.unlockRecipe(recipeDO1);
                }
            }else{
                if(recipeDO1.getDispenseId() == 0){
                    result = recipeMapper.lockingRecipeZero(recipeDO1);
                }else{
                    result = recipeMapper.lockingRecipe(recipeDO1);
                }
            }
            if (result > 0) {
            	return new ResponseModel().attr(ResponseModel.KEY_MESSAGE, Constant.OPRATION_SUCCESS);
            }
            return new ResponseModel().attr(ResponseModel.KEY_ERROR, Constant.OPRATION_FAILED);
        }
    }


	@Override
	public List<RecipeDO> listRecipeState(List<RecipeDTO> list) {
		return recipeMapper.listRecipeState(list);
	}

    @Override
    public RecipeVO getRecipeVO(RecipeDTO recipeDTO) {
        return recipeMapper.getRecipeVO(recipeDTO);
    }

    @Override
    public RecipeSerialVO getRecipeSerialVO(RecipeDTO recipeDTO) {
    	RecipeSerialVO rsv=recipeMapper.getRecipeSerial(recipeDTO);
    	if(rsv == null){
    		rsv=recipeMapper.getRecipeSerialVO(recipeDTO);
    	}
        return rsv;
    }

	@Override
	public Integer updateProccess(RecipeDO recipe) {
		return recipeMapper.updateProccess(recipe);
	}

    @Override
    public List<Report> listReportMedicineByMedicine(ReportDTO reportDTO){
        return reportMapper.listReportMedicineByMedicine(reportDTO);

    }
    @Override
    public List<Report> listReportMedicineBySettle(ReportDTO reportDTO){
        return reportMapper.listReportMedicineBySettle(reportDTO);
    }
    @Override
    public List<Report> listReportMedicineByHospital(ReportDTO reportDTO){
        return reportMapper.listReportMedicineByHospital(reportDTO);
    }

    @Override
    public DispensationReciveRecipeVO getDispensationReciveRecipeVO(Employee employee) {
        return recipeMapper.getDispensationReciveRecipeVO(employee);
    }

    @Override
    public List<DispensationReciveRecipeVO> getDispensationRecipesByUnionIdAndDispenseId(RecipeDO recipeDO) {
        return recipeMapper.getDispensationRecipesByUnionIdAndDispenseId(recipeDO);
    }

    @Override
    public List<DispensationReciveRecipeVO> getDispensationRecipesByUnionId(RecipeDO recipeDO) {
        return recipeMapper.getDispensationRecipesByUnionId(recipeDO);
    }

    @Override
    public Integer updateDispenseIdByUnion(List<DispensationReciveRecipeVO> dispensationReciveRecipeVOList) {
        return recipeMapper.updateDispenseIdByUnion(dispensationReciveRecipeVOList);
    }


}
class ComparatorRecipe1 implements Comparator{
	@Override
    public int compare(Object arg0, Object arg1) {
		RecipeDO user0=(RecipeDO)arg0;
		RecipeDO user1=(RecipeDO)arg1;
		int c=user0.getCreateTime().before(user1.getCreateTime())?-1:1;
		return c;
	}
	
}
class ComparatorRecipe2 implements Comparator{
	@Override
    public int compare(Object arg0, Object arg1) {
		RecipeDO user0=(RecipeDO)arg0;
		RecipeDO user1=(RecipeDO)arg1;
		int a=user1.getSoakTimeLong().intValue()-user0.getSoakTimeLong().intValue();
		
		return a;
	}
	
}