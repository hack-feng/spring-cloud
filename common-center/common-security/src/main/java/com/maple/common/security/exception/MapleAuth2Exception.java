package com.maple.common.security.exception;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.maple.common.security.component.MapleAuth2ExceptionSerializer;
import lombok.Getter;
import org.springframework.security.oauth2.common.exceptions.OAuth2Exception;

/**
 * @author zhua
 * @date 2019/7/30
 * 自定义OAuth2Exception
 */
@JsonSerialize(using = MapleAuth2ExceptionSerializer.class)
public class MapleAuth2Exception extends OAuth2Exception {
    @Getter
    private String errorCode;

    public MapleAuth2Exception(String msg) {
        super(msg);
    }

    public MapleAuth2Exception(String msg, String errorCode) {
        super(msg);
        this.errorCode = errorCode;
    }
}
