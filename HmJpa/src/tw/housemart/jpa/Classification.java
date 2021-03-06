package tw.housemart.jpa;

import java.io.Serializable;

import javax.persistence.*;


/**
 * The persistent class for the classification database table.
 * 
 */
@Entity
@NamedQuery(name="Classification.findAll", query="SELECT c FROM Classification c where c.sort>0 order by c.sort")
@Table(name = "classification")
public class Classification implements Serializable {

	@Id
	private String id;

	private String cnname;

	private String twname;

	private String usname;
	
	private byte sort;

	public Classification() {
	}

	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getCnname() {
		return this.cnname;
	}

	public void setCnname(String cnname) {
		this.cnname = cnname;
	}

	public String getTwname() {
		return this.twname;
	}

	public void setTwname(String twname) {
		this.twname = twname;
	}

	public String getUsname() {
		return this.usname;
	}

	public void setUsname(String usname) {
		this.usname = usname;
	}

	public byte getSort() {
		return sort;
	}

	public void setSort(byte sort) {
		this.sort = sort;
	}

}