package org.openyu.mix.sasang.service.aop;


import org.aopalliance.intercept.MethodInvocation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.openyu.mix.app.aop.supporter.AppMethodInterceptorSupporter;
import org.openyu.mix.role.vo.Role;
import org.openyu.mix.sasang.service.SasangLogService;
import org.openyu.mix.sasang.service.SasangService.PutResult;
import org.openyu.mix.sasang.service.SasangService.PutType;

/**
 * 四象所有中獎區獎勵放入包包攔截器
 */
public class SasangPutAllInterceptor extends AppMethodInterceptorSupporter {

	private static final long serialVersionUID = -6607503394143359290L;

	private static transient final Logger LOGGER = LoggerFactory
			.getLogger(SasangPutAllInterceptor.class);

	@Autowired
	@Qualifier("sasangLogService")
	protected transient SasangLogService sasangLogService;

	public SasangPutAllInterceptor() {
	}

	/**
	 * SasangService
	 * 
	 * PutResult putAll(boolean sendable, Role role)
	 */
	protected Object doInvoke(MethodInvocation methodInvocation) throws Throwable {
		Object result = null;
		try {
			// --------------------------------------------------
			// proceed前
			// --------------------------------------------------
			// 參數
			Object[] args = methodInvocation.getArguments();
			boolean sendable = (Boolean) args[0];
			Role role = (Role) args[1];

			// --------------------------------------------------
			result = methodInvocation.proceed();
			// --------------------------------------------------

			// --------------------------------------------------
			// proceed後
			// --------------------------------------------------

			// 傳回值
			PutResult ret = (PutResult) result;
			//
			if (ret != null) {
				sasangLogService.recordPut(role, PutType.ALL, ret.getAwards());
			}
		} catch (Throwable e) {
			LOGGER.error(new StringBuilder("Exception encountered during doInvoke()").toString(), e);
			// throw e;
		}
		return result;
	}

}
