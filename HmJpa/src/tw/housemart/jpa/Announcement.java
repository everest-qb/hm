package tw.housemart.jpa;

import java.io.Serializable;

import javax.persistence.*;

import java.sql.Timestamp;


@Entity
@NamedQuery(name="Announcement.findAll", query="SELECT a FROM Announcement a order by a.createT desc")
@Table(name="announcement")
public class Announcement implements Serializable {


	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;

	private String content;
	
	private String cncontent;
	
	private String uscontent;

	private Timestamp createT;

	private String link;

	public Announcement() {
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getContent() {
		return this.content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Timestamp getCreateT() {
		return this.createT;
	}

	public void setCreateT(Timestamp createT) {
		this.createT = createT;
	}

	public String getLink() {
		return this.link;
	}

	public void setLink(String link) {
		this.link = link;
	}

	public String getCncontent() {
		return cncontent;
	}

	public void setCncontent(String cncontent) {
		this.cncontent = cncontent;
	}

	public String getUscontent() {
		return uscontent;
	}

	public void setUscontent(String uscontent) {
		this.uscontent = uscontent;
	}

}