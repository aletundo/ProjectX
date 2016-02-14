package models;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.sql.*;
import models.UserBean;
import utils.DbConnection;

public class UserDAO {

	private static final UserDAO INSTANCE = new UserDAO();

	private UserDAO() {

	}

	public static UserDAO getInstance() {

		return INSTANCE;
	}

	public boolean signUpUser(UserBean user) {
		boolean stored = false;
		SecureRandom sr = new SecureRandom();
		Long salt = sr.nextLong();
		String toStoreSalt = salt.toString();
		String saltedPassword = toStoreSalt + user.getPw();
		String hashedPassword = generateHash(saltedPassword);

		user.setSalt(toStoreSalt);
		user.setHashPw(hashedPassword);

		Connection currentConn = DbConnection.connect();
		// PreparedStatement statement;
		Statement statement = null;

		if (currentConn != null) {
			// String signUpQuery = "INSERT INTO User(username, salt, hashpw)
			// VALUES(?, ?, ?)";
			String signUpQuery = "INSERT INTO User(username, salt, hashpw) VALUES('" + user.getUsername() + "','"
					+ user.getSalt() + "','" + user.getHashPw() + "')";
			try {
				/*
				 * statement = currentConn.prepareStatement(signUpQuery);
				 * statement.setString(1, user.getUsername());
				 * statement.setString(2, user.getSalt());
				 * statement.setString(3, user.getHashPw());
				 */
				statement = currentConn.prepareStatement(signUpQuery);
				System.out.println(statement.toString());

				statement.executeUpdate(signUpQuery);
				stored = true;
			} catch (SQLException e) {
				e.printStackTrace(); // TODO Handle with a Logger
			}
		}
		DbConnection.disconnect(currentConn);

		return stored;
	}

	public boolean validateUser(UserBean user) {

		Connection currentConn = DbConnection.connect();
		PreparedStatement statement;
		ResultSet rs;
		Boolean isAuthenticated = false;

		if (currentConn != null) {
			String validateQuery = "SELECT U.idUser AS Id, U.salt as Salt, U.hashPw as HashPw FROM User AS U WHERE U.username LIKE ?";
			try {
				statement = currentConn.prepareStatement(validateQuery);
				String username = user.getUsername();
				statement.setString(1, username);
				rs = statement.executeQuery();

				System.out.println(validateQuery);

				while (rs.next()) {
					user.setId(rs.getInt("Id"));
					System.out.println(rs.getString("Salt"));
					user.setSalt(rs.getString("Salt"));
					user.setHashPw(rs.getString("HashPw"));
				}
			} catch (SQLException e) {
				e.printStackTrace(); // TODO Handle with a Logger
			}
		}
		DbConnection.disconnect(currentConn);

		String saltedPassword = user.getSalt() + user.getPw();
		String hashedPassword = generateHash(saltedPassword);
		String storedPasswordHash = user.getHashPw();

		System.out.println("Salted " + saltedPassword);
		System.out.println("HashedStored " + storedPasswordHash);
		System.out.println("Salt " + user.getSalt());
		if (hashedPassword.equals(storedPasswordHash)) {
			isAuthenticated = true;
		} else {
			isAuthenticated = false;
		}
		return isAuthenticated;
	}

	private static String generateHash(String input) {
		StringBuilder hash = new StringBuilder();

		try {
			MessageDigest sha = MessageDigest.getInstance("SHA-256");
			byte[] hashedBytes = sha.digest(input.getBytes());
			char[] digits = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f' };
			for (int idx = 0; idx < hashedBytes.length; ++idx) {
				byte b = hashedBytes[idx];
				hash.append(digits[(b & 0xf0) >> 4]);
				hash.append(digits[b & 0x0f]);
			}
		} catch (NoSuchAlgorithmException e) {
			// TODO Handle with a Logger
		}

		return hash.toString();
	}
}
