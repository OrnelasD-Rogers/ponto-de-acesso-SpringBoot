package com.dio.pontodeacesso.service;

import com.dio.pontodeacesso.model.JornadaTrabalho;
import com.dio.pontodeacesso.repository.JornadaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class JornadaService {

    JornadaRepository jornadaRepository;

    @Autowired // Faz a instanciação automática da classe
    public JornadaService(JornadaRepository jornadaRepository) {
        this.jornadaRepository = jornadaRepository;
    }

    //Salva a entidade no banco através da interface JornadaRepository
    public JornadaTrabalho save(JornadaTrabalho jornadaTrabalho){
        return jornadaRepository.save(jornadaTrabalho);
    }

    //Pega a lista de JornadaTrabalho no banco através da interface JornadaRepository
    public List<JornadaTrabalho> findAll(){
        return  jornadaRepository.findAll();
    }

    //Pega a Entidade JornadaTrabalho pelo id no banco através da interface JornadaRepository
    public Optional<JornadaTrabalho> findById(Long idJornada){
        return jornadaRepository.findById(idJornada);
    }


    public void delete(Long idJornada){
        jornadaRepository.deleteById(idJornada);
    }

    public JornadaTrabalho update(JornadaTrabalho jornadaTrabalho){
        return jornadaRepository.save(jornadaTrabalho);
    }
}
