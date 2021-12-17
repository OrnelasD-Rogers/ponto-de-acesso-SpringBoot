package com.dio.pontodeacesso.controller;

import com.dio.pontodeacesso.application.controllerapi.NivelAcessoApi;
import com.dio.pontodeacesso.application.assembler.NivelAcessoAssembler;
import com.dio.pontodeacesso.application.assembler.NivelAcessoDesassembler;
import com.dio.pontodeacesso.application.model.NivelAcessoModel;
import com.dio.pontodeacesso.application.model.input.NivelAcessoInputModel;
import com.dio.pontodeacesso.domain.model.NivelAcesso;
import com.dio.pontodeacesso.domain.service.NivelAcessoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping("/nivel-acesso")
public class NivelAcessoController implements NivelAcessoApi {

    private final NivelAcessoService nivelAcessoService;
    private final NivelAcessoAssembler assembler;
    private final NivelAcessoDesassembler desassembler;

    @Override
    @GetMapping
    @ResponseStatus(code = HttpStatus.OK)
    public List<NivelAcessoModel> findAll() {
        return assembler.toCollectionModel(nivelAcessoService.findAll());
    }

    @Override
    @GetMapping("/{idNivelAcesso}")
    @ResponseStatus(HttpStatus.OK)
    public NivelAcessoModel findById(Long idNivelAcesso) {
        return assembler.toModel(nivelAcessoService.findById(idNivelAcesso));
    }

    @Override
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public NivelAcessoModel create(NivelAcessoInputModel nivelAcessoInputModel) {
        return assembler.toModel(nivelAcessoService.save(desassembler.toDomainModel(nivelAcessoInputModel)));
    }

    @Override
    @PutMapping("/{idNivelAcesso}")
    @ResponseStatus(HttpStatus.OK)
    public NivelAcessoModel update(Long idNivelAcesso, NivelAcessoInputModel nivelAcessoInputModel) {
        NivelAcesso nivelAcesso = nivelAcessoService.findById(idNivelAcesso);
        desassembler.copyToDomainModel(nivelAcessoInputModel, nivelAcesso);
        return assembler.toModel(nivelAcessoService.save(nivelAcesso));
    }

    @Override
    @DeleteMapping("/{idNivelAcesso}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(Long idNivelAcesso) {
        nivelAcessoService.delete(idNivelAcesso);
    }
}
