package com.practikum.naumen.repo;

import com.practikum.naumen.models.Staff;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface StaffRepository extends JpaRepository<Staff, Long> {

    List<Staff> findAllByPositionOrderByIdDesc(String position);
    List<Staff> findAllByOrderByIdDesc();
    List<Staff> findAllByPosition(String filter);
}
