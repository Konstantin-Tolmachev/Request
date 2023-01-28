package com.practikum.naumen.repo;

import com.practikum.naumen.models.Request;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;

@Repository
public interface RequestRepository extends JpaRepository<Request, Long> {

   /*-----------Показать заявки с статусом "Не выполнено" и "На выполнении" у сотрудников - staffHTML/request-----------*/

    @Query("SELECT u FROM Request u WHERE  u.status = 'Не выполнено' OR u.status = 'На выполнении'  ORDER BY u.id DESC")
    Collection<Request> findAllByStatusWhere();

    /*-----------Фильтры - adminHTML/request-----------*/

    List<Request> findAllByStatusAndFromWhomAndToWhom(String status, String fromWhom, String toWhom);   // filter-request-from-whom
    List<Request> findAllByStatusAndFromWhom(String status, String fromWhom);   // filter-request-from-whom
    List<Request> findAllByStatusAndToWhomOrderByIdDesc(String status, String toWhom);   // filter-request-from-whom // filter-request
    List<Request> findAllByFromWhomAndToWhom(String fromWhom, String toWhom);   // filter-request-from-whom
    List<Request> findAllByToWhomOrderByIdDesc(String toWhom);              // filter-request-from-whom // filter-request
    List<Request> findAllByFromWhomOrderByIdDesc(String fromWhom);              // filter-request-from-whom
    List<Request> findAllByStatusOrderByIdDesc(String filter);                  // filter-request and filter-request-from-whom
    List<Request> findAllByOrderByIdDesc();
}
