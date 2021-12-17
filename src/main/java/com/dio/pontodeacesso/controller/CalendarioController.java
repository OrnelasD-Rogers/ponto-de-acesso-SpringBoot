package com.dio.pontodeacesso.controller;


import com.dio.pontodeacesso.application.controllerapi.CalendarioApi;
import com.dio.pontodeacesso.application.assembler.CalendarioModelAssembler;
import com.dio.pontodeacesso.application.assembler.CalendarioModelDesassembler;
import com.dio.pontodeacesso.application.model.CalendarioModel;
import com.dio.pontodeacesso.application.model.input.CalendarioInputModel;
import com.dio.pontodeacesso.domain.model.Calendario;
import com.dio.pontodeacesso.domain.service.CalendarioService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Validated
@AllArgsConstructor
@RequestMapping("/calendario")
@RestController
public class CalendarioController implements CalendarioApi {

    CalendarioService calendarioService;
    CalendarioModelAssembler assembler;
    CalendarioModelDesassembler desassembler;

    @Override
    @GetMapping
    @ResponseStatus(code = HttpStatus.OK)
    public List<CalendarioModel> findAll() {
        return assembler.toCollectionModel(calendarioService.findAll());
    }

    @Override
    @GetMapping("/{idCalendario}")
    @ResponseStatus(HttpStatus.OK)
    public CalendarioModel findById(Long idCalendario) {
        return assembler.toModel(calendarioService.findById(idCalendario));
    }

    @Override
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CalendarioModel create(CalendarioInputModel calendarioInput) {
        return assembler.toModel(calendarioService.save(desassembler.toDomainModel(calendarioInput)));
    }

    @Override
    @PutMapping("/{idCalendario}")
    @ResponseStatus(HttpStatus.OK)
    public CalendarioModel update(Long idCalendario, CalendarioInputModel calendarioInput) {
        Calendario calendarioAtual = calendarioService.findById(idCalendario);
        desassembler.copyToDomainModel(calendarioInput, calendarioAtual);
        return assembler.toModel(calendarioService.save(calendarioAtual));
    }

    @Override
    @DeleteMapping("/{idCalendario}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(Long idCalendario) {
        calendarioService.delete(idCalendario);
    }







}
