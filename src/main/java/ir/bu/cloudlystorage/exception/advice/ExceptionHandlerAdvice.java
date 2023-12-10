package ir.bu.cloudlystorage.exception.advice;

import ir.bu.cloudlystorage.dto.ErrorDto;
import ir.bu.cloudlystorage.exception.BadCredentials;
import ir.bu.cloudlystorage.exception.TokenNotFoundException;
import ir.bu.cloudlystorage.exception.UnauthorizedError;
import ir.bu.cloudlystorage.exception.errorsList.ErrorsForCloud;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ExceptionHandlerAdvice {
    @ExceptionHandler(BadCredentials.class)
    public ResponseEntity<ErrorDto> badCredentialsHandler(BadCredentials e) {
        return new ResponseEntity<>(new ErrorDto(e.getMessage(),
                ErrorsForCloud.BAD_CREDENTIALS.getId()), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(UnauthorizedError.class)
    public ResponseEntity<ErrorDto> unauthorizedErrorHandler(UnauthorizedError e) {
        return new ResponseEntity<>(new ErrorDto(e.getMessage(),
                ErrorsForCloud.UNAUTHORIZED_ERROR.getId()), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(TokenNotFoundException.class)
    public ResponseEntity<ErrorDto> tokenNotFoundExceptionHandler(TokenNotFoundException e) {
        return new ResponseEntity<>(new ErrorDto(e.getMessage(),
                ErrorsForCloud.TOKEN_NOT_FOUND_EXCEPTION.getId()), HttpStatus.BAD_REQUEST);
    }
}
