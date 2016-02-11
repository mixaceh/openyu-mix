package org.openyu.mix.chat.ex;

import org.openyu.commons.lang.ex.BaseRuntimeException;

public class ChatException extends BaseRuntimeException {

	private static final long serialVersionUID = -4522551404520227838L;

	public ChatException(Throwable cause) {
		super(cause);
	}

	public ChatException(String message, Throwable cause) {
		super(message, cause);
	}

	public ChatException(String message) {
		super(message);
	}
}
