package com.dio.pontodeacesso.controller;

import com.dio.pontodeacesso.application.controllerapi.BancoHorasApi;
import com.dio.pontodeacesso.application.assembler.BancoHorasModelAssembler;
import com.dio.pontodeacesso.application.assembler.BancoHorasModelDesassembler;
import com.dio.pontodeacesso.application.model.BancoHorasModel;
import com.dio.pontodeacesso.application.model.input.BancoHorasInputModel;
import com.dio.pontodeacesso.domain.model.BancoHoras;
import com.dio.pontodeacesso.domain.service.BancoHorasService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;

@Validated
@RequiredArgsConstructor
@RequestMapping("/banco-horas")
@RestController
public class BancoHorasController implements BancoHorasApi {


    private final BancoHorasService bancoHorasService;
    private final BancoHorasModelAssembler assembler;
    private final BancoHorasModelDesassembler desassembler;

    @Override
    @ResponseStatus(HttpStatus.OK)
    @GetMapping
    public List<BancoHorasModel> findAll(){
        return assembler.toCollectionModel(bancoHorasService.findAll());
    }

    @Override
    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/{idBancoHoras}-{idMovimento}-{idUsuario}")
    public BancoHorasModel findById(@PathVariable @NotNull Long idBancoHoras,
                                    @PathVariable @NotNull Long idMovimento,
                                    @PathVariable @NotNull Long idUsuario){
        return assembler.toModel(bancoHorasService.findById(getBancoHorasId(idBancoHoras,idMovimento,idUsuario)));
    }

    @Override
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public BancoHorasModel create(@Valid @RequestBody BancoHorasInputModel bancoHorasInputModel){
        BancoHoras bancoHoras = desassembler.toDomainModel(bancoHorasInputModel);
        return assembler.toModel(bancoHorasService.save(bancoHoras));
    }

    @Override
    @ResponseStatus(HttpStatus.OK)
    @PutMapping("/{idBancoHoras}-{idMovimento}-{idUsuario}")
    public BancoHorasModel update(@PathVariable @NotNull Long idBancoHoras,
                                  @PathVariable @NotNull Long idMovimento,
                                  @PathVariable @NotNull Long idUsuario,
                                  @Valid @RequestBody BancoHorasInputModel bancoHorasInput){

        BancoHoras.BancoHorasId bancoHorasId = getBancoHorasId(idBancoHoras, idMovimento, idUsuario);

        BancoHoras bancoHorasAtual = bancoHorasService.findById(bancoHorasId);
        desassembler.copyToDomainModel(bancoHorasInput, bancoHorasAtual);
        return assembler.toModel(bancoHorasService.save(bancoHorasAtual));
    }

    @Override
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{idBancoHoras}-{idMovimento}-{idUsuario}")
    public void delete(@PathVariable @NotNull Long idBancoHoras,
                       @PathVariable @NotNull Long idMovimento,
                       @PathVariable @NotNull Long idUsuario){
        bancoHorasService.delete(getBancoHorasId(idBancoHoras,idMovimento,idUsuario));
    }

    private BancoHoras.BancoHorasId getBancoHorasId(Long idBancoHoras, Long idMovimento, Long idUsuario) {
        return BancoHoras.BancoHorasId.builder()
                .id_bancoHoras(idBancoHoras)
                .id_movimentacao(idMovimento)
                .id_usuario(idUsuario).build();
    }

}
