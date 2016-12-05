package tw.housemart.jpa;

import java.io.Serializable;
import javax.persistence.*;

/**
 * The primary key class for the role_permission database table.
 * 
 */
@Embeddable
public class RolePermissionPK implements Serializable {
	//default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;

	@Column(name="role_name", insertable=false, updatable=false)
	private String roleName;

	private String permission;

	public RolePermissionPK() {
	}
	public String getRoleName() {
		return this.roleName;
	}
	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}
	public String getPermission() {
		return this.permission;
	}
	public void setPermission(String permission) {
		this.permission = permission;
	}

	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof RolePermissionPK)) {
			return false;
		}
		RolePermissionPK castOther = (RolePermissionPK)other;
		return 
			this.roleName.equals(castOther.roleName)
			&& this.permission.equals(castOther.permission);
	}

	public int hashCode() {
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + this.roleName.hashCode();
		hash = hash * prime + this.permission.hashCode();
		
		return hash;
	}
}