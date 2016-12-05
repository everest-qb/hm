package tw.housemart.shiro;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.realm.jdbc.JdbcRealm;
import org.apache.shiro.util.JdbcUtils;

public class MyJdbcRealm extends JdbcRealm {

	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(
			AuthenticationToken token) throws AuthenticationException {
		AuthenticationInfo info = super.doGetAuthenticationInfo(token);
		UsernamePasswordToken upToken = (UsernamePasswordToken) token;
		String userName = upToken.getUsername();
		if(!checkActive(userName)){
			throw new LockedAccountException("Account [" + userName + "] is locked.");
		}
		return info;
	}
	
	private boolean checkActive(String userName){
		boolean returnValue=false;
		Connection conn = null;
		PreparedStatement ps = null;
	    ResultSet rs = null;
		try{
			conn=dataSource.getConnection();
			ps = conn.prepareStatement(authenticationQuery+" and active=0");
            ps.setString(1, userName);         
            rs = ps.executeQuery();
            if(!rs.next()){
            	returnValue=true;
            }
		}catch (Exception e){
			System.out.println(e.getMessage());
			 final String message = "There was a SQL error while authenticating user [" + userName + "]";
			throw new AuthenticationException(message, e);
		}finally{
			JdbcUtils.closeConnection(conn);
		}
		
		return returnValue;
	}

}
