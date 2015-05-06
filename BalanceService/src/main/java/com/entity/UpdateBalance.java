package com.entity;

import java.io.Serializable;
import javax.persistence.*;
import java.sql.Timestamp;
import java.math.BigDecimal;


/**
 * The persistent class for the update_balance database table.
 * 
 */

public class UpdateBalance extends IUpdateBalance implements Serializable {
	private static final long serialVersionUID = 1L;

	private BigDecimal balance;


	private BigDecimal debAmount;

	private String groupname;


	private int idPos;

	
	private String idRefrence;

	private String ismustdone;


	private BigDecimal kreditAmount;


	private int maxRetry;

	private String priority;

	private int retrycount;

	private Timestamp timestamp;
	
	
	public UpdateBalance() {
	}

	public BigDecimal getBalance() {
		return this.balance;
	}

	public void setBalance(BigDecimal balance) {
		this.balance = balance;
	}

	public BigDecimal getDebAmount() {
		return this.debAmount;
	}

	public void setDebAmount(BigDecimal debAmount) {
		this.debAmount = debAmount;
	}

	public String getGroupname() {
		return this.groupname;
	}

	public void setGroupname(String groupname) {
		this.groupname = groupname;
	}

	public int getIdPos() {
		return this.idPos;
	}

	public void setIdPos(int idPos) {
		this.idPos = idPos;
	}

	public String getIdRefrence() {
		return this.idRefrence;
	}

	public void setIdRefrence(String idRefrence) {
		this.idRefrence = idRefrence;
	}

	public String getIsmustdone() {
		return this.ismustdone;
	}

	public void setIsmustdone(String ismustdone) {
		this.ismustdone = ismustdone;
	}

	public BigDecimal getKreditAmount() {
		return this.kreditAmount;
	}

	public void setKreditAmount(BigDecimal kreditAmount) {
		this.kreditAmount = kreditAmount;
	}

	public int getMaxRetry() {
		return this.maxRetry;
	}

	public void setMaxRetry(int maxRetry) {
		this.maxRetry = maxRetry;
	}

	public String getPriority() {
		return this.priority;
	}

	public void setPriority(String priority) {
		this.priority = priority;
	}

	public int getRetrycount() {
		return this.retrycount;
	}

	public void setRetrycount(int retrycount) {
		this.retrycount = retrycount;
	}

	public Timestamp getTimestamp() {
		return this.timestamp;
	}

	public void setTimestamp(Timestamp timestamp) {
		this.timestamp = timestamp;
	}

}