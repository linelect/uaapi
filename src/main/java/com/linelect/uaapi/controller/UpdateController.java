package com.linelect.uaapi.controller;

import com.linelect.uaapi.model.*;
import com.linelect.uaapi.repository.*;
import com.linelect.uaapi.service.HouseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@RestController
@RequestMapping("/update")
public class UpdateController {
    private RegionRepository regionRepository;
    private AreaRepository areaRepository;
    private SettlementRepository settlementRepository;
    private StreetRepository streetRepository;
    private HouseService houseService;

    @Autowired
    public UpdateController(RegionRepository regionRepository, AreaRepository areaRepository, SettlementRepository settlementRepository, StreetRepository streetRepository, HouseService houseService) {
        this.regionRepository = regionRepository;
        this.areaRepository = areaRepository;
        this.settlementRepository = settlementRepository;
        this.streetRepository = streetRepository;
        this.houseService = houseService;
    }

    private volatile Map<String, Map<String, List<String>>> saved = new HashMap<>();

    @GetMapping
    public String updateFromUkrPost() {

        List<String> lines = new ArrayList<>();
        try (Stream<String> stream = Files.lines(Paths.get("E://Projects//Cities API//houses//houses_en.csv"))) {
            lines = stream.collect(Collectors.toList());
        } catch (Exception e) {
            e.printStackTrace();

        }
        Region region = null;
        Area area = null;
        Settlement settlement = null;
        for (String line : lines) {
            String[] arrayOfSttributs = line.split(";");

            if (!arrayOfSttributs[3].matches("\\d+")) {
                continue; //skip first line with header
            }

            String regionName = arrayOfSttributs[0];
            if (saved.get(regionName) == null) {
                region = regionRepository.save(new Region(regionName));
                saved.put(regionName, new HashMap<>());
            }

            String areaName = arrayOfSttributs[1];
            if (saved.get(regionName).get(areaName) == null) {
                area = areaRepository.save(new Area(areaName, region));
                saved.get(regionName).put(areaName, new ArrayList<>());
            }

            String settlementName = arrayOfSttributs[2];
            if (!saved.get(regionName).get(areaName).contains(settlementName)) {
                settlement = settlementRepository.save(new Settlement(settlementName, area));
                saved.get(regionName).get(areaName).add(settlementName);
            }

            String streetName = arrayOfSttributs[4];
            Street street = streetRepository.save(new Street(settlement, streetName));

            int index = Integer.valueOf(arrayOfSttributs[3]);
            if (arrayOfSttributs.length == 6) {
                String houses = arrayOfSttributs[5];
                houseService.save(street.getId(), index, houses);
            }
        }

        return "Complete successful!";
    }

}
