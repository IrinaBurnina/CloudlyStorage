package ir.bu.cloudlystorage.exception.advice;

import ir.bu.cloudlystorage.dto.ErrorDto;
import ir.bu.cloudlystorage.exception.*;
import ir.bu.cloudlystorage.exception.errorsList.ErrorsForCloud;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ExceptionHandlerAdvice {
    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<ErrorDto> badCredentialsHandler(BadCredentialsException e) {
        return new ResponseEntity<>(new ErrorDto(e.getMessage(),
                ErrorsForCloud.BAD_CREDENTIALS.getId()), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(UnauthorizedException.class)
    public ResponseEntity<ErrorDto> unauthorizedErrorHandler(UnauthorizedException e) {
        return new ResponseEntity<>(new ErrorDto(e.getMessage(),
                ErrorsForCloud.UNAUTHORIZED_ERROR.getId()), HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(TokenNotFoundException.class)
    public ResponseEntity<ErrorDto> tokenNotFoundExceptionHandler(TokenNotFoundException e) {
        return new ResponseEntity<>(new ErrorDto(e.getMessage(),
                ErrorsForCloud.TOKEN_NOT_FOUND.getId()), HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(ErrorUploadFile.class)
    public ResponseEntity<ErrorDto> errorUploadFileHandler(ErrorUploadFile e) {
        return new ResponseEntity<>(new ErrorDto(e.getMessage(),
                ErrorsForCloud.ERROR_UPLOAD_FILE.getId()), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(ErrorDeleteFile.class)
    public ResponseEntity<ErrorDto> errorDeleteFileHandler(ErrorDeleteFile e) {
        return new ResponseEntity<>(new ErrorDto(e.getMessage(),
                ErrorsForCloud.ERROR_DELETE_FILE.getId()), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ErrorInputData.class)
    public ResponseEntity<ErrorDto> errorInputDataHandler(ErrorInputData e) {
        return new ResponseEntity<>(new ErrorDto(e.getMessage(),
                ErrorsForCloud.ERROR_INPUT_DATA.getId()), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(FileNotFoundException.class)
    public ResponseEntity<ErrorDto> fileNotFoundExceptionHandler(FileNotFoundException e) {
        return new ResponseEntity<>(new ErrorDto(e.getMessage(),
                ErrorsForCloud.FILE_NOT_FOUND.getId()), HttpStatus.BAD_REQUEST);
    }
}
