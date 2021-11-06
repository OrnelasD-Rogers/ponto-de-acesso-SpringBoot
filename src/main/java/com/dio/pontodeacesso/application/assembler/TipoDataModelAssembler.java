package com.dio.pontodeacesso.application.assembler;

import com.dio.pontodeacesso.application.model.TipoDataModel;
import com.dio.pontodeacesso.domain.model.TipoData;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class TipoDataModelAssembler {

    @Autowired
    ModelMapper mapper;

    public TipoDataModel toModel(TipoData tipoData){
        return mapper.map(tipoData, TipoDataModel.class);
    }

    public List<TipoDataModel> toCollectionModel(List<TipoData> tipoDataList){
        return tipoDataList.stream()
                .map(this::toModel)
                .collect(Collectors.toList());
    }
}
