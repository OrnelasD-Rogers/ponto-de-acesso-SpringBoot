package com.dio.pontodeacesso.controller;

import com.dio.pontodeacesso.model.Empresa;
import com.dio.pontodeacesso.service.EmpresaService;
import com.dio.pontodeacesso.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/empresa")
public class EmpresaController {

    UsuarioService usuarioService;
    EmpresaService empresaService;

    @Autowired
    public EmpresaController(UsuarioService usuarioService, EmpresaService empresaService) {
        this.usuarioService = usuarioService;
        this.empresaService = empresaService;
    }

    @PostMapping
    public ResponseEntity<Empresa> create(@RequestBody Empresa empresa) {
        Empresa empresaSalva = empresaService.save(empresa);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(empresaSalva.getId_Empresa()).toUri();

        return ResponseEntity.created(location).body(empresaSalva);
    }

    @PutMapping
    public ResponseEntity<Empresa> update(@RequestBody Empresa empresa) {
        Optional<Empresa> empresaOptional = empresaService.findById(empresa.getId_Empresa());
        if (empresaOptional.isEmpty()) {
            return ResponseEntity.unprocessableEntity().build();
        }
        empresaService.save(empresa);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Empresa> delete(@PathVariable Long id) {
        Optional<Empresa> empresaOptional = empresaService.findById(id);
        if (empresaOptional.isEmpty()) {
            return ResponseEntity.unprocessableEntity().build();
        }

        empresaService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Empresa> getById(@PathVariable Long id) {
        Optional<Empresa> empresaOptional = empresaService.findById(id);
        if (empresaOptional.isEmpty()) {
            return ResponseEntity.unprocessableEntity().build();
        }
        return ResponseEntity.ok(empresaOptional.get());
    }

    @GetMapping
    public ResponseEntity<List<Empresa>> findAll( ) {
        return ResponseEntity.ok(empresaService.findAll());
    }
}
