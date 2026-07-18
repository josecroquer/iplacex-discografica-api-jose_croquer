package org.iplacex.proyectos.discografia.artistas;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import java.util.List;

@Document("artistas")
public class Artista {
    @Id
    public String id;
    public String nombre;
    public List<String> estilos;
    public int anioFundacion;
    public boolean estaActivo;
}