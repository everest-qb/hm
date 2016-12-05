package tw.housemart.jpa;

import java.io.Serializable;

import javax.persistence.*;

import static javax.persistence.GenerationType.IDENTITY;



/**
 * The persistent class for the order_detail database table.
 * 
 */
@Entity
@Table(name="order_detail")
@NamedQuery(name="OrderDetail.findAll", query="SELECT o FROM OrderDetail o")
@Cacheable(false)
public class OrderDetail implements Serializable {

	@Id
	@GeneratedValue(strategy = IDENTITY)
	private long id;

	private byte amount;
	
	private float price;

	//private String goodsId;
	@ManyToOne
	@JoinColumn(name = "goodsId")
	private Good goods;

	//bi-directional many-to-one association to Order
	@ManyToOne
	@JoinColumn(name="orderId")
	private Orders order;

	public OrderDetail() {
		
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public byte getAmount() {
		return this.amount;
	}

	public void setAmount(byte amount) {
		this.amount = amount;
	}

	public Orders getOrder() {
		return this.order;
	}

	public void setOrder(Orders order) {
		this.order = order;
	}

	public Good getGoods() {
		return goods;
	}

	public void setGoods(Good goods) {
		this.goods = goods;
	}

	public float getPrice() {
		return price;
	}

	public void setPrice(float price) {
		this.price = price;
	}

}