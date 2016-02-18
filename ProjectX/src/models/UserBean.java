package models;

public class UserBean {
	
	private int idUser;
	private String username;
	private String hashPw;
	private String salt;
	private String pw;
	private String name;
	private String surname;
	private String type; //TODO correct with enum ProjecManager, Senior, Junior
	private String skills;
	private String mail;
	
	public UserBean(){
		
	}

	public int getIdUser() {
		return idUser;
	}

	public String getPw() {
		return pw;
	}

	public void setPw(String pw) {
		this.pw = pw;
	}

	public void setIdUser(int idUser) {
		this.idUser = idUser;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getHashPw() {
		return hashPw;
	}

	public void setHashPw(String hashPw) {
		this.hashPw = hashPw;
	}

	public String getSalt() {
		return salt;
	}

	public void setSalt(String salt) {
		this.salt = salt;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSurname() {
		return surname;
	}

	public void setSurname(String surname) {
		this.surname = surname;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getSkills() {
		return skills;
	}

	public void setSkills(String skills) {
		this.skills = skills;
	}

	public String getMail() {
		return mail;
	}

	public void setMail(String mail) {
		this.mail = mail;
	}
}
