package com.controller;

import javax.jms.ConnectionFactory;

import org.infinispan.manager.EmbeddedCacheManager;

import com.telkomsigma.framework.core.integration.jms.JMSProducer;

public class BlackJmsListener extends JmsListenerRunnable {

	public BlackJmsListener(EmbeddedCacheManager cacheManager,
			ConnectionFactory cf, String selector,String cacheName, JMSProducer jmsProducer) {
		super(cacheManager, cf, selector, cacheName,jmsProducer);
		// TODO Auto-generated constructor stub
	}

}
