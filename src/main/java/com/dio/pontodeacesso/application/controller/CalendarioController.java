package com.dio.pontodeacesso.application.controller;


import com.dio.pontodeacesso.application.assembler.CalendarioModelAssembler;
import com.dio.pontodeacesso.application.assembler.CalendarioModelDesassembler;
import com.dio.pontodeacesso.application.model.CalendarioModel;
import com.dio.pontodeacesso.application.model.input.CalendarioInputModel;
import com.dio.pontodeacesso.domain.model.Calendario;
import com.dio.pontodeacesso.domain.service.CalendarioService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("/calendario")
public class CalendarioController {


    CalendarioService calendarioService;
    CalendarioModelAssembler assembler;
    CalendarioModelDesassembler desassembler;

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public CalendarioModel createCalendario(@Valid @RequestBody CalendarioInputModel calendarioInput){
        Calendario calendario = desassembler.toDomainModel(calendarioInput);
        return assembler.toModel(calendarioService.save(calendario));
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping
    public List<CalendarioModel> getCalendarioList(){
        return assembler.toCollection(calendarioService.findAllCalendario());
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/{idCalendario}")
    public CalendarioModel getCalendarioById(@PathVariable Long idCalendario){
        return assembler.toModel(calendarioService.findCalendarioById(idCalendario));
    }

    @ResponseStatus(HttpStatus.OK)
    @PutMapping("/{idCalendario}")
    public CalendarioModel updateCalendario(@PathVariable Long idCalendario,
                                            @Valid @RequestBody CalendarioInputModel calendarioInput){

        Calendario calendarioAtual = calendarioService.findCalendarioById(idCalendario);
        desassembler.copyToDomainModel(calendarioInput, calendarioAtual);
        return assembler.toModel(calendarioService.save(calendarioAtual));
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{idCalendario}")
    public void deleteCalendario(@PathVariable Long idCalendario){
        calendarioService.deleteCalendario(idCalendario);
    }
}
