package com.linelect.uaapi.repository;

import com.linelect.uaapi.model.House;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface HouseRepository extends JpaRepository<House, Long> {
    List<House> findAllByStreetId(long streetId);
    List<House> findAllByStreetIdAndPostIndex(long streetId, int postIndex);
}
