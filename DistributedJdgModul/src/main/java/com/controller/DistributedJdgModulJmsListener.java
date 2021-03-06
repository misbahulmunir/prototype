package com.controller;

import java.util.ArrayList;
import java.util.List;







import javax.jms.ConnectionFactory;
import javax.jms.JMSException;

import org.infinispan.manager.EmbeddedCacheManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import com.telkomsigma.framework.core.integration.jms.JMSProducer;


@Configuration
public class DistributedJdgModulJmsListener {

	@Autowired
	private EmbeddedCacheManager cacheManager;
	@Autowired
	private ConnectionFactory cf;
	@Autowired
	private JMSProducer jms;
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
		Thread thread = new Thread(new RedJmsListener(cacheManager, cf, "JMSCorrelationID = 'merah'","merah",jms));
		threadList.add(thread);
		thread = new Thread(new BlueJmsListener(cacheManager, cf, "JMSCorrelationID = 'biru'","biru",jms));
		threadList.add(thread);
		thread = new Thread(new BlackJmsListener(cacheManager, cf, "JMSCorrelationID = 'hitam'","hitam",jms));
		threadList.add(thread);
		thread = new Thread(new YellowJmsListener(cacheManager, cf, "JMSCorrelationID = 'kuning'","kuning",jms));
		threadList.add(thread);
		thread = new Thread(new PinkJmsListener(cacheManager, cf, "JMSCorrelationID = 'pink'","pink",jms));
		threadList.add(thread);
	}

}