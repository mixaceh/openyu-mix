package org.openyu.mix.activity.vo.target;

import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import com.sun.xml.bind.AnyTypeAdapter;

/**
 * vip目標活動
 */
@XmlJavaTypeAdapter(AnyTypeAdapter.class)
public interface VipTargetActivity extends TargetActivity
{
	String KEY = VipTargetActivity.class.getName();

}
