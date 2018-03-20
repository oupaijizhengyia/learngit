package com.tangu.tcmts.po;

import java.util.List;

import lombok.Data;

/**
 * 省
 * @author Administrator
 *
 */
@Data
public class Province {
	private String citycode;
	private String adcode;
	private String name;
	private String center;
	private String level;
	List<City> districts;
}
