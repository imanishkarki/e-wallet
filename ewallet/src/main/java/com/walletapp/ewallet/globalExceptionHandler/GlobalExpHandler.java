package com.walletapp.ewallet.globalExceptionHandler;
import com.walletapp.ewallet.errorResponse.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import java.util.NoSuchElementException;


@ControllerAdvice
public class GlobalExpHandler {
    @ExceptionHandler(DuplicateUserException.class)
    public ResponseEntity<ErrorResponse> duplicateExceptionHandler(DuplicateUserException dex, WebRequest wer){
        ErrorResponse erp =new ErrorResponse(dex.getMessage(),wer.getDescription(false),"User already exists with this Phone Number",false);
        return new ResponseEntity<ErrorResponse>(erp, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(IdNotFoundException.class)
    public ResponseEntity<ErrorResponse> idNotFoundExceptionHandler(IdNotFoundException infe, WebRequest wer){
        ErrorResponse erp = new ErrorResponse(infe.getMessage(),wer.getDescription(false),"Either sender or receiver Id is not found",false);
        return new ResponseEntity<ErrorResponse>(erp, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(NoSuchElementException.class)
    public ResponseEntity<ErrorResponse> noSuchIdExceptionHandler(NoSuchElementException nsi, WebRequest wer){
        ErrorResponse erp =new ErrorResponse(nsi.getMessage(),wer.getDescription(false),"User doesn't exists with this Id",false);
        return new ResponseEntity<ErrorResponse>(erp, HttpStatus.NOT_FOUND);
    }
}