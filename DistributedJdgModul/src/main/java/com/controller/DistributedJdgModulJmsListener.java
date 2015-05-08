package com.controller;

import java.util.ArrayList;
import java.util.List;



import javax.jms.ConnectionFactory;
import javax.jms.JMSException;

import org.infinispan.manager.EmbeddedCacheManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;


@Configuration
public class DistributedJdgModulJmsListener {

	@Autowired
	EmbeddedCacheManager cacheManager;
	@Autowired
	ConnectionFactory cf;

	/*
	 * @JmsListener(containerFactory =
	 * "jmsListenerContainerFactory",destination=
	 * "TEST",selector="JMSCorrelationID = 'merah'") public void
	 * receiveMessage(UpdateBalance balance) {
	 * System.out.println(balance.getGroupname());
	 * cacheManager.getCache("merah").put(balance.getGroupname(), balance); }
	 */
	@Bean
	public ArrayList<Thread> threadList() {

		return new ArrayList<Thread>();
	}

	@Bean
	public String CreateListener(ArrayList<Thread> threadList) {
		cacheManager.addListener(new DatagridListener(cacheManager));
		try {
			testReceiver(threadList);
			testReceiver(threadList);
			testReceiver(threadList);
			testReceiver(threadList);
			testReceiver(threadList);
			for (Thread thread : threadList) {
				thread.start();
			}
		} catch (JMSException e) {
			e.printStackTrace();
		}
		return "hahah";
	}

	public void testReceiver(List<Thread> threadList) throws JMSException {
		Thread thread = new Thread(new RedJmsListener(cacheManager, cf, "JMSCorrelationID = 'merah'","merah"));
		threadList.add(thread);
	}

}