package com.ego.dubboservice;


import com.github.pagehelper.PageHelper;
import org.apache.ibatis.plugin.Interceptor;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import java.util.Properties;


@SpringBootApplication
@EnableTransactionManagement
@EnableAspectJAutoProxy
@MapperScan("com.ego.dubboservice.mapper")
public class EgoDubboServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(EgoDubboServiceApplication.class, args);
	}

//	@Bean
//	PageHelper pageHelper(){
//		//分页插件
//		PageHelper pageHelper = new PageHelper();
//		Properties properties = new Properties();
//		properties.setProperty("reasonable", "true");
//		properties.setProperty("supportMethodsArguments", "true");
//		properties.setProperty("returnPageInfo", "check");
//		properties.setProperty("params", "count=countSql");
//		pageHelper.setProperties(properties);
//
//		//添加插件
//		new SqlSessionFactoryBean().setPlugins(new Interceptor[]{pageHelper});
//		return pageHelper;
//	}

}
