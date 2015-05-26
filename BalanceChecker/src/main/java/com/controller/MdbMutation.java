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
    
	@JmsListener(destination="queu-active-positioning", selector="JMSCorrelationID = 'position'")
	public void receiveMessage(UpdateBalance balanceObject)
	{    Thread thread= new Thread(new BalanceChekerThreadRunnable( cacheManager.getCache(balanceObject.getDebitAcount()),producer,client));
		 System.out.println("=======thread start=======");
		 thread.start();
	     //threadList.add(thread);
	}
	
}
