package com.dio.pontodeacesso.application.assembler;

import com.dio.pontodeacesso.application.model.input.EmpresaInputModel;
import com.dio.pontodeacesso.domain.model.Empresa;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class EmpresaModelDesassembler {

    @Autowired
    ModelMapper mapper;

    public Empresa toDomainModel(EmpresaInputModel empresaInputModel){
        return mapper.map(empresaInputModel, Empresa.class);
    }

    public void copyToDomainModel(EmpresaInputModel empresaInputModel, Empresa empresa){
        mapper.map(empresaInputModel, empresa);
    }
}
