package com.dio.pontodeacesso.application.controller;

import com.dio.pontodeacesso.application.controllerapi.CategoriaUsuarioApi;
import com.dio.pontodeacesso.application.assembler.CategoriaUsuarioAssembler;
import com.dio.pontodeacesso.application.assembler.CategoriaUsuarioDesassembler;
import com.dio.pontodeacesso.application.model.CategoriaUsuarioModel;
import com.dio.pontodeacesso.application.model.input.CategoriaUsuarioInputModel;
import com.dio.pontodeacesso.domain.model.CategoriaUsuario;
import com.dio.pontodeacesso.domain.service.CategoriaUsuarioService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequiredArgsConstructor
@RestController
public class CategoriaUsuarioController implements CategoriaUsuarioApi {

    private final CategoriaUsuarioService categoriaUsuarioService;
    private final CategoriaUsuarioAssembler assembler;
    private final CategoriaUsuarioDesassembler desassembler;

    @Override
    public List<CategoriaUsuarioModel> findAll() {
        return assembler.toCollectionModel(categoriaUsuarioService.findAll());
    }

    @Override
    public CategoriaUsuarioModel findById(Long idCategoriaUsuario) {
        return assembler.toModel(categoriaUsuarioService.findById(idCategoriaUsuario));
    }

    @Override
    public CategoriaUsuarioModel create(CategoriaUsuarioInputModel categoriaUsuarioInputModel) {
        return assembler.toModel(categoriaUsuarioService.save(
                desassembler.toDomainModel(categoriaUsuarioInputModel)));
    }

    @Override
    public CategoriaUsuarioModel update(Long idCategoriaUsuario, CategoriaUsuarioInputModel categoriaUsuarioInputModel) {
        CategoriaUsuario categoriaUsuario = categoriaUsuarioService.findById(idCategoriaUsuario);
        desassembler.copyToDomainModel(categoriaUsuarioInputModel, categoriaUsuario);
        return assembler.toModel(categoriaUsuarioService.save(categoriaUsuario));
    }

    @Override
    public void delete(Long idCategoriaUsuario) {
        categoriaUsuarioService.delete(idCategoriaUsuario);
    }

}
