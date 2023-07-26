package link.diskscheduler.exception;

import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;
import java.util.stream.Collectors;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler({BindException.class, UnprocessableEntityException.class})
    public ResponseEntity<UnprocessableEntityExceptionResponse> handle422Exception(Exception e) {
        List<String> violations;

        if (e instanceof BindException) {
            violations = ((BindException) e).getFieldErrors().stream()
                    .map(DefaultMessageSourceResolvable::getDefaultMessage)
                    .collect(Collectors.toList());
        } else {
            violations = List.of(e.getMessage());
        }

        return ResponseEntity.unprocessableEntity()
                .body(UnprocessableEntityExceptionResponse.from(violations));
    }

    @ExceptionHandler(CustomException.class)
    public ResponseEntity<ExceptionResponse> handleCustomException(CustomException e) {
        return new ResponseEntity<>(ExceptionResponse.from(e.getExceptionMessage()), e.getExceptionMessage().getHttpStatus());
    }
}
