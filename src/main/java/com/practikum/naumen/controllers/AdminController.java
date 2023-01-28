package com.practikum.naumen.controllers;

import com.practikum.naumen.models.*;
import com.practikum.naumen.repo.*;
import com.practikum.naumen.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;
import javax.validation.Valid;

@Controller
public class AdminController {

    @Autowired
    private UserService userService;
    @Autowired
    private StaffRepository staffRepository;
    @Autowired
    private RequestRepository requestRepository;
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private AccountRepository accountRepository;

    /*----------- Класс для вывода должностей без дубликатов -----------*/

    private static Set<String> extractPositions (Collection<Staff> staffs) {
        return staffs.stream().map(Staff::getPosition).collect(Collectors.toSet());
    }

    /*----------- Выводим таблицу сотрудников -----------*/

    @GetMapping("/admin")
    public String admin( Model model) {
        Collection<Staff> staffs = staffRepository.findAllByOrderByIdDesc();
        model.addAttribute("positions", extractPositions(staffs));
        model.addAttribute("staffs", staffs);

        model.addAttribute("title", "Администратор");
        return "adminHTML/admin";
    }

    /*----------- Добавить нового сотрудника -----------*/

    @PostMapping("/admin")
    public String allStaff( @RequestParam String fname,
                            @RequestParam String lname,
                            @RequestParam String pname,
                            @RequestParam String position,
                            Model model) {
        Staff post = new Staff (fname,lname,pname,position);
        staffRepository.save(post);
        return "redirect:/admin";
    }

    /*----------- Значения из БД занесены в форму редактирования -----------*/

    @GetMapping("/admin/{id}/edit")
    public String staffEdit (@PathVariable(value = "id") long id, Model model) {
        if(!staffRepository.existsById (id)){
            return "redirect:/admin";
        }
        Collection<Staff> staffs = staffRepository.findAllByOrderByIdDesc();
        model.addAttribute("positions", extractPositions(staffs));
        Optional<Staff> post = staffRepository.findById(id);
        ArrayList<Staff> res = new ArrayList<>();
        post.ifPresent(res::add);
        model.addAttribute("post", res);
        return "adminHTML/staffEdit";
    }

    /*-----------Редактирование сотрудника и сохранение изменений-----------*/

    @PostMapping("/admin/{id}/edit")
    public String staffUpdate(@PathVariable(value = "id") long id,
                                 @RequestParam String fname,
                                 @RequestParam String lname,
                                 @RequestParam String pname,
                                 @RequestParam String position,
                                 Model model) throws Exception {
        Staff post = staffRepository.findById(id).orElseThrow(Exception::new);
        post.setFname(fname);
        post.setLname(lname);
        post.setPname(pname);
        post.setPosition(position);
        staffRepository.save(post);
        return "redirect:/admin";
    }

    /*----------- Удалить сотрудника -----------*/

    @PostMapping("/admin/{id}/remove")
    public String staffDelete(@PathVariable(value = "id") long id, Model model) throws Exception {
        Staff post = staffRepository.findById(id).orElseThrow(Exception::new);
        staffRepository.delete(post);
        return "redirect:/admin";
    }

   /*----------- Фильтр для поиска сотрудников по должности -----------*/

    @PostMapping("filter-staff")
    public String adminFilterStaff (@RequestParam String position, Model model) {
        Collection<Staff> staffs;
        if (position !=null && !position.isEmpty()){
            staffs = staffRepository.findAllByPositionOrderByIdDesc(position);
        } else {
            staffs = staffRepository.findAllByOrderByIdDesc();
        }
        model.addAttribute("staffs", staffs);
        model.addAttribute("positions", extractPositions(staffs));
        return "adminHTML/admin";
    }

    /*----------- Вывод всех заявок -----------*/

    @GetMapping("/admin-request")
    public String adminRequest(Model model) {
        Collection<Staff> staffs = staffRepository.findAllByOrderByIdDesc();
        model.addAttribute("positions", extractPositions(staffs));
        Iterable<Request> requests = requestRepository.findAllByOrderByIdDesc();
        model.addAttribute("requests", requests);
        return "adminHTML/request";
    }

    /*----------- Добавить новую заявку! -----------*/

    @PostMapping("/admin-request")
    public String addAdminRequest(  @RequestParam String level,
                                    @RequestParam String room,
                                    @RequestParam String fromWhom,
                                    @RequestParam String text,
                                    @RequestParam String toWhom,
                                    Model model) {
        Request request;

        if  (Objects.equals(room, "")) {
            request = new Request (level, "Не указано",fromWhom, text, toWhom,"Не выполнено","-", ZonedDateTime.now(ZoneId.of("Asia/Yekaterinburg")).format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm")), "-", "-");
        }else {
            request = new Request(level, room, fromWhom, text, toWhom, "Не выполнено", "-", ZonedDateTime.now(ZoneId.of("Asia/Yekaterinburg")).format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm")), "-", "-");
        }
        model.addAttribute("requests", requestRepository.findAllByOrderByIdDesc());
        requestRepository.save(request);
        return "redirect:/admin-request";
    }

    /*----------- Значения из Бд занесены в форму ответа -----------*/

    @GetMapping("/admin-request/{id}/reply")
    public String adminRequestReply (@PathVariable(value = "id") long id, Model model) {
        if(!requestRepository.existsById (id)){
            return "redirect:/admin-request";
        }
        Collection<Staff> staffs = staffRepository.findAllByOrderByIdDesc();
        model.addAttribute("positions", extractPositions(staffs));
        Optional<Request> post = requestRepository.findById(id);
        ArrayList<Request> res = new ArrayList<>();
        post.ifPresent(res::add);
        model.addAttribute("post", res);
        List<Staff> staff = (List<Staff>) staffRepository.findAll();
        model.addAttribute("staffs", staff);
        return "adminHTML/requestReply";
    }

    /*----------- Значения из Бд занесены в форму редактирования -----------*/

    @GetMapping("/admin-request/{id}/edit")
    public String adminRequestEdit (@PathVariable(value = "id") long id, Model model) {
        if(!requestRepository.existsById (id)){
            return "redirect:/admin-request";
        }
        Collection<Staff> staffs = staffRepository.findAllByOrderByIdDesc();
        model.addAttribute("positions", extractPositions(staffs));
        Optional<Request> post = requestRepository.findById(id);
        ArrayList<Request> res = new ArrayList<>();
        post.ifPresent(res::add);
        model.addAttribute("post", res);
        List<Staff> staff = (List<Staff>) staffRepository.findAll();
        model.addAttribute("staffs", staff);
        return "adminHTML/requestEdit";
    }

    /*-----------Ответить на заявку-----------*/

    @PostMapping("/admin-request/{id}/reply")
    public String adminRequestReply(@PathVariable(value = "id") long id,
                                    @RequestParam String level,
                                    @RequestParam String room,
                                    @RequestParam String fromWhom,
                                    @RequestParam String text,
                                    @RequestParam String toWhom,
                                    @RequestParam String status,
                                    @RequestParam String name,
                                    @RequestParam String comment,
                                   Model model) throws Exception {
        Request post = requestRepository.findById(id).orElseThrow(Exception::new);
        post.setLevel(level);
        post.setRoom(room);
        post.setFromWhom(fromWhom);
        post.setText(text);
        post.setToWhom(toWhom);
        post.setStatus(status);
        post.setName(name);
        if  (comment !=null && !comment.isEmpty()) {
            post.setComment(comment);
        }else {
            post.setComment("Комментариев не оставлено");}
        post.setEndDate(ZonedDateTime.now(ZoneId.of("Asia/Yekaterinburg")).format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm")));
        requestRepository.save(post);
        return "redirect:/admin-request";
    }

    /*-----------Редактирование заявки и сохранение изменений-----------*/

    @PostMapping("/admin-request/{id}/edit")
    public String adminRequestUpdate(@PathVariable(value = "id") long id,
                                     @RequestParam String level,
                                     @RequestParam String room,
                                     @RequestParam String fromWhom,
                                     @RequestParam String text,
                                     @RequestParam String toWhom,
                                     @RequestParam String status,
                                     @RequestParam String name,
                                     @RequestParam String comment,
                                     Model model) throws Exception {
        Request post = requestRepository.findById(id).orElseThrow(Exception::new);
        post.setLevel(level);
        post.setRoom(room);
        post.setFromWhom(fromWhom);
        post.setText(text);
        post.setToWhom(toWhom);
        post.setStatus(status);
        post.setName(name);
        post.setComment(comment);
        requestRepository.save(post);
        return "redirect:/admin-request";
    }

    /*-----------Удалить заявку-----------*/

    @PostMapping("/admin-request/{id}/remove")
    public String AllRequestDelete(@PathVariable(value = "id") long id, Model model) throws Exception {
        Request post = requestRepository.findById(id).orElseThrow(Exception::new);
        requestRepository.delete(post);
        return "redirect:/admin-request";
    }

    /*-----------Фильтр по заявкам, поиск по статусу и должности исполнителя-----------*/

    @PostMapping("filter-request")
    public String AllRequestAdminFilter (@RequestParam String filter, @RequestParam String toWhom, @RequestParam String fromWhom, Model model) {
        Iterable<Request> requests;
        Collection<Staff> staffs = staffRepository.findAll();

        /*----------- Показать по статусу, заказчику и исполнителю -----------*/
        if (filter !=null && !filter.isEmpty() && fromWhom !=null && !fromWhom.isEmpty() && toWhom !=null && !toWhom.isEmpty()){
            requests = requestRepository.findAllByStatusAndFromWhomAndToWhom(filter, fromWhom, toWhom);

            /*----------- Показать по статусу и заказчику -----------*/
        }else if (filter !=null && !filter.isEmpty() && fromWhom !=null && !fromWhom.isEmpty()){
            requests = requestRepository.findAllByStatusAndFromWhom(filter, fromWhom);

            /*----------- Показать по статусу и исполнителю -----------*/
        }else if (filter !=null && !filter.isEmpty() && toWhom !=null && !toWhom.isEmpty()){
            requests = requestRepository.findAllByStatusAndToWhomOrderByIdDesc(filter, toWhom);

            /*-----------Показать по исполнителю и заказчику-----------*/
        }else if (fromWhom !=null && !fromWhom.isEmpty() && toWhom !=null && !toWhom.isEmpty()){
            requests = requestRepository.findAllByFromWhomAndToWhom(fromWhom, toWhom);

            /*-----------Показать по статусу-----------*/
        }else if (filter !=null && !filter.isEmpty()){
            requests = requestRepository.findAllByStatusOrderByIdDesc(filter);

            /*-----------Показать по заказчику-----------*/
        }else if (fromWhom !=null && !fromWhom.isEmpty()) {
            requests = requestRepository.findAllByFromWhomOrderByIdDesc(fromWhom);

            /*-----------Показать исполнителю-----------*/
        } else if (toWhom !=null && !toWhom.isEmpty()) {
            requests = requestRepository.findAllByToWhomOrderByIdDesc(toWhom);

            /*-----------Показать все когда ничего не выбрано-----------*/
        }else {
            requests = requestRepository.findAllByOrderByIdDesc();
        }
        model.addAttribute("positions", extractPositions(staffs));
        model.addAttribute("staffs", staffs);
        model.addAttribute("requests", requests);
        return "adminHTML/request";
    }

    /*----------- Вывод доступный ролей, имеющихся аккаунтов -----------*/

    @GetMapping("/admin-account")
    public String adminAccount(Model model) {
        model.addAttribute("title", "Аккаунты");
        model.addAttribute("userForm", new Account());
        model.addAttribute("listRoles", roleRepository.findAll());
        model.addAttribute("allUsers", userService.allAccounts());
        return "adminHTML/account";
    }

    /*----------- Добавить новый аккаунт с проверкой на аналогичный логин -----------*/

    @PostMapping("/admin-account")
    public String addAccount(@ModelAttribute("userForm") @Valid Account userForm, BindingResult bindingResult, Model model) {

        if (bindingResult.hasErrors()) {
            model.addAttribute("listRoles", roleRepository.findAll());
            model.addAttribute("allUsers", userService.allAccounts());
            return "adminHTML/account";
        }
        if (!userService.saveUser(userForm)){
            model.addAttribute("usernameError", "Аккаунт с таким логином уже существует");
            model.addAttribute("listRoles", roleRepository.findAll());
            model.addAttribute("allUsers", userService.allAccounts());
            return "adminHTML/account";
        }
        return "redirect:/admin-account";
    }

    /*----------- Удалить аккаунт -----------*/

    @PostMapping("/admin-account/{id}/remove")
    public String adminAccountDelete(@PathVariable(value = "id") long id, Model model) throws Exception {
        Account post = accountRepository.findById(id).orElseThrow(Exception::new);
        accountRepository.delete(post);
        return "redirect:/admin-account";
    }

    /*----------- Вывод доступных ролей -----------*/

    @GetMapping("/admin-add-roles")
    public String adminRoles( Model model) {
        Collection<Role> roles = roleRepository.findAll();
        model.addAttribute("roles", roles);
        return "adminHTML/addRoles";
    }

    /*----------- Добавить новую роль -----------*/

    @PostMapping("/admin-add-roles")
    public String adminAddRoles(  @RequestParam long id,
                                  @RequestParam String name,
                                  @RequestParam String rusName,
                                  Model model) {
        Role post;

        if  (Objects.equals(id,"" )) {
            post = new Role ("3", name, rusName);
        }else {
            post = new Role (id, name, rusName);        }
        roleRepository.save(post);
        return "redirect:/admin-add-roles";
    }

    /*----------- Удалить роль -----------*/

    @PostMapping("/admin-add-roles/{id}/remove")
    public String adminRolesDelete(@PathVariable(value = "id") long id, Model model) throws Exception {
        Role post = roleRepository.findById(id).orElseThrow(Exception::new);
        roleRepository.delete(post);
        return "redirect:/admin-add-roles";
    }
}
