package Team7.Build_Week_T7.controller;

import Team7.Build_Week_T7.entities.Clienti;
import Team7.Build_Week_T7.entities.TipoCliente;
import Team7.Build_Week_T7.exception.ValidationException;
import Team7.Build_Week_T7.payload.ClienteRespDTO;
import Team7.Build_Week_T7.payload.ClientiDTO;
import Team7.Build_Week_T7.payload.ClientiUpdateDTO;
import Team7.Build_Week_T7.payload.IndirizziDTO;
import Team7.Build_Week_T7.service.ClienteService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/clienti")
public class ClienteController {

    private final ClienteService clienteService;


    public ClienteController(ClienteService clienteService) {
        this.clienteService = clienteService;
    }

    @GetMapping
    public List<Clienti> getAllClienti() {
        return clienteService.getAllClienti();
    }

    @GetMapping("/{id}")
    public Clienti getClienteById(@PathVariable Long id){
        return clienteService.getClienteByID(id);
    }

    @GetMapping("/tipo/{tipo}")
    public List<Clienti> getClienteByTipo(@PathVariable TipoCliente tipoCliente) {
        return clienteService.getClienteByTipo(tipoCliente);
    }

    @PostMapping
    public ClienteRespDTO creaClienti(@RequestBody @Validated ClientiDTO dto, BindingResult validationResult) {
        if (validationResult.hasErrors()) {
            List<String> errors = validationResult.getFieldErrors().stream()
                    .map(fieldError -> fieldError.getDefaultMessage())
                    .toList();
            throw new ValidationException(errors);
        }
        Clienti clienti = clienteService.creaCliente(dto);
        return new ClienteRespDTO(clienti.getId());
    }


    @PutMapping("/{id}")
    public Clienti updateCliente(@PathVariable Long id, @RequestBody ClientiUpdateDTO dettagliCliente) {
        return clienteService.updateCliente(id, dettagliCliente);
    }

    @PostMapping("/{id}/upload-logo")
    public String uploadLogoCliente(@PathVariable Long id, @RequestParam("file")MultipartFile file) {
        return clienteService.uploadLogoCliente(id, file);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public void deleteById(@PathVariable Long id){
        clienteService.deleteById(id);
    }
}
