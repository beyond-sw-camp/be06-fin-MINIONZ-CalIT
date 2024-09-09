package minionz.backend.common.exception;

import lombok.Getter;
import minionz.backend.common.responses.BaseResponseStatus;

@Getter
public class BaseException extends RuntimeException {

    private final BaseResponseStatus status;

    public BaseException(BaseResponseStatus status) {
        super(status.getMessage());
        this.status = status;
    }

    public BaseException(BaseResponseStatus status, String customMessage) {
        super(customMessage);
        this.status = status;
    }
}