package com.czy.example;

import java.io.File;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.ResourceUtils;

@Component
public class StartPlayBack implements InitializingBean {

	@Autowired
	private PlayBackService playBackService;

	@Override
	public void afterPropertiesSet() throws Exception {
		File file = ResourceUtils.getFile("classpath:data_short.txt");
		playBackService.play(file);
	}

}
