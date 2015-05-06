package com.controller;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.HashMap;

import javax.validation.metadata.MethodType;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.entity.UpdateBalance;
import com.telkomsigma.framework.core.integration.ws.WSRestClient;

@Controller
public class HomeController {
	@Autowired
	WSRestClient wsrc;
	
	@RequestMapping(value="/sendinstruction")
	public String sendInstruction(){
		UpdateBalance ub = new UpdateBalance();
		ub.setBalance(new BigDecimal(1000000));
		ub.setDebAmount(new BigDecimal(1000));
		ub.setGroupname("merah"); 
		ub.setIdPos(0);
		ub.setIsmustdone("true");
		ub.setMaxRetry(5);
		ub.setPriority("max");
		ub.setRetrycount(0);
//		ub.setTimestamp(Calendar.getInstance().getTimeInMillis());
		ub.setIdRefrence("");
		wsrc.post("http://192.168.137.118:8030/PutDataIntoQueue", ub, String.class, new HashMap<String, Object>());
		return null;
		
	}
	
	@RequestMapping(value="/notification")
	public String notification(){
		
		return "notif";
		
	}
}
