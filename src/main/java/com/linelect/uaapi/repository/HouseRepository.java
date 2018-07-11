package com.linelect.uaapi.repository;

import com.linelect.uaapi.model.House;
import com.linelect.uaapi.model.Street;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface HouseRepository extends JpaRepository<House, Long> {
}
