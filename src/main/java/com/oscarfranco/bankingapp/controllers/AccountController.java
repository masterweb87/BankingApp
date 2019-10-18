package com.oscarfranco.bankingapp.controllers;

import java.util.Date;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.oscarfranco.bankingapp.entities.Account;
import com.oscarfranco.bankingapp.entities.DepositForm;
import com.oscarfranco.bankingapp.entities.Transaction;
import com.oscarfranco.bankingapp.entities.Transaction.TransactionType;
import com.oscarfranco.bankingapp.entities.WithdrawForm;
import com.oscarfranco.bankingapp.repositories.AccountRepository;
import com.oscarfranco.bankingapp.repositories.TransactionRepository;

@Controller
public class AccountController {
	
	private long accountId;
	
	@Autowired
	private AccountRepository accounts;
	
	@Autowired
	private TransactionRepository transactions;
	
	@GetMapping("/accountStart")
    public String accountStart(Model model) {
		accountId = (long) model.getAttribute("accountId");
		
        return "redirect:/accounts";
    }
	
	@GetMapping("/accounts")
    public String userAccount(Model model) {
		Optional<Account> accountOpt = accounts.findById(accountId);
		Account account = accountOpt.get();
		
		model.addAttribute("balance", account.getBalance());
		model.addAttribute("userName", account.getOwner().getFullName());
		model.addAttribute("listTransactions", account.getLastTransactions());
        return "account";
    }
	
	@GetMapping("/withdraw")
    public String withdrawForm(Model model) {
		Optional<Account> accountOpt = accounts.findById(accountId);
		Account account = accountOpt.get();
		
		model.addAttribute("balance", account.getBalance());
		model.addAttribute("withdrawForm", new WithdrawForm());
        return "withdraw";
    }
	
	@GetMapping("/deposit")
    public String depositForm(Model model) {
		Optional<Account> accountOpt = accounts.findById(accountId);
		Account account = accountOpt.get();
		
		model.addAttribute("balance", account.getBalance());
		model.addAttribute("depositForm", new DepositForm());
        return "deposit";
    }
	
	@Transactional
	@PostMapping("/withdraw")
	public String withdrawPost(@ModelAttribute("withdrawForm") WithdrawForm withdrawForm, RedirectAttributes msg) {
		int amount = withdrawForm.getAmount();
		String description = withdrawForm.getDescription();
		
		if(amount <= 0) {
			msg.addFlashAttribute("no_withdraw", String.format("Withdrawal of $%s was not allowed. The amount is zero, negative or not a number", amount));
			return "redirect:/accounts";
		}
		
		Optional<Account> accountOpt = accounts.findById(accountId);
		Account account = accountOpt.get();
		int newAmount = account.getBalance();
		
		if(amount <= newAmount) {
			newAmount -= amount;
			account.setBalance(newAmount);
			accounts.save(account);
			
			saveTransaction(account, amount, TransactionType.WITHDRAW, description);
			
			msg.addFlashAttribute("success", String.format("A withdrawal operation of $%s was executed successfully", amount));
		}
		else {
			msg.addFlashAttribute("no_withdraw", String.format("Withdrawal of $%s was not allowed. Funds are insufficient", amount));
		}
		
		return "redirect:/accounts";
	}
	
	@Transactional
	@PostMapping("/deposit")
	public String depositPost(@ModelAttribute("depositForm") DepositForm depositForm, RedirectAttributes msg) {
		int amount = depositForm.getAmount();
		String description = depositForm.getDescription();
		
		if(amount <= 0) {
			msg.addFlashAttribute("no_deposit", String.format("Deposit of $%s was not allowed. The amount is zero or negative number", amount));
			return "redirect:/accounts";
		}
		
		Optional<Account> accountOpt = accounts.findById(accountId);
		Account account = accountOpt.get();
		
		int newAmount = account.getBalance();
		newAmount += amount;
		account.setBalance(newAmount);
		accounts.save(account);
		
		saveTransaction(account, amount, TransactionType.DEPOSIT, description);
		
		msg.addFlashAttribute("success", String.format("A deposit operation of $%s was executed successfully", amount));
		
		return "redirect:/accounts";
	}
	
	private void saveTransaction(Account account, int amount, TransactionType type, String description) {
		Transaction tx = new Transaction(new Date(), amount, type, account);
		tx.setDescription(description);
		
		transactions.save(tx);
	}
	
	/**
	@GetMapping("/accounts/{id}")
	public EntityModel<Account> retrieveAccount(@PathVariable long id) {
		Optional<Account> account = accounts.findById(id);
		EntityModel<Account> resource = new EntityModel<>(account.get());
		return resource;
	}*/
}
