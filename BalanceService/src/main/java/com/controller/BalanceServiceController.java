package com.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.entity.UpdateBalance;
import com.telkomsigma.framework.core.api.jdg.JdgClientServer;
import com.telkomsigma.framework.core.api.jdg.JdgLibraryMode;
import com.telkomsigma.framework.core.integration.jms.JMSProducer;

@Controller
public class BalanceServiceController {

	@Autowired
	JMSProducer jms;
	@Autowired
	JdgLibraryMode jdg;
	@RequestMapping(value="/PutDataIntoQueue", method=RequestMethod.POST)
	@ResponseBody
	public void putDataIntoQueue(@RequestBody UpdateBalance value) {
		System.out.println(value.getGroupname());
		System.out.println(value.getKreditAmount());
		System.out.println(value.getIdRefrence());
		System.out.println(value.getIdPos());
		jms.sendObjectMessage("TEST",value,value.getGroupname());
	}
	@RequestMapping(value="/remoteCache/{cacheName}", method=RequestMethod.GET)
	@ResponseBody
	public Object remoteCache(@PathVariable("cacheName")String cacheName) {
		return jdg.getCache(cacheName);
	}
}
