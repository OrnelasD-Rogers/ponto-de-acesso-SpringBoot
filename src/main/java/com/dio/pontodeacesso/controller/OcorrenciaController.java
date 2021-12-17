package com.dio.pontodeacesso.controller;

import com.dio.pontodeacesso.application.controllerapi.OcorrenciaApi;
import com.dio.pontodeacesso.application.assembler.OcorrenciaModelAssembler;
import com.dio.pontodeacesso.application.assembler.OcorrenciaModelDesassembler;
import com.dio.pontodeacesso.application.model.OcorrenciaModel;
import com.dio.pontodeacesso.application.model.input.OcorrenciaInputModel;
import com.dio.pontodeacesso.domain.model.Ocorrencia;
import com.dio.pontodeacesso.domain.service.OcorrenciaService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping("/ocorrencia")
public class OcorrenciaController implements OcorrenciaApi {

    private final OcorrenciaService ocorrenciaService;
    private final OcorrenciaModelAssembler assembler;
    private final OcorrenciaModelDesassembler desassembler;


    @Override
    @GetMapping
    @ResponseStatus(code = HttpStatus.OK)
    public List<OcorrenciaModel> findAll() {
        return assembler.toCollectionModel(ocorrenciaService.findAll());
    }

    @Override
    @GetMapping("/{idOcorrencia}")
    @ResponseStatus(HttpStatus.OK)
    public OcorrenciaModel findById(Long idOcorrencia) {
        return assembler.toModel(ocorrenciaService.findById(idOcorrencia));
    }

    @Override
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public OcorrenciaModel create(OcorrenciaInputModel ocorrenciaInputModel) {
        Ocorrencia ocorrencia = desassembler.toDomainModel(ocorrenciaInputModel);
        return assembler.toModel(ocorrenciaService.save(ocorrencia));
    }

    @Override
    @PutMapping("/{idOcorrencia}")
    @ResponseStatus(HttpStatus.OK)
    public OcorrenciaModel update(Long idOcorrencia, OcorrenciaInputModel ocorrenciaInputModel) {
        Ocorrencia ocorrenciaAtual = ocorrenciaService.findById(idOcorrencia);
        desassembler.copyToDomainModel(ocorrenciaInputModel, ocorrenciaAtual);
        return assembler.toModel(ocorrenciaService.save(ocorrenciaAtual));
    }

    @Override
    @DeleteMapping("/{idOcorrencia}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(Long idOcorrencia) {
        ocorrenciaService.delete(idOcorrencia);
    }
}

