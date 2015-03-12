package org.openyu.mix.wuxing.log.impl;

import java.util.LinkedHashMap;
import java.util.Map;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Proxy;
import org.hibernate.annotations.Type;
import org.hibernate.search.annotations.Analyze;
import org.hibernate.search.annotations.Field;
import org.hibernate.search.annotations.FieldBridge;
import org.hibernate.search.annotations.Index;
import org.hibernate.search.annotations.Store;
import org.openyu.mix.app.log.supporter.AppLogEntitySupporter;
import org.openyu.mix.wuxing.log.WuxingPutLog;
import org.openyu.mix.wuxing.po.bridge.PutTypeBridge;
import org.openyu.mix.wuxing.service.WuxingService.PutType;
import org.openyu.commons.entity.bridge.StringIntegerBridge;

//--------------------------------------------------
//hibernate
//--------------------------------------------------
@Entity
@org.hibernate.annotations.Entity(dynamicInsert = true, dynamicUpdate = true)
@Table(name = "mix_wuxing_put_log")
@SequenceGenerator(name = "mix_wuxing_put_log_g", sequenceName = "mix_wuxing_put_log_s", allocationSize = 1)
//when use ehcache, config in ehcache.xml
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE, region = "org.openyu.mix.wuxing.log.impl.WuxingPutLogImpl")
@Proxy(lazy = false)
@org.hibernate.annotations.Table(appliesTo = "mix_wuxing_put_log", indexes = { @org.hibernate.annotations.Index(name = "idx_mix_wuxing_put_log_1", columnNames = {
		"role_id", "log_date", "put_type" }) })
//--------------------------------------------------
//search
//--------------------------------------------------
//@Indexed
public class WuxingPutLogImpl extends AppLogEntitySupporter implements WuxingPutLog
{

	private static final long serialVersionUID = 3756281645321688719L;

	private Long seq;

	private PutType putType;

	private Map<String, Integer> awards = new LinkedHashMap<String, Integer>();

	public WuxingPutLogImpl()
	{}

	@Id
	@Column(name = "seq")
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "mix_wuxing_put_log_g")
	public Long getSeq()
	{
		return seq;
	}

	public void setSeq(Long seq)
	{
		this.seq = seq;
	}

	@Column(name = "put_type", length = 13)
	@Type(type = "org.openyu.mix.wuxing.po.userType.PutTypeUserType")
	@Field(store = Store.YES, index = Index.YES, analyze = Analyze.NO)
	@FieldBridge(impl = PutTypeBridge.class)
	public PutType getPutType()
	{
		return putType;
	}

	public void setPutType(PutType putType)
	{
		this.putType = putType;
	}

	@Column(name = "awards", length = 1024)
	@Type(type = "org.openyu.commons.entity.userType.StringIntegerUserType")
	@Field(store = Store.YES, index = Index.YES, analyze = Analyze.NO)
	@FieldBridge(impl = StringIntegerBridge.class)
	public Map<String, Integer> getAwards()
	{
		return awards;
	}

	public void setAwards(Map<String, Integer> awards)
	{
		this.awards = awards;
	}

	public String toString()
	{
		ToStringBuilder builder = new ToStringBuilder(this);
		builder.appendSuper(super.toString());
		builder.append("putType", putType);
		builder.append("awards", awards);
		return builder.toString();
	}

	public Object clone()
	{
		WuxingPutLogImpl copy = null;
		copy = (WuxingPutLogImpl) super.clone();
		copy.awards = clone(awards);
		return copy;
	}
}
