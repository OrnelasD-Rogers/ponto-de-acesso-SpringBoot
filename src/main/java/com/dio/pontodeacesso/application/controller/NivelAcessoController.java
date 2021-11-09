package com.dio.pontodeacesso.application.controller;

import com.dio.pontodeacesso.application.controllerapi.NivelAcessoApi;
import com.dio.pontodeacesso.application.assembler.NivelAcessoAssembler;
import com.dio.pontodeacesso.application.assembler.NivelAcessoDesassembler;
import com.dio.pontodeacesso.application.model.NivelAcessoModel;
import com.dio.pontodeacesso.application.model.input.NivelAcessoInputModel;
import com.dio.pontodeacesso.domain.model.NivelAcesso;
import com.dio.pontodeacesso.domain.service.NivelAcessoService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequiredArgsConstructor
@RestController
public class NivelAcessoController implements NivelAcessoApi {

    private final NivelAcessoService nivelAcessoService;
    private final NivelAcessoAssembler assembler;
    private final NivelAcessoDesassembler desassembler;

    @Override
    public List<NivelAcessoModel> findAll() {
        return assembler.toCollectionModel(nivelAcessoService.findAll());
    }

    @Override
    public NivelAcessoModel findById(Long idNivelAcesso) {
        return assembler.toModel(nivelAcessoService.findById(idNivelAcesso));
    }

    @Override
    public NivelAcessoModel create(NivelAcessoInputModel nivelAcessoInputModel) {
        return assembler.toModel(nivelAcessoService.save(desassembler.toDomainModel(nivelAcessoInputModel)));
    }

    @Override
    public NivelAcessoModel update(Long idNivelAcesso, NivelAcessoInputModel nivelAcessoInputModel) {
        NivelAcesso nivelAcesso = nivelAcessoService.findById(idNivelAcesso);
        desassembler.copyToDomainModel(nivelAcessoInputModel, nivelAcesso);
        return assembler.toModel(nivelAcessoService.save(nivelAcesso));
    }

    @Override
    public void delete(Long idNivelAcesso) {
        nivelAcessoService.delete(idNivelAcesso);
    }
}
