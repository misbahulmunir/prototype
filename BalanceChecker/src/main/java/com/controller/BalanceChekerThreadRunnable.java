package com.controller;

import java.util.HashMap;

import org.infinispan.Cache;

import com.entity.UpdateBalance;
import com.telkomsigma.framework.core.integration.jms.JMSProducer;
import com.telkomsigma.framework.core.integration.ws.WSRestClient;

public class BalanceChekerThreadRunnable implements Runnable {

	private Cache<Object, Object> cache;
	private JMSProducer jmsProducer;
    private WSRestClient restClient;
	public BalanceChekerThreadRunnable(Cache<Object, Object> cache, JMSProducer producer, WSRestClient restClient) {
		this.cache = cache;
		this.jmsProducer=producer;
		this.restClient=restClient;
	}

	@Override
	public void run() {
		int count=1;
		while (cache.entrySet().size() > 0) {// TODO Auto-generated method stub
		UpdateBalance balance = (UpdateBalance) cache.get(String.valueOf(count));
			if (balance.getBalance().compareTo(balance.getDebAmount()) == -1) {
				// /send to od qu
			jmsProducer.sendObjectMessage(balance, "ODQueue", "");
			} else {
              restClient.post("10.10.125.175:8080/updateData",balance, String.class, new HashMap<String, Object>());
			}
			cache.remove(String.valueOf(count));
			count++;
		}
	}
}
