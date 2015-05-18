package com.controller;

import javassist.expr.NewArray;

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
			ConnectionFactory cf, String selector, String cacheName,
			JMSProducer jmsProducer) {
		this.cacheManager = cacheManager;
		this.cf = cf;
		this.selector = selector;
		this.cacheName = cacheName;
		this.jmsProducer = jmsProducer;
	}

	@Override
	public void run() {
		try {

			System.out.println("run---------------------");
			Connection connection = cf.createConnection();
			Session session = connection.createSession();
			Destination destination = session.createQueue("TEST");
			connection.start();
			MessageConsumer consumer = session.createConsumer(destination,
					this.selector);
			while (true) {
				Message message = consumer.receive();
				UpdateBalance balance = message.getBody(UpdateBalance.class);
				System.out.println("kredit="+balance.getKreditAmount());
				System.out.println(balance.getGroupname());
				cacheManager.getCache(this.cacheName).put(balance.getIdPos(),
						balance);
				if (!cacheManager.getCache("mutation").containsKey(
						balance.getDebitAcount())) {
					cacheManager.defineConfiguration(balance.getDebitAcount(),
							new ConfigurationBuilder().locking().clustering()
									.cacheMode(CacheMode.REPL_SYNC).build());
					cacheManager.getCache(balance.getDebitAcount())
							.addListener(
									new DatagridListener(cacheManager,
											jmsProducer));
				/*	cacheManager.getCache("mutation").put(
							balance.getDebitAcount(),
							cacheManager.getCache(balance.getDebitAcount()));*/
				}
				cacheManager.getCache(balance.getDebitAcount()).put(
						balance.getIdPos(), balance);
            /* session.close();
             connection.close();*/
			}
		} catch (JMSException e) {
			e.printStackTrace();
		}

	}

}
