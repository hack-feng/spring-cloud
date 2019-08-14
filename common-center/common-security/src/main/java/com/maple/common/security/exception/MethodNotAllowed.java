package com.maple.common.security.exception;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.maple.common.security.component.MapleAuth2ExceptionSerializer;
import org.springframework.http.HttpStatus;

/**
 * @author zhua
 * @date 2019/7/30
 */
@JsonSerialize(using = MapleAuth2ExceptionSerializer.class)
public class MethodNotAllowed extends MapleAuth2Exception {

    public MethodNotAllowed(String msg, Throwable t) {
        super(msg);
    }

    @Override
    public String getOAuth2ErrorCode() {
        return "method_not_allowed";
    }

    @Override
    public int getHttpErrorCode() {
        return HttpStatus.METHOD_NOT_ALLOWED.value();
    }

}
