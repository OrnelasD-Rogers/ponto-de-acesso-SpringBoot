package com.dio.pontodeacesso.application.controller;

import com.dio.pontodeacesso.application.assembler.TipoDataModelAssembler;
import com.dio.pontodeacesso.application.assembler.TipoDataModelDesassembler;
import com.dio.pontodeacesso.application.model.TipoDataModel;
import com.dio.pontodeacesso.application.model.input.TipoDataInputModel;
import com.dio.pontodeacesso.domain.model.TipoData;
import com.dio.pontodeacesso.domain.service.TipoDataService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;


@RequiredArgsConstructor
@RestController
@RequestMapping("/tipo-data")
public class TipoDataController {

    private final TipoDataModelDesassembler desassembler;
    private final TipoDataModelAssembler assembler;
    private final TipoDataService tipoDataService;


    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public TipoDataModel createTipoData(@Valid @RequestBody TipoDataInputModel tipoData){
        TipoData novoTipoData = tipoDataService.save(desassembler.toDomainModel(tipoData));
        return assembler.toModel(novoTipoData);
    }

    @ResponseStatus(code = HttpStatus.OK)
    @GetMapping
    public List<TipoDataModel> getTipoDataList(){
        return assembler.toCollectionModel(tipoDataService.getTipoData());
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/{idTipoData}")
    public TipoDataModel getTipoDataById(@PathVariable Long idTipoData) {
        return assembler.toModel(tipoDataService.getTipoDataById(idTipoData));
    }

    @ResponseStatus(HttpStatus.OK)
    @PutMapping("/{idTipoData}")
    public TipoDataModel updateTipoData(@PathVariable Long idTipoData,
                                        @Valid @RequestBody TipoDataInputModel tipoDataInputModel){

        TipoData tipoDataAtual = tipoDataService.getTipoDataById(idTipoData);
        desassembler.copyToDomainModel(tipoDataInputModel, tipoDataAtual);
        return assembler.toModel(tipoDataService.save(tipoDataAtual));
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{idTipoData}")
    public void deleteCalendario(@PathVariable Long idTipoData){
        tipoDataService.deleteTipoData(idTipoData);
    }

}
