package com.dio.pontodeacesso.application.exceptionhandler;

import com.dio.pontodeacesso.domain.exception.EntidadeEmUsoException;
import com.dio.pontodeacesso.domain.exception.EntidadeExistenteException;
import com.dio.pontodeacesso.domain.exception.EntidadeNaoEncontradaException;
import com.dio.pontodeacesso.domain.exception.NegocioException;
import com.fasterxml.jackson.databind.exc.IgnoredPropertyException;
import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import com.fasterxml.jackson.databind.exc.PropertyBindingException;
import com.fasterxml.jackson.databind.exc.UnrecognizedPropertyException;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.beans.TypeMismatchException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.lang.Nullable;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingPathVariableException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.stream.Collectors;

@ControllerAdvice
public class ApiExceptionHandler extends ResponseEntityExceptionHandler {

    public static final String MSG_ERRO_GENERICA_USUARIO_FINAL = "Ocorreu um erro interno inesperado no sistema. " +
            "Tente novamente e se o problema persistir, entre em contado com o administrador do sistema.";

    @Autowired
    MessageSource messageSource;

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        TipoProblema tipoProblema = TipoProblema.DADOS_INVALIDOS;
        String detalhes = "Um ou mais campos estão inválidos. Faça o preenchimento correto e tente novamente.";
        BindingResult bindingResult = ex.getBindingResult();
        List<ProblemaDetails.Objeto> objetosProblema = getProblemaObjetos(bindingResult);

        ProblemaDetails problemaDetails = criarProblemaBuilder(status, tipoProblema, detalhes)
                .objetos(objetosProblema)
                .mensagemUsuario(detalhes)
                .build();
        return handleExceptionInternal(ex, problemaDetails, headers, status, request);
    }

    @Override
    protected ResponseEntity<Object> handleNoHandlerFoundException(NoHandlerFoundException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {

        TipoProblema tipoProblema = TipoProblema.RECURSO_NAO_ENCONTRADO;
        String detail = String.format("Não existe um recurso com a URL '%s'", ex.getRequestURL());
        ProblemaDetails problemaDetails = criarProblemaBuilder(status, tipoProblema, detail)
                .mensagemUsuario(MSG_ERRO_GENERICA_USUARIO_FINAL)
                .build();
        return handleExceptionInternal(ex, problemaDetails, headers, status, request);
    }

    @Override
    protected ResponseEntity<Object> handleTypeMismatch(TypeMismatchException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        if (ex instanceof MethodArgumentTypeMismatchException){
            MethodArgumentTypeMismatchException exception = (MethodArgumentTypeMismatchException)ex;
            return handleMethodArgumentTypeMismatchException(exception, new HttpHeaders(), status, request);
        }
        return handleExceptionInternal(ex,null, headers, status,request);
    }

    private ResponseEntity<Object> handleMethodArgumentTypeMismatchException(MethodArgumentTypeMismatchException ex, HttpHeaders headers, HttpStatus status, WebRequest request){

        TipoProblema tipoProblema = TipoProblema.PARAMETRO_INVALIDO;
        String detail = String.format("O parâmetro de URL '%s' recebeu o valor '%s', que é de um tipo inválido. " +
                        "Corrija e informe um valor compatível com o tipo '%s' ",
                ex.getName(), ex.getValue() ,ex.getRequiredType().getSimpleName());
        ProblemaDetails problemaDetails = criarProblemaBuilder(status, tipoProblema, detail)
                .mensagemUsuario(MSG_ERRO_GENERICA_USUARIO_FINAL)
                .build();
        return handleExceptionInternal(ex, problemaDetails, headers, status, request);
    }

    @Override
    protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex,
                                                                  HttpHeaders headers, HttpStatus status, WebRequest request) {
        Throwable rootCause = ExceptionUtils.getRootCause(ex);

        if(rootCause instanceof InvalidFormatException){
            InvalidFormatException exception = (InvalidFormatException) rootCause;
            return handleInvalidFormatException(exception, headers, status, request);
        }
        else if(rootCause instanceof PropertyBindingException){
            PropertyBindingException exception = (PropertyBindingException) rootCause;
            return handlePropertyBindingException(exception, headers, status, request);
        }

        TipoProblema tipoProblema = TipoProblema.MENSAGEM_INCOMPREENSIVEL;
        String detalhe = "O corpo da requisição está inválido. Vefifique erro de sintaxe";
        ProblemaDetails problemaDetails = criarProblemaBuilder(status, tipoProblema, detalhe)
                .mensagemUsuario(MSG_ERRO_GENERICA_USUARIO_FINAL)
                .build();
        return super.handleExceptionInternal(ex, problemaDetails, new HttpHeaders(), status, request);
    }

    private ResponseEntity<Object> handlePropertyBindingException(PropertyBindingException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {

        TipoProblema tipoProblema = null;
        String detalhe = "";
        String path = ex.getPath().stream()
                .map(ref -> ref.getFieldName())
                .collect(Collectors.joining("."));

        if(ex instanceof IgnoredPropertyException){
            tipoProblema = TipoProblema.PROPRIEDADE_INEXISTENTE;
            detalhe = String.format("A propriedade '%s' não existe. " +
                    "Corrija ou remova esta propriedade e tente novamente", path);
        }
        else if(ex instanceof UnrecognizedPropertyException){
            tipoProblema = TipoProblema.PROPRIEDADE_DESCONHECIDA;
            detalhe = String.format("A propriedade '%s' não faz parte do domínio", path);
        }
        ProblemaDetails problemaDetails = criarProblemaBuilder(status, tipoProblema, detalhe)
                .mensagemUsuario(MSG_ERRO_GENERICA_USUARIO_FINAL)
                .build();
        return handleExceptionInternal(ex, problemaDetails, headers, status, request);
    }

    private ResponseEntity<Object> handleInvalidFormatException(InvalidFormatException ex, HttpHeaders headers,
                                                                HttpStatus status, WebRequest request) {
        String path = ex.getPath().stream()
                .map(ref -> ref.getFieldName())
                .collect(Collectors.joining("."));

        TipoProblema tipoProblema = TipoProblema.MENSAGEM_INCOMPREENSIVEL;
        String detalhe = String.format("A propriedade '%s' recebeu o valor '%s', que é de um tipo invalido. " +
                        "Corrija e informe um valor compatível com o tipo '%s'", path, ex.getValue(),
                ex.getTargetType().getSimpleName());
        ProblemaDetails problemaDetails = criarProblemaBuilder(status, tipoProblema, detalhe)
                .mensagemUsuario(MSG_ERRO_GENERICA_USUARIO_FINAL)
                .build();
        return handleExceptionInternal(ex, problemaDetails, headers, status, request);
    }

    @Override
    protected ResponseEntity<Object> handleMissingPathVariable(MissingPathVariableException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        status = HttpStatus.BAD_REQUEST;
        TipoProblema tipoProblema = TipoProblema.PARAMETRO_INVALIDO;
        String detalhes = String.format("O parâmetro %s está ausente na requisição. Verifique o erro e tente novamente", ex.getVariableName());
        ProblemaDetails problemaDetails = criarProblemaBuilder(status, tipoProblema, detalhes)
                .mensagemUsuario(detalhes)
                .build();
        return handleExceptionInternal(ex, problemaDetails, new HttpHeaders(), status, request);
    }

    @ExceptionHandler(EntidadeExistenteException.class)
    public ResponseEntity<?> handleEntidadeExistenteException(EntidadeExistenteException ex, WebRequest request){
        HttpStatus status = HttpStatus.CONFLICT;
        TipoProblema tipoProblema = TipoProblema.PROPRIEDADE_JA_EXISTENTE;
        String detalhes = ex.getMessage();
        ProblemaDetails problemaDetails = criarProblemaBuilder(status, tipoProblema, detalhes)
                .mensagemUsuario(detalhes)
                .build();
        return handleExceptionInternal(ex, problemaDetails, new HttpHeaders(), status, request);
    }



    @ExceptionHandler(EntidadeNaoEncontradaException.class)
    public ResponseEntity<?> handleEntidadeNaoEncontradaException(EntidadeNaoEncontradaException ex, WebRequest request){
        HttpStatus status = HttpStatus.NOT_FOUND;
        TipoProblema tipoProblema = TipoProblema.RECURSO_NAO_ENCONTRADO;
        String detalhes = ex.getMessage();
        ProblemaDetails problemaDetails = criarProblemaBuilder(status, tipoProblema, detalhes)
                .mensagemUsuario(detalhes)
                .build();
        return handleExceptionInternal(ex, problemaDetails, new HttpHeaders(),status,request);
    }

    @ExceptionHandler(EntidadeEmUsoException.class)
    public ResponseEntity<?> handleEntidadeEmUsoException(EntidadeEmUsoException ex, WebRequest request){
        HttpStatus status = HttpStatus.CONFLICT;
        TipoProblema tipoProblema = TipoProblema.ENTIDADE_EM_USO;
        String detalhes = ex.getMessage();
        ProblemaDetails problemaDetails = criarProblemaBuilder(status, tipoProblema, detalhes)
                .mensagemUsuario(detalhes)
                .build();
        return handleExceptionInternal(ex, problemaDetails, new HttpHeaders(),status,request);
    }

    @ExceptionHandler(NegocioException.class)
    public ResponseEntity<?> handleNegocioException(NegocioException ex, WebRequest request){
        HttpStatus status = HttpStatus.BAD_REQUEST;
        TipoProblema tipoProblema = TipoProblema.ERRO_NEGOCIO;
        String detalhes = ex.getMessage();
        ProblemaDetails problemaDetails = criarProblemaBuilder(status, tipoProblema, detalhes)
                .mensagemUsuario(detalhes)
                .build();
        return handleExceptionInternal(ex, problemaDetails, new HttpHeaders(),status,request);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> handleGenericException(Exception ex, WebRequest request){
        HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;
        TipoProblema tipoProblema = TipoProblema.ERRO_NEGOCIO;
        String detalhes = MSG_ERRO_GENERICA_USUARIO_FINAL;
        ProblemaDetails problemaDetails = criarProblemaBuilder(status, tipoProblema, detalhes)
                .mensagemUsuario(detalhes)
                .build();
        return handleExceptionInternal(ex, problemaDetails, new HttpHeaders(),status,request);
    }

    @Override
    protected ResponseEntity<Object> handleExceptionInternal(Exception ex, @Nullable Object body, HttpHeaders headers,
                                                             HttpStatus status, WebRequest request) {
        if (body == null) {
            body = ProblemaDetails.builder()
                    .status(status.value())
                    .titulo(status.getReasonPhrase())
                    .build();
        } else if(body instanceof String){
            body = ProblemaDetails.builder()
                    .status(status.value())
                    .titulo((String) body)
                    .build();
        }
        return super.handleExceptionInternal(ex, body, headers, status, request);
    }

    public ProblemaDetails.ProblemaDetailsBuilder criarProblemaBuilder(HttpStatus status, TipoProblema tipoProblema, String detalhes){
        return ProblemaDetails.builder()
                .status(status.value())
                .timestamp(OffsetDateTime.now())
                .titulo(tipoProblema.getTitulo())
                .detalhes(detalhes)
        ;
    }

    private List<ProblemaDetails.Objeto> getProblemaObjetos(BindingResult bindingResult) {
        return bindingResult.getAllErrors().stream()
                .map(objectError -> {
                    String mensagem = messageSource.getMessage(objectError, LocaleContextHolder.getLocale());
                    String nome = objectError.getObjectName();
                    if (objectError instanceof FieldError){
                        nome = ((FieldError) objectError).getField();
                    }
                    return ProblemaDetails.Objeto.builder()
                            .nome(nome)
                            .mensagemUsuario(mensagem)
                            .build();
                })
                .collect(Collectors.toList());
    }

}
