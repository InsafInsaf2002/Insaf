package project;

import java.awt.event.ActionEvent;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;


public class FXMLDocumentController implements Initializable 
{
    @FXML
    private BorderPane login_form;

    @FXML
    private Button su_createAccountBtn;

    @FXML
    private TextField si_username;

    @FXML
    private PasswordField si_password;

    @FXML
    private Button su_loginBtn;

    @FXML
    private BorderPane signup_form;

    @FXML
    private Button su_loginAccountBtn;

    @FXML
    private TextField su_username;

    @FXML
    private PasswordField su_password;

    @FXML
    private Button su_signupBtn;
    
    public Connection connect;
    private PreparedStatement prepare;
    private ResultSet result;
    public void loginAccount()
    {
        String sql;
        sql = "Select username,password FROM admin WHERE username = ? and password = ?";
        connect=database.connect();
        try
        {
            Alert alert;
            if(si_username.getText().isEmpty() || si_password.getText().isEmpty())
            {   
                alert=new Alert(AlertType.ERROR);
                alert.setTitle("Error message");
                alert.setHeaderText(null);
                alert.setContentText("please fill all blank fields");
                alert.showAndWait();
            }
            else
            {
                prepare=connect.prepareStatement(sql);
                prepare.setString(1,si_username.getText());
                prepare.setString(2,si_password.getText());  
                result=prepare.executeQuery();
                if(result.next())
                {
                    //if correct username and password
                    alert=new Alert(AlertType.INFORMATION);
                    alert.setTitle("Information message");
                    alert.setHeaderText(null);
                    alert.setContentText("Successfully login");
                    alert.showAndWait();
                }
                else
                {
                    //if incorrect username
                    alert=new Alert(AlertType.ERROR);
                    alert.setTitle("Error message");
                    alert.setHeaderText(null);
                    alert.setContentText("Incorrect username/password");
                    alert.showAndWait();
                }
            }
            
        }catch(Exception e)
        {
            e.printStackTrace();
        }
    }
    public void registerAccount()
    {
        String sql="INSERt INTO admin(username, password) VALUES(?,?)";
        connect=database.connect();
        try
        {
            Alert alert;
            if(su_username.getText().isEmpty() || su_password.getText().isEmpty())
            {   
                alert=new Alert(AlertType.ERROR);
                alert.setTitle("Error message");
                alert.setHeaderText(null);
                alert.setContentText("please fill all blank fields");
                alert.showAndWait();
            }
            else
            {
                if(su_password.getText().length()<8)
                {
                    //if correct username and password
                    alert=new Alert(AlertType.ERROR);
                    alert.setTitle("Error message");
                    alert.setHeaderText(null);
                    alert.setContentText("Invalid password,atleast 8 characters needed");
                    alert.showAndWait();
                }
                else
                {
                    prepare=connect.prepareStatement(sql);
                    prepare.setString(1,su_username.getText());
                    prepare.setString(2,su_password.getText()); 
                    prepare.executeUpdate();
                 
                    alert=new Alert(AlertType.INFORMATION);
                    alert.setTitle("Information message");
                    alert.setHeaderText(null);
                    alert.setContentText("Successfully create a new Account");
                    alert.showAndWait();
                }
            }
        }catch(Exception e)
        {
            e.printStackTrace();
        }
    }
    public void switchForm(ActionEvent event)
    {
        if(event.getSource()==su_loginAccountBtn)
        {
            login_form.setVisible(true);
            signup_form.setVisible(false);
        }else if(event.getSource()==su_createAccountBtn)
        {
            login_form.setVisible(false);
            signup_form.setVisible(true);
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
}






