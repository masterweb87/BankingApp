package com.oscarfranco.bankingapp.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.oscarfranco.bankingapp.entities.Account;
import com.oscarfranco.bankingapp.entities.NewUserForm;
import com.oscarfranco.bankingapp.entities.User;
import com.oscarfranco.bankingapp.repositories.AccountRepository;
import com.oscarfranco.bankingapp.repositories.UserRepository;

@Controller
public class UserController {
	
	private static final int BALANCE_DEFAULT = 100;
	@Autowired
	private AccountRepository accounts;
	
	@Autowired
	private UserRepository users;
	
	@GetMapping("/register")
    public String registerPage(Model model) {
		model.addAttribute("newUserForm", new NewUserForm());
        return "register";
    }
	
	@Transactional
	@PostMapping("/register")
	public String withdrawPost(@ModelAttribute("newUserForm") NewUserForm newUserForm, RedirectAttributes msg) {
		String firstName = newUserForm.getFirstName();
		String lastName = newUserForm.getLastName();
		String cardId = newUserForm.getCardId();
		int pin = newUserForm.getPin();
		
		if(!firstName.isEmpty() && !lastName.isEmpty() && (cardId.length() >= 6 && cardId.length() <= 8)) {
			Account newAccount = new Account(BALANCE_DEFAULT);
			Account accountCreated = accounts.save(newAccount);
			
			User newUser = new User(firstName, lastName, cardId, pin);
			newUser.setAccount(accountCreated);
			users.save(newUser);
			
			msg.addFlashAttribute("accountId", accountCreated.getId());
			return "redirect:/accountStart";
		}
		
		return "redirect:/";
	}
}
