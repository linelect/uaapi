package com.linelect.uaapi.controller;

        import com.linelect.uaapi.model.Region;
        import com.linelect.uaapi.repository.RegionRepository;
        import org.springframework.beans.factory.annotation.Autowired;
        import org.springframework.web.bind.annotation.*;

        import javax.servlet.http.HttpServletResponse;
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
    @ResponseBody
    public List<Region> getAll(HttpServletResponse response) {
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setHeader("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE");
        response.setHeader("Access-Control-Allow-Headers", "Content-Type");
//        response.setHeader("Access-Control-Allow-Credentials", true);
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
