package tw.housemart.jpa;

import java.io.Serializable;

import javax.persistence.*;


/**
 * The persistent class for the deliver_location database table.
 * 
 */
@Entity
@Table(name="deliver_location")
@NamedQuery(name="DeliverLocation.findAll", query="SELECT d FROM DeliverLocation d")
public class DeliverLocation implements Serializable {	

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private long id;

	private String address;

	//uni-directional many-to-one association to Community
	@ManyToOne
	@JoinColumn(name="communityId")
	private Community community;

	public DeliverLocation() {
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getAddress() {
		return this.address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public Community getCommunity() {
		return this.community;
	}

	public void setCommunity(Community community) {
		this.community = community;
	}

}