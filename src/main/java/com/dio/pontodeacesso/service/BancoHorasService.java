package com.dio.pontodeacesso.service;

import com.dio.pontodeacesso.model.BancoHoras;
import com.dio.pontodeacesso.model.BancoHorasId;
import com.dio.pontodeacesso.repository.BancoHorasRepository;
import com.dio.pontodeacesso.repository.JornadaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BancoHorasService {

    BancoHorasRepository bancoHorasRepository;

    @Autowired // Faz a instanciação automática da classe
    public BancoHorasService(BancoHorasRepository bancoHorasRepository) {
        this.bancoHorasRepository = bancoHorasRepository;
    }

    public void deleteBancoHoras(BancoHorasId bancoHorasId) {
        bancoHorasRepository.deleteById(bancoHorasId);
    }


    public BancoHoras saveBancoHoras(BancoHoras bancoHoras) {
        return  bancoHorasRepository.save(bancoHoras);
    }

    public Optional<BancoHoras> getById(BancoHorasId bancoHorasId) {
        return bancoHorasRepository.findById(bancoHorasId);
    }

    public List<BancoHoras> findAll() {
        return  bancoHorasRepository.findAll();
    }

    public BancoHoras update(BancoHoras bancoHoras) {
        return bancoHorasRepository.save(bancoHoras);
    }
}
