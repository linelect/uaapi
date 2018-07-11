package com.linelect.uaapi.repository;

import com.linelect.uaapi.model.Area;
import com.linelect.uaapi.model.Settlement;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SettlementRepository extends JpaRepository<Settlement, Long> {
    List<Settlement> findByNameAndArea(String name, Area area);
}
