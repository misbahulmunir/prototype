package com.controller;

import org.infinispan.Cache;

import com.entity.UpdateBalance;

public class BalanceChekerThreadRunnable implements Runnable {

	private Cache<Object, Object> cache;
	private UpdateBalance balanceObject;
	

	public BalanceChekerThreadRunnable(Cache<Object, Object> cache,UpdateBalance balanceObject) {
		this.cache = cache;
		this.balanceObject=balanceObject;
	}



	@Override
	public void run() {
		// TODO Auto-generated method stub
		UpdateBalance balance=(UpdateBalance)cache.get(balanceObject.getIdRefrence());
		if(balance.getBalance().compareTo(balance.getDebAmount())==-1)
		{
			
		}
		else
		{
			
		}
	}
}
