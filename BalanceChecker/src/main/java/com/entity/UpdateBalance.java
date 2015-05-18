package com.entity;

import java.io.Serializable;
import javax.persistence.*;
import java.math.BigInteger;
import java.math.BigDecimal;


/**
 * The persistent class for the update_balance database table.
 * 
 */
/*@Entity
@Table(name="update_balance")
@NamedQuery(name="UpdateBalance.findAll", query="SELECT u FROM UpdateBalance u")*/
public class UpdateBalance extends IUpdateBalance implements Serializable {
	private static final long serialVersionUID = 1L;

	
	/*@Column(name="id_pos")*/
	private int idPos;

	private BigDecimal balance;

	/*@Lob
	@Column(name="credit_acount")*/
	private String creditAcount;
	
	
	/*@Column(name="deb_amount")*/
	private BigDecimal debAmount;
	
	/*@Id
	@Lob
	@Column(name="debit_acount")*/
	private String debitAcount;

	private String groupname;

/*	@Lob
	@Column(name="id_refrence")*/
	private String idRefrence;

	private String ismustdone;

	/*@Column(name="kredit_amount")*/
	private BigDecimal kreditAmount;

	/*@Column(name="max_retry")*/
	private int maxRetry;

	private String priority;

	private int retrycount;

	private long timestamp;

	public UpdateBalance() {
	}

	public int getIdPos() {
		return this.idPos;
	}

	public void setIdPos(int idPos) {
		this.idPos = idPos;
	}

	public BigDecimal getBalance() {
		return this.balance;
	}

	public void setBalance(BigDecimal balance) {
		this.balance = balance;
	}

	public String getCreditAcount() {
		return this.creditAcount;
	}

	public void setCreditAcount(String creditAcount) {
		this.creditAcount = creditAcount;
	}

	public BigDecimal getDebAmount() {
		return this.debAmount;
	}

	public void setDebAmount(BigDecimal debAmount) {
		this.debAmount = debAmount;
	}

	public String getDebitAcount() {
		return this.debitAcount;
	}

	public void setDebitAcount(String debitAcount) {
		this.debitAcount = debitAcount;
	}

	public String getGroupname() {
		return this.groupname;
	}

	public void setGroupname(String groupname) {
		this.groupname = groupname;
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

	public long getTimestamp() {
		return this.timestamp;
	}

	public void setTimestamp(long timestamp) {
		this.timestamp = timestamp;
	}

}