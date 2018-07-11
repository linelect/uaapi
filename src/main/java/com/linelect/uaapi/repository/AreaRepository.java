package com.linelect.uaapi.repository;

import com.linelect.uaapi.model.Area;
import com.linelect.uaapi.model.Region;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AreaRepository extends JpaRepository<Area, Long> {
    List<Area> findByNameAndRegion(String name, Region region);
}
