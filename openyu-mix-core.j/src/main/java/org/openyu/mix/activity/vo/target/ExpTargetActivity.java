package org.openyu.mix.activity.vo.target;

import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import com.sun.xml.bind.AnyTypeAdapter;

/**
 * 經驗目標活動
 */
@XmlJavaTypeAdapter(AnyTypeAdapter.class)
public interface ExpTargetActivity extends TargetActivity
{
	String KEY = ExpTargetActivity.class.getName();

}
