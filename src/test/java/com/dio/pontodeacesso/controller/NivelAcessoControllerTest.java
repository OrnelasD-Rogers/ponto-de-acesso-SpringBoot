package com.dio.pontodeacesso.controller;

import com.dio.pontodeacesso.application.assembler.NivelAcessoAssembler;
import com.dio.pontodeacesso.application.assembler.NivelAcessoDesassembler;
import com.dio.pontodeacesso.application.model.NivelAcessoModel;
import com.dio.pontodeacesso.application.model.input.NivelAcessoInputModel;
import com.dio.pontodeacesso.domain.exception.EntidadeEmUsoException;
import com.dio.pontodeacesso.domain.exception.NivelDeAcessoNaoEncontradoException;
import com.dio.pontodeacesso.domain.model.NivelAcesso;
import com.dio.pontodeacesso.domain.service.NivelAcessoService;
import com.dio.pontodeacesso.util.NivelAcessoCreator;
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

@WebMvcTest(controllers = NivelAcessoController.class)
class NivelAcessoControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @MockBean
    private NivelAcessoService nivelAcessoService;
    @MockBean
    private NivelAcessoAssembler assembler;
    @MockBean
    private NivelAcessoDesassembler desassembler;

    private final Long validNivelAcessoId = 10L;

    private final String URL = "/nivel-acesso";
    private final String PATH = "/{idNivelAcesso}";

    @Test
    void findAll_Returns200AndNivelAcessoList_WhenValidUrl() throws Exception{

        List<NivelAcesso> nivelAcessoList = List.of(NivelAcessoCreator.createValidNivelAcesso());

        List<NivelAcessoModel> nivelAcessoModelList = List.of(NivelAcessoCreator.createValidNivelAcessoModel());

        when(nivelAcessoService.findAll()).thenReturn(nivelAcessoList);

        when(assembler.toCollectionModel(any())).thenReturn(nivelAcessoModelList);

        MvcResult mvcResult = mockMvc.perform(get(URL))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        String actualResponseBody = mvcResult.getResponse().getContentAsString();

        String expectedResponseBody = objectMapper.writeValueAsString(nivelAcessoModelList);

        assertThat(actualResponseBody)
                .isEqualToIgnoringWhitespace(expectedResponseBody);
    }

    @Test
    void findById_Returns200AndNivelAcesso_WhenSuccessful() throws Exception {

        NivelAcesso nivelAcesso = NivelAcessoCreator.createValidNivelAcesso();

        NivelAcessoModel nivelAcessoModel = NivelAcessoCreator.createValidNivelAcessoModel();

        when(nivelAcessoService.findById(anyLong())).thenReturn(nivelAcesso);

        when(assembler.toModel(any())).thenReturn(nivelAcessoModel);

        MvcResult mvcResult = mockMvc.perform(get(URL + PATH, validNivelAcessoId))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        String actualResponseBody = mvcResult.getResponse().getContentAsString();

        String expectedResponseBody = objectMapper.writeValueAsString(nivelAcessoModel);

        assertThat(actualResponseBody)
                .isEqualToIgnoringWhitespace(expectedResponseBody);
    }

    @Test
    void findById_Returns400AndErrorMessage_WhenIdNivelAcessoIsNotALongType() throws Exception {

        MvcResult mvcResult = mockMvc.perform(get(URL + PATH, "x"))
                .andExpect(status().isBadRequest())
                .andReturn();

        String actualResponseBody = mvcResult.getResponse().getContentAsString();

        assertThat(actualResponseBody).containsIgnoringCase(MSG_ERRO_GENERICA_USUARIO_FINAL);
    }

    @Test
    void findById_Returns404AndProblemDetails_WhenNivelAcessoNotFound() throws Exception {

        when(nivelAcessoService.findById(anyLong()))
                .thenThrow(new NivelDeAcessoNaoEncontradoException(validNivelAcessoId));

        MvcResult mvcResult = mockMvc.perform(get(URL + PATH, validNivelAcessoId))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andReturn();

        String actualResponseBody = mvcResult.getResponse().getContentAsString();

        String expectedResponseBody = String.format("O nÃ\u00ADvel de acesso de id %d nÃ£o foi encontrado", validNivelAcessoId);

        assertThat(actualResponseBody)
                .containsIgnoringCase(expectedResponseBody);
    }

    @Test
    void create_Returns201AndNivelAcesso_WhenSuccessful() throws Exception {

        NivelAcesso toBeSavedNivelAcesso = NivelAcessoCreator.createValidNivelAcesso();

        NivelAcesso savedNivelAcesso = NivelAcessoCreator.createSavedNivelAcesso();

        NivelAcessoModel nivelAcessoModel = NivelAcessoCreator.createValidNivelAcessoModel();

        NivelAcessoInputModel nivelAcessoInputModel = NivelAcessoCreator.createValidNivelAcessoInput();

        when(nivelAcessoService.save(any(NivelAcesso.class))).thenReturn(savedNivelAcesso);

        when(desassembler.toDomainModel(any())).thenReturn(toBeSavedNivelAcesso);

        when(assembler.toModel(any())).thenReturn(nivelAcessoModel);

        MvcResult mvcResult = mockMvc.perform(post(URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(nivelAcessoInputModel)))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andReturn();

        String actualResponseBody = mvcResult.getResponse().getContentAsString();

        String expectedResponse = objectMapper.writeValueAsString(nivelAcessoModel);

        assertThat(actualResponseBody).containsIgnoringCase(expectedResponse);
    }

    @Test
    void create_Returns400AndProblemDetails_WhenInvalidDescricao() throws Exception {

        NivelAcessoInputModel nivelAcessoInputModel = NivelAcessoCreator.createInvalidNivelAcessoInput();

        MvcResult mvcResult = mockMvc.perform(post(URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(nivelAcessoInputModel)))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andReturn();

        String actualResponseBody = mvcResult.getResponse().getContentAsString();

        String expectedError = "[{\"nome\":\"descricao\",\"mensagemUsuario\":\"must not be blank\"}]";

        assertThat(actualResponseBody).containsIgnoringCase(expectedError);
    }

    @Test
    void update_Returns200AndNivelAcessoModel_WhenSuccessful() throws Exception{

        NivelAcessoInputModel toBeUpdatedNivelAcessoInputModel = NivelAcessoCreator.createValidNivelAcessoInput();
        toBeUpdatedNivelAcessoInputModel.setDescricao("Atualizado");

        NivelAcesso updatedNivelAcesso = NivelAcessoCreator.createSavedNivelAcesso();
        updatedNivelAcesso.setDescricao("Atualizado");

        NivelAcesso savedNivelAcesso = NivelAcessoCreator.createSavedNivelAcesso();

        NivelAcessoModel updatedNivelAcessoModel = NivelAcessoCreator.createValidNivelAcessoModel();

        when(nivelAcessoService.findById(anyLong())).thenReturn(savedNivelAcesso);

        when(nivelAcessoService.save(savedNivelAcesso)).thenReturn(updatedNivelAcesso);

        when(assembler.toModel(any())).thenReturn(updatedNivelAcessoModel);

        MvcResult mvcResult = mockMvc.perform(put(URL + PATH, validNivelAcessoId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(toBeUpdatedNivelAcessoInputModel)))
                .andExpect(status().isOk())
                .andReturn();

        String actualResponse = mvcResult.getResponse().getContentAsString();

        String expectedResponse = objectMapper.writeValueAsString(updatedNivelAcessoModel);

        assertThat(actualResponse).containsIgnoringCase(expectedResponse);
    }

    @Test
    void update_Returns404AndProblemDetails_WhenNivelAcessoIsNotFound() throws Exception{

        NivelAcessoInputModel toBeUpdatedNivelAcessoInputModel = NivelAcessoCreator.createValidNivelAcessoInput();
        toBeUpdatedNivelAcessoInputModel.setDescricao("Atualizado");

        when(nivelAcessoService.findById(anyLong()))
                .thenThrow(new NivelDeAcessoNaoEncontradoException(validNivelAcessoId));


        MvcResult mvcResult = mockMvc.perform(put(URL + PATH, validNivelAcessoId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(toBeUpdatedNivelAcessoInputModel)))
                .andExpect(status().isNotFound())
                .andReturn();

        String actualResponse = mvcResult.getResponse().getContentAsString();

        String expectedError = String.format("O nÃ\u00ADvel de acesso de id %d nÃ£o foi encontrado", validNivelAcessoId);

        assertThat(actualResponse).containsIgnoringCase(expectedError);
    }

    @Test
    void update_Returns400AndProblemDetails_WhenDescricaoIsNull() throws Exception{

        NivelAcessoInputModel invalidNivelAcessoInput = NivelAcessoCreator.createInvalidNivelAcessoInput();

        MvcResult mvcResult = mockMvc.perform(put(URL + PATH, validNivelAcessoId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(invalidNivelAcessoInput)))
                .andExpect(status().isBadRequest())
                .andReturn();

        String actualResponse = mvcResult.getResponse().getContentAsString();

        String expectedErrors = "[{\"nome\":\"descricao\",\"mensagemUsuario\":\"must not be blank\"}]";

        assertThat(actualResponse).containsIgnoringCase(expectedErrors);
    }

    @Test
    void delete_Returns204_WhenSuccessful() throws Exception{

        MvcResult mvcResult = mockMvc.perform(delete(URL + PATH, validNivelAcessoId))
                .andExpect(status().isNoContent())
                .andReturn();
    }

    @Test
    void delete_Returns404AndProblemDetails_WhenNivelAcessoIsNotFound() throws Exception{

        doThrow(new NivelDeAcessoNaoEncontradoException(validNivelAcessoId))
                .when(nivelAcessoService).delete(validNivelAcessoId);

        MvcResult mvcResult = mockMvc.perform(delete(URL + PATH, validNivelAcessoId))
                .andExpect(status().isNotFound())
                .andReturn();

        String actualResponseBody = mvcResult.getResponse().getContentAsString();

        String expectedDetails = String.format("O nÃ\u00ADvel de acesso de id %d nÃ£o foi encontrado", validNivelAcessoId);

        assertThat(actualResponseBody).containsIgnoringCase(expectedDetails);
    }

    @Test
    void delete_Returns409AndProblemDetails_WhenNivelAcessoIsInUse() throws Exception {

        doThrow(new EntidadeEmUsoException(String.format("O Nivel de acesso com id %d está em uso e não pode ser excluido", validNivelAcessoId)))
                .when(nivelAcessoService).delete(validNivelAcessoId);

        MvcResult mvcResult = mockMvc.perform(delete(URL + PATH, validNivelAcessoId))
                .andExpect(status().isConflict())
                .andReturn();

        String actualResponseBody = mvcResult.getResponse().getContentAsString();

        String expectedDetails = String.format("O Nivel de acesso com id %d estÃ¡ em uso e nÃ£o pode ser excluido", validNivelAcessoId);

        assertThat(actualResponseBody).containsIgnoringCase(expectedDetails);
    }
}