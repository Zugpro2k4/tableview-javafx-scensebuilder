package application;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UserDAO {

	public static User checkUser(String email) throws SQLException, ClassNotFoundException {
		User user = null;
		String jdbcURL = "jdbc:ucanaccess://lib/QLNS.accdb";
		String dbUser = "";
		String dbPassword = "";
		Class.forName("net.ucanaccess.jdbc.UcanaccessDriver");
		try (Connection connection = DriverManager.getConnection(jdbcURL, dbUser, dbPassword);
				PreparedStatement statement = connection.prepareStatement("SELECT * FROM tbluser WHERE email = ?")) {
			statement.setString(1, email);
			try (ResultSet resultSet = statement.executeQuery()) {
				if (resultSet.next()) {
					user = new User();
					user.setfullname(resultSet.getString("fullname"));
					user.setEmail(email);
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw e;
		}
		return user;
	}

	public void addUser(User user) {
		String jdbcURL = "jdbc:ucanaccess://lib/QLNS.accdb";
		String dbUser = "";
		String dbPassword = "";
		try {
			String sql = "INSERT INTO tbluser (email, fullname) VALUES (?, ?)";
			try (Connection connection = DriverManager.getConnection(jdbcURL, dbUser, dbPassword);
					PreparedStatement statement = connection.prepareStatement(sql)) {
				statement.setString(1, user.getEmail());
				statement.setString(2, user.getfullname());
				statement.executeUpdate();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	public void updateUser(User User) {
		String jdbcURL = "jdbc:ucanaccess://lib/QLNS.accdb";
		String dbUser = "";
		String dbPassword = "";
		try {
			String sql = "UPDATE tbluser set fullname = ? where email = ?";
			try(Connection connection = DriverManager.getConnection(jdbcURL, dbUser, dbPassword);
					PreparedStatement statement = connection.prepareStatement(sql)){
					statement.setString(1, User.getfullname());
		            statement.setString(2, User.getEmail());
		            statement.executeUpdate();
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
	}
	
	public void deleteUser(User user) {
		String jdbcURL = "jdbc:ucanaccess://lib/QLNS.accdb";
		String dbUser = "";
		String dbPassword = "";
		try {
			String sql = "delete tbluser where email = ? and fullname = ?";
			try(Connection connection = DriverManager.getConnection(jdbcURL, dbUser, dbPassword);
					PreparedStatement statement = connection.prepareStatement(sql)
					){
				 	statement.setString(1, user.getEmail());
		            statement.setString(2, user.getfullname());
		            statement.executeUpdate();
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
	}
	public List<User> listAllUsers() {
		List<User> listUsers = new ArrayList<>();
		
		String sql = "SELECT * FROM tbluser";
		String jdbcURL = "jdbc:ucanaccess://lib/QLNS.accdb";
		String dbUser = "";
		String dbPassword = "";
		try {
			Class.forName("net.ucanaccess.jdbc.UcanaccessDriver");
			try (Connection connection = DriverManager.getConnection(jdbcURL, dbUser, dbPassword);
					PreparedStatement statement = connection.prepareStatement(sql);
					ResultSet resultSet = statement.executeQuery()) {
				while (resultSet.next()) {
					String email = resultSet.getString("email");
					String fullname = resultSet.getString("fullname");
					User user = new User(email, fullname);
					listUsers.add(user);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return listUsers;
	}

	public static void main(String[] args) {
        UserDAO dao = new UserDAO();
        try {
            User user = dao.checkUser("ndd@gmail.com");
            if (user != null) {
                System.out.println("Login successful!");
                System.out.println("Full Name: " + user.getfullname());
                System.out.println("Email: " + user.getEmail());
            } else {
                System.out.println("Login failed! Invalid email.");
            }
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
