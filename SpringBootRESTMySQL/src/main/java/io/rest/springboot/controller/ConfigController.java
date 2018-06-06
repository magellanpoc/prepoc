package io.rest.springboot.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.rest.springboot.model.Configuration;
import io.rest.springboot.repository.ConfigurationRepository;

@RestController
public class ConfigController {
	
	@Autowired
	private ConfigurationRepository repo;
	
	
	@RequestMapping(value="/insert",headers = "Accept=application/json",method = RequestMethod.POST)
	public Configuration insert(@RequestBody Configuration config)
	{
//		List<Configuration> configList = new ArrayList<Configuration>();
//		configList.add(config);
		repo.save(config);
		return config;
	}

	@RequestMapping("/show/{id}")
	public Configuration show(@PathVariable Integer id)
	{
		return repo.findOne(id);
	}
	

}
