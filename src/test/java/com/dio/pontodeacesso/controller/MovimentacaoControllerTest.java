package com.dio.pontodeacesso.controller;

import com.dio.pontodeacesso.application.assembler.MovimentacaoModelAssembler;
import com.dio.pontodeacesso.application.assembler.MovimentacaoModelDesassembler;
import com.dio.pontodeacesso.application.model.MovimentacaoModel;
import com.dio.pontodeacesso.application.model.input.MovimentacaoInputModel;
import com.dio.pontodeacesso.domain.exception.EntidadeEmUsoException;
import com.dio.pontodeacesso.domain.exception.MovimentacaoNaoEncontradaException;
import com.dio.pontodeacesso.domain.model.Movimentacao;
import com.dio.pontodeacesso.domain.service.MovimentacaoService;
import com.dio.pontodeacesso.util.MovimentacaoCreator;
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
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = MovimentacaoController.class)
public class MovimentacaoControllerTest {

    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private MovimentacaoService movimentacaoService;
    @MockBean
    private MovimentacaoModelAssembler assembler;
    @MockBean
    private MovimentacaoModelDesassembler desassembler;

    private final String URL = "/movimentacao";

    private final String PATH = "/{idMovimento}-{idUsuario}";

    private final Long ID_MOV = 1l;

    private final Long ID_USU = 1l;


    @Test
    void findAll_Returns200AndMovimentacaoList_WhenValidUrl() throws Exception{

        List<Movimentacao> movimentacaoList = List.of(MovimentacaoCreator.createValidMovimentacao());

        List<MovimentacaoModel> movimentacaoModelList = List.of(MovimentacaoCreator.createValidMovimentacaoModel());

        when(movimentacaoService.findAll()).thenReturn(movimentacaoList);

        when(assembler.toCollectionModel(any())).thenReturn(movimentacaoModelList);

        MvcResult mvcResult = mockMvc.perform(get(URL))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        String actualResponseBody = mvcResult.getResponse().getContentAsString();

        String expectedResponseBody = objectMapper.writeValueAsString(movimentacaoModelList);

        assertThat(actualResponseBody)
                .isEqualToIgnoringWhitespace(expectedResponseBody);
    }

    @Test
    void findById_Returns200AndMovimentacao_WhenSuccessful() throws Exception {

        Movimentacao movimentacao = MovimentacaoCreator.createValidMovimentacao();

        MovimentacaoModel movimentacaoModel = MovimentacaoCreator.createValidMovimentacaoModel();

        when(movimentacaoService.findById(any())).thenReturn(movimentacao);

        when(assembler.toModel(any())).thenReturn(movimentacaoModel);

        MvcResult mvcResult = mockMvc.perform(get(URL+PATH, ID_MOV, ID_USU))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        String actualResponseBody = mvcResult.getResponse().getContentAsString();

        String expectedResponseBody = objectMapper.writeValueAsString(movimentacaoModel);

        assertThat(actualResponseBody)
                .isEqualToIgnoringWhitespace(expectedResponseBody);
    }

    @Test
    void findById_Returns400AndErrorMessage_WhenIdMovimentacaoIsNotALongType() throws Exception {

        MvcResult mvcResult = mockMvc.perform(get(URL+PATH, "x", "c"))
                .andExpect(status().isBadRequest())
                .andReturn();

        String actualResponseBody = mvcResult.getResponse().getContentAsString();

        assertThat(actualResponseBody).containsIgnoringCase(MSG_ERRO_GENERICA_USUARIO_FINAL);
    }

    @Test
    void findById_Returns404AndProblemDetails_WhenMovimentacaoNotFound() throws Exception {

        Movimentacao.MovimentacaoId movId = MovimentacaoCreator.movimentacaoId(ID_MOV, ID_USU);

        when(movimentacaoService.findById(any()))
                .thenThrow(new MovimentacaoNaoEncontradaException(movId));

        MvcResult mvcResult = mockMvc.perform(get(URL+PATH, ID_MOV, ID_USU))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andReturn();

        String actualResponseBody = mvcResult.getResponse().getContentAsString();

        String expectedResponseBody = String.format("NÃ£o existe uma movimentaÃ§Ã£o com o id (%d, %d)", ID_MOV, ID_USU);

        assertThat(actualResponseBody)
                .containsIgnoringCase(expectedResponseBody);
    }

    @Test
    void create_Returns201AndMovimentacao_WhenSuccessful() throws Exception {

        Movimentacao toBeSavedMovimentacao = MovimentacaoCreator.createValidMovimentacao();

        Movimentacao savedMovimentacao = MovimentacaoCreator.createValidMovimentacao();

        MovimentacaoModel movimentacaoModel = MovimentacaoCreator.createValidMovimentacaoModel();

        MovimentacaoInputModel movimentacaoInputModel = MovimentacaoCreator.createValidMovimentacaoInput();

        when(desassembler.toDomainModel(any())).thenReturn(toBeSavedMovimentacao);

        when(movimentacaoService.save(any(Movimentacao.class))).thenReturn(savedMovimentacao);

        when(assembler.toModel(any())).thenReturn(movimentacaoModel);

        MvcResult mvcResult = mockMvc.perform(post(URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(movimentacaoInputModel)))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andReturn();

        String actualResponseBody = mvcResult.getResponse().getContentAsString();

        String expectedResponse = objectMapper.writeValueAsString(movimentacaoModel);

        assertThat(actualResponseBody).containsIgnoringCase(expectedResponse);
    }

    @Test
    void create_Returns400AndProblemDetails_WhenInvalidMovimentacaoId() throws Exception {

        MovimentacaoInputModel movimentacaoInputModel = MovimentacaoCreator.createInvalidMovimentacaoIdInput();

        MvcResult mvcResult = mockMvc.perform(post(URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(movimentacaoInputModel)))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andReturn();

        String actualResponseBody = mvcResult.getResponse().getContentAsString();

        String expectedError = "[{\"nome\":\"movimentacaoId.id_movimentacao\",\"mensagemUsuario\":\"must not be null\"}]";

        assertThat(actualResponseBody).containsIgnoringCase(expectedError);
    }


    @Test
    void create_Returns400AndProblemDetails_WhenInvalidUsuarioId() throws Exception {

        MovimentacaoInputModel movimentacaoInputModel = MovimentacaoCreator.createInvalidUsuarioIdInput();

        MvcResult mvcResult = mockMvc.perform(post(URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(movimentacaoInputModel)))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andReturn();

        String actualResponseBody = mvcResult.getResponse().getContentAsString();

        String expectedError = "[{\"nome\":\"movimentacaoId.id_usuario\",\"mensagemUsuario\":\"must not be null\"}]";

        assertThat(actualResponseBody).containsIgnoringCase(expectedError);
    }

    @Test
    void create_Returns400AndProblemDetails_WhenInvalidDataEntrada() throws Exception {

        MovimentacaoInputModel movimentacaoInputModel = MovimentacaoCreator.createInvalidDataEntradaInMovimentacaoInput();

        MvcResult mvcResult = mockMvc.perform(post(URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(movimentacaoInputModel)))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andReturn();

        String actualResponseBody = mvcResult.getResponse().getContentAsString();

        String expectedError = "[{\"nome\":\"dataEntrada\",\"mensagemUsuario\":\"must not be null\"}]";

        assertThat(actualResponseBody).containsIgnoringCase(expectedError);
    }

    @Test
    void create_Returns400AndProblemDetails_WhenInvalidDataSaida() throws Exception {

        MovimentacaoInputModel movimentacaoInputModel = MovimentacaoCreator.createInvalidDataSaidaInMovimentacaoInput();

        MvcResult mvcResult = mockMvc.perform(post(URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(movimentacaoInputModel)))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andReturn();

        String actualResponseBody = mvcResult.getResponse().getContentAsString();

        String expectedError = "[{\"nome\":\"dataSaida\",\"mensagemUsuario\":\"must not be null\"}]";

        assertThat(actualResponseBody).containsIgnoringCase(expectedError);
    }

    @Test
    void create_Returns400AndProblemDetails_WhenInvalidPeriodo() throws Exception {

        MovimentacaoInputModel movimentacaoInputModel = MovimentacaoCreator.createInvalidPeriodoInMovimentacaoInput();

        MvcResult mvcResult = mockMvc.perform(post(URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(movimentacaoInputModel)))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andReturn();

        String actualResponseBody = mvcResult.getResponse().getContentAsString();

        String expectedError = "[{\"nome\":\"periodo\",\"mensagemUsuario\":\"must not be null\"}]";

        assertThat(actualResponseBody).containsIgnoringCase(expectedError);
    }

    @Test
    void create_Returns400AndProblemDetails_WhenOcorrenciaIsNull() throws Exception {

        MovimentacaoInputModel movimentacaoInputModel = MovimentacaoCreator.createNullOcorrenciaInMovimentacaoInput();

        MvcResult mvcResult = mockMvc.perform(post(URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(movimentacaoInputModel)))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andReturn();

        String actualResponseBody = mvcResult.getResponse().getContentAsString();

        String expectedError = "[{\"nome\":\"ocorrencia\",\"mensagemUsuario\":\"must not be null\"}]";

        assertThat(actualResponseBody).containsIgnoringCase(expectedError);
    }

    @Test
    void create_Returns400AndProblemDetails_WhenId_OcorrenciaIsNull() throws Exception {

        MovimentacaoInputModel movimentacaoInputModel = MovimentacaoCreator.createNullId_OcorrenciaInMovimentacaoInput();

        MvcResult mvcResult = mockMvc.perform(post(URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(movimentacaoInputModel)))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andReturn();

        String actualResponseBody = mvcResult.getResponse().getContentAsString();

        String expectedError = "[{\"nome\":\"ocorrencia.id_ocorrencia\",\"mensagemUsuario\":\"must not be null\"}]";

        assertThat(actualResponseBody).containsIgnoringCase(expectedError);
    }

    @Test
    void create_Returns400AndProblemDetails_WhenCalendarioIsNull() throws Exception {

        MovimentacaoInputModel movimentacaoInputModel = MovimentacaoCreator.createNullCalendarioInMovimentacaoInput();

        MvcResult mvcResult = mockMvc.perform(post(URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(movimentacaoInputModel)))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andReturn();

        String actualResponseBody = mvcResult.getResponse().getContentAsString();

        String expectedError = "[{\"nome\":\"calendario\",\"mensagemUsuario\":\"must not be null\"}]";

        assertThat(actualResponseBody).containsIgnoringCase(expectedError);
    }

    @Test
    void create_Returns400AndProblemDetails_WhenId_CalendarioIsNull() throws Exception {

        MovimentacaoInputModel movimentacaoInputModel = MovimentacaoCreator.createNullId_CalendarioInMovimentacaoInput();

        MvcResult mvcResult = mockMvc.perform(post(URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(movimentacaoInputModel)))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andReturn();

        String actualResponseBody = mvcResult.getResponse().getContentAsString();

        String expectedError = "[{\"nome\":\"calendario.id_Calendario\",\"mensagemUsuario\":\"must not be null\"}]";

        assertThat(actualResponseBody).containsIgnoringCase(expectedError);
    }


    @Test
    void update_Returns200AndMovimentacaoModel_WhenSuccessful() throws Exception{

        MovimentacaoInputModel toBeUpdatedMovimentacaoInputModel = MovimentacaoCreator.createValidMovimentacaoInput();
        toBeUpdatedMovimentacaoInputModel.setPeriodo(new BigDecimal(50));

        Movimentacao updatedMovimentacao = MovimentacaoCreator.createValidMovimentacao();
        updatedMovimentacao.setPeriodo(new BigDecimal(50));

        Movimentacao savedMovimentacao = MovimentacaoCreator.createValidMovimentacao();

        MovimentacaoModel updatedMovimentacaoModel = MovimentacaoCreator.createValidMovimentacaoModel();

        when(movimentacaoService.findById(any())).thenReturn(savedMovimentacao);

        when(movimentacaoService.save(savedMovimentacao)).thenReturn(updatedMovimentacao);

        when(assembler.toModel(any())).thenReturn(updatedMovimentacaoModel);

        MvcResult mvcResult = mockMvc.perform(put(URL+PATH, ID_MOV, ID_USU)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(toBeUpdatedMovimentacaoInputModel)))
                .andExpect(status().isOk())
                .andReturn();

        String actualResponse = mvcResult.getResponse().getContentAsString();

        String expectedResponse = objectMapper.writeValueAsString(updatedMovimentacaoModel);

        assertThat(actualResponse).containsIgnoringCase(expectedResponse);
    }

    @Test
    void update_Returns400AndProblemDetails_WhenInvalidMovimentacaoId() throws Exception {

        MovimentacaoInputModel movimentacaoInputModel = MovimentacaoCreator.createInvalidMovimentacaoIdInput();

        MvcResult mvcResult = mockMvc.perform(put(URL+PATH,ID_MOV, ID_USU)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(movimentacaoInputModel)))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andReturn();

        String actualResponseBody = mvcResult.getResponse().getContentAsString();

        String expectedError = "[{\"nome\":\"movimentacaoId.id_movimentacao\",\"mensagemUsuario\":\"must not be null\"}]";

        assertThat(actualResponseBody).containsIgnoringCase(expectedError);
    }


    @Test
    void update_Returns400AndProblemDetails_WhenInvalidUsuarioId() throws Exception {

        MovimentacaoInputModel movimentacaoInputModel = MovimentacaoCreator.createInvalidUsuarioIdInput();

        MvcResult mvcResult = mockMvc.perform(put(URL+PATH,ID_MOV, ID_USU)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(movimentacaoInputModel)))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andReturn();

        String actualResponseBody = mvcResult.getResponse().getContentAsString();

        String expectedError = "[{\"nome\":\"movimentacaoId.id_usuario\",\"mensagemUsuario\":\"must not be null\"}]";

        assertThat(actualResponseBody).containsIgnoringCase(expectedError);
    }

    @Test
    void update_Returns400AndProblemDetails_WhenInvalidDataEntrada() throws Exception {

        MovimentacaoInputModel movimentacaoInputModel = MovimentacaoCreator.createInvalidDataEntradaInMovimentacaoInput();

        MvcResult mvcResult = mockMvc.perform(put(URL+PATH,ID_MOV, ID_USU)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(movimentacaoInputModel)))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andReturn();

        String actualResponseBody = mvcResult.getResponse().getContentAsString();

        String expectedError = "[{\"nome\":\"dataEntrada\",\"mensagemUsuario\":\"must not be null\"}]";

        assertThat(actualResponseBody).containsIgnoringCase(expectedError);
    }

    @Test
    void update_Returns400AndProblemDetails_WhenInvalidDataSaida() throws Exception {

        MovimentacaoInputModel movimentacaoInputModel = MovimentacaoCreator.createInvalidDataSaidaInMovimentacaoInput();

        MvcResult mvcResult = mockMvc.perform(put(URL+PATH,ID_MOV, ID_USU)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(movimentacaoInputModel)))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andReturn();

        String actualResponseBody = mvcResult.getResponse().getContentAsString();

        String expectedError = "[{\"nome\":\"dataSaida\",\"mensagemUsuario\":\"must not be null\"}]";

        assertThat(actualResponseBody).containsIgnoringCase(expectedError);
    }

    @Test
    void update_Returns400AndProblemDetails_WhenInvalidPeriodo() throws Exception {

        MovimentacaoInputModel movimentacaoInputModel = MovimentacaoCreator.createInvalidPeriodoInMovimentacaoInput();

        MvcResult mvcResult = mockMvc.perform(put(URL+PATH,ID_MOV, ID_USU)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(movimentacaoInputModel)))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andReturn();

        String actualResponseBody = mvcResult.getResponse().getContentAsString();

        String expectedError = "[{\"nome\":\"periodo\",\"mensagemUsuario\":\"must not be null\"}]";

        assertThat(actualResponseBody).containsIgnoringCase(expectedError);
    }

    @Test
    void update_Returns400AndProblemDetails_WhenOcorrenciaIsNull() throws Exception {

        MovimentacaoInputModel movimentacaoInputModel = MovimentacaoCreator.createNullOcorrenciaInMovimentacaoInput();

        MvcResult mvcResult = mockMvc.perform(put(URL+PATH,ID_MOV, ID_USU)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(movimentacaoInputModel)))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andReturn();

        String actualResponseBody = mvcResult.getResponse().getContentAsString();

        String expectedError = "[{\"nome\":\"ocorrencia\",\"mensagemUsuario\":\"must not be null\"}]";

        assertThat(actualResponseBody).containsIgnoringCase(expectedError);
    }

    @Test
    void update_Returns400AndProblemDetails_WhenId_OcorrenciaIsNull() throws Exception {

        MovimentacaoInputModel movimentacaoInputModel = MovimentacaoCreator.createNullId_OcorrenciaInMovimentacaoInput();

        MvcResult mvcResult = mockMvc.perform(put(URL+PATH,ID_MOV, ID_USU)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(movimentacaoInputModel)))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andReturn();

        String actualResponseBody = mvcResult.getResponse().getContentAsString();

        String expectedError = "[{\"nome\":\"ocorrencia.id_ocorrencia\",\"mensagemUsuario\":\"must not be null\"}]";

        assertThat(actualResponseBody).containsIgnoringCase(expectedError);
    }

    @Test
    void update_Returns400AndProblemDetails_WhenCalendarioIsNull() throws Exception {

        MovimentacaoInputModel movimentacaoInputModel = MovimentacaoCreator.createNullCalendarioInMovimentacaoInput();

        MvcResult mvcResult = mockMvc.perform(put(URL+PATH,ID_MOV, ID_USU)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(movimentacaoInputModel)))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andReturn();

        String actualResponseBody = mvcResult.getResponse().getContentAsString();

        String expectedError = "[{\"nome\":\"calendario\",\"mensagemUsuario\":\"must not be null\"}]";

        assertThat(actualResponseBody).containsIgnoringCase(expectedError);
    }

    @Test
    void update_Returns400AndProblemDetails_WhenId_CalendarioIsNull() throws Exception {

        MovimentacaoInputModel movimentacaoInputModel = MovimentacaoCreator.createNullId_CalendarioInMovimentacaoInput();

        MvcResult mvcResult = mockMvc.perform(put(URL+PATH,ID_MOV, ID_USU)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(movimentacaoInputModel)))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andReturn();

        String actualResponseBody = mvcResult.getResponse().getContentAsString();

        String expectedError = "[{\"nome\":\"calendario.id_Calendario\",\"mensagemUsuario\":\"must not be null\"}]";

        assertThat(actualResponseBody).containsIgnoringCase(expectedError);
    }

    ///////////////////////////////



    @Test
    void delete_Returns204_WhenSuccessful() throws Exception{

        MvcResult mvcResult = mockMvc.perform(delete(URL+PATH, ID_MOV, ID_USU))
                .andExpect(status().isNoContent())
                .andReturn();
    }

    @Test
    void delete_Returns404AndProblemDetails_WhenMovimentacaoIsNotFound() throws Exception{

        Movimentacao.MovimentacaoId movId = MovimentacaoCreator.movimentacaoId(ID_MOV,ID_USU);

        doThrow(new MovimentacaoNaoEncontradaException(movId))
                .when(movimentacaoService).delete(any());

        MvcResult mvcResult = mockMvc.perform(delete(URL+PATH, ID_MOV, ID_USU))
                .andExpect(status().isNotFound())
                .andReturn();

        String actualResponseBody = mvcResult.getResponse().getContentAsString();

        String expectedDetails = String.format("NÃ£o existe uma movimentaÃ§Ã£o com o id (%d, %d)", ID_MOV, ID_USU);

        assertThat(actualResponseBody).containsIgnoringCase(expectedDetails);
    }

    @Test
    void delete_Returns409AndProblemDetails_WhenMovimentacaoIsInUse() throws Exception {

        doThrow(new EntidadeEmUsoException(
                String.format(String.format("A movimentação com o código (%d, %d) não pode ser removida pois está em uso",ID_MOV,ID_USU))))
                .when(movimentacaoService).delete(any());

        MvcResult mvcResult = mockMvc.perform(delete(URL+PATH, ID_MOV, ID_USU))
                .andExpect(status().isConflict())
                .andReturn();

        String actualResponseBody = mvcResult.getResponse().getContentAsString();

        String expectedDetails = String.format("A movimentaÃ§Ã£o com o cÃ³digo (%d, %d) nÃ£o pode ser removida pois estÃ¡ em uso", ID_MOV,ID_USU);

        assertThat(actualResponseBody).containsIgnoringCase(expectedDetails);
    }
}
