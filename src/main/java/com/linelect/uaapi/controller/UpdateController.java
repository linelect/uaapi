package com.linelect.uaapi.controller;

import com.linelect.uaapi.service.UpdateUkrPostLocationsServiceImpl;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/update")
public class UpdateController {
    private UpdateUkrPostLocationsServiceImpl updateUkrPostLocationsService;

    public UpdateController(UpdateUkrPostLocationsServiceImpl updateUkrPostLocationsService) {
        this.updateUkrPostLocationsService = updateUkrPostLocationsService;
    }

    @GetMapping
    public String updateFromUkrPost() {
        updateUkrPostLocationsService.update();
        return "Done!";
    }

}
