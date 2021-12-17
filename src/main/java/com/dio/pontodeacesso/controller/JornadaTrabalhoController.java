package com.dio.pontodeacesso.controller;

import com.dio.pontodeacesso.application.controllerapi.JornadaTrabalhoApi;
import com.dio.pontodeacesso.application.assembler.JornadaTrabalhoAssembler;
import com.dio.pontodeacesso.application.assembler.JornadaTrabalhoDesassembler;
import com.dio.pontodeacesso.application.model.JornadaTrabalhoModel;
import com.dio.pontodeacesso.application.model.input.JornadaTrabalhoInputModel;
import com.dio.pontodeacesso.domain.model.JornadaTrabalho;
import com.dio.pontodeacesso.domain.service.JornadaService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Validated
@RequiredArgsConstructor
@RequestMapping("/jornada")
@RestController
public class JornadaTrabalhoController implements JornadaTrabalhoApi {

    private final JornadaService jornadaService;
    private final JornadaTrabalhoAssembler assembler;
    private final JornadaTrabalhoDesassembler desassembler;

    @Override
    @GetMapping
    @ResponseStatus(code = HttpStatus.OK)
    public List<JornadaTrabalhoModel> findAll() {
        return assembler.toCollectionModel(jornadaService.findAll());
    }

    @Override
    @GetMapping("/{idJornada}")
    @ResponseStatus(HttpStatus.OK)
    public JornadaTrabalhoModel findById(Long idJornada) {
        return assembler.toModel(jornadaService.findById(idJornada));
    }

    @Override
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public JornadaTrabalhoModel create(JornadaTrabalhoInputModel jornadaTrabalhoInputModel) {
        JornadaTrabalho jornadaTrabalho = desassembler.toDomainModel(jornadaTrabalhoInputModel);
        return assembler.toModel(jornadaService.save(jornadaTrabalho));
    }

    @Override
    @PutMapping("/{idJornada}")
    @ResponseStatus(HttpStatus.OK)
    public JornadaTrabalhoModel update(Long idJornada, JornadaTrabalhoInputModel jornadaTrabalhoInputModel) {
        JornadaTrabalho jornadaTrabalhoAtual = jornadaService.findById(idJornada);
        desassembler.copyToDomainModel(jornadaTrabalhoInputModel,jornadaTrabalhoAtual);
        return assembler.toModel(jornadaService.save(jornadaTrabalhoAtual));
    }

    @Override
    @DeleteMapping("/{idJornada}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(Long idJornada) {
        jornadaService.delete(idJornada);
    }
}
