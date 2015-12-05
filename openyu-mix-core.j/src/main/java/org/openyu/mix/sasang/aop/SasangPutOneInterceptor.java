package org.openyu.mix.sasang.aop;


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
 * 四象單擊獎勵放入包包攔截器
 */
public class SasangPutOneInterceptor extends AppMethodInterceptorSupporter {

	private static final long serialVersionUID = 6776586270397964192L;

	private static transient final Logger LOGGER = LoggerFactory
			.getLogger(SasangPutOneInterceptor.class);
	
	@Autowired
	@Qualifier("sasangLogService")
	protected transient SasangLogService sasangLogService;

	public SasangPutOneInterceptor() {
	}

	/**
	 * SasangService
	 * 
	 * PutResult putOne(boolean sendable, Role role, String itemId, int amount)
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
			String itemId = (String) args[2];
			int amount = (Integer) args[3];

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
				sasangLogService.recordPut(role, PutType.ONE, ret.getAwards());
			}
		} catch (Throwable e) {
			LOGGER.error(new StringBuilder("Exception encountered during doInvoke()").toString(), e);
			// throw e;
		}
		return result;
	}

}
