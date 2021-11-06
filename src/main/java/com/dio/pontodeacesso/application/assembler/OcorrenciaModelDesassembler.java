package com.dio.pontodeacesso.application.assembler;

import com.dio.pontodeacesso.application.model.input.OcorrenciaInputModel;
import com.dio.pontodeacesso.domain.model.Ocorrencia;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class OcorrenciaModelDesassembler {

    @Autowired
    ModelMapper mapper;

    public Ocorrencia toDomainModel(OcorrenciaInputModel ocorrenciaInputModel){
        return mapper.map(ocorrenciaInputModel, Ocorrencia.class);
    }

    public void copyToDomainModel(OcorrenciaInputModel ocorrenciaInputModel, Ocorrencia ocorrencia){
        mapper.map(ocorrenciaInputModel, ocorrencia);
    }
}
