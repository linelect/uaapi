package com.linelect.uaapi.repository;

import com.linelect.uaapi.model.Region;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RegionRepository extends JpaRepository<Region, Long> {
    List<Region> findByName(String name);
}
