package com.dio.pontodeacesso.controller;

import com.dio.pontodeacesso.model.JornadaTrabalho;
import com.dio.pontodeacesso.service.JornadaService;
import com.dio.pontodeacesso.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@RestController //Retorna dados como um JSON
@RequestMapping("/jornada") //Define um EndPoint para este controlador
public class JornadaTrabalhoController {


    JornadaService jornadaService;
    UsuarioService usuarioService;

    @Autowired
    public JornadaTrabalhoController(JornadaService jornadaService, UsuarioService usuarioService) {
        this.jornadaService = jornadaService;
        this.usuarioService = usuarioService;
    }

    @PostMapping //Notação que define o método a ser chamado quando for feita uma requisicao post
    //RequestBody => Requisita uma instancia da classe JornadaTrabalho quando o EndPoint deste Controller for chamado
    public ResponseEntity<JornadaTrabalho> create(@RequestBody JornadaTrabalho jornadaTrabalho) {
        JornadaTrabalho jornadaTrabalhoSalva = jornadaService.save(jornadaTrabalho);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(jornadaTrabalhoSalva.getId_Jornada_Trabalho()).toUri();

        return ResponseEntity.created(location).body(jornadaTrabalhoSalva);
    }

    @GetMapping("/{id}")
    public ResponseEntity<JornadaTrabalho> getById(@PathVariable Long id) {
        Optional<JornadaTrabalho> jornadaTrabalhoOptional = jornadaService.findById(id);
        if (!jornadaTrabalhoOptional.isPresent()) {
            return ResponseEntity.unprocessableEntity().build();
        }

        return ResponseEntity.ok(jornadaTrabalhoOptional.get());
    }

    @GetMapping
    public ResponseEntity<List<JornadaTrabalho>> getAll() {
        return ResponseEntity.ok(jornadaService.findAll());
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<JornadaTrabalho> delete(@PathVariable Long id) {
        Optional<JornadaTrabalho> jornadaTrabalhoOptional = jornadaService.findById(id);
        if (!jornadaTrabalhoOptional.isPresent()) {
            return ResponseEntity.unprocessableEntity().build();
        }
        jornadaService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping //Notação que define o método a ser chamado quando for feita uma requisicao post
    //RequestBody => Requisita uma instancia da classe JornadaTrabalho quando o EndPoint deste Controller for chamado
    public ResponseEntity<JornadaTrabalho> update(@RequestBody JornadaTrabalho jornadaTrabalho) {
        Optional<JornadaTrabalho> jornadaTrabalhoOptional = jornadaService.findById(jornadaTrabalho.getId_Jornada_Trabalho());
        if (!jornadaTrabalhoOptional.isPresent()) {
            return ResponseEntity.unprocessableEntity().build();
        }
        jornadaService.save(jornadaTrabalho);
        return ResponseEntity.noContent().build();
    }

}
