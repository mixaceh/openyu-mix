package org.openyu.mix.activity.vo.target;

import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import com.sun.xml.bind.AnyTypeAdapter;

/**
 * 聲望目標活動
 */
@XmlJavaTypeAdapter(AnyTypeAdapter.class)
public interface FameTargetActivity extends TargetActivity
{
	String KEY = FameTargetActivity.class.getName();

}
