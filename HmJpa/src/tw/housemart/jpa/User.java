package tw.housemart.jpa;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;
import static javax.persistence.CascadeType.PERSIST;
import static javax.persistence.FetchType.LAZY;


/**
 * The persistent class for the users database table.
 * 
 */
@Entity
@Table(name="users")
@NamedQuery(name="User.findAll", query="SELECT u FROM User u")
public class User implements Serializable {	

	@Id
	private String name;

	private String mail;

	private String password;
	
	@ManyToOne(cascade = PERSIST, fetch = LAZY)
	@JoinColumn(name = "addressId", referencedColumnName = "id")
	private DeliverLocation address;
	
	private String tel;

	//uni-directional many-to-many association to Role
	@ManyToMany(cascade={CascadeType.ALL})
	@JoinTable(
		name="user_role"
		, joinColumns={
			@JoinColumn(name="user_name")
			}
		, inverseJoinColumns={
			@JoinColumn(name="role_name")
			}
		)
	private List<Role> roles;

	public User() {
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getMail() {
		return this.mail;
	}

	public void setMail(String mail) {
		this.mail = mail;
	}

	public String getPassword() {
		return this.password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public List<Role> getRoles() {
		return this.roles;
	}

	public void setRoles(List<Role> roles) {
		this.roles = roles;
	}

	public String getTel() {
		return tel;
	}

	public void setTel(String tel) {
		this.tel = tel;
	}

	public DeliverLocation getAddress() {
		return address;
	}

	public void setAddress(DeliverLocation address) {
		this.address = address;
	}

}