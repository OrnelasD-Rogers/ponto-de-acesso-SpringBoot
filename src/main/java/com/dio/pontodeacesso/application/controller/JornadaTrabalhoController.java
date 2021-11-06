package com.dio.pontodeacesso.application.controller;

import com.dio.pontodeacesso.application.assembler.JornadaTrabalhoAssembler;
import com.dio.pontodeacesso.application.assembler.JornadaTrabalhoDesassembler;
import com.dio.pontodeacesso.application.model.JornadaTrabalhoModel;
import com.dio.pontodeacesso.application.model.input.JornadaTrabalhoInputModel;
import com.dio.pontodeacesso.domain.model.JornadaTrabalho;
import com.dio.pontodeacesso.domain.service.JornadaService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/jornada")
public class JornadaTrabalhoController {


    private final JornadaService jornadaService;
    private final JornadaTrabalhoAssembler assembler;
    private final JornadaTrabalhoDesassembler desassembler;


    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public JornadaTrabalhoModel create(@Valid @RequestBody JornadaTrabalhoInputModel jornadaTrabalhoInputModel) {

        JornadaTrabalho jornadaTrabalho = desassembler.toDomainModel(jornadaTrabalhoInputModel);
        return assembler.toModel(jornadaService.save(jornadaTrabalho));
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/{idJornada}")
    public JornadaTrabalhoModel getById(@PathVariable Long idJornada) {
        return assembler.toModel(jornadaService.findById(idJornada));
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping
    public List<JornadaTrabalhoModel> getAll() {
        return assembler.toCollectionModel(jornadaService.findAll());
    }


    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{idJornada}")
    public void delete(@PathVariable Long idJornada) {
        jornadaService.delete(idJornada);
    }

    @PutMapping("/{idJornada}")
    public JornadaTrabalhoModel update(@PathVariable long idJornada,
                                       @Valid @RequestBody JornadaTrabalhoInputModel jornadaTrabalhoInputModel) {
        JornadaTrabalho jornadaTrabalhoAtual = jornadaService.findById(idJornada);
        desassembler.copyToDomainModel(jornadaTrabalhoInputModel,jornadaTrabalhoAtual);
        return assembler.toModel(jornadaService.save(jornadaTrabalhoAtual));
    }

}
