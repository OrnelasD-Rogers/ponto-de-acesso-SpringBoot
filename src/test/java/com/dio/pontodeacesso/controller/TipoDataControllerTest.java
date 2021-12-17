package com.dio.pontodeacesso.controller;

import com.dio.pontodeacesso.application.assembler.TipoDataModelAssembler;
import com.dio.pontodeacesso.application.assembler.TipoDataModelDesassembler;
import com.dio.pontodeacesso.application.model.TipoDataModel;
import com.dio.pontodeacesso.application.model.input.TipoDataInputModel;
import com.dio.pontodeacesso.domain.exception.EntidadeEmUsoException;
import com.dio.pontodeacesso.domain.exception.TipoDataNaoEncontradoException;
import com.dio.pontodeacesso.domain.model.TipoData;
import com.dio.pontodeacesso.domain.service.TipoDataService;
import com.dio.pontodeacesso.util.TipoDataCreator;
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
import static org.mockito.BDDMockito.anyLong;
import static org.mockito.BDDMockito.when;
import static org.mockito.Mockito.doThrow;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


//As validações do Bean Validation devem estar na interface e não na implementação para os testes funcionarem
// caso necessite utilizar a notação @Validated em nível de classe
@WebMvcTest(controllers = TipoDataController.class)
public class TipoDataControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private TipoDataModelAssembler assembler;

    @MockBean
    private TipoDataModelDesassembler desassembler;

    @MockBean
    private TipoDataService tipoDataService;

    @Test
    void findAll_Returns200AndTipoDataModelList_WhenValidUrl() throws Exception {

        List<TipoData> tipoDataList = List.of(TipoDataCreator.createTipoData());

        List<TipoDataModel> tipoDataModelList = List.of(TipoDataCreator.createTipoDataModel());

        when(tipoDataService.getTipoData()).thenReturn(tipoDataList);

        when(assembler.toCollectionModel(any())).thenReturn(tipoDataModelList);

        MvcResult mvcResult = mockMvc.perform(get("/tipo-data"))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        String actualResponseBody = mvcResult.getResponse().getContentAsString();

        String expectedResponseBody = objectMapper.writeValueAsString(tipoDataModelList);

        assertThat(actualResponseBody)
                .isEqualToIgnoringWhitespace(expectedResponseBody);
    }

    @Test
    void findById_Returns200_WhenValidUrlAndParam() throws Exception {

        TipoData tipoData = TipoDataCreator.createTipoData();

        when(tipoDataService.getTipoDataById(anyLong())).thenReturn(tipoData);

        mockMvc.perform(get("/tipo-data/{idTipoData}", 10L))
                .andExpect(status().isOk())
                .andReturn();
    }

    @Test
    void findById_Returns400AndErrorMessage_WhenIdTipoDataIsNotALongType() throws Exception {

        MvcResult mvcResult = mockMvc.perform(get("/tipo-data/{idTipoData}", "x"))
                .andExpect(status().isBadRequest())
                .andReturn();

        String actualResponseBody = mvcResult.getResponse().getContentAsString();

        assertThat(actualResponseBody).containsIgnoringCase(MSG_ERRO_GENERICA_USUARIO_FINAL);
    }

    @Test
    void findById_Returns404AndErrorMessage_WhenTipoDataIsNotFound() throws Exception {

        Long idTipoData = 10L;

        when(tipoDataService.getTipoDataById(idTipoData))
                .thenThrow(new TipoDataNaoEncontradoException(idTipoData));

        MvcResult mvcResult = mockMvc.perform(get("/tipo-data/{idTipoData}", idTipoData))
                .andExpect(status().isNotFound())
                .andReturn();

        String actualResponseBody = mvcResult.getResponse().getContentAsString();

        assertThat(actualResponseBody)
                .containsIgnoringCase(String.format("NÃ£o foi encontrado um Tipo Data com o id %d", idTipoData));
    }

    @Test
    void create_Returns201AndTipoDataModel_WhenSuccessful() throws Exception {

        TipoDataInputModel tipoDataInputModel = TipoDataCreator.createTipoDataInputModel();

        TipoData newTipoData = TipoDataCreator.createTipoData();

        TipoData savedTipoData = TipoDataCreator.createSavedTipoData();

        TipoDataModel tipoDataModel = TipoDataCreator.createTipoDataModel();

        when(desassembler.toDomainModel(any())).thenReturn(newTipoData);

        when(tipoDataService.save(newTipoData)).thenReturn(savedTipoData);

        when(assembler.toModel(any())).thenReturn(tipoDataModel);

        MvcResult mvcResult = mockMvc.perform(post("/tipo-data")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(tipoDataInputModel)))
                .andExpect(status().isCreated())
                .andReturn();

        String actualResponse = mvcResult.getResponse().getContentAsString();

        String expectedResponse = objectMapper.writeValueAsString(tipoDataModel);

        assertThat(actualResponse).containsIgnoringCase(expectedResponse);

    }

    @Test
    void create_Returns400AndProblemDetails_WhenDescricaoIsNull() throws Exception {

        TipoDataInputModel invalidTipoDataInputModel = TipoDataCreator.createInvalidTipoDataInputModel();

        TipoData newTipoData = TipoDataCreator.createTipoData();

        TipoData savedTipoData = TipoDataCreator.createSavedTipoData();

        TipoDataModel tipoDataModel = TipoDataCreator.createTipoDataModel();

        when(desassembler.toDomainModel(any())).thenReturn(newTipoData);

        when(tipoDataService.save(newTipoData)).thenReturn(savedTipoData);

        when(assembler.toModel(any())).thenReturn(tipoDataModel);

        MvcResult mvcResult = mockMvc.perform(post("/tipo-data")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(invalidTipoDataInputModel)))
                .andExpect(status().isBadRequest())
                .andReturn();

        String actualResponse = mvcResult.getResponse().getContentAsString();

        String excpectedMessage = "\"objetos\":[{\"nome\":\"descricao\",\"mensagemUsuario\":\"must not be blank\"}]";

        assertThat(actualResponse).containsIgnoringCase(excpectedMessage);
    }

    @Test
    void update_Returns200AndTipoDataModel_WhenSuccessful() throws Exception{

        final Long idTipoData = 1L;

        TipoDataInputModel tipoDataInputModelToBeUpdated = TipoDataCreator.createTipoDataInputModel();
        tipoDataInputModelToBeUpdated.setDescricao("Atualizado");

        TipoData updatedTipoData = TipoDataCreator.createTipoData();
        updatedTipoData.setDescricao("Atualizado");

        TipoData savedTipoData = TipoDataCreator.createSavedTipoData();

        TipoDataModel updatedTipoDataModel = TipoDataCreator.createTipoDataModel();

        when(tipoDataService.getTipoDataById(idTipoData)).thenReturn(savedTipoData);

        when(tipoDataService.save(savedTipoData)).thenReturn(updatedTipoData);

        when(assembler.toModel(any())).thenReturn(updatedTipoDataModel);

        MvcResult mvcResult = mockMvc.perform(put("/tipo-data/{idTipoData}", idTipoData)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(tipoDataInputModelToBeUpdated)))
                .andExpect(status().isOk())
                .andReturn();

        String actualResponse = mvcResult.getResponse().getContentAsString();

        String expectedResponse = objectMapper.writeValueAsString(updatedTipoDataModel);

        assertThat(actualResponse).containsIgnoringCase(expectedResponse);
    }

    @Test
    void update_Returns400AndProblemDetails_WhenDescricaoIsNull() throws Exception{

        final Long idTipoData = 1L;

        TipoDataInputModel tipoDataInputModelToBeUpdated = TipoDataCreator.createInvalidTipoDataInputModel();

        MvcResult mvcResult = mockMvc.perform(put("/tipo-data/{idTipoData}", idTipoData)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(tipoDataInputModelToBeUpdated)))
                .andExpect(status().isBadRequest())
                .andReturn();

        String actualResponse = mvcResult.getResponse().getContentAsString();

        String expectedErrors = "[{\"nome\":\"descricao\",\"mensagemUsuario\":\"must not be blank\"}]";

        assertThat(actualResponse).containsIgnoringCase(expectedErrors);
    }

    @Test
    void update_Returns404AndProblemDetails_WhenTipoDataIsNotFound() throws Exception{

        final Long idTipoData = 10L;

        TipoDataInputModel tipoDataInputModelToBeUpdated = TipoDataCreator.createTipoDataInputModel();

        TipoData savedTipoData = TipoDataCreator.createSavedTipoData();

        when(tipoDataService.getTipoDataById(idTipoData))
                .thenThrow(new TipoDataNaoEncontradoException(idTipoData));

        MvcResult mvcResult = mockMvc.perform(put("/tipo-data/{idTipoData}", idTipoData)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(tipoDataInputModelToBeUpdated)))
                .andExpect(status().isNotFound())
                .andReturn();

        String actualResponse = mvcResult.getResponse().getContentAsString();

        String expectedErrors = String.format("NÃ£o foi encontrado um Tipo Data com o id %d", idTipoData);

        assertThat(actualResponse).containsIgnoringCase(expectedErrors);
    }

    @Test
    void delete_Returns204_WhenSuccessful() throws Exception{

        final Long idTipoData = 10L;

        MvcResult mvcResult = mockMvc.perform(delete("/tipo-data/{idTipoData}", idTipoData))
                .andExpect(status().isNoContent())
                .andReturn();
    }

    @Test
    void delete_Returns404AndProblemDetails_WhenTipoDataIsNotFound() throws Exception{

        final Long idTipoData = 10L;

        doThrow(new TipoDataNaoEncontradoException(idTipoData)).when(tipoDataService).deleteTipoData(idTipoData);

        MvcResult mvcResult = mockMvc.perform(delete("/tipo-data/{idTipoData}", idTipoData))
                .andExpect(status().isNotFound())
                .andReturn();

        String actualResponseBody = mvcResult.getResponse().getContentAsString();

        String expectedDetails = String.format("NÃ£o foi encontrado um Tipo Data com o id %d", idTipoData);

        assertThat(actualResponseBody).containsIgnoringCase(expectedDetails);

    }

    @Test
    void delete_Returns409AndProblemDetails_WhenTipoDataIsInUse() throws Exception{

        final Long idTipoData = 10L;

        doThrow(new EntidadeEmUsoException(String.format("O Tipo de Data com código %d não pode ser removido pois está em uso", idTipoData)))
                .when(tipoDataService).deleteTipoData(idTipoData);

        MvcResult mvcResult = mockMvc.perform(delete("/tipo-data/{idTipoData}", idTipoData))
                .andExpect(status().isConflict())
                .andReturn();

        String actualResponseBody = mvcResult.getResponse().getContentAsString();

        String expectedDetails = String.format("O Tipo de Data com cÃ³digo %d nÃ£o pode ser removido pois estÃ¡ em uso", idTipoData);

        assertThat(actualResponseBody).containsIgnoringCase(expectedDetails);
    }
}
