package com.controller;

import org.springframework.jms.annotation.JmsListener;

import com.entity.UpdateBalance;

public class DistributedJdgModulJmsListener {
	

	@JmsListener(destination="TEST", selector="merah")
	public void receiveMessage(UpdateBalance balance)
	{
	   System.out.println(balance.getIdPos());
	}

}