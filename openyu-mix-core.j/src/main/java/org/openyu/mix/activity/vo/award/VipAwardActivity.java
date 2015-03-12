package org.openyu.mix.activity.vo.award;

import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import org.openyu.mix.activity.vo.Activity;
import com.sun.xml.bind.AnyTypeAdapter;

/**
 * vip獎勵活動
 */
@XmlJavaTypeAdapter(AnyTypeAdapter.class)
public interface VipAwardActivity extends Activity
{
	String KEY = VipAwardActivity.class.getName();
	
	

}
