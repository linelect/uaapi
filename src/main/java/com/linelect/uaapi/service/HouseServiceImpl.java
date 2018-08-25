package com.linelect.uaapi.service;

import com.linelect.uaapi.model.House;
import com.linelect.uaapi.model.Street;
import com.linelect.uaapi.repository.HouseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class HouseServiceImpl implements HouseService {
    private HouseRepository houseRepository;

    @Autowired
    public HouseServiceImpl(HouseRepository houseRepository) {
        this.houseRepository = houseRepository;
    }

    @Override
    public List<House> getAllByStreetId(long streetId) {
        return houseRepository.findAllByStreetId(streetId);
    }

    @Override
    public House getByStreetIdAndPostIndex(long streetId, int postIndex) {
        return houseRepository.findByStreetIdAndPostIndex(streetId, postIndex);
    }

    @Override
    public House save(House house) {
        return houseRepository.save(house);
    }

    @Override
    public void saveAll(List<House> houses) {
        houseRepository.saveAll(houses);
    }

    @Override
    public List<House> getAllByNamesAndStreetId(List<String> names, long streetId) {
        return houseRepository.findAllByHouseNumberInAndStreet_Id(names, streetId);
    }
}
