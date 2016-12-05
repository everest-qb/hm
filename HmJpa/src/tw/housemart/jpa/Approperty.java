package tw.housemart.jpa;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the approperties database table.
 * 
 */
@Entity
@Table(name="approperties")
@NamedQuery(name="Approperty.findAll", query="SELECT a FROM Approperty a")
public class Approperty implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private String id;

	private String avalue;

	public Approperty() {
	}

	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getAvalue() {
		return this.avalue;
	}

	public void setAvalue(String avalue) {
		this.avalue = avalue;
	}

}