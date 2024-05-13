package ERRONKA3.klaseak;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class Usuario implements Serializable{
	
	/**
	 * Objetueei orientatutako klase bat da eta bere atributuak izena eta pasahitza da.
	 */
	private static final long serialVersionUID = 1534454592410251924L;
	
	@Id @GeneratedValue
	private long id;
	private String username;
	private String password;
	
	public Usuario() {
		
	}

	public Usuario(long id, String username, String password) {
		super();
		this.id = id;
		this.username = username;
		this.password = password;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}	

	
	
	
}
