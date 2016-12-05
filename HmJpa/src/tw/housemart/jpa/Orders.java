package tw.housemart.jpa;

import java.io.Serializable;

import javax.persistence.*;

import java.sql.Timestamp;
import java.util.List;

import static javax.persistence.GenerationType.IDENTITY;
import static javax.persistence.CascadeType.PERSIST;
import static javax.persistence.CascadeType.REMOVE;
import static javax.persistence.CascadeType.MERGE;


/**
 * The persistent class for the order database table.
 * 
 */
@Entity
@NamedQuery(name="Orders.findAll", query="SELECT o FROM Orders o")
@Table(name="orders")
@Cacheable(false)
public class Orders implements Serializable {	

	@Id
	@GeneratedValue(strategy = IDENTITY)
	private int id;

	private String tel;

	private String userName;
	
	private Timestamp createT;
	
	/**
	 * 0   ��l��
	 * 50 �B�z��
	 * 100�e�f��
	 * 127�w����
	 * -110 �ä[����
	 */
	private byte status;

	//uni-directional many-to-one association to DeliverLocation
	@ManyToOne
	@JoinColumn(name="addressId")
	private DeliverLocation deliverLocation;

	//bi-directional many-to-one association to OrderDetail
	@OneToMany(mappedBy="order", cascade = { PERSIST, REMOVE, MERGE }, orphanRemoval = true)
	private List<OrderDetail> orderDetails;

	public Orders() {
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getTel() {
		return this.tel;
	}

	public void setTel(String tel) {
		this.tel = tel;
	}

	public String getUserName() {
		return this.userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public DeliverLocation getDeliverLocation() {
		return this.deliverLocation;
	}

	public void setDeliverLocation(DeliverLocation deliverLocation) {
		this.deliverLocation = deliverLocation;
	}

	public List<OrderDetail> getOrderDetails() {
		return this.orderDetails;
	}

	public void setOrderDetails(List<OrderDetail> orderDetails) {
		this.orderDetails = orderDetails;
	}

	public OrderDetail addOrderDetail(OrderDetail orderDetail) {
		getOrderDetails().add(orderDetail);
		orderDetail.setOrder(this);

		return orderDetail;
	}

	public OrderDetail removeOrderDetail(OrderDetail orderDetail) {
		getOrderDetails().remove(orderDetail);
		orderDetail.setOrder(null);

		return orderDetail;
	}

	public Timestamp getCreateT() {
		return createT;
	}

	public void setCreateT(Timestamp createT) {
		this.createT = createT;
	}

	public byte getStatus() {
		return status;
	}

	public void setStatus(byte status) {
		this.status = status;
	}

}