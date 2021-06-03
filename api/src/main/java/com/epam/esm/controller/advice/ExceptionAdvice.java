package com.epam.esm.controller.advice;

import com.epam.esm.service.entity.enumeration.ErrorCode;
import com.epam.esm.service.entity.enumeration.ErrorMessage;
import com.epam.esm.service.exception.*;
import com.epam.esm.service.service.LocaleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.NoHandlerFoundException;

import javax.validation.ConstraintViolationException;
import java.util.List;
import java.util.stream.Collectors;

@ControllerAdvice
public class ExceptionAdvice {
    private LocaleService localeService;

    @Autowired
    public void setLocaleService(LocaleService localeService) {
        this.localeService = localeService;
    }

    @ExceptionHandler(value = {ConstraintViolationException.class})
    protected ResponseEntity<Object> exception(ConstraintViolationException e) {
        ApiException apiException = new ApiException(ErrorCode.NOT_VALID.getCode(), e.getMessage());
        return new ResponseEntity<>(apiException, new HttpHeaders(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ValidationException.class)
    public ResponseEntity<Object> exception(ValidationException e) {
        List<String> errors = e.getErrors().stream().map(errorMessage ->
                localeService.getLocaleMessage(errorMessage.getType())).collect(Collectors.toList());
        ApiException apiException = new ApiException(e.getErrorCode().getCode(), errors);
        return new ResponseEntity<>(
                apiException, new HttpHeaders(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = ResourceNotFoundException.class)
    public ResponseEntity<Object> exception(ResourceNotFoundException e) {
        ApiException apiException = new ApiException(e.getErrorCode().getCode(),
                localeService.getLocaleMessage(ErrorMessage.RESOURCE_NOT_FOUND.getType()));
        return new ResponseEntity<>(
                apiException, new HttpHeaders(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(value = PageNotFoundException.class)
    public ResponseEntity<Object> exception(PageNotFoundException e) {
        ApiException apiException = new ApiException(e.getErrorCode().getCode(),
                localeService.getLocaleMessage(e.getErrorMessage().getType()));
        return new ResponseEntity<>(apiException, new HttpHeaders(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = ResourceAlreadyExistsException.class)
    public ResponseEntity<Object> exception(ResourceAlreadyExistsException e) {
        ApiException apiException = new ApiException(e.getErrorCode().getCode(),
                localeService.getLocaleMessage(e.getErrorMessage().getType()));
        return new ResponseEntity<>(apiException, new HttpHeaders(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = UserHasNoTagsException.class)
    public ResponseEntity<Object> exception(UserHasNoTagsException e) {
        ApiException apiException = new ApiException(ErrorCode.USER_HAS_NO_TAGS.getCode(),
                localeService.getLocaleMessage(e.getErrorMessage().getType()));
        return new ResponseEntity<>(apiException, new HttpHeaders(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = CertificateDeleteException.class)
    public ResponseEntity<Object> exception(CertificateDeleteException ex) {
        ApiException apiException = new ApiException(ErrorCode.CERTIFICATE_IS_INCLUDED_IN_ORDERS.getCode(),
                localeService.getLocaleMessage(ErrorMessage.CERTIFICATE_IS_INCLUDED_IN_ORDERS.getType()));
        return new ResponseEntity<>(apiException, new HttpHeaders(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = Exception.class)
    public ResponseEntity<Object> exception(Exception e) {
        ApiException apiException = new ApiException(ErrorCode.INTERNAL_SERVER_ERROR.getCode(),
                localeService.getLocaleMessage(ErrorMessage.SOMETHING_GOES_WRONG.getType()));
        return new ResponseEntity<>(apiException, new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(value = MethodArgumentTypeMismatchException.class)
    public ResponseEntity<Object> exception(MethodArgumentTypeMismatchException e) {
        ApiException apiException = new ApiException(ErrorCode.METHOD_ARGUMENT_TYPE_MISMATCH.getCode(),
                localeService.getLocaleMessage(ErrorMessage.METHOD_ARGUMENT_TYPE_MISMATCH.getType()));
        return new ResponseEntity<>(apiException, new HttpHeaders(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = HttpMediaTypeNotSupportedException.class)
    protected ResponseEntity<Object> exception(HttpMediaTypeNotSupportedException ex) {
        ApiException apiException = new ApiException(ErrorCode.UNSUPPORTED_MEDIA_TYPE.getCode(),
                localeService.getLocaleMessage(ErrorMessage.UNSUPPORTED_MEDIA_TYPE.getType()));
        return new ResponseEntity<>(apiException, new HttpHeaders(), HttpStatus.UNSUPPORTED_MEDIA_TYPE);
    }

    @ExceptionHandler(value = NoHandlerFoundException.class)
    protected ResponseEntity<Object> exception(NoHandlerFoundException ex) {
        ApiException apiException = new ApiException(ErrorCode.METHOD_NOT_FOUND.getCode(),
                localeService.getLocaleMessage(ErrorMessage.METHOD_NOT_FOUND.getType()));
        return new ResponseEntity<>(apiException, new HttpHeaders(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(value = HttpMessageNotReadableException.class)
    protected ResponseEntity<Object> exception(HttpMessageNotReadableException ex) {
        ApiException apiException = new ApiException(ErrorCode.NOT_VALID.getCode(),
                localeService.getLocaleMessage(ErrorMessage.INCORRECT_REQUEST_VALUE.getType()));
        return new ResponseEntity<>(apiException, new HttpHeaders(), HttpStatus.BAD_REQUEST);
    }
}
