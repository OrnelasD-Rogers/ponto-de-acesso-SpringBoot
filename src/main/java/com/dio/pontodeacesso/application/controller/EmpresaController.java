package com.dio.pontodeacesso.application.controller;

import com.dio.pontodeacesso.application.controllerapi.EmpresaApi;
import com.dio.pontodeacesso.application.assembler.EmpresaModelAssembler;
import com.dio.pontodeacesso.application.assembler.EmpresaModelDesassembler;
import com.dio.pontodeacesso.application.model.EmpresaModel;
import com.dio.pontodeacesso.application.model.input.EmpresaInputModel;
import com.dio.pontodeacesso.domain.model.Empresa;
import com.dio.pontodeacesso.domain.service.EmpresaService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequiredArgsConstructor
@RestController
public class EmpresaController implements EmpresaApi {


    private final EmpresaService empresaService;
    private final EmpresaModelAssembler assembler;
    private final EmpresaModelDesassembler desassembler;

    @Override
    public List<EmpresaModel> findAll() {
        return assembler.toCollectionModel(empresaService.findAll());
    }

    @Override
    public EmpresaModel findById(Long idEmpresa) {
        return assembler.toModel(empresaService.findById(idEmpresa));
    }

    @Override
    public EmpresaModel create(EmpresaInputModel empresaInputModel) {
        return assembler.toModel(empresaService.save(desassembler.toDomainModel(empresaInputModel)));
    }

    @Override
    public EmpresaModel update(Long idEmpresa, EmpresaInputModel empresaInputModel) {
        Empresa empresaAtual = empresaService.findById(idEmpresa);
        desassembler.copyToDomainModel(empresaInputModel, empresaAtual);
        return assembler.toModel(empresaService.save(empresaAtual));
    }

    @Override
    public void delete(Long idEmpresa) {
        empresaService.delete(idEmpresa);
    }

}
