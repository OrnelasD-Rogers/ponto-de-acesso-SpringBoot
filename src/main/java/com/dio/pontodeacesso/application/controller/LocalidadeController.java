package com.dio.pontodeacesso.application.controller;

import com.dio.pontodeacesso.application.controllerapi.LocalidadeApi;
import com.dio.pontodeacesso.application.assembler.LocalidadeModelAssembler;
import com.dio.pontodeacesso.application.assembler.LocalidadeModelDesassembler;
import com.dio.pontodeacesso.application.model.LocalidadeModel;
import com.dio.pontodeacesso.application.model.input.LocalidadeInputModel;
import com.dio.pontodeacesso.domain.model.Localidade;
import com.dio.pontodeacesso.domain.service.LocalidadeService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@RequiredArgsConstructor
@RestController
public class LocalidadeController implements LocalidadeApi {

    private final LocalidadeService localidadeService;
    private final LocalidadeModelAssembler assembler;
    private final LocalidadeModelDesassembler desassembler;


    public List<LocalidadeModel> findAll(){
        return assembler.toCollectionModel(localidadeService.findAll());
    }

    public LocalidadeModel findById(Long idLocalidade, Long idNivelAcesso){
        Localidade.LocalidadeId localidadeId = getLocalidadeId(idLocalidade, idNivelAcesso);
        return assembler.toModel(localidadeService.findById(localidadeId));
    }

    public LocalidadeModel create(LocalidadeInputModel localidadeInputModel){
        return assembler.toModel(localidadeService.save(desassembler.toDomainModel(localidadeInputModel)));
    }

    public LocalidadeModel update(Long idLocalidade, Long idNivelAcesso, LocalidadeInputModel localidadeInputModel){
        Localidade localidadeAtual = localidadeService.findById(getLocalidadeId(idLocalidade, idNivelAcesso));
        desassembler.copyToDomainModel(localidadeInputModel, localidadeAtual);
        return assembler.toModel(localidadeService.save(localidadeAtual));
    }

    public void delete(Long idLocalidade, Long idNivelAcesso){
        localidadeService.delete(getLocalidadeId(idLocalidade, idNivelAcesso));
    }

    private Localidade.LocalidadeId getLocalidadeId(Long id, Long nivel_acesso_id) {
        Localidade.LocalidadeId localidadeId = Localidade.LocalidadeId.builder()
                .id(id)
                .id_nivelAcesso(nivel_acesso_id).build();
        return localidadeId;
    }
}
