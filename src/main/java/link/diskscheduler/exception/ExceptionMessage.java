package link.diskscheduler.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum ExceptionMessage {
    VIOLATE_REQUEST_DATA_CONSTRAINTS(HttpStatus.UNPROCESSABLE_ENTITY),
    NO_SCHEDULER_EXISTS_FOR_THE_REQUESTED_ALGORITHM(HttpStatus.INTERNAL_SERVER_ERROR);

    ExceptionMessage(HttpStatus httpStatus) {
        this.httpStatus = httpStatus;
    }

    private final HttpStatus httpStatus;
}
