package com.controller;

import java.util.ArrayList;
import java.lang.Thread;

import org.infinispan.Cache;
import org.infinispan.manager.EmbeddedCacheManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

import com.entity.UpdateBalance;
import com.telkomsigma.framework.core.integration.jms.JMSProducer;
import com.telkomsigma.framework.core.integration.ws.WSRestClient;

@Component
public class MdbMutation {
    @Autowired
    private EmbeddedCacheManager cacheManager;
    @Autowired
    private WSRestClient client;
    @Autowired
    private JMSProducer producer;
    private ArrayList<Thread> threadList;
    @Bean
    public ArrayList<Thread> threadList()
    {
         threadList=new ArrayList<Thread>();
         return threadList;
    }
    
	@JmsListener(destination="queu-active-positioning")
	public void receiveMessage(UpdateBalance balanceObject)
	{
		System.out.println("masuk"+balanceObject.getGroupname());
		 Thread thread= new Thread(new BalanceChekerThreadRunnable((Cache<Object, Object>) cacheManager.getCache("mutation").get(balanceObject.getDebitAcount()),producer,client));
		 thread.start();
	     threadList.add(thread);
	}
	
}
