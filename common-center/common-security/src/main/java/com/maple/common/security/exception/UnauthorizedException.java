package com.maple.common.security.exception;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.maple.common.security.component.MapleAuth2ExceptionSerializer;
import org.springframework.http.HttpStatus;

/**
 * @author zhua
 * @date 2019/7/30
 */
@JsonSerialize(using = MapleAuth2ExceptionSerializer.class)
public class UnauthorizedException extends MapleAuth2Exception {

    public UnauthorizedException(String msg, Throwable t) {
        super(msg);
    }

    @Override
    public String getOAuth2ErrorCode() {
        return "unauthorized";
    }

    @Override
    public int getHttpErrorCode() {
        return HttpStatus.UNAUTHORIZED.value();
    }

}
