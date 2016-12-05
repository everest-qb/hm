package tw.housemart.ejb;

import java.io.Serializable;

import javax.ejb.Local;

import tw.housemart.jpa.User;

@Local
public interface UserManagerLocal extends Serializable{
	
	public User findUser(String id);
}
