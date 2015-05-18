package com.controller;

import java.util.HashMap;
import java.util.Map.Entry;

import org.infinispan.Cache;

import com.entity.UpdateBalance;
import com.telkomsigma.framework.core.integration.jms.JMSProducer;
import com.telkomsigma.framework.core.integration.ws.WSRestClient;

public class BalanceChekerThreadRunnable implements Runnable {

	private Cache<Object, Object> cache;
	private JMSProducer jmsProducer;
	private WSRestClient restClient;

	public BalanceChekerThreadRunnable(Cache<Object, Object> cache2,
			JMSProducer producer, WSRestClient restClient) {
		this.cache = cache2;
		this.jmsProducer = producer;
		this.restClient = restClient;
	}

	@Override
	public void run() {

		for (Entry<Object, Object> balanced : cache.entrySet()) {
			System.out.println(balanced.getKey());
			System.out.println(((UpdateBalance) balanced.getValue())
					.getBalance());
			System.out.println(((UpdateBalance) balanced.getValue())
					.getDebAmount());
			UpdateBalance balance = (UpdateBalance) balanced.getValue();
			if (balance != null) {
				if (balance.getBalance().longValue() < balance.getDebAmount()
						.longValue()) {
					// /send to od qu
					jmsProducer.sendObjectMessage("ODQueue", balance, "");
					System.out
							.println("=====================gak tembus===========================");
				} else if (balance.getBalance().longValue() > balance
						.getDebAmount().longValue())
					restClient.post("http://10.10.125.123:8080/updateData",
							balance, String.class,
							new HashMap<String, Object>());
				System.out
						.println("=====================transaksi tembus=======================");
			}
		}
	}

	/*
	 * Integer count = cache.entrySet().size();
	 * System.out.println("====================="
	 * +count+"==========================="); while (cache.entrySet().size() >
	 * 0) {// TODO Auto-generated method stub UpdateBalance balance =
	 * (UpdateBalance) cache.get(count); if (balance != null) {
	 * System.out.println("debit amount" + balance.getDebAmount());
	 * System.out.println("balance amount" + balance.getBalance());
	 * 
	 * if (balance.getBalance().longValue()<balance.getDebAmount().longValue())
	 * { // /send to od qu jmsProducer.sendObjectMessage("ODQueue", balance, "",
	 * "object"); System.out.println(
	 * "=====================gak tembus==========================="); } else if
	 * (balance.getBalance().longValue()>balance.getDebAmount().longValue())
	 * restClient.post("http://10.10.125.123:8080/updateData", balance,
	 * String.class, new HashMap<String, Object>()); System.out.println(
	 * "=====================transaksi tembus======================="); }
	 * cache.remove(String.valueOf(count)); count--; }
	 * 
	 * }
	 */

}
