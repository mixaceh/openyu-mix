package org.openyu.mix.chat.aop;

import org.aspectj.lang.annotation.Aspect;
import org.openyu.mix.app.aop.supporter.AppAspectSupporter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Aspect
public class ChatAspect extends AppAspectSupporter {

	private static final long serialVersionUID = 2524265554035630063L;

	private static transient final Logger LOGGER = LoggerFactory.getLogger(ChatAspect.class);

	public ChatAspect() {

	}

}
