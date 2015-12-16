package org.openyu.mix.role.po.impl;

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
import org.openyu.mix.flutter.po.supporter.FlutterPoSupporter;
import org.openyu.mix.manor.po.bridge.ManorPenBridge;
import org.openyu.mix.manor.vo.ManorPen;
import org.openyu.mix.manor.vo.impl.ManorPenImpl;
import org.openyu.mix.role.po.RolePo;
import org.openyu.mix.role.po.bridge.BagPenBridge;
import org.openyu.mix.role.vo.BagPen;
import org.openyu.mix.role.vo.impl.BagPenImpl;
import org.openyu.mix.sasang.po.bridge.SasangPenBridge;
import org.openyu.mix.sasang.vo.SasangPen;
import org.openyu.mix.sasang.vo.impl.SasangPenImpl;
import org.openyu.mix.train.po.bridge.TrainPenBridge;
import org.openyu.mix.train.vo.TrainPen;
import org.openyu.mix.train.vo.impl.TrainPenImpl;
import org.openyu.mix.treasure.po.bridge.TreasurePenBridge;
import org.openyu.mix.treasure.vo.TreasurePen;
import org.openyu.mix.treasure.vo.impl.TreasurePenImpl;
import org.openyu.mix.wuxing.po.bridge.WuxingPenBridge;
import org.openyu.mix.wuxing.vo.WuxingPen;
import org.openyu.mix.wuxing.vo.impl.WuxingPenImpl;

//--------------------------------------------------
//hibernate
//--------------------------------------------------
@Entity
@org.hibernate.annotations.Entity(dynamicInsert = true, dynamicUpdate = true)
@Table(name = "mix_role")
@SequenceGenerator(name = "mix_role_g", sequenceName = "mix_role_s", allocationSize = 1)
// when use ehcache, config in ehcache.xml
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE, region = "org.openyu.mix.role.po.impl.RolePoImpl")
@Proxy(lazy = false)
@org.hibernate.annotations.Table(appliesTo = "mix_role", indexes = {
		@org.hibernate.annotations.Index(name = "idx_mix_role_1", columnNames = { "valid", "id" }) })
// --------------------------------------------------
// search
// --------------------------------------------------
// @Indexed
public class RolePoImpl extends FlutterPoSupporter implements RolePo {

	private static final long serialVersionUID = 3612195473621975166L;

	private Long seq;

	/**
	 * 是否有效
	 */
	private Boolean valid;

	/**
	 * 帳號
	 */
	// private AccountPo account;

	private String accountId;

	/**
	 * 上線時間
	 */
	private Long enterTime;

	/**
	 * 離線時間
	 */
	private Long leaveTime;

	/**
	 * 取得接收器 id
	 */
	private String acceptorId;

	/**
	 * 包包欄位
	 */
	private BagPen bagPen = new BagPenImpl();

	// ---------------------------------------------------
	// 其他模組相關欄位
	// ---------------------------------------------------

	/**
	 * 四象欄位
	 */
	private SasangPen sasangPen = new SasangPenImpl();

	/**
	 * 莊園欄位
	 */
	private ManorPen manorPen = new ManorPenImpl();

	/**
	 * 祕寶欄位
	 */
	private TreasurePen treasurePen = new TreasurePenImpl();

	/**
	 * 訓練欄位
	 */
	private TrainPen trainPen = new TrainPenImpl();

	/**
	 * 五行欄位
	 */
	private WuxingPen wuxingPen = new WuxingPenImpl();

	public RolePoImpl() {
	}

	@Id
	@Column(name = "seq")
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "mix_role_g")
	public Long getSeq() {
		return seq;
	}

	public void setSeq(Long seq) {
		this.seq = seq;
	}

	@Column(name = "valid")
	@Field(store = Store.YES, index = Index.YES, analyze = Analyze.NO)
	public Boolean getValid() {
		return valid;
	}

	public void setValid(Boolean valid) {
		this.valid = valid;
	}

	// @ManyToOne(targetEntity = AccountPoImpl.class, cascade = CascadeType.ALL,
	// fetch = FetchType.EAGER)
	// @NotFound(action = NotFoundAction.IGNORE)
	// @JoinColumn(name = "account_id")
	// @IndexedEmbedded(targetElement = AccountPoImpl.class, depth = 1)
	// public AccountPo getAccount()
	// {
	// return account;
	// }
	//
	// public void setAccount(AccountPo account)
	// {
	// this.account = account;
	// }
	@Column(name = "account_id", length = 255)
	@Field(store = Store.YES, index = Index.YES, analyze = Analyze.NO)
	public String getAccountId() {
		return accountId;
	}

	public void setAccountId(String accountId) {
		this.accountId = accountId;
	}

	@Column(name = "enter_time")
	@Field(store = Store.YES, index = Index.YES, analyze = Analyze.NO)
	public Long getEnterTime() {
		return enterTime;
	}

	public void setEnterTime(Long enterTime) {
		this.enterTime = enterTime;
	}

	@Column(name = "leave_time")
	@Field(store = Store.YES, index = Index.YES, analyze = Analyze.NO)
	public Long getLeaveTime() {
		return leaveTime;
	}

	public void setLeaveTime(Long leaveTime) {
		this.leaveTime = leaveTime;
	}

	@Column(name = "acceptor_id", length = 30)
	@Field(store = Store.YES, index = Index.YES, analyze = Analyze.NO)
	public String getAcceptorId() {
		return acceptorId;
	}

	public void setAcceptorId(String acceptorId) {
		this.acceptorId = acceptorId;
	}

	@Column(name = "bag_info", length = 8192)
	@Type(type = "org.openyu.mix.role.po.userType.BagPenUserType")
	@Field(store = Store.YES, index = Index.YES, analyze = Analyze.NO)
	@FieldBridge(impl = BagPenBridge.class)
	public BagPen getBagPen() {
		return bagPen;
	}

	public void setBagPen(BagPen bagPen) {
		this.bagPen = bagPen;
	}

	@Column(name = "sasang_info", length = 1024)
	@Type(type = "org.openyu.mix.sasang.po.userType.SasangPenUserType")
	@Field(store = Store.YES, index = Index.YES, analyze = Analyze.NO)
	@FieldBridge(impl = SasangPenBridge.class)
	public SasangPen getSasangPen() {
		return sasangPen;
	}

	public void setSasangPen(SasangPen sasangPen) {
		this.sasangPen = sasangPen;
	}

	@Column(name = "manor_info", length = 2048)
	@Type(type = "org.openyu.mix.manor.po.userType.ManorPenUserType")
	@Field(store = Store.YES, index = Index.YES, analyze = Analyze.NO)
	@FieldBridge(impl = ManorPenBridge.class)
	public ManorPen getManorPen() {
		return manorPen;
	}

	public void setManorPen(ManorPen manorPen) {
		this.manorPen = manorPen;
	}

	@Column(name = "treasure_info", length = 1024)
	@Type(type = "org.openyu.mix.treasure.po.userType.TreasurePenUserType")
	@Field(store = Store.YES, index = Index.YES, analyze = Analyze.NO)
	@FieldBridge(impl = TreasurePenBridge.class)
	public TreasurePen getTreasurePen() {
		return treasurePen;
	}

	public void setTreasurePen(TreasurePen treasurePen) {
		this.treasurePen = treasurePen;
	}

	@Column(name = "train_info", length = 512)
	@Type(type = "org.openyu.mix.train.po.userType.TrainPenUserType")
	@Field(store = Store.YES, index = Index.YES, analyze = Analyze.NO)
	@FieldBridge(impl = TrainPenBridge.class)
	public TrainPen getTrainPen() {
		return trainPen;
	}

	public void setTrainPen(TrainPen trainPen) {
		this.trainPen = trainPen;
	}

	@Column(name = "wuxing_info", length = 1024)
	@Type(type = "org.openyu.mix.wuxing.po.userType.WuxingPenUserType")
	@Field(store = Store.YES, index = Index.YES, analyze = Analyze.NO)
	@FieldBridge(impl = WuxingPenBridge.class)
	public WuxingPen getWuxingPen() {
		return wuxingPen;
	}

	public void setWuxingPen(WuxingPen wuxingPen) {
		this.wuxingPen = wuxingPen;
	}

	public String toString() {
		ToStringBuilder builder = new ToStringBuilder(this);
		builder.appendSuper(super.toString());
		builder.append("valid", valid);
		// builder.append("account", (account != null ? account.getId() :
		// null));
		builder.append("accountId", accountId);
		builder.append("enterTime", enterTime);
		builder.append("leaveTime", leaveTime);
		//
		builder.append("bagPen", bagPen);
		builder.append("sasangPen", sasangPen);
		builder.append("manorPen", manorPen);
		builder.append("treasurePen", treasurePen);
		builder.append("trainPen", trainPen);
		builder.append("wuxingPen", wuxingPen);
		return builder.toString();
	}

	public Object clone() {
		RolePoImpl copy = null;
		copy = (RolePoImpl) super.clone();
		// copy.account = clone(copy);
		copy.bagPen = clone(bagPen);
		copy.sasangPen = clone(sasangPen);
		copy.manorPen = clone(manorPen);
		copy.treasurePen = clone(treasurePen);
		copy.trainPen = clone(trainPen);
		copy.wuxingPen = clone(wuxingPen);
		return copy;
	}
}
