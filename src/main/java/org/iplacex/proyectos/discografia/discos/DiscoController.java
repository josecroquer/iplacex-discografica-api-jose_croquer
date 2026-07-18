package org.iplacex.proyectos.discografia.discos;

import org.iplacex.proyectos.discografia.artistas.IArtistaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin 
@RequestMapping("/api") 
public class DiscoController {

    @Autowired
    private IDiscoRepository discoRepository; 

    @Autowired
    private IArtistaRepository artistaRepository; 

    // POST: /api/disco
    @PostMapping(value = "/disco", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE) // [cite: 70, 77]
    public ResponseEntity<Object> HandlePostDiscoRequest(@RequestBody Disco disco) { 
        // Validación cruzada: evaluar si el artista existe antes de insertar el disco
        if (!artistaRepository.existsById(disco.idArtista)) { 
            return new ResponseEntity<>("Error: El artista asociado no existe.", HttpStatus.NOT_FOUND); 
        }
        Disco nuevoDisco = discoRepository.save(disco); 
        return new ResponseEntity<>(nuevoDisco, HttpStatus.CREATED); 
    }

    // GET: /api/discos
    @GetMapping(value = "/discos", produces = MediaType.APPLICATION_JSON_VALUE) 
    public ResponseEntity<List<Disco>> HandleGetDiscosRequest() { 
        List<Disco> discos = discoRepository.findAll(); 
        return new ResponseEntity<>(discos, HttpStatus.OK); 
    }

    // GET: /api/disco/{id}
    @GetMapping(value = "/disco/{id}", produces = MediaType.APPLICATION_JSON_VALUE) 
    public ResponseEntity<Object> HandleGetDiscoRequest(@PathVariable String id) { 
        return discoRepository.findById(id)
                .<ResponseEntity<Object>>map(disco -> new ResponseEntity<>(disco, HttpStatus.OK)) 
                .orElseGet(() -> new ResponseEntity<>("Disco no encontrado", HttpStatus.NOT_FOUND)); 
    }

    // GET: /api/artista/{id}/discos
    @GetMapping(value = "/artista/{id}/discos", produces = MediaType.APPLICATION_JSON_VALUE) 
    public ResponseEntity<List<Disco>> HandleGetDiscosByArtistaRequest(@PathVariable String id) { 
        List<Disco> discos = discoRepository.findDiscosByIdArtista(id); 
        return new ResponseEntity<>(discos, HttpStatus.OK);
    }
}