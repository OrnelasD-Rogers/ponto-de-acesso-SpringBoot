package com.dio.pontodeacesso.controller;

import com.dio.pontodeacesso.model.Ocorrencia;
import com.dio.pontodeacesso.service.MovimentacaoService;
import com.dio.pontodeacesso.service.OcorrenciaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/ocorrencia")
public class OcorrenciaController {

    OcorrenciaService ocorrenciaService;
    MovimentacaoService movimentacaoService;

    @Autowired
    public OcorrenciaController(OcorrenciaService ocorrenciaService, MovimentacaoService movimentacaoService) {
        this.ocorrenciaService = ocorrenciaService;
        this.movimentacaoService = movimentacaoService;
    }


    @PostMapping
    public ResponseEntity<Ocorrencia> create(@RequestBody Ocorrencia ocorrencia) {
        Ocorrencia ocorrenciaSalva = ocorrenciaService.save(ocorrencia);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(ocorrenciaSalva.getId_Ocorrencia()).toUri();

        return ResponseEntity.created(location).body(ocorrenciaSalva);
    }

    @PutMapping
    public ResponseEntity<Ocorrencia> update(@RequestBody Ocorrencia ocorrencia) {
        Optional<Ocorrencia> ocorrenciaOptional = ocorrenciaService.findById(ocorrencia.getId_Ocorrencia());
        if (ocorrenciaOptional.isEmpty()) {
            return ResponseEntity.unprocessableEntity().build();
        }
        ocorrenciaService.save(ocorrencia);

        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Ocorrencia> delete(@PathVariable Long id) {
        Optional<Ocorrencia> ocorrenciaOptional = ocorrenciaService.findById(id);
        if (ocorrenciaOptional.isEmpty()) {
            return ResponseEntity.unprocessableEntity().build();
        }

        ocorrenciaService.delete(ocorrenciaOptional.get().getId_Ocorrencia());

        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Ocorrencia> getById(@PathVariable Long id) {
        Optional<Ocorrencia> ocorrenciaOptional = ocorrenciaService.findById(id);
        if (ocorrenciaOptional.isEmpty()) {
            return ResponseEntity.unprocessableEntity().build();
        }

        return ResponseEntity.ok(ocorrenciaOptional.get());
    }

    @GetMapping
    public ResponseEntity<List<Ocorrencia>> findAll() {
        return ResponseEntity.ok(ocorrenciaService.findAll());
    }
}

