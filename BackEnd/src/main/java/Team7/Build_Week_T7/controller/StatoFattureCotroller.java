package Team7.Build_Week_T7.controller;

import Team7.Build_Week_T7.entities.StatoFatture;
import Team7.Build_Week_T7.payload.StatoFattureDTO;
import Team7.Build_Week_T7.service.StatoFatturaService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/statiFatture")
public class StatoFattureCotroller {

    @Autowired
    private StatoFatturaService statoFatturaService;

    @PostMapping

    public ResponseEntity<StatoFatture> creaStato(@RequestBody @Valid StatoFattureDTO dto){
        try {
            StatoFatture nuovoStato= statoFatturaService.creaNuovoStato(dto);
            return ResponseEntity.status(HttpStatus.CREATED).body(nuovoStato);
        } catch (IllegalArgumentException e){
            return ResponseEntity.badRequest().body(null);
        }
    }
    @PutMapping("/{id}")

    public ResponseEntity<StatoFatture> modificaStato(
            @PathVariable Long id,
            @RequestBody @Valid StatoFattureDTO dto) {
        try {
            StatoFatture stato = statoFatturaService.aggiornaStato(id, dto);
            return ResponseEntity.ok(stato);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }
    @DeleteMapping("/{id}")

    public ResponseEntity<Void> disattivaStato(@PathVariable Long id) {
        try {
            statoFatturaService.disattivaStato(id);
            return ResponseEntity.noContent().build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/tutti")
    public ResponseEntity<List<StatoFatture>> getAllStati() {
        return ResponseEntity.ok(statoFatturaService.getAllStati());
    }


}
