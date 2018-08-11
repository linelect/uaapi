package com.linelect.uaapi.service;

import com.linelect.uaapi.model.House;

import java.util.List;

public interface HouseService {
    List<String> getAllByStreetId(long streetId);
    List<String> getAllByStreetIdAndPostIndex(long streetId, int postIndex);
    House save(long streetId, int postIndex, String houseNumbers);
}
