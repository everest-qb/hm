package tw.housemart.jpa;

import java.io.Serializable;

import javax.persistence.*;


/**
 * The persistent class for the goodsgroup database table.
 * 
 */
@Entity
@NamedQuery(name="Goodsgroup.findAll", query="SELECT g FROM Goodsgroup g where g.sort>0 order by g.sort")
@Table(name="goodsgroup")
public class Goodsgroup implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private String id;

	private String cnname;

	private String twname;

	private String usname;
	
	private byte sort;

	public Goodsgroup() {
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