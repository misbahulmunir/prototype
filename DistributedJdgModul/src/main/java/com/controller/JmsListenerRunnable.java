package com.controller;


import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.Session;

import org.infinispan.Cache;
import org.infinispan.configuration.cache.CacheMode;
import org.infinispan.configuration.cache.ConfigurationBuilder;
import org.infinispan.manager.EmbeddedCacheManager;

import com.entity.UpdateBalance;
import com.telkomsigma.framework.core.integration.jms.JMSProducer;

public abstract class JmsListenerRunnable implements Runnable {

	private EmbeddedCacheManager cacheManager;
	private ConnectionFactory cf;
	private String selector;
	private String cacheName;
	private JMSProducer jmsProducer;

	public JmsListenerRunnable(EmbeddedCacheManager cacheManager,
			ConnectionFactory cf, String selector, String cacheName, JMSProducer jmsProducer) {
		this.cacheManager = cacheManager;
		this.cf = cf;
		this.selector = selector;
		this.cacheName = cacheName;
		this.jmsProducer=jmsProducer;
	}

	@Override
	public void run() {
		try {
			while (true) {
				System.out.println("run---------------------");
				Connection connection = cf.createConnection();
				Session session = connection.createSession();
				Destination destination = session.createQueue("TEST");
				connection.start();
				MessageConsumer consumer = session.createConsumer(destination,
						this.selector);
				Message message = consumer.receive();
				UpdateBalance balance = message.getBody(UpdateBalance.class);
				System.out.println(balance.getGroupname());
				session.close();
				connection.close();
				cacheManager.getCache(this.cacheName).put(balance.getIdPos(),
						balance);
				if (!cacheManager.getCache("mutation").containsKey(
						balance.getDebitAcount())) {
					cacheManager.defineConfiguration(
							balance.getDebitAcount(),
							new ConfigurationBuilder().clustering()
									.cacheMode(CacheMode.REPL_SYNC).build());
					Cache<String, Object>balanceCache=cacheManager.getCache(balance.getDebitAcount());
					balanceCache.addListener(new DatagridListener(cacheManager, jmsProducer));
					cacheManager.getCache("mutation").put(
							balance.getDebitAcount(),
							balanceCache);
				} else {
					cacheManager.getCache(balance.getDebitAcount()).put(String.valueOf(balance.getIdPos()),balance);
				}

			}
		} catch (JMSException e) {
			e.printStackTrace();
		}

	}

}
