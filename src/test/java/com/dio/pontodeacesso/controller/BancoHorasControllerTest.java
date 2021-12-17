package com.dio.pontodeacesso.controller;

import com.dio.pontodeacesso.application.assembler.BancoHorasModelAssembler;
import com.dio.pontodeacesso.application.assembler.BancoHorasModelDesassembler;
import com.dio.pontodeacesso.application.model.BancoHorasModel;
import com.dio.pontodeacesso.application.model.input.BancoHorasInputModel;
import com.dio.pontodeacesso.domain.exception.BancoHorasNaoEncontrado;
import com.dio.pontodeacesso.domain.model.BancoHoras;
import com.dio.pontodeacesso.domain.service.BancoHorasService;
import com.dio.pontodeacesso.util.BancoHorasCreator;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.math.BigDecimal;
import java.util.List;

import static com.dio.pontodeacesso.application.exceptionhandler.ApiExceptionHandler.MSG_ERRO_GENERICA_USUARIO_FINAL;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = BancoHorasController.class)
public class BancoHorasControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @MockBean
    private BancoHorasService bancoHorasService;
    @MockBean
    private BancoHorasModelAssembler assembler;
    @MockBean
    private BancoHorasModelDesassembler desassembler;

    private final Long ID_BANCO_HORAS = 1L;
    private final Long ID_USUARIO = 1L;
    private final Long ID_MOVIMENTACAO= 1L;
    private final String URL = "/banco-horas";
    private final String PATH = "/{idBancoHoras}-{idMovimento}-{idUsuario}";

    @Test
    void findAll_Returns200AndBancoHorasList_WhenValidUrl() throws Exception{

        List<BancoHoras> bancoHorasList = List.of(BancoHorasCreator.createValidBancoHoras());

        List<BancoHorasModel> bancoHorasModelList = List.of(BancoHorasCreator.createValidBancoHorasModel());

        when(bancoHorasService.findAll()).thenReturn(bancoHorasList);

        when(assembler.toCollectionModel(any())).thenReturn(bancoHorasModelList);

        MvcResult mvcResult = mockMvc.perform(get(URL))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        String actualResponseBody = mvcResult.getResponse().getContentAsString();

        String expectedResponseBody = objectMapper.writeValueAsString(bancoHorasModelList);

        assertThat(actualResponseBody)
                .isEqualToIgnoringWhitespace(expectedResponseBody);
    }

    @Test
    void findById_Returns200AndBancoHoras_WhenSuccessful() throws Exception {

        BancoHoras bancoHoras = BancoHorasCreator.createValidBancoHoras();

        BancoHorasModel bancoHorasModel = BancoHorasCreator.createValidBancoHorasModel();

        when(bancoHorasService.findById(any())).thenReturn(bancoHoras);

        when(assembler.toModel(any())).thenReturn(bancoHorasModel);

        MvcResult mvcResult = mockMvc.perform(get(URL + PATH, ID_BANCO_HORAS, ID_MOVIMENTACAO, ID_USUARIO))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        String actualResponseBody = mvcResult.getResponse().getContentAsString();

        String expectedResponseBody = objectMapper.writeValueAsString(bancoHorasModel);

        assertThat(actualResponseBody)
                .isEqualToIgnoringWhitespace(expectedResponseBody);
    }

    @Test
    void findById_Returns400AndErrorMessage_WhenIdBancoHorasIsNotALongType() throws Exception {

        MvcResult mvcResult = mockMvc.perform(get(URL + PATH, "x", "y", "z"))
                .andExpect(status().isBadRequest())
                .andReturn();

        String actualResponseBody = mvcResult.getResponse().getContentAsString();

        assertThat(actualResponseBody).containsIgnoringCase(MSG_ERRO_GENERICA_USUARIO_FINAL);
    }

    @Test
    void findById_Returns404AndProblemDetails_WhenBancoHorasNotFound() throws Exception {

        when(bancoHorasService.findById(any()))
                .thenThrow(new BancoHorasNaoEncontrado(BancoHorasCreator.createIdBancoHoras(ID_BANCO_HORAS, ID_MOVIMENTACAO, ID_USUARIO)));

        MvcResult mvcResult = mockMvc.perform(get(URL + PATH, ID_BANCO_HORAS, ID_MOVIMENTACAO, ID_USUARIO))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andReturn();

        String actualResponseBody = mvcResult.getResponse().getContentAsString();

        String expectedResponseBody = String.format("O banco com o id (%d, %d, %d) nÃ£o foi encontrado", ID_BANCO_HORAS, ID_MOVIMENTACAO, ID_USUARIO);

        assertThat(actualResponseBody)
                .containsIgnoringCase(expectedResponseBody);
    }

    @Test
    void create_Returns201AndBancoHoras_WhenSuccessful() throws Exception {

        BancoHoras toBeSavedBancoHoras = BancoHorasCreator.createValidBancoHoras();

        BancoHoras savedBancoHoras = BancoHorasCreator.createSavedBancoHoras();

        BancoHorasModel bancoHorasModel = BancoHorasCreator.createValidBancoHorasModel();

        BancoHorasInputModel bancoHorasInputModel = BancoHorasCreator.createValidBancoHorasInputModel();

        when(bancoHorasService.save(any(BancoHoras.class))).thenReturn(savedBancoHoras);

        when(desassembler.toDomainModel(any())).thenReturn(toBeSavedBancoHoras);

        when(assembler.toModel(any())).thenReturn(bancoHorasModel);

        MvcResult mvcResult = mockMvc.perform(post(URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(bancoHorasInputModel)))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andReturn();

        String actualResponseBody = mvcResult.getResponse().getContentAsString();

        String expectedResponse = objectMapper.writeValueAsString(bancoHorasModel);

        assertThat(actualResponseBody).containsIgnoringCase(expectedResponse);
    }

    @Test
    void create_Returns400AndProblemDetails_WhenDataTrabalhadaIsNull() throws Exception {

        BancoHorasInputModel bancoHorasInputModel = BancoHorasCreator.createInvalidDataTrabalhadaInBancoHorasInput();

        MvcResult mvcResult = mockMvc.perform(post(URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(bancoHorasInputModel)))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andReturn();

        String actualResponseBody = mvcResult.getResponse().getContentAsString();

        String expectedError = "[{\"nome\":\"dataTrabalhada\",\"mensagemUsuario\":\"must not be null\"}]";

        assertThat(actualResponseBody).containsIgnoringCase(expectedError);
    }

    @Test
    void update_Returns200AndBancoHorasModel_WhenSuccessful() throws Exception{

        BancoHorasInputModel toBeUpdatedBancoHorasInputModel = BancoHorasCreator.createValidBancoHorasInputModel();
        toBeUpdatedBancoHorasInputModel.setSaldoHoras(new BigDecimal(50));

        BancoHoras updatedBancoHoras = BancoHorasCreator.createSavedBancoHoras();
        updatedBancoHoras.setSaldoHoras(new BigDecimal(50));

        BancoHoras savedBancoHoras = BancoHorasCreator.createSavedBancoHoras();

        BancoHorasModel updatedBancoHorasModel = BancoHorasCreator.createValidBancoHorasModel();

        when(bancoHorasService.findById(any())).thenReturn(savedBancoHoras);

        when(bancoHorasService.save(savedBancoHoras)).thenReturn(updatedBancoHoras);

        when(assembler.toModel(any())).thenReturn(updatedBancoHorasModel);

        MvcResult mvcResult = mockMvc.perform(put(URL + PATH, ID_BANCO_HORAS, ID_MOVIMENTACAO, ID_USUARIO)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(toBeUpdatedBancoHorasInputModel)))
                .andExpect(status().isOk())
                .andReturn();

        String actualResponse = mvcResult.getResponse().getContentAsString();

        String expectedResponse = objectMapper.writeValueAsString(updatedBancoHorasModel);

        assertThat(actualResponse).containsIgnoringCase(expectedResponse);
    }

    @Test
    void update_Returns404AndProblemDetails_WhenBancoHorasIsNotFound() throws Exception{

        BancoHorasInputModel toBeUpdatedBancoHorasInputModel = BancoHorasCreator.createValidBancoHorasInputModel();
        toBeUpdatedBancoHorasInputModel.setSaldoHoras(new BigDecimal(50));

        when(bancoHorasService.findById(any()))
                .thenThrow(new BancoHorasNaoEncontrado(BancoHorasCreator.createIdBancoHoras(ID_BANCO_HORAS, ID_MOVIMENTACAO, ID_USUARIO)));


        MvcResult mvcResult = mockMvc.perform(put(URL + PATH, ID_BANCO_HORAS, ID_MOVIMENTACAO, ID_USUARIO)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(toBeUpdatedBancoHorasInputModel)))
                .andExpect(status().isNotFound())
                .andReturn();

        String actualResponse = mvcResult.getResponse().getContentAsString();

        String expectedError = String.format("O banco com o id (%d, %d, %d) nÃ£o foi encontrado", ID_BANCO_HORAS, ID_MOVIMENTACAO, ID_USUARIO);

        assertThat(actualResponse).containsIgnoringCase(expectedError);
    }

    @Test
    void update_Returns400AndProblemDetails_WhenDataTrabalhadaIsNull() throws Exception{

        BancoHorasInputModel invalidBancoHorasInput = BancoHorasCreator.createInvalidDataTrabalhadaInBancoHorasInput();

        MvcResult mvcResult = mockMvc.perform(put(URL + PATH, ID_BANCO_HORAS, ID_MOVIMENTACAO, ID_USUARIO)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(invalidBancoHorasInput)))
                .andExpect(status().isBadRequest())
                .andReturn();

        String actualResponse = mvcResult.getResponse().getContentAsString();

        String expectedErrors = "[{\"nome\":\"dataTrabalhada\",\"mensagemUsuario\":\"must not be null\"}]";

        assertThat(actualResponse).containsIgnoringCase(expectedErrors);
    }

    @Test
    void delete_Returns204_WhenSuccessful() throws Exception{

        MvcResult mvcResult = mockMvc.perform(delete(URL + PATH, ID_BANCO_HORAS, ID_MOVIMENTACAO, ID_USUARIO))
                .andExpect(status().isNoContent())
                .andReturn();
    }

    @Test
    void delete_Returns404AndProblemDetails_WhenBancoHorasIsNotFound() throws Exception{

        doThrow(new BancoHorasNaoEncontrado(BancoHorasCreator.createIdBancoHoras(ID_BANCO_HORAS, ID_MOVIMENTACAO, ID_USUARIO)))
                .when(bancoHorasService).delete(BancoHorasCreator.createIdBancoHoras(ID_BANCO_HORAS, ID_MOVIMENTACAO, ID_USUARIO));

        MvcResult mvcResult = mockMvc.perform(delete(URL + PATH, ID_BANCO_HORAS, ID_MOVIMENTACAO, ID_USUARIO))
                .andExpect(status().isNotFound())
                .andReturn();

        String actualResponseBody = mvcResult.getResponse().getContentAsString();

        String expectedDetails = String.format("O banco com o id (%d, %d, %d) nÃ£o foi encontrado", ID_BANCO_HORAS, ID_MOVIMENTACAO, ID_USUARIO);

        assertThat(actualResponseBody).containsIgnoringCase(expectedDetails);
    }

}
