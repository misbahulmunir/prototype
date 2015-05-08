package com.controller;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.Session;

import org.infinispan.manager.EmbeddedCacheManager;

import com.entity.UpdateBalance;

public abstract class JmsListenerRunnable implements Runnable {

	private EmbeddedCacheManager cacheManager;
	private ConnectionFactory cf;
	private String selector;
	private String cacheName;
    
	
	public JmsListenerRunnable(EmbeddedCacheManager cacheManager,
			ConnectionFactory cf, String selector, String cacheName) {
		this.cacheManager = cacheManager;
		this.cf = cf;
		this.selector=selector;
		this.cacheName=cacheName;
	}


	@Override
	public void run() {
		try{
		while(true){
	    System.out.println("run---------------------");
		Connection connection=cf.createConnection();
 		Session session=connection.createSession();
 		Destination destination=session.createQueue("TEST");
 		connection.start();
 		MessageConsumer consumer=session.createConsumer(destination,this.selector);
 		Message message=consumer.receive();
 	    UpdateBalance balance=message.getBody(UpdateBalance.class);
 	    System.out.println(balance.getGroupname());
 	    session.close();
 	    connection.close();
 	    cacheManager.getCache(this.cacheName).put(balance.getGroupname(), balance);
 	   cacheManager.getCache("balance").put(balance.getGroupname(), balance);
		}
		}
		catch(JMSException e)
		{
			e.printStackTrace();
		}
		
	}

}
