package tw.housemart.jpa;

import java.io.Serializable;
import javax.persistence.*;

/**
 * The primary key class for the banner database table.
 * 
 */
@Embeddable
public class BannerPK implements Serializable {
	//default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;

	private String id;

	private String gId;

	public BannerPK() {
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

	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof BannerPK)) {
			return false;
		}
		BannerPK castOther = (BannerPK)other;
		return 
			this.id.equals(castOther.id)
			&& this.gId.equals(castOther.gId);
	}

	public int hashCode() {
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + this.id.hashCode();
		hash = hash * prime + this.gId.hashCode();
		
		return hash;
	}
}