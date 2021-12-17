package com.dio.pontodeacesso.repository;

import com.dio.pontodeacesso.util.DataBaseManipulator.DatabaseCleaner;
import org.flywaydb.core.Flyway;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class MySqlTest {

    //Classe utilitária que faz a limpeza dos dados do banco
    @Autowired
    public DatabaseCleaner databaseCleaner;

    @Autowired
    Flyway flyway;

    @BeforeEach
    void setUp() {
        databaseCleaner.clearTables();
        //Após a limpeza é feita a migração e adicionado uma base de dados fresca para manter a qualidade dos testes
        flyway.migrate();
    }
}
