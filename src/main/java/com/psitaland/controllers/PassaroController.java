package com.psitaland.controllers;

import com.psitaland.services.PassaroService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/passaros")
public class PassaroController {

    @Autowired
    private PassaroService passaroService;

    // Endpoint: PUT http://localhost:8080/api/passaros/1/mover/5
    @PutMapping("/{id}/mover/{gaiolaId}")
    public ResponseEntity<String> moverPassaro(@PathVariable Integer id, @PathVariable Integer gaiolaId) {
        passaroService.moverParaGaiola(id, gaiolaId);
        return ResponseEntity.ok("Pássaro movido com sucesso para a gaiola " + gaiolaId);
    }
}