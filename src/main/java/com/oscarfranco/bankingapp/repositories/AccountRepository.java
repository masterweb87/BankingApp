package com.oscarfranco.bankingapp.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.oscarfranco.bankingapp.entities.Account;

@Repository
public interface AccountRepository extends JpaRepository<Account, Long> {

}
