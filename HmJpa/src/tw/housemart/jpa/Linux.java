package tw.housemart.jpa;

import java.io.Serializable;

import javax.persistence.*;


/**
 * The persistent class for the linux database table.
 * 
 */
@Entity
@NamedQuery(name="Linux.findAll", query="SELECT l FROM Linux l")
@Table(name="linux")
@Cacheable(false)
public class Linux implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private String name;

	private String password;

	public Linux() {
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPassword() {
		return this.password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

}