package com.dio.pontodeacesso.controller;

import com.dio.pontodeacesso.model.*;
import com.dio.pontodeacesso.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/usuario")
public class UsuarioController {

    UsuarioService usuarioService;
    CategoriaUsuarioService categoriaUsuarioService;
    EmpresaService empresaService;
    NivelAcessoService nivelAcessoService;
    JornadaService jornadaService;

    @Autowired
    public UsuarioController(UsuarioService usuarioService, CategoriaUsuarioService categoriaUsuarioService, EmpresaService empresaService, NivelAcessoService nivelAcessoService, JornadaService jornadaService) {
        this.usuarioService = usuarioService;
        this.categoriaUsuarioService = categoriaUsuarioService;
        this.empresaService = empresaService;
        this.nivelAcessoService = nivelAcessoService;
        this.jornadaService = jornadaService;
    }

    @PostMapping
    public ResponseEntity<Usuario> create(@RequestBody Usuario usuario) {
        Optional<CategoriaUsuario> categoriaUsuarioOptional = categoriaUsuarioService.findById(usuario.getCategoriaUsuario().getId_Cat_Usuario());
        if (!categoriaUsuarioOptional.isPresent()) {
            return ResponseEntity.unprocessableEntity().build();
        }
        usuario.setCategoriaUsuario(categoriaUsuarioOptional.get());

        Optional<Empresa> empresaOptional = empresaService.findById(usuario.getEmpresa().getId_Empresa());
        if (!empresaOptional.isPresent()) {
            return ResponseEntity.unprocessableEntity().build();
        }
        usuario.setEmpresa(empresaOptional.get());

        Optional<NivelAcesso> nivelAcessoOptional = nivelAcessoService.findById(usuario.getNivelAcesso().getId_NivelAcesso());
        if (!nivelAcessoOptional.isPresent()) {
            return ResponseEntity.unprocessableEntity().build();
        }
        usuario.setNivelAcesso(nivelAcessoOptional.get());

        Optional<JornadaTrabalho> jornadaTrabalhoOptional = jornadaService.findById(usuario.getJornadaTrabalho().getId_Jornada_Trabalho());
        if (!jornadaTrabalhoOptional.isPresent()) {
            return ResponseEntity.unprocessableEntity().build();
        }
        usuario.setJornadaTrabalho(jornadaTrabalhoOptional.get());

        Usuario usuarioSalvo = usuarioService.save(usuario);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(usuarioSalvo.getId_Usuario()).toUri();

        return ResponseEntity.created(location).body(usuarioSalvo);
    }

    @PutMapping
    public ResponseEntity<Usuario> update(@RequestBody Usuario usuario) {
        Optional<Usuario> usuarioOptional = usuarioService.findById(usuario.getId_Usuario());
        if (!usuarioOptional.isPresent()) {
            return ResponseEntity.unprocessableEntity().build();
        }
        Optional<CategoriaUsuario> categoriaUsuarioOptional = categoriaUsuarioService.findById(usuario.getCategoriaUsuario().getId_Cat_Usuario());
        if (!categoriaUsuarioOptional.isPresent()) {
            return ResponseEntity.unprocessableEntity().build();
        }
        usuario.setCategoriaUsuario(categoriaUsuarioOptional.get());

        Optional<Empresa> empresaOptional = empresaService.findById(usuario.getEmpresa().getId_Empresa());
        if (!empresaOptional.isPresent()) {
            return ResponseEntity.unprocessableEntity().build();
        }
        usuario.setEmpresa(empresaOptional.get());

        Optional<NivelAcesso> nivelAcessoOptional = nivelAcessoService.findById(usuario.getNivelAcesso().getId_NivelAcesso());
        if (!nivelAcessoOptional.isPresent()) {
            return ResponseEntity.unprocessableEntity().build();
        }
        usuario.setNivelAcesso(nivelAcessoOptional.get());

        Optional<JornadaTrabalho> jornadaTrabalhoOptional = jornadaService.findById(usuario.getJornadaTrabalho().getId_Jornada_Trabalho());
        if (!jornadaTrabalhoOptional.isPresent()) {
            return ResponseEntity.unprocessableEntity().build();
        }
        usuario.setJornadaTrabalho(jornadaTrabalhoOptional.get());

        Usuario usuarioSalvo = usuarioService.save(usuario);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Usuario> delete(@PathVariable Long id) {
        Optional<Usuario> usuarioOptional = usuarioService.findById(id);
        if (!usuarioOptional.isPresent()) {
            return ResponseEntity.unprocessableEntity().build();
        }

        usuarioService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping
    public ResponseEntity<List<Usuario>> findAll() {
        return ResponseEntity.ok(usuarioService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Usuario> findById(@PathVariable Long id) {
        Optional<Usuario> usuarioOptional = usuarioService.findById(id);
        if (!usuarioOptional.isPresent()) {
            return ResponseEntity.unprocessableEntity().build();
        }

        return ResponseEntity.ok(usuarioOptional.get());
    }
}
