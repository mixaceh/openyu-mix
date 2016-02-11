package org.openyu.mix.activity.vo.target;

import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import com.sun.xml.bind.AnyTypeAdapter;

/**
 * 金幣目標活動
 */
@XmlJavaTypeAdapter(AnyTypeAdapter.class)
public interface GoldTargetActivity extends TargetActivity
{
	String KEY = GoldTargetActivity.class.getName();

}
