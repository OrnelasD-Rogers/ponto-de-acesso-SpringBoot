package com.dio.pontodeacesso.application.assembler;

import com.dio.pontodeacesso.application.model.input.TipoDataInputModel;
import com.dio.pontodeacesso.domain.model.TipoData;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class TipoDataModelDesassembler {

    @Autowired
    ModelMapper mapper;

    public TipoData toDomainModel(TipoDataInputModel tipoDataInputModel){
        return mapper.map(tipoDataInputModel, TipoData.class);
    }

    public void copyToDomainModel(TipoDataInputModel tipoDataInputModel, TipoData tipoData){
        mapper.map(tipoDataInputModel, tipoData);
    }
}
