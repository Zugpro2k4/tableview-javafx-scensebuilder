package application;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class LoginModel {
    private List<User> listUser;

    public LoginModel() {
        listUser = new ArrayList<>();
        loadUsersFromDatabase();
    }

    private void loadUsersFromDatabase() {
        String jdbcURL = "jdbc:ucanaccess://lib/QLNS.accdb"; // Đường dẫn đến cơ sở dữ liệu Access
        String dbUser = ""; // Tên người dùng cơ sở dữ liệu (nếu có)
        String dbPassword = ""; // Mật khẩu cơ sở dữ liệu (nếu có)

        String sql = "SELECT email, fullname FROM tbluser";

        try (Connection connection = DriverManager.getConnection(jdbcURL, dbUser, dbPassword);
             PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                String email = resultSet.getString("email");
                String fullname = resultSet.getString("fullname");
                User user = new User(email, fullname);
                listUser.add(user);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void themUser(User user) {
        listUser.add(user);
        // Cần thêm dữ liệu mới vào cả cơ sở dữ liệu Access ở đây nếu cần
    }

    public boolean checkExist(String email) {
        for (User user : listUser) {
            if (user.getEmail().equals(email)) {
                return true;
            }
        }
        return false;
    }

    public List<User> getListUser() {
        return listUser;
    }

    public User getUser(String email) {
        for (User user : listUser) {
            if (user.getEmail().equals(email)) {
                return user;
            }
        }
        return null;
    }
}
