package com.dio.pontodeacesso.controller;

import com.dio.pontodeacesso.application.assembler.TipoDataModelAssembler;
import com.dio.pontodeacesso.application.assembler.TipoDataModelDesassembler;
import com.dio.pontodeacesso.application.controllerapi.TipoDataApi;
import com.dio.pontodeacesso.application.model.TipoDataModel;
import com.dio.pontodeacesso.application.model.input.TipoDataInputModel;
import com.dio.pontodeacesso.domain.model.TipoData;
import com.dio.pontodeacesso.domain.service.TipoDataService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.stream.Collectors;

@Validated
@RestController
@RequestMapping("/tipo-data")
@RequiredArgsConstructor
public class TipoDataController implements TipoDataApi {

    private final TipoDataModelAssembler assembler;
    private final TipoDataModelDesassembler desassembler;
    private final TipoDataService tipoDataService;

    @Override
    @GetMapping
    @ResponseStatus(code = HttpStatus.OK)
    public List<TipoDataModel> findAll() {

        return assembler.toCollectionModel(tipoDataService.getTipoData());
    }


    @Override
    @GetMapping("/{idTipoData}")
    @ResponseStatus(HttpStatus.OK)
    public TipoDataModel findById(@PathVariable @NotNull Long idTipoData) {

        return assembler.toModel(tipoDataService.getTipoDataById(idTipoData));
    }

    @Override
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public TipoDataModel create(@Valid @RequestBody TipoDataInputModel tipoDataInputModel) {

        TipoData novoTipoData = desassembler.toDomainModel(tipoDataInputModel);
        return assembler.toModel(tipoDataService.save(novoTipoData));
    }

    @Override
    @PutMapping("/{idTipoData}")
    @ResponseStatus(HttpStatus.OK)
    public TipoDataModel update(@PathVariable @NotNull Long idTipoData,
                                @Valid @RequestBody TipoDataInputModel tipoDataInputModel) {

        TipoData tipoDataAtual = tipoDataService.getTipoDataById(idTipoData);
        desassembler.copyToDomainModel(tipoDataInputModel, tipoDataAtual);
        return assembler.toModel(tipoDataService.save(tipoDataAtual));
    }

    @Override
    @DeleteMapping("/{idTipoData}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable @NotNull Long idTipoData) {
        tipoDataService.deleteTipoData(idTipoData);
    }

}
