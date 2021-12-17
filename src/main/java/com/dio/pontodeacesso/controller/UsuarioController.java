package com.dio.pontodeacesso.controller;

import com.dio.pontodeacesso.application.controllerapi.UsuarioApi;
import com.dio.pontodeacesso.application.assembler.UsuarioModelAssembler;
import com.dio.pontodeacesso.application.assembler.UsuarioModelDesassembler;
import com.dio.pontodeacesso.application.model.UsuarioModel;
import com.dio.pontodeacesso.application.model.input.UsuarioInputModel;
import com.dio.pontodeacesso.domain.model.Usuario;
import com.dio.pontodeacesso.domain.service.UsuarioService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping("/usuario")
public class UsuarioController implements UsuarioApi {


    private final UsuarioService usuarioService;
    private final UsuarioModelAssembler assembler;
    private final UsuarioModelDesassembler desassembler;

    @Override
    @ResponseStatus(HttpStatus.OK)
    @GetMapping
    public List<UsuarioModel> findAll() {
        return assembler.toCollectionModel(usuarioService.findAll());
    }

    @Override
    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/{idUsuario}")
    public UsuarioModel findById(Long idUsuario) {
        return assembler.toModel(usuarioService.findById(idUsuario));
    }

    @Override
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public UsuarioModel create(UsuarioInputModel usuarioInputModel) {
        return assembler.toModel(usuarioService.save(desassembler.toDomainModel(usuarioInputModel)));
    }

    @Override
    @ResponseStatus(HttpStatus.OK)
    @PutMapping("/{idUsuario}")
    public UsuarioModel update(Long idUsuario, UsuarioInputModel usuarioInputModel) {
        Usuario usuarioAtual = usuarioService.findById(idUsuario);
        desassembler.copyToDomainModel(usuarioInputModel, usuarioAtual);
        return assembler.toModel(usuarioService.save(usuarioAtual));
    }

    @Override
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{idUsuario}")
    public void delete(Long idUsuario) {
        usuarioService.delete(idUsuario);
    }
}
