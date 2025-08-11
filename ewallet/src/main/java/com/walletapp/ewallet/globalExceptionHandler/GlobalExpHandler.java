package com.walletapp.ewallet.globalExceptionHandler;
import com.walletapp.ewallet.errorResponse.ErrorResp;
import com.walletapp.ewallet.errorResponse.ErrorResponse;
import com.walletapp.ewallet.service.serviceImpl.ErrorCodeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import java.util.NoSuchElementException;

@ControllerAdvice
@RequiredArgsConstructor
public class GlobalExpHandler {

    private final ErrorCodeService errorCodeService;

    @ExceptionHandler(DuplicateUserException.class)
    public ResponseEntity<ErrorResponse> duplicateExceptionHandler(DuplicateUserException dex, WebRequest wer){
        ErrorResponse erp =new ErrorResponse(dex.getMessage(),wer.getDescription(false),"User already exist",false);
        return new ResponseEntity<ErrorResponse>(erp, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(WalletException.class)
    public ResponseEntity<ErrorResp> walletExceptionHandler(WalletException wex){
        ErrorResp erp = new ErrorResp(
                errorCodeService.getMessage(wex.getCode()),
                wex.getCode(),
                false );
        return new ResponseEntity<ErrorResp>(erp, wex.getStatus());
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

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ErrorResponse> handleMalformedJson(HttpMessageNotReadableException ex){
        ErrorResponse errorMessage =new ErrorResponse( ex.getMostSpecificCause().getMessage(), null, "Malformed JSON request", false);
        return new ResponseEntity<ErrorResponse>(errorMessage, HttpStatus.NOT_FOUND );
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> fieldValidationException(MethodArgumentNotValidException mex){
        StringBuilder errorMessage = new StringBuilder();
        mex.getBindingResult().getFieldErrors().forEach(fieldError -> {
            errorMessage.append(fieldError.getField())
                        .append(": ")
                        .append(fieldError.getDefaultMessage())
                        .append("; ");
        });
        ErrorResponse erp = new ErrorResponse(errorMessage.toString(), null, "Validation failed", false);
        return new ResponseEntity<ErrorResponse>(erp, HttpStatus.BAD_REQUEST);
    }

//    @ExceptionHandler(MethodArgumentNotValidException.class)
//    public ResponseEntity<ErrorResponse> handleValidationExceptions(MethodArgumentNotValidException ex) {
//        ErrorResponse errorResponse = new ErrorResponse(ex.getMessage(), null, "Validation failed", false);
//        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
//    }
}