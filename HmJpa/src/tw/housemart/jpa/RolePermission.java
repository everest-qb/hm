package tw.housemart.jpa;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the role_permission database table.
 * 
 */
@Entity
@Table(name="role_permission")
@NamedQuery(name="RolePermission.findAll", query="SELECT r FROM RolePermission r")
public class RolePermission implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private RolePermissionPK id;

	//bi-directional many-to-one association to Role
	@ManyToOne
	@JoinColumn(name = "role_name",insertable=false,updatable=false)
	private Role role;

	public RolePermission() {
	}

	public RolePermissionPK getId() {
		return this.id;
	}

	public void setId(RolePermissionPK id) {
		this.id = id;
	}

	public Role getRole() {
		return this.role;
	}

	public void setRole(Role role) {
		this.role = role;
	}

}