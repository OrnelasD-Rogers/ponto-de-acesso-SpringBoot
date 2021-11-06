package com.dio.pontodeacesso.application.assembler;

import com.dio.pontodeacesso.application.model.CalendarioModel;
import com.dio.pontodeacesso.domain.model.Calendario;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class CalendarioModelAssembler {

    @Autowired
    ModelMapper mapper;

    public CalendarioModel toModel(Calendario calendario){
        return mapper.map(calendario, CalendarioModel.class);
    }

    public List<CalendarioModel> toCollection(List<Calendario> calendarios){
        return calendarios.stream()
                .map(this::toModel)
                .collect(Collectors.toList());
    }
}
