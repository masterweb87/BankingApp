package com.oscarfranco.bankingapp.entities;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
public class Transaction {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	
	private Date date;
	private int amount;
	private String description;
	
	@Enumerated(EnumType.STRING)
	private TransactionType type;
	
	@ManyToOne(fetch=FetchType.LAZY)
	private Account account;
	
	public enum TransactionType {
		DEPOSIT,
		WITHDRAW,
		DEBIT_TRANSFER,
		CHECK
	}
	
	protected Transaction() {}
	
	public Transaction(Date date, int amount, TransactionType type, Account account) {
		this.date = date;
		this.amount = amount;
		this.type = type;
		this.account = account;
	}
	
	public long getId() {
		return id;
	}
	
	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public int getAmount() {
		return amount;
	}

	public void setAmount(int amount) {
		this.amount = amount;
	}

	public TransactionType getType() {
		return type;
	}

	public void setType(TransactionType type) {
		this.type = type;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Account getAccount() {
		return account;
	}

	public void setAccount(Account account) {
		this.account = account;
	}
}
