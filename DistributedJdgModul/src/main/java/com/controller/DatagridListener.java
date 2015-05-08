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
import org.infinispan.notifications.cachelistener.event.TopologyChangedEvent;
import org.jboss.logging.Logger;

import com.entity.UpdateBalance;

/**
 * An Infinispan listener that simply logs cache entries being created and
 * removed
 * 
 * @author Pete Muir
 * 
 */
@Listener
public class DatagridListener {

   private Logger log = Logger.getLogger(DatagridListener.class);
   private Cache<String, Object> cacheActivePositioning;
   public DatagridListener(EmbeddedCacheManager cacheManager) {
	// TODO Auto-generated constructor stub
      this.cacheActivePositioning=cacheManager.getCache("active_positioning");
   }
   @CacheEntryCreated
   public void observeAdd(CacheEntryCreatedEvent<Object, Object> event) {
      if (event.isPre())
         return;
      if(cacheActivePositioning.entrySet().size()>0)
      {
        if(!cacheActivePositioning.containsKey((String)event.getKey()))
          {
        	if(((UpdateBalance)cacheActivePositioning.get((String)event.getKey())).getTimestamp()<((UpdateBalance)event.getValue()).getTimestamp())
                cacheActivePositioning.remove((String)event.getKey());  
            cacheActivePositioning.put((String)event.getKey(),((UpdateBalance)event).getTimestamp());
          }
      }
      log.infof("Cache entry %s added in cache %s", event.getKey(), event.getCache());
   }

   @CacheEntryModified
   public void observeUpdate(CacheEntryModifiedEvent<Object, Object> event) {
      if (event.isPre())
         return;

      log.infof("Cache entry %s = %s modified in cache %s", event.getKey(), event.getValue(), event.getCache());
   }

   @CacheEntryRemoved
   public void observeRemove(CacheEntryRemovedEvent<Object, Object> event) {
      if (event.isPre())
         return;

      log.infof("Cache entry %s removed in cache %s", event.getKey(), event.getCache());
   }

   @TopologyChanged
   public void observeTopologyChange(TopologyChangedEvent<Object, Object> event) {
      if (event.isPre())
         return;

      log.infof("Cache %s topology changed, new membership is %s", event.getCache().getName(), event.getConsistentHashAtEnd().getMembers());
   }
}
