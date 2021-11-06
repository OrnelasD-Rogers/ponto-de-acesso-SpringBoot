package com.dio.pontodeacesso.application.controller;

import com.dio.pontodeacesso.application.assembler.OcorrenciaModelAssembler;
import com.dio.pontodeacesso.application.assembler.OcorrenciaModelDesassembler;
import com.dio.pontodeacesso.application.model.OcorrenciaModel;
import com.dio.pontodeacesso.application.model.input.OcorrenciaInputModel;
import com.dio.pontodeacesso.domain.model.Ocorrencia;
import com.dio.pontodeacesso.domain.service.OcorrenciaService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;


@RequiredArgsConstructor
@RestController
@RequestMapping("/ocorrencia")
public class OcorrenciaController {

    private final OcorrenciaService ocorrenciaService;
    private final OcorrenciaModelAssembler assembler;
    private final OcorrenciaModelDesassembler desassembler;


    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public OcorrenciaModel create(@Valid @RequestBody OcorrenciaInputModel ocorrenciaInputModel) {
        Ocorrencia ocorrencia = desassembler.toDomainModel(ocorrenciaInputModel);
        return assembler.toModel(ocorrenciaService.save(ocorrencia));
    }

    @ResponseStatus(HttpStatus.OK)
    @PutMapping("/{idOcorrencia}")
    public OcorrenciaModel update(@PathVariable Long idOcorrencia,
                                  @Valid @RequestBody OcorrenciaInputModel ocorrenciaInputModel) {
        Ocorrencia ocorrenciaAtual = ocorrenciaService.findById(idOcorrencia);
        desassembler.copyToDomainModel(ocorrenciaInputModel, ocorrenciaAtual);
        return assembler.toModel(ocorrenciaService.save(ocorrenciaAtual));
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{idOcorrencia}")
    public void delete(@PathVariable Long idOcorrencia) {
        ocorrenciaService.delete(idOcorrencia);
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/{idOcorrencia}")
    public OcorrenciaModel getById(@PathVariable Long idOcorrencia) {
        return assembler.toModel(ocorrenciaService.findById(idOcorrencia));
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping
    public List<OcorrenciaModel> findAll() {
        return assembler.toCollectionModel(ocorrenciaService.findAll());
    }
}

