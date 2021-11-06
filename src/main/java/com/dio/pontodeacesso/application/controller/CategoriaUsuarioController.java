package com.dio.pontodeacesso.application.controller;

import com.dio.pontodeacesso.application.assembler.CategoriaUsuarioAssembler;
import com.dio.pontodeacesso.application.assembler.CategoriaUsuarioDesassembler;
import com.dio.pontodeacesso.application.model.CategoriaUsuarioModel;
import com.dio.pontodeacesso.application.model.input.CategoriaUsuarioInputModel;
import com.dio.pontodeacesso.domain.model.CategoriaUsuario;
import com.dio.pontodeacesso.domain.service.CategoriaUsuarioService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/categoria-usuario")
public class CategoriaUsuarioController {

    private final CategoriaUsuarioService categoriaUsuarioService;
    private final CategoriaUsuarioAssembler assembler;
    private final CategoriaUsuarioDesassembler desassembler;

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public CategoriaUsuarioModel create(@Valid @RequestBody CategoriaUsuarioInputModel categoriaUsuarioInputModel) {

        return assembler.toModel(categoriaUsuarioService.save(
                desassembler.toDomainModel(categoriaUsuarioInputModel)));
    }

    @ResponseStatus(HttpStatus.OK)
    @PutMapping("/{idCategoriaUsuario}")
    public CategoriaUsuarioModel update(@PathVariable long idCategoriaUsuario,
            @Valid @RequestBody CategoriaUsuarioInputModel categoriaUsuarioInputModel) {

        CategoriaUsuario categoriaUsuario = categoriaUsuarioService.findById(idCategoriaUsuario);
        desassembler.copyToDomainModel(categoriaUsuarioInputModel, categoriaUsuario);
        return assembler.toModel(categoriaUsuarioService.save(categoriaUsuario));
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{idCategoriaUsuario}")
    public void delete(@PathVariable Long idCategoriaUsuario) {
        categoriaUsuarioService.delete(idCategoriaUsuario);
    }

    @GetMapping("/{idCategoriaUsuario}")
    public CategoriaUsuarioModel getById(@PathVariable Long idCategoriaUsuario) {
        return assembler.toModel(categoriaUsuarioService.findById(idCategoriaUsuario));

    }

    @GetMapping
    public List<CategoriaUsuarioModel> findAll() {
        return assembler.toCollectionModel(categoriaUsuarioService.findAll());
    }
}
