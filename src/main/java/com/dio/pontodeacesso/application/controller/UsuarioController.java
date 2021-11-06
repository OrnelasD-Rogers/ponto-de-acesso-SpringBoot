package com.dio.pontodeacesso.application.controller;

import com.dio.pontodeacesso.application.assembler.UsuarioModelAssembler;
import com.dio.pontodeacesso.application.assembler.UsuarioModelDesassembler;
import com.dio.pontodeacesso.application.model.UsuarioModel;
import com.dio.pontodeacesso.application.model.input.UsuarioInputModel;
import com.dio.pontodeacesso.domain.model.Usuario;
import com.dio.pontodeacesso.domain.service.UsuarioService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/usuario")
public class UsuarioController {

    private final UsuarioService usuarioService;
    private final UsuarioModelAssembler assembler;
    private final UsuarioModelDesassembler desassembler;



    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public UsuarioModel create(@Valid @RequestBody UsuarioInputModel usuarioInputModel) {
        return assembler.toModel(usuarioService.save(desassembler.toDomainModel(usuarioInputModel)));
    }

    @ResponseStatus(HttpStatus.OK)
    @PutMapping("/{idUsuario}")
    public UsuarioModel update(@PathVariable long idUsuario,
            @Valid @RequestBody UsuarioInputModel usuarioInputModel) {

        Usuario usuarioAtual = usuarioService.findById(idUsuario);
        desassembler.copyToDomainModel(usuarioInputModel, usuarioAtual);
        return assembler.toModel(usuarioService.save(usuarioAtual));
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{idUsuario}")
    public void delete(@PathVariable Long idUsuario) {
        usuarioService.delete(idUsuario);
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping
    public List<UsuarioModel> findAll() {
        return assembler.toCollectionModel(usuarioService.findAll());
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/{idUsuario}")
    public UsuarioModel findById(@PathVariable Long idUsuario) {
        return assembler.toModel(usuarioService.findById(idUsuario));
    }
}
