package tw.housemart.jpa;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the supplier database table.
 * 
 */
@Entity
@NamedQuery(name="Supplier.findAll", query="SELECT s FROM Supplier s")
@Table(name="supplier")
public class Supplier implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;

	private String address;

	private int maxGid;

	private String name;

	private String tel;

	public Supplier() {
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getAddress() {
		return this.address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public int getMaxGid() {
		return this.maxGid;
	}

	public void setMaxGid(int maxGid) {
		this.maxGid = maxGid;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getTel() {
		return this.tel;
	}

	public void setTel(String tel) {
		this.tel = tel;
	}

}