package com.dio.pontodeacesso.application.controller;

import com.dio.pontodeacesso.application.controllerapi.OcorrenciaApi;
import com.dio.pontodeacesso.application.assembler.OcorrenciaModelAssembler;
import com.dio.pontodeacesso.application.assembler.OcorrenciaModelDesassembler;
import com.dio.pontodeacesso.application.model.OcorrenciaModel;
import com.dio.pontodeacesso.application.model.input.OcorrenciaInputModel;
import com.dio.pontodeacesso.domain.model.Ocorrencia;
import com.dio.pontodeacesso.domain.service.OcorrenciaService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@RequiredArgsConstructor
@RestController
public class OcorrenciaController implements OcorrenciaApi {

    private final OcorrenciaService ocorrenciaService;
    private final OcorrenciaModelAssembler assembler;
    private final OcorrenciaModelDesassembler desassembler;

    @Override
    public List<OcorrenciaModel> findAll() {
        return assembler.toCollectionModel(ocorrenciaService.findAll());
    }

    @Override
    public OcorrenciaModel findById(Long idOcorrencia) {
        return assembler.toModel(ocorrenciaService.findById(idOcorrencia));
    }

    @Override
    public OcorrenciaModel create(OcorrenciaInputModel ocorrenciaInputModel) {
        Ocorrencia ocorrencia = desassembler.toDomainModel(ocorrenciaInputModel);
        return assembler.toModel(ocorrenciaService.save(ocorrencia));
    }

    @Override
    public OcorrenciaModel update(Long idOcorrencia, OcorrenciaInputModel ocorrenciaInputModel) {
        Ocorrencia ocorrenciaAtual = ocorrenciaService.findById(idOcorrencia);
        desassembler.copyToDomainModel(ocorrenciaInputModel, ocorrenciaAtual);
        return assembler.toModel(ocorrenciaService.save(ocorrenciaAtual));
    }

    @Override
    public void delete(Long idOcorrencia) {
        ocorrenciaService.delete(idOcorrencia);
    }
}

