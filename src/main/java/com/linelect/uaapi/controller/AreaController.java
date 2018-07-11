package com.linelect.uaapi.controller;

import com.linelect.uaapi.model.Area;
import com.linelect.uaapi.repository.AreaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/areas")
public class AreaController {
    private AreaRepository areaRepository;

    @Autowired
    public AreaController(AreaRepository areaRepository) {
        this.areaRepository = areaRepository;
    }

    @GetMapping
    public List<Area> getAll() {
        return areaRepository.findAll();
    }

    @GetMapping("/{id}")
    public Area getAreaById(Long id) {
        return areaRepository.findById(id).get();
    }

    @PostMapping
    public Area saveArea(Area area) {
        return areaRepository.save(area);
    }
}
