package br.edu.ifpb.dac.groupd.model.entities;

import java.io.Serializable;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Entity
@Table(name="alarms")
public class Alarm implements Serializable{
	
	private static final long serialVersionUID = 9023853450528858907L;
	
	@Id
	@Column(name="id")
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;

	@NotNull
	@Column(name="seen")
	private boolean seen;
	
	@Valid
	@NotNull
	@OneToOne
	@JoinColumn(name="fence_id")
	private Fence fence;
	
	@Valid
	@OneToOne
	@JoinColumn(name="location_id")
	private Location location;
	
	@Min(0)
	@Column(name="distance", columnDefinition = "NUMERIC")
	private Double distance;
	
	@Min(0)
	@Column(name="exceeded", columnDefinition = "NUMERIC")
	private Double exceeded;
	
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public boolean isSeen() {
		return seen;
	}

	public void setSeen(boolean seen) {
		this.seen = seen;
	}

	public Fence getFence() {
		return fence;
	}

	public void setFence(Fence fence) {
		this.fence = fence;
	}

	public Location getLocation() {
		return location;
	}

	public void setLocation(Location location) {
		this.location = location;
	}

	public Double getDistance() {
		return distance;
	}

	public void setDistance(Double distance) {
		this.distance = distance;
	}

	public Double getExceeded() {
		return exceeded;
	}

	public void setExceeded(Double exceeded) {
		this.exceeded = exceeded;
	}

	@Override
	public int hashCode() {
		return Objects.hash(id);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Alarm other = (Alarm) obj;
		return Objects.equals(id, other.id);
	}
	
}
