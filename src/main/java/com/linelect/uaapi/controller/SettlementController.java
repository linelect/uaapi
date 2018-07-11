package com.linelect.uaapi.controller;

import com.linelect.uaapi.model.Settlement;
import com.linelect.uaapi.repository.SettlementRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/settlement")
public class SettlementController {
    private SettlementRepository settlementRepository;

    @Autowired
    public SettlementController(SettlementRepository settlementRepository) {
        this.settlementRepository = settlementRepository;
    }

    @GetMapping
    public List<Settlement> getAll() {
        return settlementRepository.findAll();
    }

    @GetMapping("/{id}")
    public Settlement getById(Long id) {
        return settlementRepository.findById(id).get();
    }
}
