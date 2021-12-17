package com.dio.pontodeacesso.controller;

import com.dio.pontodeacesso.application.assembler.CategoriaUsuarioAssembler;
import com.dio.pontodeacesso.application.assembler.CategoriaUsuarioDesassembler;
import com.dio.pontodeacesso.application.model.CategoriaUsuarioModel;
import com.dio.pontodeacesso.application.model.input.CategoriaUsuarioInputModel;
import com.dio.pontodeacesso.domain.exception.CategoriaUsuarioNaoEncontrado;
import com.dio.pontodeacesso.domain.exception.EntidadeEmUsoException;
import com.dio.pontodeacesso.domain.model.CategoriaUsuario;
import com.dio.pontodeacesso.domain.service.CategoriaUsuarioService;
import com.dio.pontodeacesso.util.CategoriaUsuarioCreator;
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

@WebMvcTest(controllers = CategoriaUsuarioController.class)
public class CategoriaUsuarioControllerTest {

    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private CategoriaUsuarioService categoriaUsuarioService;
    @MockBean
    private CategoriaUsuarioAssembler assembler;
    @MockBean
    private CategoriaUsuarioDesassembler desassembler;

    private final Long ID_CAT_USU = 1L;
    private final String URL = "/categoria-usuario";
    private final String PATH = "/{idCategoriaUsuario}";

    @Test
    void findAll_Returns200AndCategoriaUsuarioList_WhenValidUrl() throws Exception{

        List<CategoriaUsuario> categoriaUsuarioList = List.of(CategoriaUsuarioCreator.createValidCategoriaUsuario());

        List<CategoriaUsuarioModel> categoriaUsuarioModelList = List.of(CategoriaUsuarioCreator.createValidCategoriaUsuarioModel());

        when(categoriaUsuarioService.findAll()).thenReturn(categoriaUsuarioList);

        when(assembler.toCollectionModel(any())).thenReturn(categoriaUsuarioModelList);

        MvcResult mvcResult = mockMvc.perform(get(URL))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        String actualResponseBody = mvcResult.getResponse().getContentAsString();

        String expectedResponseBody = objectMapper.writeValueAsString(categoriaUsuarioModelList);

        assertThat(actualResponseBody)
                .isEqualToIgnoringWhitespace(expectedResponseBody);
    }

    @Test
    void findById_Returns200AndCategoriaUsuario_WhenSuccessful() throws Exception {

        CategoriaUsuario categoriaUsuario = CategoriaUsuarioCreator.createValidCategoriaUsuario();

        CategoriaUsuarioModel categoriaUsuarioModel = CategoriaUsuarioCreator.createValidCategoriaUsuarioModel();

        when(categoriaUsuarioService.findById(anyLong())).thenReturn(categoriaUsuario);

        when(assembler.toModel(any())).thenReturn(categoriaUsuarioModel);

        MvcResult mvcResult = mockMvc.perform(get(URL + PATH, ID_CAT_USU))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        String actualResponseBody = mvcResult.getResponse().getContentAsString();

        String expectedResponseBody = objectMapper.writeValueAsString(categoriaUsuarioModel);

        assertThat(actualResponseBody)
                .isEqualToIgnoringWhitespace(expectedResponseBody);
    }

    @Test
    void findById_Returns400AndErrorMessage_WhenIdCategoriaUsuarioIsNotALongType() throws Exception {

        MvcResult mvcResult = mockMvc.perform(get(URL + PATH, "x"))
                .andExpect(status().isBadRequest())
                .andReturn();

        String actualResponseBody = mvcResult.getResponse().getContentAsString();

        assertThat(actualResponseBody).containsIgnoringCase(MSG_ERRO_GENERICA_USUARIO_FINAL);
    }

    @Test
    void findById_Returns404AndProblemDetails_WhenCategoriaUsuarioIsNotFound() throws Exception {

        when(categoriaUsuarioService.findById(anyLong()))
                .thenThrow(new CategoriaUsuarioNaoEncontrado(ID_CAT_USU));

        MvcResult mvcResult = mockMvc.perform(get(URL + PATH, ID_CAT_USU))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andReturn();

        String actualResponseBody = mvcResult.getResponse().getContentAsString();

        String expectedResponseBody = String.format("A Categoria de usuÃ¡rio com id %d nÃ£o foi encontrada", ID_CAT_USU);

        assertThat(actualResponseBody)
                .containsIgnoringCase(expectedResponseBody);
    }

    @Test
    void create_Returns201AndCategoriaUsuario_WhenSuccessful() throws Exception {

        CategoriaUsuario toBeSavedCategoriaUsuario = CategoriaUsuarioCreator.createValidCategoriaUsuario();

        CategoriaUsuario savedCategoriaUsuario = CategoriaUsuarioCreator.createSavedCategoriaUsuario();

        CategoriaUsuarioModel categoriaUsuarioModel = CategoriaUsuarioCreator.createValidCategoriaUsuarioModel();

        CategoriaUsuarioInputModel categoriaUsuarioInputModel = CategoriaUsuarioCreator.createValidCategoriaUsuarioInputModel();

        when(categoriaUsuarioService.save(any(CategoriaUsuario.class))).thenReturn(savedCategoriaUsuario);

        when(desassembler.toDomainModel(any())).thenReturn(toBeSavedCategoriaUsuario);

        when(assembler.toModel(any())).thenReturn(categoriaUsuarioModel);

        MvcResult mvcResult = mockMvc.perform(post(URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(categoriaUsuarioInputModel)))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andReturn();

        String actualResponseBody = mvcResult.getResponse().getContentAsString();

        String expectedResponse = objectMapper.writeValueAsString(categoriaUsuarioModel);

        assertThat(actualResponseBody).containsIgnoringCase(expectedResponse);
    }

    @Test
    void create_Returns400AndProblemDetails_WhenDescricaoIsNull() throws Exception {

        CategoriaUsuarioInputModel categoriaUsuarioInputModel = CategoriaUsuarioCreator.createInvalidDescricaoInCategoriaUsuarioInputModel();

        MvcResult mvcResult = mockMvc.perform(post(URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(categoriaUsuarioInputModel)))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andReturn();

        String actualResponseBody = mvcResult.getResponse().getContentAsString();

        String expectedError = "[{\"nome\":\"descricao\",\"mensagemUsuario\":\"must not be blank\"}]";

        assertThat(actualResponseBody).containsIgnoringCase(expectedError);
    }

    @Test
    void update_Returns200AndCategoriaUsuarioModel_WhenSuccessful() throws Exception{

        CategoriaUsuarioInputModel toBeUpdatedCategoriaUsuarioInputModel = CategoriaUsuarioCreator.createValidCategoriaUsuarioInputModel();
        toBeUpdatedCategoriaUsuarioInputModel.setDescricao("Atualizado");

        CategoriaUsuario updatedCategoriaUsuario = CategoriaUsuarioCreator.createSavedCategoriaUsuario();
        updatedCategoriaUsuario.setDescricao("Atualizado");

        CategoriaUsuario savedCategoriaUsuario = CategoriaUsuarioCreator.createSavedCategoriaUsuario();

        CategoriaUsuarioModel updatedCategoriaUsuarioModel = CategoriaUsuarioCreator.createValidCategoriaUsuarioModel();

        when(categoriaUsuarioService.findById(anyLong())).thenReturn(savedCategoriaUsuario);

        when(categoriaUsuarioService.save(savedCategoriaUsuario)).thenReturn(updatedCategoriaUsuario);

        when(assembler.toModel(any())).thenReturn(updatedCategoriaUsuarioModel);

        MvcResult mvcResult = mockMvc.perform(put(URL + PATH, ID_CAT_USU)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(toBeUpdatedCategoriaUsuarioInputModel)))
                .andExpect(status().isOk())
                .andReturn();

        String actualResponse = mvcResult.getResponse().getContentAsString();

        String expectedResponse = objectMapper.writeValueAsString(updatedCategoriaUsuarioModel);

        assertThat(actualResponse).containsIgnoringCase(expectedResponse);
    }

    @Test
    void update_Returns404AndProblemDetails_WhenCategoriaUsuarioIsNotFound() throws Exception{

        CategoriaUsuarioInputModel toBeUpdatedCategoriaUsuarioInputModel = CategoriaUsuarioCreator.createValidCategoriaUsuarioInputModel();
        toBeUpdatedCategoriaUsuarioInputModel.setDescricao("Atualizado");

        when(categoriaUsuarioService.findById(anyLong()))
                .thenThrow(new CategoriaUsuarioNaoEncontrado(ID_CAT_USU));


        MvcResult mvcResult = mockMvc.perform(put(URL + PATH, ID_CAT_USU)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(toBeUpdatedCategoriaUsuarioInputModel)))
                .andExpect(status().isNotFound())
                .andReturn();

        String actualResponse = mvcResult.getResponse().getContentAsString();

        String expectedError = String.format("A Categoria de usuÃ¡rio com id %d nÃ£o foi encontrada", ID_CAT_USU);

        assertThat(actualResponse).containsIgnoringCase(expectedError);
    }

    @Test
    void update_Returns400AndProblemDetails_WhenDescricaoIsNull() throws Exception{

        CategoriaUsuarioInputModel invalidCategoriaUsuarioInput = CategoriaUsuarioCreator.createInvalidDescricaoInCategoriaUsuarioInputModel();

        MvcResult mvcResult = mockMvc.perform(put(URL + PATH, ID_CAT_USU)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(invalidCategoriaUsuarioInput)))
                .andExpect(status().isBadRequest())
                .andReturn();

        String actualResponse = mvcResult.getResponse().getContentAsString();

        String expectedErrors = "[{\"nome\":\"descricao\",\"mensagemUsuario\":\"must not be blank\"}]";

        assertThat(actualResponse).containsIgnoringCase(expectedErrors);
    }
    @Test
    void delete_Returns204_WhenSuccessful() throws Exception{

        MvcResult mvcResult = mockMvc.perform(delete(URL + PATH, ID_CAT_USU))
                .andExpect(status().isNoContent())
                .andReturn();
    }

    @Test
    void delete_Returns404AndProblemDetails_WhenCategoriaUsuarioIsNotFound() throws Exception{

        doThrow(new CategoriaUsuarioNaoEncontrado(ID_CAT_USU))
                .when(categoriaUsuarioService).delete(ID_CAT_USU);

        MvcResult mvcResult = mockMvc.perform(delete(URL + PATH, ID_CAT_USU))
                .andExpect(status().isNotFound())
                .andReturn();

        String actualResponseBody = mvcResult.getResponse().getContentAsString();

        String expectedDetails = String.format("A Categoria de usuÃ¡rio com id %d nÃ£o foi encontrada", ID_CAT_USU);

        assertThat(actualResponseBody).containsIgnoringCase(expectedDetails);
    }

    @Test
    void delete_Returns409AndProblemDetails_WhenCategoriaUsuarioIsInUse() throws Exception {

        doThrow(new EntidadeEmUsoException(String.format("A Categoria de Usuario com o código %d não pode ser removida pois está em uso", ID_CAT_USU)))
                .when(categoriaUsuarioService).delete(ID_CAT_USU);

        MvcResult mvcResult = mockMvc.perform(delete(URL + PATH, ID_CAT_USU))
                .andExpect(status().isConflict())
                .andReturn();

        String actualResponseBody = mvcResult.getResponse().getContentAsString();

        String expectedDetails = String.format("A Categoria de Usuario com o cÃ³digo %d nÃ£o pode ser removida pois estÃ¡ em uso", ID_CAT_USU);

        assertThat(actualResponseBody).containsIgnoringCase(expectedDetails);
    }

}
