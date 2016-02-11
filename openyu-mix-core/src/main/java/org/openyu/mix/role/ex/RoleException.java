package org.openyu.mix.role.ex;

import org.openyu.commons.lang.ex.BaseRuntimeException;

public class RoleException extends BaseRuntimeException {

	private static final long serialVersionUID = -4522551404520227838L;

	public RoleException(Throwable cause) {
		super(cause);
	}

	public RoleException(String message, Throwable cause) {
		super(message, cause);
	}

	public RoleException(String message) {
		super(message);
	}
}
