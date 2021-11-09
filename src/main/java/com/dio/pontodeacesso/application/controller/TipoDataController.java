package com.dio.pontodeacesso.application.controller;

import com.dio.pontodeacesso.application.controllerapi.TipoDataApi;
import com.dio.pontodeacesso.application.assembler.TipoDataModelAssembler;
import com.dio.pontodeacesso.application.assembler.TipoDataModelDesassembler;
import com.dio.pontodeacesso.application.model.TipoDataModel;
import com.dio.pontodeacesso.application.model.input.TipoDataInputModel;
import com.dio.pontodeacesso.domain.model.TipoData;
import com.dio.pontodeacesso.domain.service.TipoDataService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@RequiredArgsConstructor
@RestController
public class TipoDataController implements TipoDataApi {

    private final TipoDataModelDesassembler desassembler;
    private final TipoDataModelAssembler assembler;
    private final TipoDataService tipoDataService;

    @Override
    public List<TipoDataModel> findAll() {
        return assembler.toCollectionModel(tipoDataService.getTipoData());
    }

    @Override
    public TipoDataModel findById(Long idTipoData) {
        return assembler.toModel(tipoDataService.getTipoDataById(idTipoData));
    }

    @Override
    public TipoDataModel create(TipoDataInputModel tipoData) {
        TipoData novoTipoData = tipoDataService.save(desassembler.toDomainModel(tipoData));
        return assembler.toModel(novoTipoData);
    }

    @Override
    public TipoDataModel update(Long idTipoData, TipoDataInputModel tipoDataInputModel) {
        TipoData tipoDataAtual = tipoDataService.getTipoDataById(idTipoData);
        desassembler.copyToDomainModel(tipoDataInputModel, tipoDataAtual);
        return assembler.toModel(tipoDataService.save(tipoDataAtual));
    }

    @Override
    public void delete(Long idTipoData) {
        tipoDataService.deleteTipoData(idTipoData);
    }
}
