package com.dio.pontodeacesso.controller;

import com.dio.pontodeacesso.application.assembler.JornadaTrabalhoAssembler;
import com.dio.pontodeacesso.application.assembler.JornadaTrabalhoDesassembler;
import com.dio.pontodeacesso.application.model.JornadaTrabalhoModel;
import com.dio.pontodeacesso.application.model.input.JornadaTrabalhoInputModel;
import com.dio.pontodeacesso.domain.exception.EntidadeEmUsoException;
import com.dio.pontodeacesso.domain.exception.JornadaTrabalhoNaoEncontradaException;
import com.dio.pontodeacesso.domain.model.JornadaTrabalho;
import com.dio.pontodeacesso.domain.service.JornadaService;
import com.dio.pontodeacesso.util.JornadaTrabalhoCreator;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.util.List;

import static com.dio.pontodeacesso.application.exceptionhandler.ApiExceptionHandler.MSG_ERRO_GENERICA_USUARIO_FINAL;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = JornadaTrabalhoController.class)
public class JornadaTrabalhoControllerTest {

    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private JornadaService jornadaService;
    @MockBean
    private JornadaTrabalhoAssembler assembler;
    @MockBean
    private JornadaTrabalhoDesassembler desassembler;

    private final Long ID_JORNADA = 1L;

    private final String URL = "/jornada";

    private final String PATH = "/{idJornada}";

    @Test
    void findAll_Returns200AndJornadaTrabalhoList_WhenValidUrl() throws Exception {

        List<JornadaTrabalho> jornadaTrabalhoList = List.of(JornadaTrabalhoCreator.createValidJornadaTrabalho());

        List<JornadaTrabalhoModel> jornadaTrabalhoModelList = List.of(JornadaTrabalhoCreator.createValidJornadaTrabalhoModel());

        when(jornadaService.findAll()).thenReturn(jornadaTrabalhoList);

        when(assembler.toCollectionModel(any())).thenReturn(jornadaTrabalhoModelList);

        MvcResult mvcResult = mockMvc.perform(get(URL))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        String actualResponseBody = mvcResult.getResponse().getContentAsString();

        String expectedResponseBody = objectMapper.writeValueAsString(jornadaTrabalhoModelList);

        assertThat(actualResponseBody)
                .isEqualToIgnoringWhitespace(expectedResponseBody);
    }

    @Test
    void findById_Returns200AndJornadaTrabalho_WhenSuccessful() throws Exception {

        JornadaTrabalho jornadaTrabalho = JornadaTrabalhoCreator.createValidJornadaTrabalho();

        JornadaTrabalhoModel jornadaTrabalhoModel = JornadaTrabalhoCreator.createValidJornadaTrabalhoModel();

        when(jornadaService.findById(anyLong())).thenReturn(jornadaTrabalho);

        when(assembler.toModel(any())).thenReturn(jornadaTrabalhoModel);

        MvcResult mvcResult = mockMvc.perform(get(URL + PATH, ID_JORNADA))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        String actualResponseBody = mvcResult.getResponse().getContentAsString();

        String expectedResponseBody = objectMapper.writeValueAsString(jornadaTrabalhoModel);

        assertThat(actualResponseBody)
                .isEqualToIgnoringWhitespace(expectedResponseBody);
    }

    @Test
    void findById_Returns400AndErrorMessage_WhenIdJornadaTrabalhoIsNotALongType() throws Exception {

        MvcResult mvcResult = mockMvc.perform(get(URL + PATH, "x", "y"))
                .andExpect(status().isBadRequest())
                .andReturn();

        String actualResponseBody = mvcResult.getResponse().getContentAsString();

        assertThat(actualResponseBody).containsIgnoringCase(MSG_ERRO_GENERICA_USUARIO_FINAL);
    }

    @Test
    void findById_Returns404AndProblemDetails_WhenJornadaTrabalhoNotFound() throws Exception {

        when(jornadaService.findById(anyLong()))
                .thenThrow(new JornadaTrabalhoNaoEncontradaException(ID_JORNADA));

        MvcResult mvcResult = mockMvc.perform(get(URL + PATH, ID_JORNADA))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andReturn();

        String actualResponseBody = mvcResult.getResponse().getContentAsString();

        String expectedResponseBody = String.format("A entidade Jornada de Trabalho com id %d nÃ£o foi encontrada", ID_JORNADA);

        assertThat(actualResponseBody)
                .containsIgnoringCase(expectedResponseBody);
    }

    @Test
    void create_Returns201AndJornadaTrabalho_WhenSuccessful() throws Exception {

        JornadaTrabalho toBeSavedJornadaTrabalho = JornadaTrabalhoCreator.createValidJornadaTrabalhoToBeSaved();

        JornadaTrabalho savedJornadaTrabalho = JornadaTrabalhoCreator.createValidJornadaTrabalho();

        JornadaTrabalhoModel jornadaTrabalhoModel = JornadaTrabalhoCreator.createValidJornadaTrabalhoModel();

        JornadaTrabalhoInputModel jornadaTrabalhoInputModel = JornadaTrabalhoCreator.createValidJornadaTrabalhoInputModel();

        when(jornadaService.save(any(JornadaTrabalho.class))).thenReturn(savedJornadaTrabalho);

        when(desassembler.toDomainModel(any())).thenReturn(toBeSavedJornadaTrabalho);

        when(assembler.toModel(any())).thenReturn(jornadaTrabalhoModel);

        MvcResult mvcResult = mockMvc.perform(post(URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(jornadaTrabalhoInputModel)))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andReturn();

        String actualResponseBody = mvcResult.getResponse().getContentAsString();

        String expectedResponse = objectMapper.writeValueAsString(jornadaTrabalhoModel);

        assertThat(actualResponseBody).containsIgnoringCase(expectedResponse);
    }

    @Test
    void create_Returns400AndProblemDetails_WhenInvalidDescricao() throws Exception {

        JornadaTrabalhoInputModel jornadaTrabalhoInputModel = JornadaTrabalhoCreator.createInvalidDescricaoInJornadaTrabalhoInputModel();

        MvcResult mvcResult = mockMvc.perform(post(URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(jornadaTrabalhoInputModel)))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andReturn();

        String actualResponseBody = mvcResult.getResponse().getContentAsString();

        String expectedError = "[{\"nome\":\"descricao\",\"mensagemUsuario\":\"must not be blank\"}]";

        assertThat(actualResponseBody).containsIgnoringCase(expectedError);
    }

    @Test
    void update_Returns200AndJornadaTrabalhoModel_WhenSuccessful() throws Exception{

        JornadaTrabalhoInputModel toBeUpdatedJornadaTrabalhoInputModel = JornadaTrabalhoCreator.createValidJornadaTrabalhoInputModel();
        toBeUpdatedJornadaTrabalhoInputModel.setDescricao("Atualizado");

        JornadaTrabalho updatedJornadaTrabalho = JornadaTrabalhoCreator.createValidJornadaTrabalho();
        updatedJornadaTrabalho.setDescricao("Atualizado");

        JornadaTrabalho savedJornadaTrabalho = JornadaTrabalhoCreator.createValidJornadaTrabalho();

        JornadaTrabalhoModel updatedJornadaTrabalhoModel = JornadaTrabalhoCreator.createValidJornadaTrabalhoModel();
        updatedJornadaTrabalho.setDescricao("Atualizado");

        when(jornadaService.findById(anyLong())).thenReturn(savedJornadaTrabalho);

        when(jornadaService.save(savedJornadaTrabalho)).thenReturn(updatedJornadaTrabalho);

        when(assembler.toModel(any())).thenReturn(updatedJornadaTrabalhoModel);

        MvcResult mvcResult = mockMvc.perform(put(URL+PATH, ID_JORNADA)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(toBeUpdatedJornadaTrabalhoInputModel)))
                .andExpect(status().isOk())
                .andReturn();

        String actualResponse = mvcResult.getResponse().getContentAsString();

        String expectedResponse = objectMapper.writeValueAsString(updatedJornadaTrabalhoModel);

        assertThat(actualResponse).containsIgnoringCase(expectedResponse);
    }

    @Test
    void update_Returns404AndProblemDetails_WhenJornadaTrabalhoIsNotFound() throws Exception{

        JornadaTrabalhoInputModel toBeUpdatedJornadaTrabalhoInputModel = JornadaTrabalhoCreator.createValidJornadaTrabalhoInputModel();
        toBeUpdatedJornadaTrabalhoInputModel.setDescricao("Atualizado");

        when(jornadaService.findById(anyLong()))
                .thenThrow(new JornadaTrabalhoNaoEncontradaException(ID_JORNADA));


        MvcResult mvcResult = mockMvc.perform(put(URL+PATH, ID_JORNADA)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(toBeUpdatedJornadaTrabalhoInputModel)))
                .andExpect(status().isNotFound())
                .andReturn();

        String actualResponse = mvcResult.getResponse().getContentAsString();

        String expectedError = String.format("A entidade Jornada de Trabalho com id %d nÃ£o foi encontrada", ID_JORNADA);

        assertThat(actualResponse).containsIgnoringCase(expectedError);
    }

    @Test
    void update_Returns400AndProblemDetails_WhenDescricaoIsNull() throws Exception{

        JornadaTrabalhoInputModel invalidJornadaTrabalhoInput = JornadaTrabalhoCreator.createInvalidDescricaoInJornadaTrabalhoInputModel();

        MvcResult mvcResult = mockMvc.perform(put(URL+PATH, ID_JORNADA)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(invalidJornadaTrabalhoInput)))
                .andExpect(status().isBadRequest())
                .andReturn();

        String actualResponse = mvcResult.getResponse().getContentAsString();

        String expectedErrors = "[{\"nome\":\"descricao\",\"mensagemUsuario\":\"must not be blank\"}]";

        assertThat(actualResponse).containsIgnoringCase(expectedErrors);
    }

    @Test
    void delete_Returns204_WhenSuccessful() throws Exception{

        MvcResult mvcResult = mockMvc.perform(delete(URL+PATH, ID_JORNADA))
                .andExpect(status().isNoContent())
                .andReturn();
    }

    @Test
    void delete_Returns404AndProblemDetails_WhenJornadaTrabalhoIsNotFound() throws Exception{

        doThrow(new JornadaTrabalhoNaoEncontradaException(ID_JORNADA))
                .when(jornadaService).delete(ID_JORNADA);

        MvcResult mvcResult = mockMvc.perform(delete(URL+PATH, ID_JORNADA))
                .andExpect(status().isNotFound())
                .andReturn();

        String actualResponseBody = mvcResult.getResponse().getContentAsString();

        String expectedDetails = String.format("A entidade Jornada de Trabalho com id %d nÃ£o foi encontrada", ID_JORNADA);

        assertThat(actualResponseBody).containsIgnoringCase(expectedDetails);
    }

    @Test
    void delete_Returns409AndProblemDetails_WhenJornadaTrabalhoIsInUse() throws Exception {

        doThrow(new EntidadeEmUsoException(String.format("A Jornada de Trabalho com código %d não pode ser removida pois está em uso", ID_JORNADA)))
                .when(jornadaService).delete(ID_JORNADA);

        MvcResult mvcResult = mockMvc.perform(delete(URL+PATH, ID_JORNADA))
                .andExpect(status().isConflict())
                .andReturn();

        String actualResponseBody = mvcResult.getResponse().getContentAsString();

        String expectedDetails = String.format("A Jornada de Trabalho com cÃ³digo %d nÃ£o pode ser removida pois estÃ¡ em uso", ID_JORNADA);

        assertThat(actualResponseBody).containsIgnoringCase(expectedDetails);
    }

}

