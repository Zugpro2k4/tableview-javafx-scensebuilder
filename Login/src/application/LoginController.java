package application;

import java.io.IOException;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;


public class LoginController {
	@FXML
	TextField emailTF;
	@FXML
	TextField fullnameTF;
	@FXML
	Label msg;
	
	LoginModel loginModel = new LoginModel();
	
	@FXML
	public void onClickRegisterButton() {
	    String email = emailTF.getText();
	    String fullname = fullnameTF.getText();

	    if (loginModel.checkExist(email)) {
	        msg.setText("Đăng nhập thành công");
	        User user = loginModel.getUser(email);
	        try {
	            goHomeScreen(user);
	            emailTF.getScene().getWindow().hide();
	        } catch (IOException e) {
	            e.printStackTrace();
	        }
	    } else {
	        msg.setText("Sai Email!");
	    }
	}

	
	public void goHomeScreen(User user) throws IOException {
	    FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("Home.fxml"));
	    Parent root = fxmlLoader.load();

	    HomeController controller = fxmlLoader.getController();

	    controller.setLoginedUser(user);
	    Stage homeStage = new Stage();
	    homeStage.setTitle("Home");
	    homeStage.setScene(new Scene(root));
	    homeStage.show();
	}
	}
