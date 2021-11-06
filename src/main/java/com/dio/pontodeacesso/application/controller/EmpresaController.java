package com.dio.pontodeacesso.application.controller;

import com.dio.pontodeacesso.application.assembler.EmpresaModelAssembler;
import com.dio.pontodeacesso.application.assembler.EmpresaModelDesassembler;
import com.dio.pontodeacesso.application.model.EmpresaModel;
import com.dio.pontodeacesso.application.model.input.EmpresaInputModel;
import com.dio.pontodeacesso.domain.model.Empresa;
import com.dio.pontodeacesso.domain.service.EmpresaService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/empresa")
public class EmpresaController {


    private final EmpresaService empresaService;
    private final EmpresaModelAssembler assembler;
    private final EmpresaModelDesassembler desassembler;

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public EmpresaModel create(@Valid @RequestBody EmpresaInputModel empresaInputModel) {
        return assembler.toModel(empresaService.save(desassembler.toDomainModel(empresaInputModel)));
    }

    @ResponseStatus(HttpStatus.OK)
    @PutMapping("/{idEmpresa}")
    public EmpresaModel update(@PathVariable long idEmpresa,
                               @Valid @RequestBody EmpresaInputModel empresaInputModel) {

        Empresa empresaAtual = empresaService.findById(idEmpresa);
        desassembler.copyToDomainModel(empresaInputModel, empresaAtual);
        return assembler.toModel(empresaService.save(empresaAtual));
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{idEmpresa}")
    public void delete(@PathVariable Long idEmpresa) {
        empresaService.delete(idEmpresa);
    }

    @GetMapping("/{idEmpresa}")
    public EmpresaModel getById(@PathVariable Long idEmpresa) {
        return assembler.toModel(empresaService.findById(idEmpresa));
    }

    @GetMapping
    public List<EmpresaModel> findAll( ) {
        return assembler.toCollectionModel(empresaService.findAll());
    }
}
