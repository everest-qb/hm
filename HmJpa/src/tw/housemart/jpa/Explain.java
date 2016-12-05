package tw.housemart.jpa;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the explains database table.
 * 
 */
@Entity
@Table(name="explains")
@NamedQuery(name="Explain.findAll", query="SELECT e FROM Explain e")
public class Explain implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private String id;

	public Explain() {
	}

	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

}