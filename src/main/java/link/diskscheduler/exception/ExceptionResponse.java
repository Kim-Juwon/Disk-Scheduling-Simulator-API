package link.diskscheduler.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor(staticName = "from")
public class ExceptionResponse {
    private final ExceptionMessage message;
}
