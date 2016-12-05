package tw.housemart.jpa;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the goods database table.
 * 
 */
@Entity
@Table(name="goods")
@NamedQuery(name="Good.findAll", query="SELECT g FROM Good g")
public class Good implements Serializable {	

	@Id
	private String gId;

	private String icon;

	private String name;

	private String picName01;

	private String picName02;

	private float price;

	private String web;
	private String title;
	
	@Column(name = "description")
	private String desc;

	public Good() {
	}

	public String getGId() {
		return this.gId;
	}

	public void setGId(String gId) {
		this.gId = gId;
	}

	public String getIcon() {
		return this.icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPicName01() {
		return this.picName01;
	}

	public void setPicName01(String picName01) {
		this.picName01 = picName01;
	}

	public String getPicName02() {
		return this.picName02;
	}

	public void setPicName02(String picName02) {
		this.picName02 = picName02;
	}

	public float getPrice() {
		return this.price;
	}

	public void setPrice(float price) {
		this.price = price;
	}

	public String getWeb() {
		return this.web;
	}

	public void setWeb(String web) {
		this.web = web;
	}

	public String getDesc() {
		return desc;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

}