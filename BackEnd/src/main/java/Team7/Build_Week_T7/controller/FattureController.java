package Team7.Build_Week_T7.controller;


import Team7.Build_Week_T7.entities.Fatture;
import Team7.Build_Week_T7.payload.FattureDTO;
import Team7.Build_Week_T7.payload.FattureUpdateDTO;
import Team7.Build_Week_T7.service.FattureService;
import Team7.Build_Week_T7.exception.ValidationException;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;


import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/fatture")
public class FattureController {

    @Autowired
    private FattureService fattureService;


    @GetMapping
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

    @PostMapping("/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    @ResponseStatus(HttpStatus.CREATED)
    public Fatture createFattura(@PathVariable Long id,
                                 @RequestBody @Valid FattureDTO fattureDTO,
                                 BindingResult validationResult) {
        if (validationResult.hasErrors()) {
            List<String> errors = validationResult.getFieldErrors().stream()
                    .map(fieldError -> fieldError.getDefaultMessage())
                    .toList();
            throw new ValidationException(errors);
        }
        return fattureService.save(fattureDTO,id);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public Fatture updateFattura(@PathVariable Long id,
                                 @RequestBody @Validated FattureUpdateDTO updateDTO,
                                 BindingResult validationResult) {
        if (validationResult.hasErrors()) {
            List<String> errors = validationResult.getFieldErrors().stream()
                    .map(fieldError -> fieldError.getDefaultMessage())
                    .toList();
            throw new ValidationException(errors);
        }
        return fattureService.findByIdAndUpdate(id, updateDTO);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PreAuthorize("hasAuthority('ADMIN')")
    public void deleteFattura(@PathVariable Long id) {
        fattureService.findByIdAndDelete(id);
    }

    @GetMapping("/searchFatture")
    public List<Fatture> searchFatture(
            @RequestParam(required = false) String cliente,
            @RequestParam(required = false) String stato,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate data,
            @RequestParam(required = false) Integer anno,
            @RequestParam(required = false) Integer importoMin,
            @RequestParam(required = false) Integer importoMax,
            @RequestParam(required = false) String sortBy
    ) {
        return fattureService.searchFatture(cliente, stato, data, anno, importoMin, importoMax, sortBy);
    }

}
