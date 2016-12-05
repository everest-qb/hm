package tw.housemart.jpa;

import java.io.Serializable;

import javax.persistence.*;


/**
 * The persistent class for the advertise database table.
 * 
 */
@Entity
@NamedQuery(name="Advertise.findAll", query="SELECT a FROM Advertise a")
@Table(name = "advertise")
public class Advertise implements Serializable {

	@Id
	private String id;

	private String gId;

	private String picSrc;

	public Advertise() {
	}

	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getGId() {
		return this.gId;
	}

	public void setGId(String gId) {
		this.gId = gId;
	}

	public String getPicSrc() {
		return this.picSrc;
	}

	public void setPicSrc(String picSrc) {
		this.picSrc = picSrc;
	}

}