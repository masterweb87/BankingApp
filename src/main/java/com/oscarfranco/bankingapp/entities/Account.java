package com.oscarfranco.bankingapp.entities;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class Account {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	
	@OneToOne(mappedBy="account")
	private User owner;
	
	private int balance;
	
	@JsonIgnore
	@OneToMany(mappedBy="account")
	private List<Transaction> lastTransactions;
	
	protected Account() {}
	
	public Account(int balance) {
		this.balance = balance;
	}
	
	public long getId() {
		return id;
	}
	
	public User getOwner() {
		return owner;
	}
	
	public void setOwner(User owner) {
		this.owner = owner;
	}

	public int getBalance() {
		return balance;
	}
	
	public void setBalance(int balance) {
		this.balance = balance;
	}

	public List<Transaction> getLastTransactions() {
		return lastTransactions;
	}

	public void setLastTransactions(List<Transaction> lastTransactions) {
		this.lastTransactions = lastTransactions;
	}
	
}
