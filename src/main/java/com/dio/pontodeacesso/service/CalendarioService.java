package com.dio.pontodeacesso.service;


import com.dio.pontodeacesso.model.Calendario;
import com.dio.pontodeacesso.repository.CalendarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CalendarioService {

    CalendarioRepository calendarioRepository;

    @Autowired // Faz a instanciação automática da classe
    public CalendarioService(CalendarioRepository calendarioRepository) {
        this.calendarioRepository = calendarioRepository;
    }

    public Calendario save(Calendario calendario) {
        return calendarioRepository.save(calendario);
    }

    public List<Calendario> getCalendarioList() {
        return calendarioRepository.findAll();
    }

    public Optional<Calendario> getCalendarioById(Long idCalendario) {
        return calendarioRepository.findById(idCalendario);
    }

    public Calendario update(Calendario calendario) {
        return calendarioRepository.save(calendario);
    }

    public void deleteCalendario(Long id) {
        calendarioRepository.deleteById(id);
    }
}
