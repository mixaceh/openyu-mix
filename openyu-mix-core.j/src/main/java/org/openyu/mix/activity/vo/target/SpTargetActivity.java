package org.openyu.mix.activity.vo.target;

import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import com.sun.xml.bind.AnyTypeAdapter;

/**
 * 技能經驗目標活動
 */
@XmlJavaTypeAdapter(AnyTypeAdapter.class)
public interface SpTargetActivity extends TargetActivity
{
	String KEY = SpTargetActivity.class.getName();

}
