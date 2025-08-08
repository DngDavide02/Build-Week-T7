package Team7.Build_Week_T7.service;

import Team7.Build_Week_T7.entities.Clienti;
import Team7.Build_Week_T7.entities.Indirizzi;
import Team7.Build_Week_T7.entities.TipoCliente;
import Team7.Build_Week_T7.exception.BadRequestException;
import Team7.Build_Week_T7.exception.NotFoundException;
import Team7.Build_Week_T7.payload.ClientiDTO;
import Team7.Build_Week_T7.payload.ClientiUpdateDTO;
import Team7.Build_Week_T7.repository.ClientiRepository;
import Team7.Build_Week_T7.repository.ComuneRepository;
import Team7.Build_Week_T7.repository.IndirizziRepository;
import Team7.Build_Week_T7.specification.ClientiSpec;
import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;


@Service
public class ClienteService {

    @Autowired
    private ClientiRepository clientiRepository;

    @Autowired
    private Cloudinary cloudinary;

    @Autowired
    private ComuneRepository comuneRepository;

    @Autowired
    private IndirizziService indirizziService;

    @Autowired
    private ComuneService comuneService;

    @Autowired
    private IndirizziRepository indirizziRepository;


    public ClienteService(ClientiRepository clientiRepository, Cloudinary cloudinary, ComuneRepository comuneRepository) {
        this.clientiRepository = clientiRepository;
        this.cloudinary = cloudinary;
        this.comuneRepository = comuneRepository;
    }

    public List<Clienti> getAllClienti() {
        return clientiRepository.findAll();
    }

    public List<Clienti> getClienteByTipo(TipoCliente tipoCliente) {
        return clientiRepository.findByTipoCliente(tipoCliente);
    }

    public Clienti getClienteByID(Long id) {
        return clientiRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(id));
    }

    public Clienti creaCliente(ClientiDTO dto) {
        if (clientiRepository.existsByPartitaIVA(dto.partitaIVA())) {
            throw new BadRequestException("cliente con partita IVA già esistente");
        }
        try {
            Indirizzi newIndirizzi = new Indirizzi(dto.via(), dto.civico(), dto.localita(), dto.cap(), comuneService.findById(dto.comuneId()));
            Indirizzi savedIndirizzi = this.indirizziRepository.save(newIndirizzi);


            Clienti cliente = new Clienti(
                    dto.ragioneSociale(),
                    dto.partitaIVA(),
                    dto.email(),
                    LocalDate.now(),
                    dto.dataUltimoContatto(),
                    dto.fatturatoAnnuale(),
                    dto.pec(),
                    dto.telefono(),
                    dto.emailContatto(),
                    dto.nomeContatto(),
                    dto.cognomeContatto(),
                    dto.telefonoContatto(),
                    dto.tipoCliente(),
                    newIndirizzi,
                    newIndirizzi
            );
            return clientiRepository.save(cliente);
        } catch (Exception e) {
            throw new BadRequestException("formato inserito non corretto");
        }
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


    public Clienti updateCliente(Long id, ClientiUpdateDTO dettagliCliente) {
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
            cliente.setNome(dettagliCliente.nomeContatto());

        if (dettagliCliente.cognomeContatto() != null)
            cliente.setCognome(dettagliCliente.cognomeContatto());

        if (dettagliCliente.telefonoContatto() != null)
            cliente.setTelefonoContatto(dettagliCliente.telefonoContatto());

        if (dettagliCliente.logoAziendale() != null)
            cliente.setLogoAziendale(dettagliCliente.logoAziendale());

        if (dettagliCliente.tipoCliente() != null)
            cliente.setTipoCliente(dettagliCliente.tipoCliente());

        return clientiRepository.save(cliente);
    }

    public void deleteById(Long id) {
        clientiRepository.deleteById(id);
    }

    public List<Clienti> getClienti(
            Integer max,
            Integer min,
            LocalDate dataInserimento,
            LocalDate dataUltimoContratto,
            String cognome,
            String nome,
            String provincia,
            String sortBy
    ) {
        List<String> campi = List.of(
                "cognome",
                "nome",
                "fatturatoAnnuale",
                "dataInserimento",
                "dataUltimoContratto",
                "provincia"
        );
        Specification<Clienti> specification = (root, query, cb) -> cb.conjunction();

        if (min != null && max != null) {
            specification = specification.and(ClientiSpec.fatturatoCompresoTra(min, max));
        } else if (min != null) {
            specification = specification.and(ClientiSpec.fatturatoMaggioreDi(min));
        } else if (max != null) {
            specification = specification.and(ClientiSpec.fatturatoMinoreeDi(max));
        }


        if (dataInserimento != null) {
            specification = specification.and(ClientiSpec.dataDiInserimentoMaggioreDi(dataInserimento));
        }

        if (dataUltimoContratto != null) {
            specification = specification.and(ClientiSpec.dataUltimoContrattoMaggioreDi(dataUltimoContratto));
        }

        if (cognome != null && !cognome.isEmpty()) {
            specification = specification.and(ClientiSpec.findByCognomeContratto(cognome));
        }

        if (nome != null && !nome.isEmpty()) {
            specification = specification.and(ClientiSpec.findByNomeContatto(nome));
        }

        if (provincia != null && !provincia.isEmpty()) {
            specification = specification.and(ClientiSpec.findByProvincia(provincia));
        }


        Sort sort = Sort.unsorted();

        if (sortBy != null) {
            switch (sortBy.toLowerCase()) {
                case "cognome" -> sort = Sort.by("cognome").ascending();
                case "nome" -> sort = Sort.by("nome").ascending();
                case "fatturatoannuale" -> sort = Sort.by("fatturatoAnnuale").ascending();
                case "datainserimento" -> sort = Sort.by("dataInserimento").ascending();
                case "dataultimocontratto" -> sort = Sort.by("dataUltimoContratto").ascending();
                case "provincia" -> sort = Sort.by("sedeLegale.comune.provincia.provincia").ascending();
            }
        }


        return clientiRepository.findAll(specification, sort);
    }
}
