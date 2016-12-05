package tw.housemart.jpa;

import java.io.Serializable;

import javax.persistence.*;

import static javax.persistence.GenerationType.IDENTITY;


/**
 * The persistent class for the community database table.
 * 
 */
@Entity
@NamedQuery(name="Community.findAll", query="SELECT c FROM Community c")
@Table(name = "community")
public class Community implements Serializable {	

	@Id
	@GeneratedValue(strategy=IDENTITY)
	private int id;

	private String name;
	
	private String address;

	public Community() {
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

}