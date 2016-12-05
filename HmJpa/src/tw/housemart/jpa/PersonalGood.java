package tw.housemart.jpa;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the personal_goods database table.
 * 
 */
@Entity
@Table(name="personal_goods")
public class PersonalGood implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private PersonalGoodPK id;

	public PersonalGood() {
	}

	public PersonalGoodPK getId() {
		return this.id;
	}

	public void setId(PersonalGoodPK id) {
		this.id = id;
	}

}