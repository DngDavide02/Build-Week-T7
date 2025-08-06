package Team7.Build_Week_T7.controller;


import Team7.Build_Week_T7.entities.Fatture;
import Team7.Build_Week_T7.service.FattureService;
import jakarta.validation.Valid;
import org.hibernate.query.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'USER')")
    public ResponseEntity<Fatture> getFatturaById(@PathVariable Long id) {
        return fattureService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    @PreAuthorize("hasAuthority('ADMIN')")
    @ResponseStatus(HttpStatus.CREATED)
    public Fatture createFattura(@RequestBody @Valid Fatture fattura) {
        return fattureService.save(fattura);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<Fatture> updateFattura(
            @PathVariable Long id,
            @RequestBody @Valid Fatture fattura) {
        try {
            Fatture updatedFattura = fattureService.updateFattura(id, fattura);
            return ResponseEntity.ok(updatedFattura);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<Void> deleteFattura(@PathVariable Long id) {
        if (fattureService.existsById(id)) {
            fattureService.deleteById(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }


}
