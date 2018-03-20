package com.tangu;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

import javax.annotation.PostConstruct;
import java.util.TimeZone;
/**
 * @author fenglei on 8/29/17.
 */
@SpringBootApplication
@MapperScan("com.tangu.tmcts.dao")
@EnableCaching
public class TcmtsApplication {

	@PostConstruct
	void statred(){
		TimeZone.setDefault(TimeZone.getTimeZone("CTT"));
	}

	public static void main(String[] args) {
		SpringApplication.run(TcmtsApplication.class, args);
	}
}
