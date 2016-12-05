package tw.housemart.cdi.interceptor;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.interceptor.AroundInvoke;
import javax.interceptor.Interceptor;
import javax.interceptor.InvocationContext;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.AuthorizationException;
import org.apache.shiro.authz.Permission;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.apache.shiro.authz.permission.WildcardPermission;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import tw.housemart.cdi.annotation.ShiroSecurity;

@ShiroSecurity
@Interceptor
public class Security {
	private Logger log = LoggerFactory.getLogger(Security.class);

	@AroundInvoke
	public Object securityAround(InvocationContext ctx) throws Exception {
		log.info("Securing {}.{}({})", new Object[] { ctx.getClass().getName(),
				ctx.getMethod(), ctx.getParameters() });
		Subject subject = SecurityUtils.getSubject();
		final Class<? extends Object> runtimeClass = ctx.getTarget().getClass();

		/*boolean requiresAuthentication = false;
		try {
			ctx.getMethod().getAnnotation(RequiresAuthentication.class);
			requiresAuthentication = true;
		} catch (NullPointerException e) {
			requiresAuthentication = false;
		}

		if (!requiresAuthentication) {
			try {
				runtimeClass.getAnnotation(RequiresAuthentication.class);
				requiresAuthentication = true;
			} catch (NullPointerException e) {
				requiresAuthentication = false;
			}
		}

		if (requiresAuthentication) {
			try {
				if (!subject.isAuthenticated()) {
					throw new AuthorizationException();
				}
			} catch (Exception e) {
				log.error("Access denied - {}: {}", e.getClass().getName(),
						e.getMessage());
				throw e;

			}
		}*/

		boolean requiresRoles = false;
		List<String> listOfRoles = null;

		try {
			RequiresRoles roles = ctx.getMethod().getAnnotation(
					RequiresRoles.class);
			listOfRoles = Arrays.asList(roles.value());
			requiresRoles = true;
		} catch (NullPointerException e) {
			requiresRoles = false;
		}

		if (!requiresRoles || listOfRoles == null) {
			try {
				RequiresRoles roles = runtimeClass
						.getAnnotation(RequiresRoles.class);
				listOfRoles = Arrays.asList(roles.value());
				requiresRoles = true;
			} catch (NullPointerException e) {
				requiresRoles = false;
			}
		}

		if (requiresRoles && listOfRoles != null) {
			try {
				boolean[] boolRoles = subject.hasRoles(listOfRoles);
				boolean roleVerified = false;
				for (boolean b : boolRoles) {
					if (b) {
						roleVerified = true;
						break;
					}
				}
				if (!roleVerified) {
					throw new AuthorizationException(
							"Access denied. User doesn't have enough privilege Roles:"
									+ listOfRoles + " to access this page.");
				}
			} catch (Exception e) {
				log.error("Access denied - {}: {}", e.getClass().getName(),
						e.getMessage());
				throw e;

			}
		}

		boolean requiresPermissions = false;
		List<String> listOfPermissionsString = null;

		try {
			RequiresPermissions permissions = ctx.getMethod().getAnnotation(
					RequiresPermissions.class);
			listOfPermissionsString = Arrays.asList(permissions.value());
			requiresPermissions = true;
		} catch (NullPointerException e) {
			requiresPermissions = false;
		}

		if (!requiresPermissions || listOfPermissionsString == null) {

			try {
				RequiresPermissions permissions = runtimeClass
						.getAnnotation(RequiresPermissions.class);
				listOfPermissionsString = Arrays.asList(permissions.value());
				requiresPermissions = true;
			} catch (NullPointerException e) {
				requiresPermissions = false;
			}
		}

		if (requiresPermissions && listOfPermissionsString != null) {
			List<Permission> listOfPermissions = new ArrayList<Permission>();
			for (String p : listOfPermissionsString) {
				listOfPermissions.add((Permission) new WildcardPermission(p));
			}
			try {
				boolean[] boolPermissions = subject
						.isPermitted(listOfPermissions);
				boolean permitted = false;
				for (boolean b : boolPermissions) {
					if (b) {
						permitted = true;
						break;
					}
				}
				if (!permitted) {
					throw new AuthorizationException(
							"Access denied. User doesn't have enough privilege Permissions:"
									+ listOfRoles + " to access this page.");
				}
			} catch (Exception e) {
				log.error("Access denied - {}: {}", e.getClass().getName(),
						e.getMessage());
				throw e;

			}
		}
		return ctx.proceed();
	}

	/**
	 * @RequiresAuthentication
	 * @RequiresRoles({ "ADMIN" })
	 * @RequiresPermissions({ "Add", "Del" })
	 */
}
