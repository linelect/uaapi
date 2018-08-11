package com.linelect.uaapi.service;

import com.linelect.uaapi.model.House;
import com.linelect.uaapi.model.Street;
import com.linelect.uaapi.repository.HouseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class HouseServiceImpl implements HouseService {
    private HouseRepository houseRepository;
    private final String HOUSES_SEPARATOR = ",";

    @Autowired
    public HouseServiceImpl(HouseRepository houseRepository) {
        this.houseRepository = houseRepository;
    }

    @Override
    public List<String> getAllByStreetId(long streetId) {
        List<House> houses = houseRepository.findAllByStreetId(streetId);
        return houses.stream()
                .map(h -> Arrays.asList(h.getHouseNumbers().split(HOUSES_SEPARATOR)))
                .flatMap(List::stream)
                .collect(Collectors.toList());
    }

    @Override
    public List<String> getAllByStreetIdAndPostIndex(long streetId, int postIndex) {
        List<House> allByStreetIdAndPostIndex = houseRepository.findAllByStreetIdAndPostIndex(streetId, postIndex);
        return allByStreetIdAndPostIndex.stream()
                .map(h -> Arrays.asList(h.getHouseNumbers().split(HOUSES_SEPARATOR)))
                .flatMap(List::stream)
                .collect(Collectors.toList());
    }

    @Override
    public House save(long streetId, int postIndex, String houseNumbers) {
        List<String> allHouses = getAllByStreetIdAndPostIndex(streetId, postIndex);
        List<String> addHouses = Arrays.asList(houseNumbers.split(HOUSES_SEPARATOR));
        allHouses.removeAll(addHouses);
        allHouses.addAll(addHouses);

        return houseRepository.save(
                new House(allHouses.stream().collect(Collectors.joining(HOUSES_SEPARATOR)),
                postIndex,
                new Street(streetId))
        );
    }
}
