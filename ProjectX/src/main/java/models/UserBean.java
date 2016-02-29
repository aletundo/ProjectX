package models;

public class UserBean {

	private int idUser;
	private String username;
	private String hashPw;
	private String salt;
	private String pw;
	private String fullname;
	private String type;
	private String skills;
	private String mail;
	private long temporaryHoursAvailable;

	public UserBean() {
		/* Say why it's empty */
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

	public String getFullname() {
		return fullname;
	}

	public void setFullname(String fullname) {
		this.fullname = fullname;
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

	public long getTemporaryHoursAvailable() {
		return temporaryHoursAvailable;
	}

	public void setTemporaryHoursAvailable(long temporaryHoursAvailable) {
		this.temporaryHoursAvailable = temporaryHoursAvailable;
	}

	@Override
	public String toString() {
		return "UserBean [idUser=" + idUser + ", username=" + username + ", hashPw=" + hashPw + ", salt=" + salt
				+ ", pw=" + pw + ", fullname=" + fullname + ", type=" + type + ", skills=" + skills + ", mail=" + mail
				+ "]";
	}
}
