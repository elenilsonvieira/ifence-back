package br.edu.ifpb.dac.groupd.presentation.dto;

import java.util.Objects;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import br.edu.ifpb.dac.groupd.business.validation.constraints.ValidEmail;

public class UserRequest {

	@NotNull
	@Size(min=3, max=50)
	private String name;
	
	@NotNull
	@NotEmpty
	@ValidEmail
	private String email;
	
	@NotNull
	@Size(min=8, max=30)
	@Pattern(regexp="^[^\\s]+$", message= "{password.Pattern}")
	private String password;

	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name == null ? null : name.trim();
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email == null ? null : email.trim();
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password == null ? null : password.trim();
	}

	@Override
	public int hashCode() {
		return Objects.hash(email, name, password);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		UserRequest other = (UserRequest) obj;
		return Objects.equals(email, other.email) && Objects.equals(name, other.name)
				&& Objects.equals(password, other.password);
	}
	
}
