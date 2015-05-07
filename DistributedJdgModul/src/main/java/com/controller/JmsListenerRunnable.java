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

public class JmsListenerRunnable implements Runnable {

	private EmbeddedCacheManager cacheManager;
	private ConnectionFactory cf;
    
	
	public JmsListenerRunnable(EmbeddedCacheManager cacheManager,
			ConnectionFactory cf) {
		this.cacheManager = cacheManager;
		this.cf = cf;
	}


	@Override
	public void run() {
		try{
	    System.out.println("run---------------------");
		Connection connection=cf.createConnection();
 		Session session=connection.createSession();
 		Destination destination=session.createQueue("TEST");
 		connection.start();
 		String selector="JMSCorrelationID = 'merah'";
 		MessageConsumer consumer=session.createConsumer(destination,selector);
 		Message message=consumer.receive();
 	    UpdateBalance balance=message.getBody(UpdateBalance.class);
 	    System.out.println(balance.getGroupname());
 	    session.close();
 	    connection.close();
 	    cacheManager.getCache("merah").put(balance.getGroupname(), balance);
		}
		catch(JMSException e)
		{
			e.printStackTrace();
		}
		
	}

}
