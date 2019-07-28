package com.maple.common.security.exception;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.maple.common.security.component.MapleAuth2ExceptionSerializer;
import org.springframework.http.HttpStatus;

/**
 * @author zhua
 * @date 2019/2/1
 */
@JsonSerialize(using = MapleAuth2ExceptionSerializer.class)
public class ServerErrorException extends MapleAuth2Exception {

	public ServerErrorException(String msg, Throwable t) {
		super(msg);
	}

	@Override
	public String getOAuth2ErrorCode() {
		return "server_error";
	}

	@Override
	public int getHttpErrorCode() {
		return HttpStatus.INTERNAL_SERVER_ERROR.value();
	}

}
