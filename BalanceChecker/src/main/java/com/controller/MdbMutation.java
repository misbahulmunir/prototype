package com.controller;

import java.util.ArrayList;
import java.lang.Thread;
import org.infinispan.manager.EmbeddedCacheManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;
import com.entity.UpdateBalance;

@Component
public class MdbMutation {
    @Autowired
    private EmbeddedCacheManager cacheManager;
    private ArrayList<Thread> threadList;
    @Bean
    public ArrayList<Thread> threadList()
    {
         threadList=new ArrayList<Thread>();
         return threadList;
    }
	@JmsListener(destination="MutationQueue")
	public void receiveMessage(UpdateBalance balanceObject)
	{
		 Thread thread= new Thread(new BalanceChekerThreadRunnable(cacheManager.getCache("balance"), balanceObject));
		 thread.start();
	     threadList.add(thread);
	}
	
}
