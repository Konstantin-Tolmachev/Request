package com.practikum.naumen.service;

import com.practikum.naumen.models.Account;
import com.practikum.naumen.repo.AccountRepository;
import com.practikum.naumen.repo.RoleRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.Optional;

@Service
public class UserService implements UserDetailsService {
    @PersistenceContext
    private EntityManager em;
    @Autowired
    AccountRepository accountRepository;
    @Autowired
    RoleRepository roleRepository;
    @Autowired
    BCryptPasswordEncoder bCryptPasswordEncoder;
    @Autowired
    org.springframework.security.crypto.password.PasswordEncoder PasswordEncoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        Account account = accountRepository.findByUsername(username);
        if (account == null) {
            throw new UsernameNotFoundException("User not found");
        }
        return account;
    }

    public Account findAccountById(Long accountId) {
        Optional<Account> userFromDb = accountRepository.findById(accountId);
        return userFromDb.orElse(new Account());
    }

    public List<Account> allAccounts() {
        return accountRepository.findAllByOrderByIdDesc();
    }

//    public boolean saveUser(Account account) {
//        Account userFromDB = accountRepository.findByUsername(account.getUsername());
//        if (userFromDB != null) {
//            return false;
//        }
//        account.setRoles(Collections.singleton(new Role(1L, "ROLE_ADMIN")));
//        account.setPassword(PasswordEncoder.encode(account.getPassword()));
//        accountRepository.save(account);
//        return true;
//    }

    public boolean saveUser(Account account) {
        Account userFromDB = accountRepository.findByUsername(account.getUsername());
        if (userFromDB != null) {
            return false;
        }
//        account.setRoles(Collections.singleton(new Role(1L, "ROLE_ADMIN","Администратор")));
        account.setPassword(bCryptPasswordEncoder.encode(account.getPassword()));
        accountRepository.save(account);
        return true;
    }
}

