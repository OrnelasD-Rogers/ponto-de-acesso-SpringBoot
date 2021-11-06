package com.dio.pontodeacesso.application.controller;

import com.dio.pontodeacesso.application.assembler.NivelAcessoAssembler;
import com.dio.pontodeacesso.application.assembler.NivelAcessoDesassembler;
import com.dio.pontodeacesso.application.model.NivelAcessoModel;
import com.dio.pontodeacesso.application.model.input.NivelAcessoInputModel;
import com.dio.pontodeacesso.domain.model.NivelAcesso;
import com.dio.pontodeacesso.domain.service.NivelAcessoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/nivel-acesso")
public class NivelAcessoController {

    private final NivelAcessoService nivelAcessoService;
    private final NivelAcessoAssembler assembler;
    private final NivelAcessoDesassembler desassembler;

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public NivelAcessoModel create(@Valid @RequestBody NivelAcessoInputModel nivelAcessoInputModel){

        return assembler.toModel(nivelAcessoService.save(desassembler.toDomainModel(nivelAcessoInputModel)));
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping
    public List<NivelAcessoModel> findAll(){
        return assembler.toCollectionModel(nivelAcessoService.findAll());
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/{idNivelAcesso}")
    public NivelAcessoModel findById(@PathVariable Long idNivelAcesso) {
        return assembler.toModel(nivelAcessoService.findById(idNivelAcesso));
    }

    @ResponseStatus(HttpStatus.OK)
    @PutMapping("/{idNivelAcesso}")
    public NivelAcessoModel updateTipoData(@PathVariable long idNivelAcesso,
            @Valid @RequestBody NivelAcessoInputModel nivelAcessoInputModel){

        NivelAcesso nivelAcesso = nivelAcessoService.findById(idNivelAcesso);
        desassembler.copyToDomainModel(nivelAcessoInputModel, nivelAcesso);
        return assembler.toModel(nivelAcessoService.save(nivelAcesso));
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{idNivelAcesso}")
    public void deleteCalendario(@PathVariable Long idNivelAcesso){
        nivelAcessoService.delete(idNivelAcesso);
    }
}
