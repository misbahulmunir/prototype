package com.controller;

import javax.jms.ConnectionFactory;

import org.infinispan.manager.EmbeddedCacheManager;

public class BlueJmsListener extends JmsListenerRunnable{

	public BlueJmsListener(EmbeddedCacheManager cacheManager,
			ConnectionFactory cf, String selector,String cacheName) {
		super(cacheManager, cf, selector,  cacheName);
		// TODO Auto-generated constructor stub
	}

}
