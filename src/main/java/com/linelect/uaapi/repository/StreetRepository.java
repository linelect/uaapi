package com.linelect.uaapi.repository;

import com.linelect.uaapi.model.Settlement;
import com.linelect.uaapi.model.Street;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface StreetRepository extends JpaRepository<Street, Long> {
    List<Street> findByNameAndSettlement(String name, Settlement settlement);
}
