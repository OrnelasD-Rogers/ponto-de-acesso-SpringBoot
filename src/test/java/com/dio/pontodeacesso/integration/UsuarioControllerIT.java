package com.dio.pontodeacesso.integration;

import com.dio.pontodeacesso.application.exceptionhandler.ProblemaDetails;
import com.dio.pontodeacesso.application.model.UsuarioModel;
import com.dio.pontodeacesso.application.model.input.UsuarioInputModel;
import com.dio.pontodeacesso.domain.model.Usuario;
import com.dio.pontodeacesso.repository.MySqlTest;
import com.dio.pontodeacesso.repository.UsuarioRepository;
import com.dio.pontodeacesso.util.UsuarioCreator;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.TestPropertySource;

import java.util.List;

@TestPropertySource("/application-test.properties")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class UsuarioControllerIT extends MySqlTest {

    private static final Long ID_INEXISTENTE = 100L;

    @Autowired
    private TestRestTemplate testRestTemplate;
    @Autowired
    private UsuarioRepository usuarioRepository;

    @Test
    void findAll_ReturnsListOfUsuarios_WhenSuccessful(){

        int usuarioCount = (int) usuarioRepository.count();

        ResponseEntity<List<UsuarioModel>> usuarioListResponseEntity = testRestTemplate.exchange("/usuario", HttpMethod.GET, null,
                new ParameterizedTypeReference<>() {
                });

        Assertions.assertThat(usuarioListResponseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);

        Assertions.assertThat(usuarioListResponseEntity.getBody())
                .isNotNull()
                .isNotEmpty()
                .hasSize(usuarioCount);
    }

    @Test
    void findById_ReturnsUsuarioModel_WhenSuccessful() {
        Usuario savedUsuario = usuarioRepository.save(UsuarioCreator.createValidUsuario());

        Long usuarioId = savedUsuario.getId_usuario();

        ResponseEntity<UsuarioModel> usuarioModelResponseEntity = testRestTemplate.getForEntity(
                "/usuario/{idUsuario}", UsuarioModel.class, usuarioId);

        Assertions.assertThat(usuarioModelResponseEntity).isNotNull();

        Assertions.assertThat(usuarioModelResponseEntity.getBody()).isNotNull();

        Assertions.assertThat(usuarioModelResponseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);

        Assertions.assertThat(usuarioModelResponseEntity.getBody().getId_usuario()).isEqualTo(usuarioId);
    }

    @Test
    void findById_ReturnsProblemaDetails_WhenUsuarioIdWasNotFound() {

        ResponseEntity<ProblemaDetails> problemaResponseEntity = testRestTemplate.getForEntity(
                "/usuario/{idUsuario}", ProblemaDetails.class, ID_INEXISTENTE);

        Assertions.assertThat(problemaResponseEntity).isNotNull();

        Assertions.assertThat(problemaResponseEntity.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);

        Assertions.assertThat(problemaResponseEntity.getBody()).isNotNull();
    }

    @Test
    void create_ReturnsUsuarioModel_WhenSuccessful() {

        ResponseEntity<UsuarioModel> usuarioResponse = testRestTemplate
                .postForEntity("/usuario", UsuarioCreator.createValidUsuarioInputModel(), UsuarioModel.class);

        Assertions.assertThat(usuarioResponse).isNotNull();

        Assertions.assertThat(usuarioResponse.getStatusCode()).isEqualTo(HttpStatus.CREATED);

        Assertions.assertThat(usuarioResponse.getBody().getId_usuario())
                .isNotNull();
    }

    @Test
    void create_ReturnsProblemDetailsWith404StatusCode_WhenEmpresaWasNotFound(){

        ResponseEntity<ProblemaDetails> detailsResponseEntity = testRestTemplate
                .postForEntity("/usuario", UsuarioCreator.createInvalidFkEmpresaForUsuarioInputModel(), ProblemaDetails.class);


        Assertions.assertThat(detailsResponseEntity).isNotNull();

        Assertions.assertThat(detailsResponseEntity.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);

        Assertions.assertThat(detailsResponseEntity.getBody())
                .isNotNull();

        Assertions.assertThat(detailsResponseEntity.getBody().getDetalhes()).containsIgnoringCase("empresa");
    }
}
