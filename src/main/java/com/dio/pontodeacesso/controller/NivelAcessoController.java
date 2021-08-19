package com.dio.pontodeacesso.controller;

import com.dio.pontodeacesso.model.NivelAcesso;
import com.dio.pontodeacesso.model.TipoData;
import com.dio.pontodeacesso.service.LocalidadeService;
import com.dio.pontodeacesso.service.NivelAcessoService;
import com.dio.pontodeacesso.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/nivel-acesso")
public class NivelAcessoController {
    NivelAcessoService nivelAcessoService;
    LocalidadeService localidadeService;
    UsuarioService usuarioService;

    @Autowired
    public NivelAcessoController(NivelAcessoService nivelAcessoService, LocalidadeService localidadeService, UsuarioService usuarioService) {
        this.nivelAcessoService = nivelAcessoService;
        this.localidadeService = localidadeService;
        this.usuarioService = usuarioService;
    }

    @PostMapping
    public ResponseEntity<NivelAcesso> create(@RequestBody NivelAcesso nivelAcesso){

        NivelAcesso nivelAcessoSalvo = nivelAcessoService.save(nivelAcesso);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(nivelAcessoSalvo.getId_NivelAcesso()).toUri();

        return ResponseEntity.created(location).body(nivelAcessoSalvo);
    }

    @GetMapping
    public ResponseEntity<List<NivelAcesso>> findAll(){
        return ResponseEntity.ok(nivelAcessoService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<NivelAcesso> findById(@PathVariable Long id) {
        Optional<NivelAcesso> optionalNivelAcesso = nivelAcessoService.findById(id);
        if (optionalNivelAcesso.isEmpty()) {
            return ResponseEntity.unprocessableEntity().build();
        }

        return ResponseEntity.ok(optionalNivelAcesso.get());
    }

    @PutMapping
    public ResponseEntity<NivelAcesso> updateTipoData(@RequestBody NivelAcesso nivelAcesso){
        Optional<NivelAcesso> optionalNivelAcesso = nivelAcessoService.findById(nivelAcesso.getId_NivelAcesso());
        if (optionalNivelAcesso.isEmpty()) {
            return ResponseEntity.unprocessableEntity().build();
        }

        nivelAcesso.setId_NivelAcesso(optionalNivelAcesso.get().getId_NivelAcesso());
        nivelAcessoService.save(nivelAcesso);

        return ResponseEntity.noContent().build();

    }

    @DeleteMapping("/{id}")
    public ResponseEntity<NivelAcesso> deleteCalendario(@PathVariable Long id){
        Optional<NivelAcesso> optionalNivelAcesso = nivelAcessoService.findById(id);
        if (optionalNivelAcesso.isEmpty()) {
            return ResponseEntity.unprocessableEntity().build();
        }

        nivelAcessoService.delete(optionalNivelAcesso.get().getId_NivelAcesso());
        return ResponseEntity.noContent().build();
    }
}
