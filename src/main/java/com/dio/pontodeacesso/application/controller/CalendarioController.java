package com.dio.pontodeacesso.application.controller;


import com.dio.pontodeacesso.application.controllerapi.CalendarioApi;
import com.dio.pontodeacesso.application.assembler.CalendarioModelAssembler;
import com.dio.pontodeacesso.application.assembler.CalendarioModelDesassembler;
import com.dio.pontodeacesso.application.model.CalendarioModel;
import com.dio.pontodeacesso.application.model.input.CalendarioInputModel;
import com.dio.pontodeacesso.domain.model.Calendario;
import com.dio.pontodeacesso.domain.service.CalendarioService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@AllArgsConstructor
@RestController
public class CalendarioController implements CalendarioApi {

    CalendarioService calendarioService;
    CalendarioModelAssembler assembler;
    CalendarioModelDesassembler desassembler;

    @Override
    public List<CalendarioModel> findAll() {
        return assembler.toCollection(calendarioService.findAllCalendario());
    }

    @Override
    public CalendarioModel findById(Long idCalendario) {
        return assembler.toModel(calendarioService.findCalendarioById(idCalendario));
    }

    @Override
    public CalendarioModel create(CalendarioInputModel calendarioInput) {
        return assembler.toModel(calendarioService.save(desassembler.toDomainModel(calendarioInput)));
    }

    @Override
    public CalendarioModel update(Long idCalendario, CalendarioInputModel calendarioInput) {
        Calendario calendarioAtual = calendarioService.findCalendarioById(idCalendario);
        desassembler.copyToDomainModel(calendarioInput, calendarioAtual);
        return assembler.toModel(calendarioService.save(calendarioAtual));
    }

    @Override
    public void delete(Long idCalendario) {
        calendarioService.deleteCalendario(idCalendario);
    }







}
