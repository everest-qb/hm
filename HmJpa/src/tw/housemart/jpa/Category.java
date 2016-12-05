package tw.housemart.jpa;

import java.io.Serializable;

import javax.persistence.*;

import java.util.List;


/**
 * The persistent class for the category database table.
 * 
 */
@Entity
@NamedQuery(name="Category.findAll", query="SELECT c FROM Category c")
@Table(name = "category")
public class Category implements Serializable {	

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;

	private String description;

	private String name;

	//uni-directional many-to-many association to Good
	@ManyToMany(cascade={CascadeType.ALL})
	@JoinTable(
		name="category_goods"
		, joinColumns={
			@JoinColumn(name="categoryId")
			}
		, inverseJoinColumns={
			@JoinColumn(name="goodsId")
			}
		)
	private List<Good> goods;

	public Category() {
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<Good> getGoods() {
		return this.goods;
	}

	public void setGoods(List<Good> goods) {
		this.goods = goods;
	}

}