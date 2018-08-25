package com.linelect.uaapi.service;

import com.linelect.uaapi.model.House;

import java.util.List;

public interface HouseService {
    List<House> getAllByStreetId(long streetId);
    House getByStreetIdAndPostIndex(long streetId, int postIndex);
    House save(House house);
    void saveAll(List<House> houses);
    List<House> getAllByNamesAndStreetId(List<String> names, long streetId);
}
