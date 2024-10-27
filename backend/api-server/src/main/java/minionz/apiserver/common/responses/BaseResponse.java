package minionz.apiserver.common.responses;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BaseResponse<T> {
    private Boolean success;
    private Integer code;
    private String message;
    private T result;


    public BaseResponse(BaseResponseStatus baseResponseStatus) {
        this.success = baseResponseStatus.isSuccess();
        this.code = baseResponseStatus.getCode();
        this.message = baseResponseStatus.getMessage();
    }

    public BaseResponse( BaseResponseStatus baseResponseStatus, T result) {
        this.success = baseResponseStatus.isSuccess();
        this.code = baseResponseStatus.getCode();
        this.message = baseResponseStatus.getMessage();
        this.result = result;
    }
}