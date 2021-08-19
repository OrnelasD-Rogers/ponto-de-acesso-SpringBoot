package com.dio.pontodeacesso.controller;

import com.dio.pontodeacesso.model.Localidade;
import com.dio.pontodeacesso.model.NivelAcesso;
import com.dio.pontodeacesso.service.LocalidadeService;
import com.dio.pontodeacesso.service.NivelAcessoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/localidade")
public class LocalidadeController {

    LocalidadeService localidadeService;
    NivelAcessoService nivelAcessoService;

    @Autowired
    public LocalidadeController(LocalidadeService localidadeService, NivelAcessoService nivelAcessoService) {
        this.localidadeService = localidadeService;
        this.nivelAcessoService = nivelAcessoService;
    }

    @PostMapping
    public ResponseEntity<Localidade> create(@RequestBody Localidade localidade){
        Optional<NivelAcesso> optionalNivelAcesso = nivelAcessoService.findById(localidade.getNivelAcesso().getId_NivelAcesso());
        if (optionalNivelAcesso.isEmpty()) {
            return ResponseEntity.unprocessableEntity().build();
        }

        localidade.setNivelAcesso(optionalNivelAcesso.get());
        Localidade localidadeSalva = localidadeService.save(localidade);

        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(localidadeSalva.getId()).toUri();

        return ResponseEntity.created(location).body(localidadeSalva);
    }

    @GetMapping
    public ResponseEntity<List<Localidade>> findAll(){
        return ResponseEntity.ok(localidadeService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Localidade> getCalendarioById(@PathVariable Long id)throws Exception{
        Optional<Localidade> optionalLocalidade = localidadeService.findById(id);
        if (optionalLocalidade.isEmpty()) {
            return ResponseEntity.unprocessableEntity().build();
        }

        return ResponseEntity.ok(optionalLocalidade.get());
    }

    @PutMapping
    public ResponseEntity<Localidade> update(@RequestBody Localidade localidade){
        Optional<NivelAcesso> OptionalNivelAcesso = nivelAcessoService.findById(localidade.getNivelAcesso().getId_NivelAcesso());
        if (OptionalNivelAcesso.isEmpty()) {
            return ResponseEntity.unprocessableEntity().build();
        }

        Optional<Localidade> OptionalLocalidade = localidadeService.findById(localidade.getId());
        if (OptionalLocalidade.isEmpty()) {
            return ResponseEntity.unprocessableEntity().build();
        }

        localidade.setNivelAcesso(OptionalLocalidade.get().getNivelAcesso());
        localidade.setId(OptionalLocalidade.get().getId());
        localidadeService.save(localidade);

        return ResponseEntity.noContent().build();

    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Localidade> delete(@PathVariable Long id){

        Optional<Localidade> optionalLocalidade = localidadeService.findById(id);
        if (optionalLocalidade.isEmpty()) {
            return ResponseEntity.unprocessableEntity().build();
        }
        localidadeService.delete(optionalLocalidade.get().getId());

        return ResponseEntity.noContent().build();
    }
}
