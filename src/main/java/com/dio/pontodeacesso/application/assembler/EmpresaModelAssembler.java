package com.dio.pontodeacesso.application.assembler;

import com.dio.pontodeacesso.application.model.EmpresaModel;
import com.dio.pontodeacesso.domain.model.Empresa;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class EmpresaModelAssembler {

    @Autowired
    ModelMapper mapper;

    public EmpresaModel toModel(Empresa empresa){
        return mapper.map(empresa, EmpresaModel.class);
    }

    public List<EmpresaModel> toCollectionModel(List<Empresa> empresaList){
        return empresaList.stream()
                .map(this::toModel)
                .collect(Collectors.toList());
    }
}
