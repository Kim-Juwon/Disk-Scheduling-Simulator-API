package link.diskscheduler.exception;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.List;

@Getter
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class UnprocessableEntityExceptionResponse {
    private final ExceptionMessage message;
    private final List<String> violations;

    public static UnprocessableEntityExceptionResponse from(List<String> violations) {
        return new UnprocessableEntityExceptionResponse(ExceptionMessage.VIOLATE_REQUEST_DATA_CONSTRAINTS, violations);
    }
}
