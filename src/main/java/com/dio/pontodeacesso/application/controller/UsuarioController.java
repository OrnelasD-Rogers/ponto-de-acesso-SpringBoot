package com.dio.pontodeacesso.application.controller;

import com.dio.pontodeacesso.application.controllerapi.UsuarioApi;
import com.dio.pontodeacesso.application.assembler.UsuarioModelAssembler;
import com.dio.pontodeacesso.application.assembler.UsuarioModelDesassembler;
import com.dio.pontodeacesso.application.model.UsuarioModel;
import com.dio.pontodeacesso.application.model.input.UsuarioInputModel;
import com.dio.pontodeacesso.domain.model.Usuario;
import com.dio.pontodeacesso.domain.service.UsuarioService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequiredArgsConstructor
@RestController
public class UsuarioController implements UsuarioApi {


    private final UsuarioService usuarioService;
    private final UsuarioModelAssembler assembler;
    private final UsuarioModelDesassembler desassembler;

    @Override
    public List<UsuarioModel> findAll() {
        return assembler.toCollectionModel(usuarioService.findAll());
    }

    @Override
    public UsuarioModel findById(Long idUsuario) {
        return assembler.toModel(usuarioService.findById(idUsuario));
    }

    @Override
    public UsuarioModel create(UsuarioInputModel usuarioInputModel) {
        return assembler.toModel(usuarioService.save(desassembler.toDomainModel(usuarioInputModel)));
    }

    @Override
    public UsuarioModel update(Long idUsuario, UsuarioInputModel usuarioInputModel) {
        Usuario usuarioAtual = usuarioService.findById(idUsuario);
        desassembler.copyToDomainModel(usuarioInputModel, usuarioAtual);
        return assembler.toModel(usuarioService.save(usuarioAtual));
    }

    @Override
    public void delete(Long idUsuario) {
        usuarioService.delete(idUsuario);
    }
}
