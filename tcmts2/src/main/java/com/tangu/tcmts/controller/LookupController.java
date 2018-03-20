package com.tangu.tcmts.controller;

import com.tangu.common.util.entity.ResponseModel;
import com.tangu.tcmts.bean.SysPropertyUtil;
import com.tangu.tcmts.po.SysConfig;
import com.tangu.tcmts.po.SysLookup;
import com.tangu.tcmts.service.SysConfigService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.CacheManager;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.stream.Collectors;

import static com.tangu.tcmts.util.Constants.TYPE_ONE;

/**
 * @author fenglei
 * @date 10/31/17
 */

@Api(value = "/lookup", description = "资源查找")
@RestController
@RequestMapping("/lookup")
public class LookupController {

	@Autowired
	SysConfigService sysConfigService;

	@Autowired
	SysPropertyUtil sysPropertyUtil;

	@Autowired
	CacheManager cacheManager;

	@ApiOperation(tags = "全局配置", value = "查询配置文件", notes = "查询配置文件，页面首次加载时访问并保存到前端", response = SysConfig.class)
	@RequestMapping(value = "/config", method = RequestMethod.GET)
	public Object config() {
		// type 1表示只需要传给前台的值，这个值是定义在数据库sys_config表注释里
		Map<String, Object> configMap = sysConfigService.listConfigByType(TYPE_ONE).stream()
				.collect(Collectors.toMap(SysConfig::getName, SysConfig::getValue));
		return new ResponseModel(configMap);
	}

	@ApiOperation(tags = "下拉框数据", value = "不同的{code}对应不同的下拉，需要新增请联系相关后台", notes = "exceptionType:异常类型, medicineStandard:药品规格, medicineUnit:药品单位,priceType:价格类别,specialBoilType: 特煎方式 "
			+ "tagPrintType:标签打印类型,transferType:配送方式（公司）,tabooType:配伍禁忌,packingType:包装方式"
			+ "creamFormulaPrintTyp:膏方打印,businessType:快件类型,boilType:煎药方式,recipePrintType:处方打印"
			+ "printType:打印模板类型,packingSize:包装规格,eatingInstruction:服法,expressListType:快递面单类型,"
			+ "exportRecipeType:处方导出类型,takeType：用法"
			+ "shelvesType:货架类型 1:门诊 2:住院 3:快递", response = SysLookup.class)
	@RequestMapping(value = "/code/{code}", method = RequestMethod.GET)
	public Object code(@PathVariable String code) {
		return new ResponseModel(sysConfigService.listLookupByCode(code));
	}

	// @PreAuthorize(HasRoleAdmin) //TODO 必须管理员才能调用此接口
	@ApiOperation(tags = "非前端接口", value = "刷新缓存")
	@RequestMapping(value = "/refresh", method = RequestMethod.GET)
	public Object refresh() {
		cacheManager.getCacheNames().parallelStream().forEach(name -> cacheManager.getCache(name).clear());
		return "success";
	}

}
