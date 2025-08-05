package Team7.Build_Week_T7.service;

import Team7.Build_Week_T7.entities.Clienti;
import Team7.Build_Week_T7.entities.TipoCliente;
import Team7.Build_Week_T7.exception.BadRequestException;
import Team7.Build_Week_T7.exception.NotFoundException;
import Team7.Build_Week_T7.payload.ClientiUpdateDTO;
import Team7.Build_Week_T7.repository.ClientiRepository;
import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;
import java.util.Optional;


@Service
public class ClienteService {

    private final ClientiRepository clientiRepository;
    private final Cloudinary cloudinary;


    public ClienteService(ClientiRepository clientiRepository, Cloudinary cloudinary) {
        this.clientiRepository = clientiRepository;
        this.cloudinary = cloudinary;
    }

    public List<Clienti> getAllClienti() {
        return clientiRepository.findAll();
    }

    public List<Clienti> getClienteByTipo(TipoCliente tipoCliente) {
        return clientiRepository.findByTipoCliente(tipoCliente);
    }

    public Clienti getClienteByID(Long id){
        return clientiRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(id));
    }

    public Clienti creaCliente (Clienti cliente) {
        if (clientiRepository.existsByPartitaIVA(cliente.getPartitaIVA())) {
            throw new BadRequestException("cliente con partita IVA gia esistente");
        }
        return clientiRepository.save(cliente);
    }

    public String uploadLogoCliente(Long clienteId, MultipartFile file) {
        try {
            Map<?, ?> result = cloudinary.uploader().upload(file.getBytes(), ObjectUtils.emptyMap());
            String imageURL = (String) result.get("secure_url");

            Clienti cliente = clientiRepository.findById(clienteId)
                    .orElseThrow(() -> new NotFoundException("Cliente non trovato con id: " + clienteId));
            cliente.setLogoAziendale(imageURL);
            clientiRepository.save(cliente);

            return imageURL;
        } catch (Exception e) {
            throw new BadRequestException("Errore durante il caricamento del logo: " + e.getMessage());
        }
    }


    public Clienti updateCliente (Long id, ClientiUpdateDTO dettagliCliente) {
        Clienti cliente = clientiRepository.findById(id).orElseThrow(() -> new NotFoundException("cliente con id: " + id + " non trovato"));

        if (dettagliCliente.ragioneSociale() != null)
            cliente.setRagioneSociale(dettagliCliente.ragioneSociale());

        if (dettagliCliente.partitaIVA() != null)
            cliente.setPartitaIVA(dettagliCliente.partitaIVA());

        if (dettagliCliente.email() != null)
            cliente.setEmail(dettagliCliente.email());

        if (dettagliCliente.dataUltimoContatto() != null)
            cliente.setDataUltimoContatto(dettagliCliente.dataUltimoContatto());

        if (dettagliCliente.fatturatoAnnuale() != 0)
            cliente.setFatturatoAnnuale(dettagliCliente.fatturatoAnnuale());

        if (dettagliCliente.pec() != null)
            cliente.setPec(dettagliCliente.pec());

        if (dettagliCliente.telefono() != null)
            cliente.setTelefono(dettagliCliente.telefono());

        if (dettagliCliente.emailContatto() != null)
            cliente.setEmailContatto(dettagliCliente.emailContatto());

        if (dettagliCliente.nomeContatto() != null)
            cliente.setNomeContatto(dettagliCliente.nomeContatto());

        if (dettagliCliente.cognomeContatto() != null)
            cliente.setCognomeContatto(dettagliCliente.cognomeContatto());

        if (dettagliCliente.telefonoContatto() != null)
            cliente.setTelefonoContatto(dettagliCliente.telefonoContatto());

        if (dettagliCliente.logoAziendale() != null)
            cliente.setLogoAziendale(dettagliCliente.logoAziendale());

        if (dettagliCliente.tipoCliente() != null)
            cliente.setTipoCliente(dettagliCliente.tipoCliente());

        return clientiRepository.save(cliente);
    }
}
