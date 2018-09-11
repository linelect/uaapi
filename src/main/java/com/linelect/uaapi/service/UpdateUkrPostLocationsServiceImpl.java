package com.linelect.uaapi.service;

import com.linelect.uaapi.model.*;
import com.linelect.uaapi.repository.AreaRepository;
import com.linelect.uaapi.repository.RegionRepository;
import com.linelect.uaapi.repository.SettlementRepository;
import com.linelect.uaapi.repository.StreetRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.*;
import java.util.stream.Collectors;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

@Service
public class UpdateUkrPostLocationsServiceImpl implements UpdateLocationsService {

    private final Logger LOGGER = LoggerFactory.getLogger(UpdateUkrPostLocationsServiceImpl.class);
    private static final String ZIP_FILE_URL = "http://services.ukrposhta.com/postindex_new/upload/houses.zip";
    private static final String FILE_NAME_IN_ZIP = "houses_en.csv";
    private static final String FILE_ENCODING = "cp1251";
    private volatile Map<String, Map<String, List<String>>> saved = new HashMap<>();
    private volatile Map<Settlement, List<String>> savedStreets = new HashMap<>();

    private RegionRepository regionRepository;
    private AreaRepository areaRepository;
    private SettlementRepository settlementRepository;
    private StreetRepository streetRepository;
    private HouseService houseService;

    @Autowired
    public UpdateUkrPostLocationsServiceImpl(RegionRepository regionRepository, AreaRepository areaRepository,
                                             SettlementRepository settlementRepository, StreetRepository streetRepository,
                                             HouseService houseService) {
        this.regionRepository = regionRepository;
        this.areaRepository = areaRepository;
        this.settlementRepository = settlementRepository;
        this.streetRepository = streetRepository;
        this.houseService = houseService;
    }

    @Override
    public void update() {
        LOGGER.info("Start update locations from UkrPost.");

        List<String> lines = new ArrayList<>();
//        try (Stream<String> stream = Files.lines(Paths.get("D:\\Projects\\Cities API\\houses\\houses_en.csv"))) {
//            lines = stream.collect(Collectors.toList());
//        } catch (Exception e) {
//            LOGGER.error(e.getMessage());
//        }
        lines = getLinesFromRemoteZipFile();

        Region region = null;
        Area area = null;
        Settlement settlement = null;
        Street street = null;
        int lineNumber = 0;

        for (String line : lines) {
            lineNumber++;
            String[] arrayOfSttributs = line.split(";");

            if (arrayOfSttributs.length > 3 && !arrayOfSttributs[3].matches("\\d+")) {
                LOGGER.debug("Skipped line number: {}", lineNumber);
                continue; //skip first line with header
            }

            String regionName = arrayOfSttributs[0];
            if (saved.get(regionName) == null) {
                region = regionRepository.save(new Region(regionName));
                saved.put(regionName, new HashMap<>());
            }

            String areaName = arrayOfSttributs[1];
            String settlementName = arrayOfSttributs[2];
            areaName = "".equals(areaName) ? settlementName : areaName;
            if (saved.get(regionName).get(areaName) == null) {
                area = areaRepository.save(new Area(areaName, region));
                saved.get(regionName).put(areaName, new ArrayList<>());
            }

            if (!saved.get(regionName).get(areaName).contains(settlementName)) {
                settlement = settlementRepository.save(new Settlement(settlementName, area));
                saved.get(regionName).get(areaName).add(settlementName);
                savedStreets.put(settlement, new ArrayList<>());
            }

            String streetName = arrayOfSttributs[4];
            if (!savedStreets.get(settlement).contains(streetName)) {
                street = streetRepository.save(new Street(settlement, streetName));
                savedStreets.get(settlement).add(streetName);
            }

            int index = Integer.valueOf(arrayOfSttributs[3]);
            List<House> houses = new ArrayList<>();
            if (arrayOfSttributs.length == 6 && street != null) {
                String housesString = arrayOfSttributs[5];
                String[] houseNumbersArray = housesString.split(",");
                List<House> existingHouses = houseService.getAllByNamesAndStreetId(Arrays.asList(houseNumbersArray), street.getId());
                List<String> existingHouseNumbers = existingHouses.stream()
                        .map(House::getHouseNumber)
                        .collect(Collectors.toList());
                for (String h : houseNumbersArray) {
                    if (!existingHouseNumbers.contains(h)) {
                        houses.add(new House(h.trim(), Integer.valueOf(arrayOfSttributs[3]), street));
                    }
                }
                houseService.saveAll(houses);
            }
        }
        LOGGER.info("Update locations from UkrPost successfully completed.");
    }

    private List<String> getLinesFromRemoteZipFile() {
        List<String> lines = new ArrayList<>();
        try {
            URL url = new URL(ZIP_FILE_URL);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            InputStream in = connection.getInputStream();
            ZipInputStream zipIn = new ZipInputStream(in);
            ZipEntry entry = zipIn.getNextEntry();
            while (entry != null) {
                if (!entry.isDirectory() && FILE_NAME_IN_ZIP.equals(entry.getName())) {
                    Scanner sc = new Scanner(zipIn, FILE_ENCODING);
                    while(sc.hasNextLine()) {
                        lines.add(sc.nextLine());
                    }
                }
                zipIn.closeEntry();
                entry = zipIn.getNextEntry();
            }
        } catch (IOException e) {
            LOGGER.error("Error while reading file from UkrPost site.", e);
        }
        return lines;
    }
}
