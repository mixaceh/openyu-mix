package org.openyu.mix.app.vo;

import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import org.openyu.commons.bean.BaseBean;
import com.sun.xml.bind.AnyTypeAdapter;

/**
 * 檢查結果
 */
@XmlJavaTypeAdapter(AnyTypeAdapter.class)
public interface AppResult extends BaseBean
{

	String KEY = AppResult.class.getName();
}
