package com.dio.pontodeacesso.application.assembler;

import com.dio.pontodeacesso.application.model.OcorrenciaModel;
import com.dio.pontodeacesso.domain.model.Ocorrencia;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class OcorrenciaModelAssembler {

    @Autowired
    ModelMapper mapper;

    public OcorrenciaModel toModel(Ocorrencia ocorrencia){
        return mapper.map(ocorrencia, OcorrenciaModel.class);
    }

    public List<OcorrenciaModel> toCollectionModel(List<Ocorrencia> ocorrencias){
        return ocorrencias.stream()
                .map(this::toModel)
                .collect(Collectors.toList());
    }
}
