package com.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.entity.UpdateBalance;
import com.telkomsigma.framework.core.integration.jms.JMSProducer;

@Controller
public class BalanceServiceController {

	@Autowired
	JMSProducer jms;
	@RequestMapping(value="/PutDataIntoQueue", method=RequestMethod.POST)
	@ResponseBody
	public void putDataIntoQueue(@RequestBody UpdateBalance value) {
		jms.sendObjectMessage(value, "TEST", value.getGroupname());
	}
}
