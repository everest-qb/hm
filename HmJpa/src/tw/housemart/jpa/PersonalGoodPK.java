package tw.housemart.jpa;

import java.io.Serializable;
import javax.persistence.*;

/**
 * The primary key class for the personal_goods database table.
 * 
 */
@Embeddable
public class PersonalGoodPK implements Serializable {
	//default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;

	private String name;

	private String gId;

	public PersonalGoodPK() {
	}
	public String getName() {
		return this.name;
	}
	public void setName(String name) {
		this.name = name;
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
		if (!(other instanceof PersonalGoodPK)) {
			return false;
		}
		PersonalGoodPK castOther = (PersonalGoodPK)other;
		return 
			this.name.equals(castOther.name)
			&& this.gId.equals(castOther.gId);
	}

	public int hashCode() {
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + this.name.hashCode();
		hash = hash * prime + this.gId.hashCode();
		
		return hash;
	}
}