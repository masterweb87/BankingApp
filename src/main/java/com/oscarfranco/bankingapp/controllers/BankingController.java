package com.oscarfranco.bankingapp.controllers;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.oscarfranco.bankingapp.entities.User;
import com.oscarfranco.bankingapp.entities.UserForm;
import com.oscarfranco.bankingapp.repositories.UserRepository;

@Controller
public class BankingController {

	@Autowired
	private UserRepository users;
	
	@GetMapping("/")
    public String homePage(Model model) {
		model.addAttribute("userForm", new UserForm());
        return "index";
    }
	
	@PostMapping("/auth")
    public String login(@ModelAttribute("userForm") UserForm userForm, RedirectAttributes accounts) {
		String cardId = userForm.getCardId();
		int pin = userForm.getPin();
		
		Optional<User> user = users.findByCardId(cardId);
		if(user == null || user.isEmpty()) {
			return "redirect:/";
		}
		
		if(user.get().getPin() == pin) {
			accounts.addFlashAttribute("accountId", user.get().getAccountId());
			return "redirect:/accountStart";
		}
		return "redirect:/";
    }
	
	@GetMapping("/logout")
    public String logout() {
        return "redirect:/";
    }
}
