package com.dio.pontodeacesso.application.controller;

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
@RestController
@RequestMapping("/banco-horas")
public class BancoHorasController {


    private final BancoHorasService bancoHorasService;
    private final BancoHorasModelAssembler assembler;
    private final BancoHorasModelDesassembler desassembler;

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public BancoHorasModel createBancoHoras(@Valid @RequestBody BancoHorasInputModel bancoHorasInputModel){
        BancoHoras bancoHoras = desassembler.toDomainModel(bancoHorasInputModel);
        return assembler.toModel(bancoHorasService.saveBancoHoras(bancoHoras));
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/{idBancoHoras}-{idMovimento}-{idUsuario}")
    public BancoHorasModel getBancoHorasById(@PathVariable @NotNull Long idBancoHoras,
                                             @PathVariable @NotNull Long idMovimento,
                                             @PathVariable @NotNull Long idUsuario){
        return assembler.toModel(bancoHorasService.findById(getBancoHorasId(idBancoHoras,idMovimento,idUsuario)));
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping
    public List<BancoHorasModel> getBancoHorasList(){
        return assembler.toCollectionModel(bancoHorasService.findAll());
    }

    @ResponseStatus(HttpStatus.OK)
    @PutMapping("/{idBancoHoras}-{idMovimento}-{idUsuario}")
    public BancoHorasModel updateBancoHoras(@PathVariable @NotNull Long idBancoHoras,
                                            @PathVariable @NotNull Long idMovimento,
                                            @PathVariable @NotNull Long idUsuario,
                                            @Valid @RequestBody BancoHorasInputModel bancoHorasInput){

        BancoHoras.BancoHorasId bancoHorasId = getBancoHorasId(idBancoHoras, idMovimento, idUsuario);

        BancoHoras bancoHorasAtual = bancoHorasService.findById(bancoHorasId);
        desassembler.copyToDomainModel(bancoHorasInput, bancoHorasAtual);
        return assembler.toModel(bancoHorasService.saveBancoHoras(bancoHorasAtual));
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{idBancoHoras}-{idMovimento}-{idUsuario}")
    public void deleteById(@PathVariable @NotNull Long idBancoHoras,
                           @PathVariable @NotNull Long idMovimento,
                           @PathVariable @NotNull Long idUsuario){
        bancoHorasService.deleteBancoHoras(getBancoHorasId(idBancoHoras,idMovimento,idUsuario));
    }

    private BancoHoras.BancoHorasId getBancoHorasId(Long idBancoHoras, Long idMovimento, Long idUsuario) {
        return BancoHoras.BancoHorasId.builder()
                .id_bancoHoras(idBancoHoras)
                .id_movimentacao(idMovimento)
                .id_usuario(idUsuario).build();
    }

}
