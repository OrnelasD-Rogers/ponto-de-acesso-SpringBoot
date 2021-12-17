package com.dio.pontodeacesso.controller;

import com.dio.pontodeacesso.application.assembler.OcorrenciaModelAssembler;
import com.dio.pontodeacesso.application.assembler.OcorrenciaModelDesassembler;
import com.dio.pontodeacesso.application.model.OcorrenciaModel;
import com.dio.pontodeacesso.application.model.input.OcorrenciaInputModel;
import com.dio.pontodeacesso.domain.exception.EntidadeEmUsoException;
import com.dio.pontodeacesso.domain.exception.OcorrenciaNaoEncontradaException;
import com.dio.pontodeacesso.domain.model.Ocorrencia;
import com.dio.pontodeacesso.domain.service.OcorrenciaService;
import com.dio.pontodeacesso.util.OcorrenciaCreator;
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

@WebMvcTest(controllers = OcorrenciaController.class)
public class OcorrenciaControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @MockBean
    private OcorrenciaService ocorrenciaService;
    @MockBean
    private OcorrenciaModelAssembler assembler;
    @MockBean
    private OcorrenciaModelDesassembler desassembler;

    private final String URL = "/ocorrencia";

    private final String PATH = "/{idOcorrencia}";

    private final Long validOcorrenciaId = 10L;

    @Test
    void findAll_Returns200AndOcorrenciaList_WhenValidUrl() throws Exception{

        List<Ocorrencia> ocorrenciaList = List.of(OcorrenciaCreator.createValidOcorrencia());

        List<OcorrenciaModel> ocorrenciaModelList = List.of(OcorrenciaCreator.createValidOcorrenciaModel());

        when(ocorrenciaService.findAll()).thenReturn(ocorrenciaList);

        when(assembler.toCollectionModel(any())).thenReturn(ocorrenciaModelList);

        MvcResult mvcResult = mockMvc.perform(get(URL))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        String actualResponseBody = mvcResult.getResponse().getContentAsString();

        String expectedResponseBody = objectMapper.writeValueAsString(ocorrenciaModelList);

        assertThat(actualResponseBody)
                .isEqualToIgnoringWhitespace(expectedResponseBody);
    }

    @Test
    void findById_Returns200AndOcorrencia_WhenSuccessful() throws Exception {

        Ocorrencia ocorrencia = OcorrenciaCreator.createValidOcorrencia();

        OcorrenciaModel ocorrenciaModel = OcorrenciaCreator.createValidOcorrenciaModel();

        when(ocorrenciaService.findById(anyLong())).thenReturn(ocorrencia);

        when(assembler.toModel(any())).thenReturn(ocorrenciaModel);

        MvcResult mvcResult = mockMvc.perform(get(URL + PATH, validOcorrenciaId))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        String actualResponseBody = mvcResult.getResponse().getContentAsString();

        String expectedResponseBody = objectMapper.writeValueAsString(ocorrenciaModel);

        assertThat(actualResponseBody)
                .isEqualToIgnoringWhitespace(expectedResponseBody);
    }

    @Test
    void findById_Returns400AndErrorMessage_WhenIdOcorrenciaIsNotALongType() throws Exception {

        MvcResult mvcResult = mockMvc.perform(get(URL + PATH, "x"))
                .andExpect(status().isBadRequest())
                .andReturn();

        String actualResponseBody = mvcResult.getResponse().getContentAsString();

        assertThat(actualResponseBody).containsIgnoringCase(MSG_ERRO_GENERICA_USUARIO_FINAL);
    }

    @Test
    void findById_Returns404AndProblemDetails_WhenOcorrenciaNotFound() throws Exception {

        when(ocorrenciaService.findById(anyLong()))
                .thenThrow(new OcorrenciaNaoEncontradaException(validOcorrenciaId));

        MvcResult mvcResult = mockMvc.perform(get(URL + PATH, validOcorrenciaId))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andReturn();

        String actualResponseBody = mvcResult.getResponse().getContentAsString();

        String expectedResponseBody = String.format("NÃ£o foi encontrado uma Ocorrencia com o id %d", validOcorrenciaId);

        assertThat(actualResponseBody)
                .containsIgnoringCase(expectedResponseBody);
    }

    @Test
    void create_Returns201AndOcorrencia_WhenSuccessful() throws Exception {

        Ocorrencia toBeSavedOcorrencia = OcorrenciaCreator.createValidOcorrencia();

        Ocorrencia savedOcorrencia = OcorrenciaCreator.createValidOcorrencia();

        OcorrenciaModel ocorrenciaModel = OcorrenciaCreator.createValidOcorrenciaModel();

        OcorrenciaInputModel nivelAcessoInputModel = OcorrenciaCreator.createValidOcorrenciaInputModel();

        when(ocorrenciaService.save(any(Ocorrencia.class))).thenReturn(savedOcorrencia);

        when(desassembler.toDomainModel(any())).thenReturn(toBeSavedOcorrencia);

        when(assembler.toModel(any())).thenReturn(ocorrenciaModel);

        MvcResult mvcResult = mockMvc.perform(post(URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(nivelAcessoInputModel)))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andReturn();

        String actualResponseBody = mvcResult.getResponse().getContentAsString();

        String expectedResponse = objectMapper.writeValueAsString(ocorrenciaModel);

        assertThat(actualResponseBody).containsIgnoringCase(expectedResponse);
    }

    @Test
    void create_Returns400AndProblemDetails_WhenInvalidDescricao() throws Exception {

        OcorrenciaInputModel ocorrenciaInputModel = OcorrenciaCreator.createInvalidDescInOcorrenciaInputModel();

        MvcResult mvcResult = mockMvc.perform(post(URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(ocorrenciaInputModel)))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andReturn();

        String actualResponseBody = mvcResult.getResponse().getContentAsString();

        String expectedError = "[{\"nome\":\"descricao\",\"mensagemUsuario\":\"must not be blank\"}]";

        assertThat(actualResponseBody).containsIgnoringCase(expectedError);
    }
    @Test
    void create_Returns400AndProblemDetails_WhenInvalidNome() throws Exception {

        OcorrenciaInputModel ocorrenciaInputModel = OcorrenciaCreator.createInvalidNomeInOcorrenciaInputModel();

        MvcResult mvcResult = mockMvc.perform(post(URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(ocorrenciaInputModel)))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andReturn();

        String actualResponseBody = mvcResult.getResponse().getContentAsString();

        String expectedError = "[{\"nome\":\"nome\",\"mensagemUsuario\":\"must not be blank\"}]";

        assertThat(actualResponseBody).containsIgnoringCase(expectedError);
    }

    @Test
    void update_Returns200AndOcorrenciaModel_WhenSuccessful() throws Exception{

        OcorrenciaInputModel toBeUpdatedOcorrenciaInputModel = OcorrenciaCreator.createValidOcorrenciaInputModel();
        toBeUpdatedOcorrenciaInputModel.setDescricao("Atualizado");

        Ocorrencia updatedOcorrencia = OcorrenciaCreator.createValidOcorrencia();
        updatedOcorrencia.setDescricao("Atualizado");

        Ocorrencia savedOcorrencia = OcorrenciaCreator.createValidOcorrencia();

        OcorrenciaModel updatedOcorrenciaModel = OcorrenciaCreator.createValidOcorrenciaModel();

        when(ocorrenciaService.findById(anyLong())).thenReturn(savedOcorrencia);

        when(ocorrenciaService.save(savedOcorrencia)).thenReturn(updatedOcorrencia);

        when(assembler.toModel(any())).thenReturn(updatedOcorrenciaModel);

        MvcResult mvcResult = mockMvc.perform(put(URL + PATH, validOcorrenciaId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(toBeUpdatedOcorrenciaInputModel)))
                .andExpect(status().isOk())
                .andReturn();

        String actualResponse = mvcResult.getResponse().getContentAsString();

        String expectedResponse = objectMapper.writeValueAsString(updatedOcorrenciaModel);

        assertThat(actualResponse).containsIgnoringCase(expectedResponse);
    }

    @Test
    void update_Returns404AndProblemDetails_WhenOcorrenciaIsNotFound() throws Exception{

        OcorrenciaInputModel toBeUpdatedOcorrenciaInputModel = OcorrenciaCreator.createValidOcorrenciaInputModel();
        toBeUpdatedOcorrenciaInputModel.setDescricao("Atualizado");

        when(ocorrenciaService.findById(anyLong()))
                .thenThrow(new OcorrenciaNaoEncontradaException(validOcorrenciaId));


        MvcResult mvcResult = mockMvc.perform(put(URL + PATH, validOcorrenciaId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(toBeUpdatedOcorrenciaInputModel)))
                .andExpect(status().isNotFound())
                .andReturn();

        String actualResponse = mvcResult.getResponse().getContentAsString();

        String expectedError = String.format("NÃ£o foi encontrado uma Ocorrencia com o id %d", validOcorrenciaId);

        assertThat(actualResponse).containsIgnoringCase(expectedError);
    }

    @Test
    void update_Returns400AndProblemDetails_WhenDescricaoIsNull() throws Exception{

        OcorrenciaInputModel invalidOcorrenciaInputModel = OcorrenciaCreator.createInvalidDescInOcorrenciaInputModel();

        MvcResult mvcResult = mockMvc.perform(put(URL + PATH, validOcorrenciaId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(invalidOcorrenciaInputModel)))
                .andExpect(status().isBadRequest())
                .andReturn();

        String actualResponse = mvcResult.getResponse().getContentAsString();

        String expectedErrors = "[{\"nome\":\"descricao\",\"mensagemUsuario\":\"must not be blank\"}]";

        assertThat(actualResponse).containsIgnoringCase(expectedErrors);
    }

    @Test
    void delete_Returns204_WhenSuccessful() throws Exception{

        MvcResult mvcResult = mockMvc.perform(delete(URL + PATH, validOcorrenciaId))
                .andExpect(status().isNoContent())
                .andReturn();
    }

    @Test
    void delete_Returns404AndProblemDetails_WhenOcorrenciaIsNotFound() throws Exception{

        doThrow(new OcorrenciaNaoEncontradaException(validOcorrenciaId))
                .when(ocorrenciaService).delete(validOcorrenciaId);

        MvcResult mvcResult = mockMvc.perform(delete(URL + PATH, validOcorrenciaId))
                .andExpect(status().isNotFound())
                .andReturn();

        String actualResponseBody = mvcResult.getResponse().getContentAsString();

        String expectedDetails = String.format("NÃ£o foi encontrado uma Ocorrencia com o id %d", validOcorrenciaId);

        assertThat(actualResponseBody).containsIgnoringCase(expectedDetails);
    }

    @Test
    void delete_Returns409AndProblemDetails_WhenOcorrenciaIsInUse() throws Exception {

        doThrow(new EntidadeEmUsoException(String.format("A ocorrencia com id %d está em uso e não pode ser excluida", validOcorrenciaId)))
                .when(ocorrenciaService).delete(validOcorrenciaId);

        MvcResult mvcResult = mockMvc.perform(delete(URL + PATH, validOcorrenciaId))
                .andExpect(status().isConflict())
                .andReturn();

        String actualResponseBody = mvcResult.getResponse().getContentAsString();

        String expectedDetails = String.format("A ocorrencia com id %d estÃ¡ em uso e nÃ£o pode ser excluida", validOcorrenciaId);

        assertThat(actualResponseBody).containsIgnoringCase(expectedDetails);
    }
}
