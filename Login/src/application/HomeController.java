package application;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
public class HomeController implements Initializable {
    @FXML
    private Label LoginedFullname;
    @FXML
    private TableView<User> userListTV;
    @FXML
    private TableColumn<User, String> usernameCol;
    @FXML
    private TableColumn<User, String> fullnameCol;
    @FXML
    private TextField emaiTF;
    @FXML
    private TextField fullnameTF;
   

    private UserDAO um; 

    private User loginedUser;
    //private ObservableList<User> temporaryUserList;

    public User getLoginedUser() {
        return loginedUser;
    }

    public void setLoginedUser(User loginedUser) {
        this.loginedUser = loginedUser;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        um = new UserDAO();

        if (loginedUser != null) {
            Platform.runLater(() -> {
                LoginedFullname.setText(loginedUser.getfullname());
            });
        } else {
            // Xử lý trường hợp loginedUser là null
        	System.out.println("OH MAII GOTT");
        }

        usernameCol.setCellValueFactory(new PropertyValueFactory<User, String>("email"));
        fullnameCol.setCellValueFactory(new PropertyValueFactory<User, String>("fullname"));

        List<application.User> userlist = um.listAllUsers();
        ObservableList<application.User> obslist = FXCollections.observableArrayList(userlist);
        userListTV.setItems(obslist);
        userListTV.setOnMouseClicked(e -> {
            if (e.getClickCount() == 1) {
                onClickRow();
            }
        });

        //temporaryUserList = FXCollections.observableArrayList(); // Khởi tạo danh sách tạm thời
    }
    
    @FXML
    private void logout() {
        try {
            // Load the FXML file for login screen
            FXMLLoader loader = new FXMLLoader(getClass().getResource("Register.fxml"));
            Parent root = loader.load();

            // Create a new stage for the login screen
            Stage loginStage = new Stage();
            loginStage.setScene(new Scene(root));
            loginStage.show();

            // Close the current stage (home screen)
            Stage currentStage = (Stage) LoginedFullname.getScene().getWindow();
            currentStage.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    @FXML
    private void addUser(ActionEvent event) {
        String email = emaiTF.getText();
        String fullname = fullnameTF.getText();
        User newUser = new User(email, fullname);

        // Thêm người dùng vào danh sách tạm thời
        um.addUser(newUser);

        // Thêm người dùng mới vào danh sách hiển thị trực tiếp trên TableView
        userListTV.getItems().add(newUser);

        // Xóa nội dung trong các trường văn bản sau khi thêm người dùng
        emaiTF.clear();
        fullnameTF.clear();
    }


    @FXML
    private void updateUser(ActionEvent event) {
        String email = emaiTF.getText();
        String fullname = fullnameTF.getText();
        User newUser = new User(email, fullname);

        // Cập nhật người dùng trong cơ sở dữ liệu
        um.updateUser(newUser);

        // Cập nhật thông tin người dùng trong TableView
        User selectedItem = userListTV.getSelectionModel().getSelectedItem();
        if (selectedItem != null) {
            selectedItem.setEmail(email);
            selectedItem.setfullname(fullname);
            
            // Refresh TableView
            userListTV.impl_updatePeer(); // Refresh the entire TableView
        }

        // Xóa nội dung trong các trường văn bản sau khi cập nhật người dùng
        emaiTF.clear();
        fullnameTF.clear();
    }

    @FXML
    private void deleteUser(ActionEvent event) {
        User selectedItem = userListTV.getSelectionModel().getSelectedItem();
        if (selectedItem != null) {
            // Xóa người dùng từ cơ sở dữ liệu
            um.deleteUser(selectedItem);

            // Xóa người dùng khỏi TableView
            userListTV.getItems().remove(selectedItem);

            // Xóa nội dung trong các trường văn bản sau khi xóa người dùng
            emaiTF.clear();
            fullnameTF.clear();
        }
    }
    @FXML
    public void exit() {
    	Platform.exit();
    }
    
    
    
    @FXML
    public void onClickRow() {
        User selectedItem = userListTV.getSelectionModel().getSelectedItem();
        if (selectedItem != null) {
            emaiTF.setText(selectedItem.getEmail());
            fullnameTF.setText(selectedItem.getfullname());
        }
    }
    
    
    public void updateUser(User user) {
        String jdbcURL = "jdbc:ucanaccess://lib/QLNS.accdb";
        String dbUser = "";
        String dbPassword = "";
        
        try {
            String sql = "UPDATE tbluser SET fullname = ? WHERE email = ?";
            
            try(Connection connection = DriverManager.getConnection(jdbcURL, dbUser, dbPassword);
                PreparedStatement statement = connection.prepareStatement(sql)) {
                statement.setString(1, user.getfullname());
                statement.setString(2, user.getEmail());
                
                // Thực thi truy vấn cập nhật
                int rowsUpdated = statement.executeUpdate();
                
                if (rowsUpdated > 0) {
                    System.out.println("Thông tin người dùng đã được cập nhật thành công trong cơ sở dữ liệu.");
                } else {
                    System.out.println("Không có bản ghi nào được cập nhật trong cơ sở dữ liệu.");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
