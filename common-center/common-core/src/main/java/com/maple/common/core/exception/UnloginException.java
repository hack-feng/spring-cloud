package com.maple.common.core.exception;

import lombok.NoArgsConstructor;

/**
 * @author zhua
 * @date 2018年06月22日16:22:10
 */
@NoArgsConstructor
public class UnloginException extends RuntimeException {
	private static final long serialVersionUID = 1L;

	public UnloginException(String message) {
		super(message);
	}

	public UnloginException(Throwable cause) {
		super(cause);
	}

	public UnloginException(String message, Throwable cause) {
		super(message, cause);
	}

	public UnloginException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

}
