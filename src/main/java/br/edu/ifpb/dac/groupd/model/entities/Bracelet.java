package br.edu.ifpb.dac.groupd.model.entities;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Entity
@Table(name="bracelets")
public class Bracelet implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 8321201080965139583L;

	@Id
	@Column(name="id")
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;

	@NotEmpty
	@Size(min=1,max=50)
	@Column(name="name")
	private String name;
	
	@ManyToMany(fetch = FetchType.EAGER, mappedBy="bracelets")
	private Set<Fence> fences = new HashSet<>();
	
	
	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	@JoinTable(name = "bracelet_location",
		joinColumns = @JoinColumn(name = "bracelet_id"),
		inverseJoinColumns = @JoinColumn(name = "location_id"))
	private Set<Location> locations = new HashSet<>();
	
	@OneToOne
	@JoinColumn(name="bracelet_monitor")
	private Fence monitor;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinTable(name="user_bracelet",
		joinColumns = @JoinColumn(name="bracelet_id"),
		inverseJoinColumns = @JoinColumn(name="user_id"))	
	private User user;
	
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		if(name == null)
			return ;
		this.name = name.trim();
	}

	public Set<Fence> getFences() {
		return fences;
	}

	public void setFences(Set<Fence> fences) {
		this.fences = fences;
	}

	public Set<Location> getLocations() {
		return locations;
	}

	public void setLocations(Set<Location> locations) {
		this.locations = locations;
	}
	
	public void addLocation(@Valid Location location) {
		if(location != null) {
			location.setBracelet(this);
			locations.add(location);
		}
	}

	public Fence getMonitor() {
		return monitor;
	}

	public void setMonitor(Fence monitor) {
		this.monitor = monitor;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}
	
	
	
}
