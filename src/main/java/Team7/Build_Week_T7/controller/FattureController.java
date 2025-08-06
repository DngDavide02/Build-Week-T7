package Team7.Build_Week_T7.controller;


import Team7.Build_Week_T7.entities.Fatture;
import Team7.Build_Week_T7.service.FattureService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/fatture")
public class FattureController {

    @Autowired
    private FattureService fattureService;


    @GetMapping
    @PreAuthorize("hasAnyAuthority('ADMIN', 'USER')")
    public List<Fatture> getAllFatture() {
        return fattureService.findAllFatture();
    }

}
