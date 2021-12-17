package com.dio.pontodeacesso.controller;

import com.dio.pontodeacesso.application.assembler.UsuarioModelAssembler;
import com.dio.pontodeacesso.application.assembler.UsuarioModelDesassembler;
import com.dio.pontodeacesso.application.model.UsuarioModel;
import com.dio.pontodeacesso.application.model.input.UsuarioInputModel;
import com.dio.pontodeacesso.domain.exception.EntidadeEmUsoException;
import com.dio.pontodeacesso.domain.exception.UsuarioNaoEncontradoException;
import com.dio.pontodeacesso.domain.model.Usuario;
import com.dio.pontodeacesso.domain.service.UsuarioService;
import com.dio.pontodeacesso.util.UsuarioCreator;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.util.List;

import static com.dio.pontodeacesso.application.exceptionhandler.ApiExceptionHandler.MSG_ERRO_GENERICA_USUARIO_FINAL;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@WebMvcTest(controllers = UsuarioController.class)
class UsuarioControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @MockBean
    private UsuarioService usuarioService;
    @MockBean
    private UsuarioModelAssembler assembler;
    @MockBean
    private UsuarioModelDesassembler desassembler;

    private final String URL = "/usuario";

    private final Long validUserId = 10L;

    @Test
    void findAll_Returns200AndListOfUsuarioModel_WhenSuccessful() throws Exception {

        List<Usuario> usuarioList = List.of(UsuarioCreator.createValidUsuario());

        List<UsuarioModel> usuarioModelList = List.of(UsuarioCreator.returnValidUsuarioModel());

        when(usuarioService.findAll()).thenReturn(usuarioList);

        when(assembler.toCollectionModel(usuarioList)).thenReturn(usuarioModelList);

        MvcResult mvcResult = mockMvc.perform(get(URL))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        String actualResponseBody = mvcResult.getResponse().getContentAsString();

        String expectedResponseBody = objectMapper.writeValueAsString(usuarioModelList);

        assertThat(actualResponseBody)
                .isEqualToIgnoringWhitespace(expectedResponseBody);

    }

    @Test
    void findById_Returns200AndUsuarioModel_WhenSuccessful() throws Exception {

        Usuario usuario = UsuarioCreator.createValidUsuario();

        UsuarioModel usuarioModel = UsuarioCreator.returnValidUsuarioModel();

        when(usuarioService.findById(ArgumentMatchers.anyLong())).thenReturn(usuario);

        when(assembler.toModel(ArgumentMatchers.any())).thenReturn(usuarioModel);

        MvcResult mvcResult = mockMvc.perform(get(URL + "/{idUsuario}", validUserId))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        String actualResponseBody = mvcResult.getResponse().getContentAsString();

        String expectedResponseBody = objectMapper.writeValueAsString(usuarioModel);

        assertThat(actualResponseBody)
                .isEqualToIgnoringWhitespace(expectedResponseBody);
    }

    @Test
    void findById_Returns400AndErrorMessage_WhenIdUsuarioIsNotALongType() throws Exception {

        MvcResult mvcResult = mockMvc.perform(get(URL + "/{idUsuario}", "x"))
                .andExpect(status().isBadRequest())
                .andReturn();

        String actualResponseBody = mvcResult.getResponse().getContentAsString();

        assertThat(actualResponseBody).containsIgnoringCase(MSG_ERRO_GENERICA_USUARIO_FINAL);
    }

    @Test
    void findById_Returns404AndProblemDetails_WhenUsuarioIsNotFound() throws Exception {

        when(usuarioService.findById(anyLong())).thenThrow(new UsuarioNaoEncontradoException(validUserId));

        MvcResult mvcResult = mockMvc.perform(get(URL + "/{idUsuario}", validUserId))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andReturn();

        String actualResponseBody = mvcResult.getResponse().getContentAsString();

        String expectedResponseBody = String.format("O usuÃ¡rio com o id %d nÃ£o foi encotnrado", validUserId);

        assertThat(actualResponseBody)
                .containsIgnoringCase(expectedResponseBody);
    }

    @Test
    void create_Returns201AndUsuarioModel_WhenSuccessful() throws Exception {

        Usuario usuario = UsuarioCreator.createValidUsuario();

        UsuarioInputModel usuarioInputModel = UsuarioCreator.createValidUsuarioInputModel();

        UsuarioModel usuarioModel = UsuarioCreator.returnValidUsuarioModel();

        when(desassembler.toDomainModel(any())).thenReturn(usuario);

        when(assembler.toModel(any())).thenReturn(usuarioModel);

        when(usuarioService.save(any(Usuario.class))).thenReturn(usuario);

        MvcResult mvcResult = mockMvc.perform(post(URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(usuarioInputModel)))
                .andExpect(status().isCreated())
                .andReturn();

        String actualResponseBody = mvcResult.getResponse().getContentAsString();

        String expectedResponse = objectMapper.writeValueAsString(usuarioModel);

        assertThat(actualResponseBody).containsIgnoringCase(expectedResponse);
    }

    @Test
    void create_Returns400AndProblemDetails_WhenNomeIsBlank() throws Exception {

        UsuarioInputModel usuarioInputModel = UsuarioCreator.createInvalidUsuarioNomeInputModel();

        MvcResult mvcResult = mockMvc.perform(post(URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(usuarioInputModel)))
                .andExpect(status().isBadRequest())
                .andReturn();

        String actualResponseBody = mvcResult.getResponse().getContentAsString();

        String expectedResponse = "objetos\":[{\"nome\":\"nome\",\"mensagemUsuario\":\"must not be blank\"}]";

        assertThat(actualResponseBody).containsIgnoringCase(expectedResponse);
    }

    @Test
    void create_Returns400AndProblemDetails_WhenToleranciaIsNull() throws Exception {

        UsuarioInputModel usuarioInputModel = UsuarioCreator.createInvalidToleranciaForUsuarioInputModel();

        MvcResult mvcResult = mockMvc.perform(post(URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(usuarioInputModel)))
                .andExpect(status().isBadRequest())
                .andReturn();

        String actualResponseBody = mvcResult.getResponse().getContentAsString();

        String expectedResponse = "objetos\":[{\"nome\":\"tolerancia\",\"mensagemUsuario\":\"must not be null\"}]}";

        assertThat(actualResponseBody).containsIgnoringCase(expectedResponse);
    }

    @Test
    void create_Returns400AndProblemDetails_WhenInicioJornadaIsNull() throws Exception {

        UsuarioInputModel usuarioInputModel = UsuarioCreator.createInvalidIniciojornadaForUsuarioInputModel();

        MvcResult mvcResult = mockMvc.perform(post(URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(usuarioInputModel)))
                .andExpect(status().isBadRequest())
                .andReturn();

        String actualResponseBody = mvcResult.getResponse().getContentAsString();

        String expectedResponse = "objetos\":[{\"nome\":\"inicioJornada\",\"mensagemUsuario\":\"must not be null\"}]}";

        assertThat(actualResponseBody).containsIgnoringCase(expectedResponse);
    }

    @Test
    void create_Returns400AndProblemDetails_WhenFinalJornadaIsNull() throws Exception {

        UsuarioInputModel usuarioInputModel = UsuarioCreator.createInvalidFinaljornadaForUsuarioInputModel();

        MvcResult mvcResult = mockMvc.perform(post(URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(usuarioInputModel)))
                .andExpect(status().isBadRequest())
                .andReturn();

        String actualResponseBody = mvcResult.getResponse().getContentAsString();

        String expectedResponse = "objetos\":[{\"nome\":\"finalJornada\",\"mensagemUsuario\":\"must not be null\"}]}";

        assertThat(actualResponseBody).containsIgnoringCase(expectedResponse);
    }

    @Test
    void create_Returns400AndProblemDetails_WhenCategoriaUsuarioIsNull() throws Exception {

        UsuarioInputModel usuarioInputModel = UsuarioCreator.createInvalidCategoriaUsuarioForUsuarioInputModel();

        MvcResult mvcResult = mockMvc.perform(post(URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(usuarioInputModel)))
                .andExpect(status().isBadRequest())
                .andReturn();

        String actualResponseBody = mvcResult.getResponse().getContentAsString();

        String expectedResponse = "objetos\":[{\"nome\":\"categoriaUsuario.id_cat_usuario\",\"mensagemUsuario\":\"must not be null\"}]}";

        assertThat(actualResponseBody).containsIgnoringCase(expectedResponse);
    }

    @Test
    void create_Returns400AndProblemDetails_WhenEmpresaIsNull() throws Exception {

        UsuarioInputModel usuarioInputModel = UsuarioCreator.createInvalidEmpresaForUsuarioInputModel();

        MvcResult mvcResult = mockMvc.perform(post(URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(usuarioInputModel)))
                .andExpect(status().isBadRequest())
                .andReturn();

        String actualResponseBody = mvcResult.getResponse().getContentAsString();

        String expectedResponse = "objetos\":[{\"nome\":\"empresa.id_empresa\",\"mensagemUsuario\":\"must not be null\"}]}";

        assertThat(actualResponseBody).containsIgnoringCase(expectedResponse);
    }

    @Test
    void create_Returns400AndProblemDetails_WhenNivelAcessoIsNull() throws Exception {

        UsuarioInputModel usuarioInputModel = UsuarioCreator.createInvalidNivelAcessoForUsuarioInputModel();

        MvcResult mvcResult = mockMvc.perform(post(URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(usuarioInputModel)))
                .andExpect(status().isBadRequest())
                .andReturn();

        String actualResponseBody = mvcResult.getResponse().getContentAsString();

        String expectedResponse = "objetos\":[{\"nome\":\"nivelAcesso.id_nivelAcesso\",\"mensagemUsuario\":\"must not be null\"}]}";

        assertThat(actualResponseBody).containsIgnoringCase(expectedResponse);
    }

    @Test
    void create_Returns400AndProblemDetails_WhenJornadaTrabalhoIsNull() throws Exception {

        UsuarioInputModel usuarioInputModel = UsuarioCreator.createInvalidJornadaTrabalhoForUsuarioInputModel();

        MvcResult mvcResult = mockMvc.perform(post(URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(usuarioInputModel)))
                .andExpect(status().isBadRequest())
                .andReturn();

        String actualResponseBody = mvcResult.getResponse().getContentAsString();

        String expectedResponse = "objetos\":[{\"nome\":\"jornadaTrabalho.id_jornada_trabalho\",\"mensagemUsuario\":\"must not be null\"}]}";

        assertThat(actualResponseBody).containsIgnoringCase(expectedResponse);
    }

    //////////////////////

    @Test
    void update_Returns200AndUsuarioModel_WhenSuccessful() throws Exception {

        Usuario usuario = UsuarioCreator.createValidUsuario();

        UsuarioInputModel usuarioInputModel = UsuarioCreator.createValidUsuarioInputModel();

        UsuarioModel usuarioModel = UsuarioCreator.returnValidUsuarioModel();

        when(desassembler.toDomainModel(any())).thenReturn(usuario);

        when(assembler.toModel(any())).thenReturn(usuarioModel);

        when(usuarioService.save(any(Usuario.class))).thenReturn(usuario);

        when(usuarioService.findById(anyLong())).thenReturn(usuario);

        MvcResult mvcResult = mockMvc.perform(put(URL + "/{idUsuario}", validUserId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(usuarioInputModel)))
                .andExpect(status().isOk())
                .andReturn();

        String actualResponseBody = mvcResult.getResponse().getContentAsString();

        String expectedResponse = objectMapper.writeValueAsString(usuarioModel);

        assertThat(actualResponseBody).containsIgnoringCase(expectedResponse);
    }

    @Test
    void update_Returns400AndProblemDetails_WhenNomeIsBlank() throws Exception {

        UsuarioInputModel usuarioInputModel = UsuarioCreator.createInvalidUsuarioNomeInputModel();

        MvcResult mvcResult = mockMvc.perform(put(URL + "/{idUsuario}", validUserId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(usuarioInputModel)))
                .andExpect(status().isBadRequest())
                .andReturn();

        String actualResponseBody = mvcResult.getResponse().getContentAsString();

        String expectedResponse = "objetos\":[{\"nome\":\"nome\",\"mensagemUsuario\":\"must not be blank\"}]";

        assertThat(actualResponseBody).containsIgnoringCase(expectedResponse);
    }

    @Test
    void update_Returns400AndProblemDetails_WhenToleranciaIsNull() throws Exception {

        UsuarioInputModel usuarioInputModel = UsuarioCreator.createInvalidToleranciaForUsuarioInputModel();

        MvcResult mvcResult = mockMvc.perform(put(URL + "/{idUsuario}", validUserId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(usuarioInputModel)))
                .andExpect(status().isBadRequest())
                .andReturn();

        String actualResponseBody = mvcResult.getResponse().getContentAsString();

        String expectedResponse = "objetos\":[{\"nome\":\"tolerancia\",\"mensagemUsuario\":\"must not be null\"}]}";

        assertThat(actualResponseBody).containsIgnoringCase(expectedResponse);
    }

    @Test
    void update_Returns400AndProblemDetails_WhenInicioJornadaIsNull() throws Exception {

        UsuarioInputModel usuarioInputModel = UsuarioCreator.createInvalidIniciojornadaForUsuarioInputModel();

        MvcResult mvcResult = mockMvc.perform(put(URL + "/{idUsuario}", validUserId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(usuarioInputModel)))
                .andExpect(status().isBadRequest())
                .andReturn();

        String actualResponseBody = mvcResult.getResponse().getContentAsString();

        String expectedResponse = "objetos\":[{\"nome\":\"inicioJornada\",\"mensagemUsuario\":\"must not be null\"}]}";

        assertThat(actualResponseBody).containsIgnoringCase(expectedResponse);
    }

    @Test
    void update_Returns400AndProblemDetails_WhenFinalJornadaIsNull() throws Exception {

        UsuarioInputModel usuarioInputModel = UsuarioCreator.createInvalidFinaljornadaForUsuarioInputModel();

        MvcResult mvcResult = mockMvc.perform(put(URL + "/{idUsuario}", validUserId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(usuarioInputModel)))
                .andExpect(status().isBadRequest())
                .andReturn();

        String actualResponseBody = mvcResult.getResponse().getContentAsString();

        String expectedResponse = "objetos\":[{\"nome\":\"finalJornada\",\"mensagemUsuario\":\"must not be null\"}]}";

        assertThat(actualResponseBody).containsIgnoringCase(expectedResponse);
    }

    @Test
    void update_Returns400AndProblemDetails_WhenCategoriaUsuarioIsNull() throws Exception {

        UsuarioInputModel usuarioInputModel = UsuarioCreator.createInvalidCategoriaUsuarioForUsuarioInputModel();

        MvcResult mvcResult = mockMvc.perform(put(URL + "/{idUsuario}", validUserId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(usuarioInputModel)))
                .andExpect(status().isBadRequest())
                .andReturn();

        String actualResponseBody = mvcResult.getResponse().getContentAsString();

        String expectedResponse = "objetos\":[{\"nome\":\"categoriaUsuario.id_cat_usuario\",\"mensagemUsuario\":\"must not be null\"}]}";

        assertThat(actualResponseBody).containsIgnoringCase(expectedResponse);
    }

    @Test
    void update_Returns400AndProblemDetails_WhenEmpresaIsNull() throws Exception {

        UsuarioInputModel usuarioInputModel = UsuarioCreator.createInvalidEmpresaForUsuarioInputModel();

        MvcResult mvcResult = mockMvc.perform(put(URL + "/{idUsuario}", validUserId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(usuarioInputModel)))
                .andExpect(status().isBadRequest())
                .andReturn();

        String actualResponseBody = mvcResult.getResponse().getContentAsString();

        String expectedResponse = "objetos\":[{\"nome\":\"empresa.id_empresa\",\"mensagemUsuario\":\"must not be null\"}]}";

        assertThat(actualResponseBody).containsIgnoringCase(expectedResponse);
    }

    @Test
    void update_Returns400AndProblemDetails_WhenNivelAcessoIsNull() throws Exception {

        UsuarioInputModel usuarioInputModel = UsuarioCreator.createInvalidNivelAcessoForUsuarioInputModel();

        MvcResult mvcResult = mockMvc.perform(put(URL + "/{idUsuario}", validUserId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(usuarioInputModel)))
                .andExpect(status().isBadRequest())
                .andReturn();

        String actualResponseBody = mvcResult.getResponse().getContentAsString();

        String expectedResponse = "objetos\":[{\"nome\":\"nivelAcesso.id_nivelAcesso\",\"mensagemUsuario\":\"must not be null\"}]}";

        assertThat(actualResponseBody).containsIgnoringCase(expectedResponse);
    }

    @Test
    void update_Returns400AndProblemDetails_WhenJornadaTrabalhoIsNull() throws Exception {

        UsuarioInputModel usuarioInputModel = UsuarioCreator.createInvalidJornadaTrabalhoForUsuarioInputModel();

        MvcResult mvcResult = mockMvc.perform(put(URL + "/{idUsuario}", validUserId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(usuarioInputModel)))
                .andExpect(status().isBadRequest())
                .andReturn();

        String actualResponseBody = mvcResult.getResponse().getContentAsString();

        String expectedResponse = "objetos\":[{\"nome\":\"jornadaTrabalho.id_jornada_trabalho\",\"mensagemUsuario\":\"must not be null\"}]}";

        assertThat(actualResponseBody).containsIgnoringCase(expectedResponse);
    }



    @Test
    void update_Returns400AndErrorMessage_WhenIdUsuarioIsNotALongType() throws Exception {

        UsuarioInputModel usuarioInputModel = UsuarioCreator.createInvalidJornadaTrabalhoForUsuarioInputModel();

        MvcResult mvcResult = mockMvc.perform(put(URL + "/{idUsuario}", "x")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(usuarioInputModel)))
                .andExpect(status().isBadRequest())
                .andReturn();

        String actualResponseBody = mvcResult.getResponse().getContentAsString();

        assertThat(actualResponseBody).containsIgnoringCase(MSG_ERRO_GENERICA_USUARIO_FINAL);
    }

    @Test
    void update_Returns404AndProblemDetails_WhenUsuarioIsNotFound() throws Exception {

        UsuarioInputModel usuarioInputModel = UsuarioCreator.createValidUsuarioInputModel();

        when(usuarioService.findById(anyLong())).thenThrow(new UsuarioNaoEncontradoException(validUserId));

        MvcResult mvcResult = mockMvc.perform(put(URL + "/{idUsuario}", validUserId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(usuarioInputModel)))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andReturn();

        String actualResponseBody = mvcResult.getResponse().getContentAsString();

        String expectedResponseBody = String.format("O usuÃ¡rio com o id %d nÃ£o foi encotnrado", validUserId);

        assertThat(actualResponseBody)
                .containsIgnoringCase(expectedResponseBody);
    }

    @Test
    void delete_Returns204_WhenSuccessful() throws Exception{

        MvcResult mvcResult = mockMvc.perform(delete(URL + "/{idUsuario}", validUserId))
                .andExpect(status().isNoContent())
                .andReturn();
    }

    @Test
    void delete_Returns404AndProblemDetails_WhenUsuarioIsNotFound() throws Exception{

        doThrow(new UsuarioNaoEncontradoException(validUserId))
                .when(usuarioService).delete(validUserId);

        MvcResult mvcResult = mockMvc.perform(delete(URL + "/{idUsuario}", validUserId))
                .andExpect(status().isNotFound())
                .andReturn();

        String actualResponseBody = mvcResult.getResponse().getContentAsString();

        String expectedDetails = String.format("O usuÃ¡rio com o id %d nÃ£o foi encotnrado", validUserId);

        assertThat(actualResponseBody).containsIgnoringCase(expectedDetails);
    }

    @Test
    void delete_Returns409AndProblemDetails_WhenNivelAcessoIsInUse() throws Exception {

        doThrow(new EntidadeEmUsoException(String.format("O Nivel de acesso com id %d está em uso e não pode ser excluido", validUserId)))
                .when(usuarioService).delete(validUserId);

        MvcResult mvcResult = mockMvc.perform(delete(URL + "/{idUsuario}", validUserId))
                .andExpect(status().isConflict())
                .andReturn();

        String actualResponseBody = mvcResult.getResponse().getContentAsString();

        String expectedDetails = String.format("O Nivel de acesso com id %d estÃ¡ em uso e nÃ£o pode ser excluido", validUserId);

        assertThat(actualResponseBody).containsIgnoringCase(expectedDetails);
    }
}