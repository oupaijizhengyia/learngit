package com.tangu.commom;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.context.annotation.Configuration;

import com.alibaba.fastjson.JSONArray;
import com.tangu.tcmts.po.City;
import com.tangu.tcmts.po.Province;
import com.tangu.tcmts.util.Constants;

import lombok.extern.slf4j.Slf4j;

/**
 * 使用@PostConstruct初始化配置
 * 
 * @author Administrator
 *
 */
@Slf4j
@Configuration
public class InitializationConfig {
	@PostConstruct
	public void getJsonStr() throws FileNotFoundException {
		//URL path = this.getClass().getClassLoader().getResource("/static/data/china2.json");
		//File file = ResourceUtils.getFile("classpath:static/data/china2.json");
		StringBuilder json = new StringBuilder();
		BufferedReader reader = null;
		InputStream in;
		try {
			in =InitializationConfig.class.getClassLoader().getResourceAsStream("static/data/china2.json");
			reader = new BufferedReader(new InputStreamReader(in, "UTF-8"));// 读取文件
			String tempString = null;
			while ((tempString = reader.readLine()) != null) {
				json.append(tempString);
			}
			reader.close();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (reader != null) {
				try {
					reader.close();
				} catch (IOException el) {
					el.printStackTrace();
					log.error(el.getMessage(), el);
				}
			}
		}
		List<Province> listProvince = JSONArray.parseArray(json.toString(), Province.class);
		for (Province p : listProvince) {
			for (City city : p.getDistricts()) {
				if (p.getName() != null && "上海市".equals(p.getName())) {
					Constants.rangeList.addAll(city.getDistricts());
				}
				Constants.cityList.add(city);
			}
		}
	}
}
