package com.linelect.uaapi.controller;

import com.linelect.uaapi.model.Region;
import com.linelect.uaapi.repository.RegionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/regions")
public class RegionController {
    private RegionRepository regionRepository;

    @Autowired
    public RegionController(RegionRepository regionRepository) {
        this.regionRepository = regionRepository;
    }

    @GetMapping
    public List<Region> getAll() {
        return regionRepository.findAll();
    }

    @GetMapping("/{id}")
    public Region getById(Long id) {
        return regionRepository.findById(id).get();
    }

    @PostMapping
    public Region save(Region region) {
        return regionRepository.save(region);
    }

}
