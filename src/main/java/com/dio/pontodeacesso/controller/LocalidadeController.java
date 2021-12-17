package com.dio.pontodeacesso.controller;

import com.dio.pontodeacesso.application.controllerapi.LocalidadeApi;
import com.dio.pontodeacesso.application.assembler.LocalidadeModelAssembler;
import com.dio.pontodeacesso.application.assembler.LocalidadeModelDesassembler;
import com.dio.pontodeacesso.application.model.LocalidadeModel;
import com.dio.pontodeacesso.application.model.input.LocalidadeInputModel;
import com.dio.pontodeacesso.domain.model.Localidade;
import com.dio.pontodeacesso.domain.service.LocalidadeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping("/localidade")
public class LocalidadeController implements LocalidadeApi {

    private final LocalidadeService localidadeService;
    private final LocalidadeModelAssembler assembler;
    private final LocalidadeModelDesassembler desassembler;

    @GetMapping
    @ResponseStatus(code = HttpStatus.OK)
    public List<LocalidadeModel> findAll(){
        return assembler.toCollectionModel(localidadeService.findAll());
    }

    @GetMapping("/{idLocalidade}-{idNivelAcesso}")
    @ResponseStatus(HttpStatus.OK)
    public LocalidadeModel findById(Long idLocalidade, Long idNivelAcesso){
        Localidade.LocalidadeId localidadeId = getLocalidadeId(idLocalidade, idNivelAcesso);
        return assembler.toModel(localidadeService.findById(localidadeId));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public LocalidadeModel create(LocalidadeInputModel localidadeInputModel){
        return assembler.toModel(localidadeService.save(desassembler.toDomainModel(localidadeInputModel)));
    }

    @PutMapping("/{idLocalidade}-{idNivelAcesso}")
    @ResponseStatus(HttpStatus.OK)
    public LocalidadeModel update(Long idLocalidade, Long idNivelAcesso, LocalidadeInputModel localidadeInputModel){
        Localidade localidadeAtual = localidadeService.findById(getLocalidadeId(idLocalidade, idNivelAcesso));
        desassembler.copyToDomainModel(localidadeInputModel, localidadeAtual);
        return assembler.toModel(localidadeService.save(localidadeAtual));
    }

    @DeleteMapping("/{idLocalidade}-{idNivelAcesso}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(Long idLocalidade, Long idNivelAcesso){
        localidadeService.delete(getLocalidadeId(idLocalidade, idNivelAcesso));
    }

    private Localidade.LocalidadeId getLocalidadeId(Long id, Long nivel_acesso_id) {
        return Localidade.LocalidadeId.builder()
                .id(id)
                .id_nivelAcesso(nivel_acesso_id).build();
    }
}
