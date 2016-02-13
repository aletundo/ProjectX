package utils;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.HashMap;
import java.util.Map;
public class SecureLogin {

//TODO Convert DB hashmap into MySQL db connection/methods
	Map<String, String> DB = new HashMap<String, String>();

	public void signup(String username, String password) {
		SecureRandom sr = new SecureRandom();
		Long salt = sr.nextLong();
		String toStoreSalt = salt.toString();
		String saltedPassword = toStoreSalt + password;
		String hashedPassword = generateHash(saltedPassword);
		DB.put(username, hashedPassword);
		DB.put(username, toStoreSalt);
	}

	public Boolean validateUser(String user, String pw) {
		Boolean isAuthenticated = false;
		String storedSalt = ""; //TODO Get from db 
		String saltedPassword = storedSalt + pw;
		String hashedPassword = generateHash(saltedPassword);

		String storedPasswordHash = DB.get(user);
		if(hashedPassword.equals(storedPasswordHash)){
			isAuthenticated = true;
		}else{
			isAuthenticated = false;
		}
		return isAuthenticated;
	}

	private
static String generateHash(String input) {
		StringBuilder hash = new StringBuilder();

		try {
			MessageDigest sha = MessageDigest.getInstance("SHA-256");
			byte[] hashedBytes = sha.digest(input.getBytes());
			char[] digits = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
					'a', 'b', 'c', 'd', 'e', 'f' };
			for (int idx = 0; idx < hashedBytes.length; ++idx) {
				byte b = hashedBytes[idx];
				hash.append(digits[(b & 0xf0) >> 4]);
				hash.append(digits[b & 0x0f]);
			}
		} catch (NoSuchAlgorithmException e) {
			//TODO Handle with a Logger
		}

		return hash.toString();
	}

}

