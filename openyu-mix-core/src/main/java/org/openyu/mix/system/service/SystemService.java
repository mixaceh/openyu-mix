package org.openyu.mix.system.service;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import org.openyu.mix.app.service.AppService;
import org.openyu.mix.app.service.RelationConnectable;
import org.openyu.mix.app.service.ContextConnectable;
import org.openyu.mix.system.vo.Context;
import org.openyu.mix.system.vo.Relation;
import org.openyu.commons.enumz.IntEnum;

/**
 * 系統服務
 */
public interface SystemService extends AppService, ContextConnectable,
		RelationConnectable {
	/**
	 * 錯誤類別
	 */
	public enum ErrorType implements IntEnum {
		/**
		 * 未知
		 */
		UNKNOWN(-1),

		/**
		 * 沒有錯誤
		 */
		NO_ERROR(0),

		/**
		 * 伺服器關連不存在
		 */
		RELATION_NOT_EXIST(11),

		;

		private final int value;

		private ErrorType(int value) {
			this.value = value;
		}

		public int getValue() {
			return value;
		}

		public String toString() {
			ToStringBuilder builder = new ToStringBuilder(this,
					ToStringStyle.SIMPLE_STYLE);
			builder.append("(" + value + ") " + super.toString());
			return builder.toString();
		}
	}

	// --------------------------------------------------
	// biz
	// --------------------------------------------------

	/**
	 * 取得本文
	 * @return
	 */
	Context getContext();

	/**
	 * 設定本文
	 * 
	 * @param context
	 */
	void setContext(Context context);

	// --------------------------------------------------
	/**
	 * 建構本文
	 * 
	 * @param contextId
	 * @param name
	 *            名稱
	 * @return
	 */
	Context createContext(String contextId, String name);

	/**
	 * 建構伺服器關連
	 * 
	 * @param relationId
	 * @param name
	 *            名稱
	 * @return
	 */
	Relation createRelation(String relationId, String name);

}
