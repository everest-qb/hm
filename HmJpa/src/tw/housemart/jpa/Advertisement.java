package tw.housemart.jpa;

import java.io.Serializable;

import javax.persistence.*;


/**
 * The persistent class for the advertisement database table.
 * 
 */
@Entity
@NamedQuery(name="Advertisement.findAll", query="SELECT a FROM Advertisement a")
@Table(name="advertisement")
public class Advertisement implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private String id;

	private String cnname;

	private String twname;

	private String usname;

	public Advertisement() {
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

}