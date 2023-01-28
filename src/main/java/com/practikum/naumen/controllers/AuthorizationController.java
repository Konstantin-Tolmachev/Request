package com.practikum.naumen.controllers;

import com.practikum.naumen.models.Account;
import com.practikum.naumen.models.Request;
import com.practikum.naumen.models.Staff;

import com.practikum.naumen.repo.StaffRepository;
import com.practikum.naumen.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Collection;
import java.util.Objects;

@Controller
public class AuthorizationController {

    @Autowired
    private StaffRepository staffRepository;

    @Autowired
    private UserService userService;

    /*-----------Стартовая страница авторизации-----------*/

    @GetMapping("/")
    public String authorization( Model model) {
        model.addAttribute("title", "Авторизация");
        return "homeHTML/authorization";
    }





    /*----------- Выводим таблицу сотрудников -----------*/

    @GetMapping("/authorization")
    public String authorizations( Model model) {
        Collection<Staff> staffs = staffRepository.findAll();
        model.addAttribute("staffs", staffs);
        model.addAttribute("title", "Авторизация");
        return "homeHTML/authorizationTest";
    }

    /*----------- Добавить нового сотрудника -----------*/

    @PostMapping("/authorization")
    public String allStaff( @RequestParam String fname,
                            @RequestParam String lname,
                            Model model) {
        Staff post;
        if  (Objects.equals(lname, "")) {
            post = new Staff (fname,"Не указано", "Не указано","Не указано");
        }else {
            post = new Staff(fname, lname, "Не указано", "Не указано");
        }

        staffRepository.save(post);
        model.addAttribute("staffs", staffRepository.findAll());
        return "redirect:/hello";
    }

    @PostMapping("/authorization/{id}/remove")
    public String authorizationDelete(@PathVariable(value = "id") long id, Model model) throws Exception {
        Staff post = staffRepository.findById(id).orElseThrow(Exception::new);
        staffRepository.delete(post);
        return "redirect:/hello";
    }

    @GetMapping("/hello")
    public String hello ( Model model) {
        model.addAttribute("title", "Приветствие");
        return "homeHTML/hello";
    }


//    @GetMapping("/registration")
//    public String Registration(Model model) {
//        model.addAttribute("userForm", new Account());
//        return "homeHTML/registration";
//    }
//
//
//    @PostMapping("/registration")
//    public String addAccounte(@ModelAttribute("userForm") @Valid Account userForm, BindingResult bindingResult, Model model) {
//
//        if (bindingResult.hasErrors()) {
//            return "homeHTML/registration";
//        }
//        if (!userService.saveUser(userForm)){
//            model.addAttribute("usernameError", "Пользователь с таким именем уже существует");
//            return "homeHTML/registration";
//        }
//
//        return "redirect:/";
//    }
}
