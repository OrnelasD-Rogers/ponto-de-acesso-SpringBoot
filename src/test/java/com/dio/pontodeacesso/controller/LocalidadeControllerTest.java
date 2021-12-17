package com.dio.pontodeacesso.controller;

import com.dio.pontodeacesso.application.assembler.LocalidadeModelAssembler;
import com.dio.pontodeacesso.application.assembler.LocalidadeModelDesassembler;
import com.dio.pontodeacesso.application.model.LocalidadeModel;
import com.dio.pontodeacesso.application.model.input.LocalidadeInputModel;
import com.dio.pontodeacesso.domain.exception.LocalidadeNaoEncontradaException;
import com.dio.pontodeacesso.domain.model.Localidade;
import com.dio.pontodeacesso.domain.service.LocalidadeService;
import com.dio.pontodeacesso.util.LocalidadeCreator;
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
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = LocalidadeController.class)
public class LocalidadeControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @MockBean
    private LocalidadeService localidadeService;
    @MockBean
    private LocalidadeModelAssembler assembler;
    @MockBean
    private LocalidadeModelDesassembler desassembler;

    private final String URL = "/localidade";

    private final String PATH = "/{idLocalidade}-{idLocalidade}";

    private final Long ID_LOCALIDADE = 1l;

    private final Long ID_NIVEL_ACESSO = 1l;

    @Test
    void findAll_Returns200AndLocalidadeList_WhenValidUrl() throws Exception{

        List<Localidade> localidadeList = List.of(LocalidadeCreator.createValidLocalidade());

        List<LocalidadeModel> localidadeModelList = List.of(LocalidadeCreator.createLocalidadeModel());

        when(localidadeService.findAll()).thenReturn(localidadeList);

        when(assembler.toCollectionModel(any())).thenReturn(localidadeModelList);

        MvcResult mvcResult = mockMvc.perform(get(URL))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        String actualResponseBody = mvcResult.getResponse().getContentAsString();

        String expectedResponseBody = objectMapper.writeValueAsString(localidadeModelList);

        assertThat(actualResponseBody)
                .isEqualToIgnoringWhitespace(expectedResponseBody);
    }

    @Test
    void findById_Returns200AndLocalidade_WhenSuccessful() throws Exception {

        Localidade localidade = LocalidadeCreator.createValidLocalidade();

        LocalidadeModel localidadeModel = LocalidadeCreator.createLocalidadeModel();

        when(localidadeService.findById(any())).thenReturn(localidade);

        when(assembler.toModel(any())).thenReturn(localidadeModel);

        MvcResult mvcResult = mockMvc.perform(get(URL+PATH, ID_LOCALIDADE, ID_NIVEL_ACESSO))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        String actualResponseBody = mvcResult.getResponse().getContentAsString();

        String expectedResponseBody = objectMapper.writeValueAsString(localidadeModel);

        assertThat(actualResponseBody)
                .isEqualToIgnoringWhitespace(expectedResponseBody);
    }

    @Test
    void findById_Returns400AndErrorMessage_WhenIdLocalidadeIsNotALongType() throws Exception {

        MvcResult mvcResult = mockMvc.perform(get(URL+PATH, "x", "y"))
                .andExpect(status().isBadRequest())
                .andReturn();

        String actualResponseBody = mvcResult.getResponse().getContentAsString();

        assertThat(actualResponseBody).containsIgnoringCase(MSG_ERRO_GENERICA_USUARIO_FINAL);
    }

    @Test
    void findById_Returns404AndProblemDetails_WhenLocalidadeNotFound() throws Exception {

        when(localidadeService.findById(any()))
                .thenThrow(new LocalidadeNaoEncontradaException(LocalidadeCreator.createLocalidadeId(ID_LOCALIDADE,ID_NIVEL_ACESSO)));

        MvcResult mvcResult = mockMvc.perform(get(URL+PATH, ID_LOCALIDADE, ID_NIVEL_ACESSO))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andReturn();

        String actualResponseBody = mvcResult.getResponse().getContentAsString();

        String expectedResponseBody = String.format("A localidade com o id (%d, %d) nÃ£o foi encontrada", ID_LOCALIDADE, ID_NIVEL_ACESSO);

        assertThat(actualResponseBody)
                .containsIgnoringCase(expectedResponseBody);
    }

    @Test
    void create_Returns201AndLocalidade_WhenSuccessful() throws Exception {

        Localidade toBeSavedLocalidade = LocalidadeCreator.createValidLocalidade();

        Localidade savedLocalidade = LocalidadeCreator.createValidLocalidade();

        LocalidadeModel localidadeModel = LocalidadeCreator.createLocalidadeModel();

        LocalidadeInputModel localidadeInputModel = LocalidadeCreator.createValidLocalidadeInput();

        when(localidadeService.save(any(Localidade.class))).thenReturn(savedLocalidade);

        when(desassembler.toDomainModel(any())).thenReturn(toBeSavedLocalidade);

        when(assembler.toModel(any())).thenReturn(localidadeModel);

        MvcResult mvcResult = mockMvc.perform(post(URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(localidadeInputModel)))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andReturn();

        String actualResponseBody = mvcResult.getResponse().getContentAsString();

        String expectedResponse = objectMapper.writeValueAsString(localidadeModel);

        assertThat(actualResponseBody).containsIgnoringCase(expectedResponse);
    }

    @Test
    void create_Returns400AndProblemDetails_WhenIdLocalidadeIsNull() throws Exception {

        LocalidadeInputModel localidadeInputModel = LocalidadeCreator.createInvalidIdInLocalidadeInput();

        MvcResult mvcResult = mockMvc.perform(post(URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(localidadeInputModel)))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andReturn();

        String actualResponseBody = mvcResult.getResponse().getContentAsString();

        String expectedError = "[{\"nome\":\"localidadeId\",\"mensagemUsuario\":\"must not be null\"}]";

        assertThat(actualResponseBody).containsIgnoringCase(expectedError);
    }

    @Test
    void create_Returns400AndProblemDetails_WhenIdInLocalidadeIdIsNull() throws Exception {

        LocalidadeInputModel localidadeInputModel = LocalidadeCreator.createInvalidIdInLocalidadeInputId();

        MvcResult mvcResult = mockMvc.perform(post(URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(localidadeInputModel)))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andReturn();

        String actualResponseBody = mvcResult.getResponse().getContentAsString();

        String expectedError = "[{\"nome\":\"localidadeId.id\",\"mensagemUsuario\":\"must not be null\"}]";

        assertThat(actualResponseBody).containsIgnoringCase(expectedError);
    }

    @Test
    void create_Returns400AndProblemDetails_WhenNivelAcessoIdInLocalidadeIdIsNull() throws Exception {

        LocalidadeInputModel localidadeInputModel = LocalidadeCreator.createInvalidNivelAcessoIdInLocalidadeInput();

        MvcResult mvcResult = mockMvc.perform(post(URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(localidadeInputModel)))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andReturn();

        String actualResponseBody = mvcResult.getResponse().getContentAsString();

        String expectedError = "[{\"nome\":\"localidadeId.id_NivelAcesso\",\"mensagemUsuario\":\"must not be null\"}]";

        assertThat(actualResponseBody).containsIgnoringCase(expectedError);
    }

    @Test
    void create_Returns400AndProblemDetails_WhenDescricaoIsNull() throws Exception {

        LocalidadeInputModel localidadeInputModel = LocalidadeCreator.createInvalidDescricaoInLocalidadeInput();

        MvcResult mvcResult = mockMvc.perform(post(URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(localidadeInputModel)))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andReturn();

        String actualResponseBody = mvcResult.getResponse().getContentAsString();

        String expectedError = "[{\"nome\":\"descricao\",\"mensagemUsuario\":\"must not be blank\"}]";

        assertThat(actualResponseBody).containsIgnoringCase(expectedError);
    }

    @Test
    void update_Returns200AndLocalidadeModel_WhenSuccessful() throws Exception{

        LocalidadeInputModel toBeUpdatedLocalidadeInputModel = LocalidadeCreator.createValidLocalidadeInput();
        toBeUpdatedLocalidadeInputModel.setDescricao("Atualizado");

        Localidade updatedLocalidade = LocalidadeCreator.createValidLocalidade();
        updatedLocalidade.setDescricao("Atualizado");

        Localidade savedLocalidade = LocalidadeCreator.createValidLocalidade();

        LocalidadeModel updatedLocalidadeModel = LocalidadeCreator.createLocalidadeModel();

        when(localidadeService.findById(any())).thenReturn(savedLocalidade);

        when(localidadeService.save(savedLocalidade)).thenReturn(updatedLocalidade);

        when(assembler.toModel(any())).thenReturn(updatedLocalidadeModel);

        MvcResult mvcResult = mockMvc.perform(put(URL+PATH, ID_LOCALIDADE, ID_NIVEL_ACESSO)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(updatedLocalidadeModel)))
                .andExpect(status().isOk())
                .andReturn();

        String actualResponse = mvcResult.getResponse().getContentAsString();

        String expectedResponse = objectMapper.writeValueAsString(updatedLocalidadeModel);

        assertThat(actualResponse).containsIgnoringCase(expectedResponse);
    }

    @Test
    void update_Returns404AndProblemDetails_WhenLocalidadeIsNotFound() throws Exception{

        LocalidadeInputModel toBeUpdatedLocalidadeInputModel = LocalidadeCreator.createValidLocalidadeInput();
        toBeUpdatedLocalidadeInputModel.setDescricao("Atualizado");

        when(localidadeService.findById(any()))
                .thenThrow(new LocalidadeNaoEncontradaException(LocalidadeCreator.createLocalidadeId(ID_LOCALIDADE, ID_NIVEL_ACESSO)));


        MvcResult mvcResult = mockMvc.perform(put(URL+PATH, ID_LOCALIDADE, ID_NIVEL_ACESSO)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(toBeUpdatedLocalidadeInputModel)))
                .andExpect(status().isNotFound())
                .andReturn();

        String actualResponse = mvcResult.getResponse().getContentAsString();

        String expectedError = String.format("A localidade com o id (%d, %d) nÃ£o foi encontrada", ID_LOCALIDADE, ID_NIVEL_ACESSO);

        assertThat(actualResponse).containsIgnoringCase(expectedError);
    }

    @Test
    void update_Returns400AndProblemDetails_WhenDescricaoIsNull() throws Exception{

        LocalidadeInputModel invalidLocalidadeInput = LocalidadeCreator.createInvalidDescricaoInLocalidadeInput();

        MvcResult mvcResult = mockMvc.perform(put(URL+PATH, ID_LOCALIDADE, ID_NIVEL_ACESSO)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(invalidLocalidadeInput)))
                .andExpect(status().isBadRequest())
                .andReturn();

        String actualResponse = mvcResult.getResponse().getContentAsString();

        String expectedErrors = "[{\"nome\":\"descricao\",\"mensagemUsuario\":\"must not be blank\"}]";

        assertThat(actualResponse).containsIgnoringCase(expectedErrors);
    }

    @Test
    void delete_Returns204_WhenSuccessful() throws Exception{

        MvcResult mvcResult = mockMvc.perform(delete(URL+PATH, ID_LOCALIDADE, ID_NIVEL_ACESSO))
                .andExpect(status().isNoContent())
                .andReturn();
    }

    @Test
    void delete_Returns404AndProblemDetails_WhenLocalidadeIsNotFound() throws Exception{

        doThrow(new LocalidadeNaoEncontradaException(LocalidadeCreator.createLocalidadeId(ID_LOCALIDADE, ID_NIVEL_ACESSO)))
                .when(localidadeService).delete(any());

        MvcResult mvcResult = mockMvc.perform(delete(URL+PATH, ID_LOCALIDADE, ID_NIVEL_ACESSO))
                .andExpect(status().isNotFound())
                .andReturn();

        String actualResponseBody = mvcResult.getResponse().getContentAsString();

        String expectedDetails = String.format("A localidade com o id (%d, %d) nÃ£o foi encontrada", ID_LOCALIDADE, ID_NIVEL_ACESSO);

        assertThat(actualResponseBody).containsIgnoringCase(expectedDetails);
    }



}
