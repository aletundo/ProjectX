package models;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import models.UserBean;
import utils.DbConnection;

public class UserDAO {

	private static final UserDAO INSTANCE = new UserDAO();

	private UserDAO() {

	}

	public static UserDAO getInstance() {

		return INSTANCE;
	}

	public List<UserBean> getCandidateSupervisors() {
		List<UserBean> candidates = new ArrayList<UserBean>();
		ResultSet rs = null;
		Statement statement = null;
		Connection currentConn = DbConnection.connect();
		if (currentConn != null) {
			final String createView = "CREATE VIEW busyusers AS SELECT DISTINCT U.idUser AS IdUser FROM "
					+ "user AS U JOIN project AS P ON U.idUser = P.idProjectManager "
					+ "UNION SELECT DISTINCT U.idUser AS IdUser "
					+ "FROM user AS U JOIN stage AS S ON U.idUser = S.idSupervisor";
			final String dropView = "DROP VIEW busyusers";
			final String cadidatesQuery = "SELECT U.idUser AS IdUser, U.fullname AS Fullname, U.type AS Type FROM user AS U WHERE U.type NOT LIKE 'Junior' AND U.idUser NOT IN(SELECT B.IdUser FROM busyusers AS B)";
			try {
				statement = currentConn.createStatement();
				statement.executeUpdate(createView);
				//statement = currentConn.prepareStatement(cadidatesQuery);
				rs = statement.executeQuery(cadidatesQuery);
				while (rs.next()) {
					UserBean user = new UserBean();
					user.setIdUser(rs.getInt("IdUser"));
					user.setFullname(rs.getString("Fullname"));
					user.setType(rs.getString("Type"));
					candidates.add(user);
				}
				statement.executeUpdate(dropView);
			} catch (SQLException e) {
				e.printStackTrace();
				// TODO handle with a Logger
			} finally {
				DbConnection.disconnect(currentConn, rs, statement);
			}
		}

		return candidates;
	}
	
	public List<UserBean> getCandidateDevelopers() {
		List<UserBean> candidates = new ArrayList<UserBean>();
		ResultSet rs = null;
		Statement statement = null;
		Connection currentConn = DbConnection.connect();
		if (currentConn != null) {
			final String createView = "CREATE VIEW busyusers AS SELECT DISTINCT U.idUser AS IdUser FROM "
					+ "user AS U JOIN project AS P ON U.idUser = P.idProjectManager "
					+ "UNION SELECT DISTINCT U.idUser AS IdUser "
					+ "FROM user AS U JOIN stage AS S ON U.idUser = S.idSupervisor";
			final String dropView = "DROP VIEW busyusers";
			final String cadidatesQuery = "SELECT U.idUser AS IdUser, U.fullname AS Fullname, U.type AS Type FROM user AS U WHERE U.type NOT LIKE 'ProjectManager' AND U.idUser NOT IN(SELECT B.IdUser FROM busyusers AS B)";
			try {
				statement = currentConn.createStatement();
				statement.executeUpdate(createView);
				//statement = currentConn.prepareStatement(cadidatesQuery);
				rs = statement.executeQuery(cadidatesQuery);
				while (rs.next()) {
					UserBean user = new UserBean();
					user.setIdUser(rs.getInt("IdUser"));
					user.setFullname(rs.getString("Fullname"));
					user.setType(rs.getString("Type"));
					candidates.add(user);
				}
				statement.executeUpdate(dropView);
			} catch (SQLException e) {
				e.printStackTrace();
				// TODO handle with a Logger
			} finally {
				DbConnection.disconnect(currentConn, rs, statement);
			}
		}

		return candidates;
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
			final String signUpQuery = "INSERT INTO user(username, fullname, mail, skills, salt, hashpw, type) VALUES('"
					+ user.getUsername() + "','" + user.getFullname() + "','" + user.getMail()
					+ "','" + user.getSkills() + "','" + user.getSalt() + "','" + user.getHashPw() + "','"
					+ user.getType() + "')";
			try {
				/*
				 * statement = currentConn.prepareStatement(signUpQuery);
				 * statement.setString(1, user.getUsername());
				 * statement.setString(2, user.getSalt());
				 * statement.setString(3, user.getHashPw());
				 */
				statement = currentConn.prepareStatement(signUpQuery);

				statement.executeUpdate(signUpQuery);
				stored = true;
			} catch (SQLException e) {
				e.printStackTrace(); // TODO Handle with a Logger
			} finally {
				DbConnection.disconnect(currentConn, statement);
			}
		}

		return stored;
	}

	/**
	 * Validate a user comparing his stored hash pw with an hash calculated at
	 * runtime with the user's pw and the stored random salt
	 * 
	 * @param user,
	 *            a bean which contains user's username and pw to be validated
	 * @return isAuthenticated
	 */
	public boolean validateUser(UserBean user) {

		Connection currentConn = DbConnection.connect();
		PreparedStatement statement = null;
		ResultSet rs = null;
		Boolean isAuthenticated = false;

		if (currentConn != null) {
			final String validateQuery = "SELECT U.idUser AS IdUser, U.salt as Salt, U.hashPw AS HashPW, U.type As Type FROM user AS U WHERE U.username LIKE ?";
			try {
				statement = currentConn.prepareStatement(validateQuery);
				String username = user.getUsername();
				statement.setString(1, username);
				rs = statement.executeQuery();

				while (rs.next()) {
					user.setIdUser(rs.getInt("IdUser"));
					user.setSalt(rs.getString("Salt"));
					user.setHashPw(rs.getString("HashPw"));
					user.setType(rs.getString("Type"));
				}
			} catch (SQLException e) {
				e.printStackTrace(); // TODO Handle with a Logger
			} finally {
				DbConnection.disconnect(currentConn, rs, statement);
			}

		}

		String saltedPassword = user.getSalt() + user.getPw();
		String hashedPassword = generateHash(saltedPassword);
		String storedPasswordHash = user.getHashPw();

		if (hashedPassword.equals(storedPasswordHash)) {
			isAuthenticated = true;
		} else {
			isAuthenticated = false;
		}
		return isAuthenticated;
	}

	/**
	 * Generate a SHA-256 hash
	 * 
	 * @param saltedPw,
	 *            a string composed by a random salt and user's pw
	 * @return hash, a string hashed with SHA-256
	 */
	private static String generateHash(String saltedPw) {
		StringBuilder hash = new StringBuilder();

		try {
			MessageDigest sha256 = MessageDigest.getInstance("SHA-256");
			byte[] hashedBytes = sha256.digest(saltedPw.getBytes());
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
