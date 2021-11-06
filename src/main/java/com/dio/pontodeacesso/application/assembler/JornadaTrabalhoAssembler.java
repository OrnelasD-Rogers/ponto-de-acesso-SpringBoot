package com.dio.pontodeacesso.application.assembler;

import com.dio.pontodeacesso.application.model.JornadaTrabalhoModel;
import com.dio.pontodeacesso.domain.model.JornadaTrabalho;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class JornadaTrabalhoAssembler {

    @Autowired
    ModelMapper mapper;

    public JornadaTrabalhoModel toModel(JornadaTrabalho jornadaTrabalho){
        return mapper.map(jornadaTrabalho, JornadaTrabalhoModel.class);
    }

    public List<JornadaTrabalhoModel> toCollectionModel(List<JornadaTrabalho> jornadaTrabalhoList){
        return jornadaTrabalhoList.stream()
                .map(this::toModel)
                .collect(Collectors.toList());
    }
}
