package com.controller;

import javax.jms.ConnectionFactory;

import org.infinispan.manager.EmbeddedCacheManager;

public class RedJmsListener extends JmsListenerRunnable {

	public RedJmsListener(EmbeddedCacheManager cacheManager,
			ConnectionFactory cf, String selector, String cacheName) {
		super(cacheManager, cf,selector, cacheName);
		// TODO Auto-generated constructor stub
	}

}
