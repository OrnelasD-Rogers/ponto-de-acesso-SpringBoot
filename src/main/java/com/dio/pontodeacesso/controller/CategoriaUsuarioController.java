package com.dio.pontodeacesso.controller;

import com.dio.pontodeacesso.application.controllerapi.CategoriaUsuarioApi;
import com.dio.pontodeacesso.application.assembler.CategoriaUsuarioAssembler;
import com.dio.pontodeacesso.application.assembler.CategoriaUsuarioDesassembler;
import com.dio.pontodeacesso.application.model.CategoriaUsuarioModel;
import com.dio.pontodeacesso.application.model.input.CategoriaUsuarioInputModel;
import com.dio.pontodeacesso.domain.model.CategoriaUsuario;
import com.dio.pontodeacesso.domain.service.CategoriaUsuarioService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Validated
@RequiredArgsConstructor
@RequestMapping("/categoria-usuario")
@RestController
public class CategoriaUsuarioController implements CategoriaUsuarioApi {

    private final CategoriaUsuarioService categoriaUsuarioService;
    private final CategoriaUsuarioAssembler assembler;
    private final CategoriaUsuarioDesassembler desassembler;

    @Override
    @GetMapping
    @ResponseStatus(code = HttpStatus.OK)
    public List<CategoriaUsuarioModel> findAll() {
        return assembler.toCollectionModel(categoriaUsuarioService.findAll());
    }

    @Override
    @GetMapping("/{idCategoriaUsuario}")
    @ResponseStatus(HttpStatus.OK)
    public CategoriaUsuarioModel findById(Long idCategoriaUsuario) {
        return assembler.toModel(categoriaUsuarioService.findById(idCategoriaUsuario));
    }

    @Override
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CategoriaUsuarioModel create(CategoriaUsuarioInputModel categoriaUsuarioInputModel) {
        return assembler.toModel(categoriaUsuarioService.save(
                desassembler.toDomainModel(categoriaUsuarioInputModel)));
    }

    @Override
    @PutMapping("/{idCategoriaUsuario}")
    @ResponseStatus(HttpStatus.OK)
    public CategoriaUsuarioModel update(Long idCategoriaUsuario, CategoriaUsuarioInputModel categoriaUsuarioInputModel) {
        CategoriaUsuario categoriaUsuario = categoriaUsuarioService.findById(idCategoriaUsuario);
        desassembler.copyToDomainModel(categoriaUsuarioInputModel, categoriaUsuario);
        return assembler.toModel(categoriaUsuarioService.save(categoriaUsuario));
    }

    @Override
    @DeleteMapping("/{idCategoriaUsuario}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(Long idCategoriaUsuario) {
        categoriaUsuarioService.delete(idCategoriaUsuario);
    }

}
