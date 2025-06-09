package com.example.bankapp.service;

import com.example.bankapp.model.Account;
import com.example.bankapp.repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TransactionService {

    @Autowired
    private AccountRepository accountRepository;

    public synchronized void transfer(Long fromAccountId, Long toAccountId, double amount) {
        Account from = accountRepository.findById(fromAccountId).orElseThrow();
        Account to = accountRepository.findById(toAccountId).orElseThrow();

        if (from.getBalance() < amount) {
            throw new IllegalArgumentException("Insufficient balance");
        }

        from.setBalance(from.getBalance() - amount);
        to.setBalance(to.getBalance() + amount);

        accountRepository.save(from);
        accountRepository.save(to);
    }
}
