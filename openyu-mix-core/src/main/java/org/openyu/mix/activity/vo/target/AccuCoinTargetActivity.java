package org.openyu.mix.activity.vo.target;

import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import com.sun.xml.bind.AnyTypeAdapter;

/**
 * 累計消耗儲值幣目標活動
 */
@XmlJavaTypeAdapter(AnyTypeAdapter.class)
public interface AccuCoinTargetActivity extends TargetActivity
{
	String KEY = AccuCoinTargetActivity.class.getName();

}
