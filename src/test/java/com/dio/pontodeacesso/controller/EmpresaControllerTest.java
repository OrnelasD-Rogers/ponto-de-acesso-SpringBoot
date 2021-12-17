package com.dio.pontodeacesso.controller;

import com.dio.pontodeacesso.application.assembler.EmpresaModelAssembler;
import com.dio.pontodeacesso.application.assembler.EmpresaModelDesassembler;
import com.dio.pontodeacesso.application.model.EmpresaModel;
import com.dio.pontodeacesso.application.model.input.EmpresaInputModel;
import com.dio.pontodeacesso.domain.exception.EntidadeEmUsoException;
import com.dio.pontodeacesso.domain.exception.EmpresaNaoEncontradaException;
import com.dio.pontodeacesso.domain.model.Empresa;
import com.dio.pontodeacesso.domain.service.EmpresaService;
import com.dio.pontodeacesso.util.EmpresaCreator;
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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = EmpresaController.class)
public class EmpresaControllerTest {

    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private EmpresaService empresaService;
    @MockBean
    private EmpresaModelAssembler assembler;
    @MockBean
    private EmpresaModelDesassembler desassembler;

    private final Long ID_EMPRESA = 1L;
    private final String URL = "/empresa";
    private final String PATH = "/{idEmpresa}";

    @Test
    void findAll_Returns200AndEmpresaList_WhenValidUrl() throws Exception{

        List<Empresa> empresaList = List.of(EmpresaCreator.createValidEmpresa());

        List<EmpresaModel> empresaModelList = List.of(EmpresaCreator.createValidEmpresaModel());

        when(empresaService.findAll()).thenReturn(empresaList);

        when(assembler.toCollectionModel(any())).thenReturn(empresaModelList);

        MvcResult mvcResult = mockMvc.perform(get(URL))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        String actualResponseBody = mvcResult.getResponse().getContentAsString();

        String expectedResponseBody = objectMapper.writeValueAsString(empresaModelList);

        assertThat(actualResponseBody)
                .isEqualToIgnoringWhitespace(expectedResponseBody);
    }

    @Test
    void findById_Returns200AndEmpresa_WhenSuccessful() throws Exception {

        Empresa empresa = EmpresaCreator.createValidEmpresa();

        EmpresaModel empresaModel = EmpresaCreator.createValidEmpresaModel();

        when(empresaService.findById(anyLong())).thenReturn(empresa);

        when(assembler.toModel(any())).thenReturn(empresaModel);

        MvcResult mvcResult = mockMvc.perform(get(URL + PATH, ID_EMPRESA))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        String actualResponseBody = mvcResult.getResponse().getContentAsString();

        String expectedResponseBody = objectMapper.writeValueAsString(empresaModel);

        assertThat(actualResponseBody)
                .isEqualToIgnoringWhitespace(expectedResponseBody);
    }

    @Test
    void findById_Returns400AndErrorMessage_WhenIdEmpresaIsNotALongType() throws Exception {

        MvcResult mvcResult = mockMvc.perform(get(URL + PATH, "x"))
                .andExpect(status().isBadRequest())
                .andReturn();

        String actualResponseBody = mvcResult.getResponse().getContentAsString();

        assertThat(actualResponseBody).containsIgnoringCase(MSG_ERRO_GENERICA_USUARIO_FINAL);
    }

    @Test
    void findById_Returns404AndProblemDetails_WhenEmpresaIsNotFound() throws Exception {

        when(empresaService.findById(anyLong()))
                .thenThrow(new EmpresaNaoEncontradaException(ID_EMPRESA));

        MvcResult mvcResult = mockMvc.perform(get(URL + PATH, ID_EMPRESA))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andReturn();

        String actualResponseBody = mvcResult.getResponse().getContentAsString();

        String expectedResponseBody = String.format("A empresa com o id %d nÃ£o foi encotnrada", ID_EMPRESA);

        assertThat(actualResponseBody)
                .containsIgnoringCase(expectedResponseBody);
    }

    @Test
    void create_Returns201AndEmpresa_WhenSuccessful() throws Exception {

        Empresa toBeSavedEmpresa = EmpresaCreator.createValidEmpresa();

        Empresa savedEmpresa = EmpresaCreator.createSavedEmpresa();

        EmpresaModel empresaModel = EmpresaCreator.createValidEmpresaModel();

        EmpresaInputModel empresaInputModel = EmpresaCreator.createValidEmpresaInputModel();

        when(empresaService.save(any(Empresa.class))).thenReturn(savedEmpresa);

        when(desassembler.toDomainModel(any())).thenReturn(toBeSavedEmpresa);

        when(assembler.toModel(any())).thenReturn(empresaModel);

        MvcResult mvcResult = mockMvc.perform(post(URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(empresaInputModel)))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andReturn();

        String actualResponseBody = mvcResult.getResponse().getContentAsString();

        String expectedResponse = objectMapper.writeValueAsString(empresaModel);

        assertThat(actualResponseBody).containsIgnoringCase(expectedResponse);
    }

    @Test
    void create_Returns400AndProblemDetails_WhenDescricaoIsNull() throws Exception {

        EmpresaInputModel empresaInputModel = EmpresaCreator.createInvalidDescricaoInEmpresaInputModel();

        MvcResult mvcResult = mockMvc.perform(post(URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(empresaInputModel)))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andReturn();

        String actualResponseBody = mvcResult.getResponse().getContentAsString();

        String expectedError = "[{\"nome\":\"descricao\",\"mensagemUsuario\":\"must not be blank\"}]";

        assertThat(actualResponseBody).containsIgnoringCase(expectedError);
    }

    @Test
    void create_Returns400AndProblemDetails_WhenCnpjIsNull() throws Exception {

        EmpresaInputModel empresaInputModel = EmpresaCreator.createInvalidCnpjInEmpresaInputModel();

        MvcResult mvcResult = mockMvc.perform(post(URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(empresaInputModel)))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andReturn();

        String actualResponseBody = mvcResult.getResponse().getContentAsString();

        String expectedError = "[{\"nome\":\"cnpj\",\"mensagemUsuario\":\"must not be blank\"}]";

        assertThat(actualResponseBody).containsIgnoringCase(expectedError);
    }

    @Test
    void create_Returns400AndProblemDetails_WhenEnderecoIsNull() throws Exception {

        EmpresaInputModel empresaInputModel = EmpresaCreator.createInvalidEnderecoInEmpresaInputModel();

        MvcResult mvcResult = mockMvc.perform(post(URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(empresaInputModel)))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andReturn();

        String actualResponseBody = mvcResult.getResponse().getContentAsString();

        String expectedError = "[{\"nome\":\"endereco\",\"mensagemUsuario\":\"must not be blank\"}]";

        assertThat(actualResponseBody).containsIgnoringCase(expectedError);
    }

    @Test
    void create_Returns400AndProblemDetails_WhenBairroIsNull() throws Exception {

        EmpresaInputModel empresaInputModel = EmpresaCreator.createInvalidBairroInEmpresaInputModel();

        MvcResult mvcResult = mockMvc.perform(post(URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(empresaInputModel)))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andReturn();

        String actualResponseBody = mvcResult.getResponse().getContentAsString();

        String expectedError = "[{\"nome\":\"bairro\",\"mensagemUsuario\":\"must not be blank\"}]";

        assertThat(actualResponseBody).containsIgnoringCase(expectedError);
    }

    @Test
    void create_Returns400AndProblemDetails_WhenCidadeIsNull() throws Exception {

        EmpresaInputModel empresaInputModel = EmpresaCreator.createInvalidCidadeInEmpresaInputModel();

        MvcResult mvcResult = mockMvc.perform(post(URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(empresaInputModel)))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andReturn();

        String actualResponseBody = mvcResult.getResponse().getContentAsString();

        String expectedError = "[{\"nome\":\"cidade\",\"mensagemUsuario\":\"must not be blank\"}]";

        assertThat(actualResponseBody).containsIgnoringCase(expectedError);
    }

    @Test
    void create_Returns400AndProblemDetails_WhenEstadoIsNull() throws Exception {

        EmpresaInputModel empresaInputModel = EmpresaCreator.createInvalidEstadoInEmpresaInputModel();

        MvcResult mvcResult = mockMvc.perform(post(URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(empresaInputModel)))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andReturn();

        String actualResponseBody = mvcResult.getResponse().getContentAsString();

        String expectedError = "[{\"nome\":\"estado\",\"mensagemUsuario\":\"must not be blank\"}]";

        assertThat(actualResponseBody).containsIgnoringCase(expectedError);
    }

    @Test
    void create_Returns400AndProblemDetails_WhenTelefoneIsNull() throws Exception {

        EmpresaInputModel empresaInputModel = EmpresaCreator.createInvalidTelefoneInEmpresaInputModel();

        MvcResult mvcResult = mockMvc.perform(post(URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(empresaInputModel)))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andReturn();

        String actualResponseBody = mvcResult.getResponse().getContentAsString();

        String expectedError = "[{\"nome\":\"telefone\",\"mensagemUsuario\":\"must not be blank\"}]";

        assertThat(actualResponseBody).containsIgnoringCase(expectedError);
    }

    @Test
    void update_Returns200AndEmpresaModel_WhenSuccessful() throws Exception{

        EmpresaInputModel toBeUpdatedEmpresaInputModel = EmpresaCreator.createValidEmpresaInputModel();
        toBeUpdatedEmpresaInputModel.setDescricao("Atualizado");

        Empresa updatedEmpresa = EmpresaCreator.createSavedEmpresa();
        updatedEmpresa.setDescricao("Atualizado");

        Empresa savedEmpresa = EmpresaCreator.createSavedEmpresa();

        EmpresaModel updatedEmpresaModel = EmpresaCreator.createValidEmpresaModel();

        when(empresaService.findById(anyLong())).thenReturn(savedEmpresa);

        when(empresaService.save(savedEmpresa)).thenReturn(updatedEmpresa);

        when(assembler.toModel(any())).thenReturn(updatedEmpresaModel);

        MvcResult mvcResult = mockMvc.perform(put(URL + PATH, ID_EMPRESA)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(toBeUpdatedEmpresaInputModel)))
                .andExpect(status().isOk())
                .andReturn();

        String actualResponse = mvcResult.getResponse().getContentAsString();

        String expectedResponse = objectMapper.writeValueAsString(updatedEmpresaModel);

        assertThat(actualResponse).containsIgnoringCase(expectedResponse);
    }

    @Test
    void update_Returns404AndProblemDetails_WhenEmpresaIsNotFound() throws Exception{

        EmpresaInputModel toBeUpdatedEmpresaInputModel = EmpresaCreator.createValidEmpresaInputModel();
        toBeUpdatedEmpresaInputModel.setDescricao("Atualizado");

        when(empresaService.findById(anyLong()))
                .thenThrow(new EmpresaNaoEncontradaException(ID_EMPRESA));


        MvcResult mvcResult = mockMvc.perform(put(URL + PATH, ID_EMPRESA)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(toBeUpdatedEmpresaInputModel)))
                .andExpect(status().isNotFound())
                .andReturn();

        String actualResponse = mvcResult.getResponse().getContentAsString();

        String expectedError = String.format("A empresa com o id %d nÃ£o foi encotnrada", ID_EMPRESA);

        assertThat(actualResponse).containsIgnoringCase(expectedError);
    }

    @Test
    void update_Returns400AndProblemDetails_WhenDescricaoIsNull() throws Exception{

        EmpresaInputModel invalidEmpresaInput = EmpresaCreator.createInvalidDescricaoInEmpresaInputModel();

        MvcResult mvcResult = mockMvc.perform(put(URL + PATH, ID_EMPRESA)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(invalidEmpresaInput)))
                .andExpect(status().isBadRequest())
                .andReturn();

        String actualResponse = mvcResult.getResponse().getContentAsString();

        String expectedErrors = "[{\"nome\":\"descricao\",\"mensagemUsuario\":\"must not be blank\"}]";

        assertThat(actualResponse).containsIgnoringCase(expectedErrors);
    }

    @Test
    void update_Returns400AndProblemDetails_WhenCnpjIsNull() throws Exception{

        EmpresaInputModel invalidEmpresaInput = EmpresaCreator.createInvalidCnpjInEmpresaInputModel();

        MvcResult mvcResult = mockMvc.perform(put(URL + PATH, ID_EMPRESA)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(invalidEmpresaInput)))
                .andExpect(status().isBadRequest())
                .andReturn();

        String actualResponse = mvcResult.getResponse().getContentAsString();

        String expectedErrors = "[{\"nome\":\"cnpj\",\"mensagemUsuario\":\"must not be blank\"}]";

        assertThat(actualResponse).containsIgnoringCase(expectedErrors);
    }

    @Test
    void update_Returns400AndProblemDetails_WhenEnderecoIsNull() throws Exception{

        EmpresaInputModel invalidEmpresaInput = EmpresaCreator.createInvalidEnderecoInEmpresaInputModel();

        MvcResult mvcResult = mockMvc.perform(put(URL + PATH, ID_EMPRESA)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(invalidEmpresaInput)))
                .andExpect(status().isBadRequest())
                .andReturn();

        String actualResponse = mvcResult.getResponse().getContentAsString();

        String expectedErrors = "[{\"nome\":\"endereco\",\"mensagemUsuario\":\"must not be blank\"}]";

        assertThat(actualResponse).containsIgnoringCase(expectedErrors);
    }

    @Test
    void update_Returns400AndProblemDetails_WhenBairroIsNull() throws Exception{

        EmpresaInputModel invalidEmpresaInput = EmpresaCreator.createInvalidBairroInEmpresaInputModel();

        MvcResult mvcResult = mockMvc.perform(put(URL + PATH, ID_EMPRESA)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(invalidEmpresaInput)))
                .andExpect(status().isBadRequest())
                .andReturn();

        String actualResponse = mvcResult.getResponse().getContentAsString();

        String expectedErrors = "[{\"nome\":\"bairro\",\"mensagemUsuario\":\"must not be blank\"}]";

        assertThat(actualResponse).containsIgnoringCase(expectedErrors);
    }

    @Test
    void update_Returns400AndProblemDetails_WhenCidadeIsNull() throws Exception{

        EmpresaInputModel invalidEmpresaInput = EmpresaCreator.createInvalidCidadeInEmpresaInputModel();

        MvcResult mvcResult = mockMvc.perform(put(URL + PATH, ID_EMPRESA)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(invalidEmpresaInput)))
                .andExpect(status().isBadRequest())
                .andReturn();

        String actualResponse = mvcResult.getResponse().getContentAsString();

        String expectedErrors = "[{\"nome\":\"cidade\",\"mensagemUsuario\":\"must not be blank\"}]";

        assertThat(actualResponse).containsIgnoringCase(expectedErrors);
    }

    @Test
    void update_Returns400AndProblemDetails_WhenEstadoIsNull() throws Exception{

        EmpresaInputModel invalidEmpresaInput = EmpresaCreator.createInvalidEstadoInEmpresaInputModel();

        MvcResult mvcResult = mockMvc.perform(put(URL + PATH, ID_EMPRESA)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(invalidEmpresaInput)))
                .andExpect(status().isBadRequest())
                .andReturn();

        String actualResponse = mvcResult.getResponse().getContentAsString();

        String expectedErrors = "[{\"nome\":\"estado\",\"mensagemUsuario\":\"must not be blank\"}]";

        assertThat(actualResponse).containsIgnoringCase(expectedErrors);
    }

    @Test
    void update_Returns400AndProblemDetails_WhenTelefoneIsNull() throws Exception{

        EmpresaInputModel invalidEmpresaInput = EmpresaCreator.createInvalidTelefoneInEmpresaInputModel();

        MvcResult mvcResult = mockMvc.perform(put(URL + PATH, ID_EMPRESA)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(invalidEmpresaInput)))
                .andExpect(status().isBadRequest())
                .andReturn();

        String actualResponse = mvcResult.getResponse().getContentAsString();

        String expectedErrors = "[{\"nome\":\"telefone\",\"mensagemUsuario\":\"must not be blank\"}]";

        assertThat(actualResponse).containsIgnoringCase(expectedErrors);
    }

    @Test
    void delete_Returns204_WhenSuccessful() throws Exception{

        MvcResult mvcResult = mockMvc.perform(delete(URL + PATH, ID_EMPRESA))
                .andExpect(status().isNoContent())
                .andReturn();
    }

    @Test
    void delete_Returns404AndProblemDetails_WhenEmpresaIsNotFound() throws Exception{

        doThrow(new EmpresaNaoEncontradaException(ID_EMPRESA))
                .when(empresaService).delete(ID_EMPRESA);

        MvcResult mvcResult = mockMvc.perform(delete(URL + PATH, ID_EMPRESA))
                .andExpect(status().isNotFound())
                .andReturn();

        String actualResponseBody = mvcResult.getResponse().getContentAsString();

        String expectedDetails = String.format("A empresa com o id %d nÃ£o foi encotnrada", ID_EMPRESA);

        assertThat(actualResponseBody).containsIgnoringCase(expectedDetails);
    }

    @Test
    void delete_Returns409AndProblemDetails_WhenEmpresaIsInUse() throws Exception {

        doThrow(new EntidadeEmUsoException(String.format("A Empresa com o id %d está em uso e não pode ser excluida", ID_EMPRESA)))
                .when(empresaService).delete(ID_EMPRESA);

        MvcResult mvcResult = mockMvc.perform(delete(URL + PATH, ID_EMPRESA))
                .andExpect(status().isConflict())
                .andReturn();

        String actualResponseBody = mvcResult.getResponse().getContentAsString();

        String expectedDetails = String.format("A Empresa com o id %d estÃ¡ em uso e nÃ£o pode ser excluida", ID_EMPRESA);

        assertThat(actualResponseBody).containsIgnoringCase(expectedDetails);
    }
}
