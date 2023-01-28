package com.practikum.naumen.controllers;

import com.practikum.naumen.models.Request;
import com.practikum.naumen.models.Staff;
import com.practikum.naumen.repo.RequestRepository;
import com.practikum.naumen.repo.StaffRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

@Controller
public class StaffController {

    @Autowired
    private RequestRepository requestRepository;
    @Autowired
    private StaffRepository staffRepository;

    /*----------- Класс для вывода должностей без дубликатов -----------*/

    private static Set<String> extractPositions (Collection<Staff> staffs) {
        return staffs.stream().map(Staff::getPosition).collect(Collectors.toSet());
    }

    /*----------- Вывод всех заявок -----------*/

    @GetMapping("/staff-request")
    public String staffRequest(Model model) {
        Collection<Request> requests = requestRepository.findAllByStatusWhere();
        model.addAttribute("requests", requests);
        Collection<Staff> staffs = staffRepository.findAllByOrderByIdDesc();
        model.addAttribute("positions", extractPositions(staffs));
        return "staffHTML/request";
    }

    /*----------- Добавить новую заявку -----------*/

    @PostMapping("/staff-request")
    public String addStaffRequest(@RequestParam String level,
                                  @RequestParam String room,
                                  @RequestParam String fromWhom,
                                  @RequestParam String text,
                                  @RequestParam String toWhom,
                                  Model model) {
        Request request;
        if  (Objects.equals(room, "")) {
            request = new Request (level, "Не указано",fromWhom, text, toWhom,"Не выполнено","-", ZonedDateTime.now(ZoneId.of("Asia/Yekaterinburg")).format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm")), "-", "-");
        }else {
            request = new Request(level, room, fromWhom, text, toWhom, "Не выполнено","-", ZonedDateTime.now(ZoneId.of("Asia/Yekaterinburg")).format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm")), "-", "-");
        }
        model.addAttribute("requests", requestRepository.findAllByOrderByIdDesc());
        requestRepository.save(request);
        return "redirect:/staff-request";
    }

    /*----------- Значения из Бд занесены в форму редактирования -----------*/

    @GetMapping("/staff-request/{id}/reply")
    public String staffRequest (@PathVariable(value = "id") long id, Model model) {
        if(!requestRepository.existsById (id)){
            return "redirect:/staff-request";
        }
        Collection<Staff> staffs = staffRepository.findAllByOrderByIdDesc();
        model.addAttribute("positions", extractPositions(staffs));
        Optional<Request> post = requestRepository.findById(id);
        ArrayList<Request> res = new ArrayList<>();
        post.ifPresent(res::add);
        model.addAttribute("post", res);
        List<Staff> staff = (List<Staff>) staffRepository.findAll();
        model.addAttribute("staffs", staff);
        return "staffHTML/requestReply";
    }

    /*-----------Редактирование заявки и сохранение изменений-----------*/

    @PostMapping("/staff-request/{id}/reply")
    public String staffRequestUpdate(@PathVariable(value = "id") long id,
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
        return "redirect:/staff-request";
    }

    /*-----------Фильтр по заявкам, поиск по статусу-----------*/

    @PostMapping("staff-filter-request")
    public String staffRequestFilter (@RequestParam String filter, @RequestParam String toWhom, Model model) {
        Iterable<Request> requests;
        if (filter !=null && !filter.isEmpty()){
            requests = requestRepository.findAllByStatusAndToWhomOrderByIdDesc(filter, toWhom);
        }else {
            return "redirect:/staff-request";
        }
        model.addAttribute("requests", requests);
        return "staffHTML/request";
    }

    /*-----------Вывод статус заявок-----------*/

    @GetMapping("/staff-status")
    public String staffStatusRequest( Model model) {
        Collection<Staff> staffs = staffRepository.findAllByOrderByIdDesc();
        model.addAttribute("positions", extractPositions(staffs));
        Iterable<Request> requests = requestRepository.findAllByOrderByIdDesc();
        model.addAttribute("requests", requests);
        return "staffHTML/requestStatus";
    }

    /*-----------Фильтр по заявкам, поиск по статусу и должности завителя-----------*/
    @PostMapping("staff-filter-request-from-whom")
    public String AllRequestAdminFilterFromWhom (@RequestParam String filter, @RequestParam String toWhom, Model model) {
        Iterable<Request> requests;
        Collection<Staff> staffs = staffRepository.findAll();

        if (filter !=null && !filter.isEmpty() && toWhom !=null && !toWhom.isEmpty()){
            requests = requestRepository.findAllByStatusAndToWhomOrderByIdDesc(filter, toWhom);
        }else if (filter !=null && !filter.isEmpty()){
            requests = requestRepository.findAllByStatusOrderByIdDesc(filter);
        }else if (toWhom !=null && !toWhom.isEmpty()) {
            requests = requestRepository.findAllByToWhomOrderByIdDesc(toWhom);
        }else {
            requests = requestRepository.findAllByOrderByIdDesc();
        }
        model.addAttribute("positions", extractPositions(staffs));
        model.addAttribute("staffs", staffs);
        model.addAttribute("requests", requests);
        return "staffHTML/requestStatus";
    }
}
