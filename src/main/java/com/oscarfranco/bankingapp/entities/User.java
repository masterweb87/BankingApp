package com.oscarfranco.bankingapp.entities;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
public class User {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	
	private String firstName;
	private String lastName;
	
	@NotNull
	private int pin;
	
	@NotNull
	@Size(min=6, max=8, message="The ID the user will use to authenticate")
	private String cardId;
	
	@OneToOne(fetch=FetchType.LAZY)
	private Account account;
	
	protected User() {}
	
	public User(String firstName, String lastName, String cardId, int pin) {
		this.firstName = firstName;
		this.lastName = lastName;
		this.cardId = cardId;
		this.pin = pin;
	}

	public long getId() {
		return id;
	}
	
	public String getFirstName() {
		return firstName;
	}
	
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	
	public String getFullName() {
		return firstName + " " + lastName;
	}

	public int getPin() {
		return pin;
	}

	public void setPin(int pin) {
		this.pin = pin;
	}

	public String getCardId() {
		return cardId;
	}

	public void setCardId(String cardId) {
		this.cardId = cardId;
	}
	
	public long getAccountId() {
		return account.getId();
	}
	
	public void setAccount(Account account) {
		this.account = account;
	}

	@Override
	public String toString() {
		return "User [id=(" + id + "), name=" + firstName + " " + lastName + "]";
	}
}