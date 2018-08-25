package com.linelect.uaapi.repository;

import com.linelect.uaapi.model.House;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface HouseRepository extends JpaRepository<House, Long> {
    List<House> findAllByStreetId(long streetId);
    List<House> findAllByHouseNumberInAndStreet_Id(List<String> names, long streetId);
    House findByStreetIdAndPostIndex(long streetId, int postIndex);
}
