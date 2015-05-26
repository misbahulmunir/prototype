/*
 * JBoss, Home of Professional Open Source
 * Copyright 2011 Red Hat Inc. and/or its affiliates and other
 * contributors as indicated by the @author tags. All rights reserved.
 * See the copyright.txt in the distribution for a full listing of
 * individual contributors.
 *
 * This is free software; you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License as
 * published by the Free Software Foundation; either version 2.1 of
 * the License, or (at your option) any later version.
 *
 * This software is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this software; if not, write to the Free
 * Software Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA
 * 02110-1301 USA, or see the FSF site: http://www.fsf.org.
 */
package com.controller;

import org.infinispan.Cache;
import org.infinispan.manager.EmbeddedCacheManager;
import org.infinispan.notifications.Listener;
import org.infinispan.notifications.cachelistener.annotation.CacheEntryCreated;
import org.infinispan.notifications.cachelistener.annotation.CacheEntryModified;
import org.infinispan.notifications.cachelistener.annotation.CacheEntryRemoved;
import org.infinispan.notifications.cachelistener.annotation.TopologyChanged;
import org.infinispan.notifications.cachelistener.event.CacheEntryCreatedEvent;
import org.infinispan.notifications.cachelistener.event.CacheEntryModifiedEvent;
import org.infinispan.notifications.cachelistener.event.CacheEntryRemovedEvent;
import org.infinispan.notifications.cachelistener.event.Event;
import org.infinispan.notifications.cachelistener.event.TopologyChangedEvent;
import org.infinispan.notifications.cachemanagerlistener.annotation.CacheStarted;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.entity.UpdateBalance;
import com.telkomsigma.framework.core.integration.jms.JMSProducer;

/**
 * An Infinispan listener that simply logs cache entries being created and
 * removed
 * 
 * @author Pete Muir
 * 
 */
@Listener
public class DatagridListener {

	private Cache<String, Object> cacheActivePositioning;
	private JMSProducer jmsProducer;
	private Logger log = LoggerFactory.getLogger(DatagridListener.class);

	public DatagridListener(EmbeddedCacheManager cacheManager,
			JMSProducer jmsProducer) {
		// TODO Auto-generated constructor stub
		this.cacheActivePositioning = cacheManager
				.getCache("active-positioning");
		this.jmsProducer = jmsProducer;
	}

	@CacheEntryCreated
	public void observeAdd(CacheEntryCreatedEvent<Integer, Object> event) {
		if (event.isPre())
			return;

		log.info("======================masuk add=======================================");
		if (cacheActivePositioning.containsKey(((UpdateBalance) event
				.getValue()).getIdBroker())) {
			if (((UpdateBalance) event.getValue()).getTimestamp() > (Long) cacheActivePositioning
					.get(((UpdateBalance) event.getValue()).getIdBroker())) {
				log.info("cache removed");
				cacheActivePositioning
						.remove(((UpdateBalance) event.getValue())
								.getIdBroker());
			}
		} else if (!cacheActivePositioning.containsKey(((UpdateBalance) event
				.getValue()).getIdBroker())) {
			cacheActivePositioning.putIfAbsent(
					((UpdateBalance) event.getValue()).getIdBroker(),
					((UpdateBalance) event.getValue()).getTimestamp());
			jmsProducer.sendObjectMessage("queu-active-positioning",
					event.getValue(), "position");
		}

	}

	@CacheEntryModified
	public void observeUpdate(CacheEntryModifiedEvent<Integer, Object> event) {
		if (event.isPre())
			return;

	}

	@CacheEntryRemoved
	public void observeRemove(CacheEntryRemovedEvent<Integer, Object> event) {
		if (event.isPre())
			return;

	}

	@TopologyChanged
	public void observeTopologyChange(
			TopologyChangedEvent<Integer, Object> event) {
		if (event.isPre())
			return;

	}
}
