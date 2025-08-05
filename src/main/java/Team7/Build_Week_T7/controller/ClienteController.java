package Team7.Build_Week_T7.controller;

import Team7.Build_Week_T7.entities.Clienti;
import Team7.Build_Week_T7.entities.TipoCliente;
import Team7.Build_Week_T7.payload.ClientiUpdateDTO;
import Team7.Build_Week_T7.service.ClienteService;
import org.springframework.http.ResponseEntity;
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
    public Clienti creaClienti (@RequestBody Clienti cliente) {
        return clienteService.creaCliente(cliente);
    }

    @PutMapping("/{id}")
    public Clienti updateCliente(@PathVariable Long id, @RequestBody ClientiUpdateDTO dettagliCliente) {
        return clienteService.updateCliente(id, dettagliCliente);
    }

    @PostMapping("/{id}/upload-logo")
    public String uploadLogoCliente(@PathVariable Long id, @RequestParam("file")MultipartFile file) {
        return clienteService.uploadLogoCliente(id, file);
    }
}
