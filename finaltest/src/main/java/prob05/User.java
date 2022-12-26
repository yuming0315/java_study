package prob05;

public class User {
	private String id;	
	private String password;

	public User(String id, String password) {
		this.id = id;
		this.password = password;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}
	
	@Override
	public boolean equals(Object obj) throws PasswordDismatchException {
		if (this == obj)
			return true;
		if (obj == null || getClass() != obj.getClass() )
			return false;
		
		User other = (User) obj;
		if (id == null || !id.equals(other.id)) {
			return false;
		} 
		
		if(!password.equals(other.password)) {
			throw new PasswordDismatchException();
		}
		
		return true;
	}
	
	
}
