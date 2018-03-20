package com.tangu.tcmts.po;

import java.util.List;

import lombok.Data;

/**
 * 市
 * @author Administrator
 *
 */
@Data
public class City {
	private String citycode;
	private String adcode;
	private String name;
	private String center;
	private String level;
	List<Region> districts;
}
