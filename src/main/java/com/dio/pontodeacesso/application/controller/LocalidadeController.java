package com.dio.pontodeacesso.application.controller;

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

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;

@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping("/localidade")
public class LocalidadeController {

    private final LocalidadeService localidadeService;
    private final LocalidadeModelAssembler assembler;
    private final LocalidadeModelDesassembler desassembler;


    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public LocalidadeModel create(@Valid @RequestBody LocalidadeInputModel localidadeInputModel){
        return assembler.toModel(localidadeService.save(desassembler.toDomainModel(localidadeInputModel)));
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping
    public List<LocalidadeModel> findAll(){
        return assembler.toCollectionModel(localidadeService.findAll());
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/{idLocalidade}-{idNivelAcesso}")
    public LocalidadeModel getCalendarioById(@PathVariable @NotNull Long idLocalidade,
                                             @PathVariable @NotNull Long idNivelAcesso){

        Localidade.LocalidadeId localidadeId = getLocalidadeId(idLocalidade, idNivelAcesso);
        return assembler.toModel(localidadeService.findById(localidadeId));
    }


    @ResponseStatus(HttpStatus.OK)
    @PutMapping("/{idLocalidade}-{idNivelAcesso}")
    public LocalidadeModel update(@PathVariable @NotNull Long idLocalidade,
                                  @PathVariable @NotNull Long idNivelAcesso,
                                  @Valid @RequestBody LocalidadeInputModel localidadeInputModel){

        Localidade localidadeAtual = localidadeService.findById(getLocalidadeId(idLocalidade, idNivelAcesso));
        desassembler.copyToDomainModel(localidadeInputModel, localidadeAtual);
        return assembler.toModel(localidadeService.save(localidadeAtual));

    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{idLocalidade}-{idNivelAcesso}")
    public void delete(@PathVariable @NotNull Long idLocalidade,
                       @PathVariable @NotNull Long idNivelAcesso){
        localidadeService.delete(getLocalidadeId(idLocalidade, idNivelAcesso));
    }

    private Localidade.LocalidadeId getLocalidadeId(Long id, Long nivel_acesso_id) {
        Localidade.LocalidadeId localidadeId = Localidade.LocalidadeId.builder()
                .id(id)
                .id_nivelAcesso(nivel_acesso_id).build();
        return localidadeId;
    }
}
