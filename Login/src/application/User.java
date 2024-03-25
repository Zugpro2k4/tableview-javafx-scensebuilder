package application;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class User {
    private StringProperty email;
    private StringProperty fullname;

    public User() {
        this.email = new SimpleStringProperty();
        this.fullname = new SimpleStringProperty();
    }

    public User(String email, String fullname) {
        this.email = new SimpleStringProperty(email);
        this.fullname = new SimpleStringProperty(fullname);
    }

    // Getter and Setter for email
    public String getEmail() {
        return email.get();
    }

    public void setEmail(String email) {
        this.email.set(email);
    }

    public StringProperty emailProperty() {
        return email;
    }

    // Getter and Setter for fullname
    public String getfullname() {
        return fullname.get();
    }

    public void setfullname(String fullname) {
        this.fullname.set(fullname);
    }

    public StringProperty fullnameProperty() {
        return fullname;
    }
}
