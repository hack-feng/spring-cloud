package com.maple.common.security.exception;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.maple.common.security.component.MapleAuth2ExceptionSerializer;

/**
 * @author zhua
 * @date 2019/7/30
 */
@JsonSerialize(using = MapleAuth2ExceptionSerializer.class)
public class InvalidException extends MapleAuth2Exception {

    public InvalidException(String msg, Throwable t) {
        super(msg);
    }

    @Override
    public String getOAuth2ErrorCode() {
        return "invalid_exception";
    }

    @Override
    public int getHttpErrorCode() {
        return 426;
    }

}
