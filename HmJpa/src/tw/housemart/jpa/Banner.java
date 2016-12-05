package tw.housemart.jpa;

import java.io.Serializable;

import javax.persistence.*;


/**
 * The persistent class for the banner database table.
 * 
 */
@Entity
@NamedQuery(name="Banner.findAll", query="SELECT b FROM Banner b")
@Table(name = "banner")
public class Banner implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private BannerPK id;

	public Banner() {
	}

	public BannerPK getId() {
		return this.id;
	}

	public void setId(BannerPK id) {
		this.id = id;
	}

}