package com.practikum.naumen.service;

import com.practikum.naumen.models.Account;
import org.springframework.stereotype.Service;

@Service
public class UserValidationService {

    /*----------- Сообщение если аккаунт существует при создании нового аккаунта -----------*/

    public String validateUser(Account userForm) {
        String message = "";
        return message;
    }
}
