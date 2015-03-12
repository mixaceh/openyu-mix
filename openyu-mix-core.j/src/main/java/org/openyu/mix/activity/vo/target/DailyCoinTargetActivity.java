package org.openyu.mix.activity.vo.target;

import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import com.sun.xml.bind.AnyTypeAdapter;

/**
 * 每日消耗儲值幣目標活動
 */
@XmlJavaTypeAdapter(AnyTypeAdapter.class)
public interface DailyCoinTargetActivity extends TargetActivity
{
	String KEY = DailyCoinTargetActivity.class.getName();

}
