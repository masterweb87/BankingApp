package com.oscarfranco.bankingapp.controllers;

import java.net.URI;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.oscarfranco.bankingapp.entities.Account;
import com.oscarfranco.bankingapp.repositories.AccountRepository;

@RestController
public class AccountRestController {
	
	@Autowired
	private AccountRepository accounts;
	
	@GetMapping("/accounts/{id}")
	public EntityModel<Account> retrieveAccount(@PathVariable long id) {
		Optional<Account> account = accounts.findById(id);
		EntityModel<Account> resource = new EntityModel<>(account.get());
		return resource;
	}
	
	@PostMapping("/accounts")
	public ResponseEntity<Object> updateAccount(@Valid @RequestBody Account account) {
		Optional<Account> accountUpdateOpt = accounts.findById(account.getId());
		EntityModel<Account> resource = new EntityModel<>(accountUpdateOpt.get());
		
		Account accountUpdate = resource.getContent();
		accountUpdate.setBalance(account.getBalance());
		accounts.save(accountUpdate);
		
		URI location = ServletUriComponentsBuilder
				.fromCurrentRequest()
				.path("/{id}")
				.buildAndExpand(accountUpdate.getId())
				.toUri();
		
		return ResponseEntity.created(location).build();
	}
}
