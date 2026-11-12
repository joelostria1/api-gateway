package com.examen.ms.apigateway.controller;

import com.examen.ms.apigateway.models.ClientDto;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestClient;

@Slf4j
@RestController
@RequestMapping("/api")
public class ApiController {

    private final RestClient restClient;

    public ApiController(){
        this.restClient = RestClient.create("http://localhost:8082");
    }

    @GetMapping("/client/{id}")
    public ResponseEntity<?> obtenerCliente(@PathVariable Long id){
        log.info("Iniciamos obtencion de info de cliente");
        try {
            ResponseEntity<ClientDto> response = restClient.get().uri("/retrieve/{id}", id).retrieve().toEntity(ClientDto.class);
            log.info("Devolvemos informacion");
            return ResponseEntity.ok().body(response.getBody());
        }catch (Exception e){
            log.error("No se pudo obtener informacion del servicio cliente "+ e.getMessage());
            return ResponseEntity.internalServerError().body("No se encontro el cliente con id: "+ id);

        }

    }

    @PostMapping("/client/new")
    public ResponseEntity<?> guardarCliente(@Valid @RequestBody ClientDto cliente){
        log.info("Iniciamos guardado de cliente nuevo");
        try {
            ResponseEntity<Long> response = restClient.post().uri("/save/").body(cliente).retrieve().toEntity(Long.class);
            log.info("Devolvemos informacion");
            assert response.getBody() != null;
            if(response.getBody() == 0) return ResponseEntity.status(409).body("El usuario ya esta registrado en base de datos");
            return ResponseEntity.ok().body("Se creo el cliente con Id: "+response.getBody());
        }catch (Exception e){
            log.error("Error de comunicacion con microservicio "+ e.getMessage());
            return ResponseEntity.internalServerError().body("Error en la comunicacion con el servicio de clientes");

        }
    }

    @PutMapping("/client/update")
    public ResponseEntity<?> actualizaCliente(@Valid @RequestBody ClientDto cliente){
        log.info("Iniciamos actualizacion de cliente existente");
        try {
            restClient.put().uri("/update/").body(cliente).retrieve().toEntity(ClientDto.class);
            log.info("Devolvemos informacion");
            return ResponseEntity.ok().body("Se actualizo la informacion del usuario con id: "+cliente.getId());
        }catch (Exception e){
            log.error("Error de comunicacion con microservicio "+ e.getMessage());
            return ResponseEntity.internalServerError().body("No se pudo realizar la actualizacion del cliente");

        }
    }

    @DeleteMapping("/client/delete/{id}")
    public ResponseEntity<?> actualizaCliente(@PathVariable Long id){
        log.info("Iniciamos eliminacion de cliente existente");
        try {
            restClient.delete().uri("/delete/{id}",id).retrieve().toEntity(ClientDto.class);
            log.info("Devolvemos informacion");
            return ResponseEntity.ok().body("Se elimino el cliente de manera correcta");
        }catch (Exception e){
            log.error("Error de comunicacion con microservicio "+ e.getMessage());
            return ResponseEntity.internalServerError().body("Error en la eliminacion del cliente");

        }
    }
}
