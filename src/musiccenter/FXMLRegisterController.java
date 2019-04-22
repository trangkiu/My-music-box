package musiccenter;

import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

/**
 * FXML Controller class
 *
 * @author Trang Nguyen
 */
public class FXMLRegisterController implements Initializable {

    @FXML
    private TextField lbUserName;
    @FXML
    private PasswordField lbUserPasswd;
    @FXML
    private PasswordField lbReEnPasswd;
    @FXML
    private Label lbErrUserName;
    @FXML
    private Label lbErrPasswd;
    @FXML
    private Label lbErrRePasswd;

    private List_of_user userList;
    private handleUserEnter handleUserEnter;
    private static Parent root;
    private ArrayList<user> listOfUSer = new ArrayList<user>();

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            userList.getListOfUser(listOfUSer);
        } catch (FileNotFoundException ex) {
            Logger.getLogger(FXMLDocumentController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void handleButtonRegisterNew(ActionEvent event) {
        try {           
            checkEmptyEnter(lbUserName,lbErrUserName );
            checkEmptyEnter(lbUserPasswd,lbErrPasswd );
            checkEmptyEnter(lbReEnPasswd,lbErrRePasswd );
            
            // check duplicate user 
            if (userList.checkUserValidation(lbUserName.getText()) >= 0) {
                lbErrUserName.setText("* username already exist");
                lbUserName.clear();
                throw new Exception();
            }
            if (!lbUserPasswd.getText().equals(lbReEnPasswd.getText())) {
                lbErrRePasswd.setText("* password doesn't match");
                throw new Exception();
            }
      
            // save user into file 
            userList.appendUserFile(lbUserName.getText(), lbUserPasswd.getText());
            
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setContentText("Create successfully");
            alert.showAndWait();
        } catch (Exception e) {
            System.out.println("User checking error");
        }
            lbUserName.setText(null);
            lbUserPasswd.setText(null);
            lbReEnPasswd.setText(null);
            
    }

    @FXML
    private void handleButtonGoBack(ActionEvent event) {
        Parent newRoot;
        try {
            newRoot = FXMLLoader.load(getClass().getResource("FXMLDocument.fxml"));
            root = ((Node) event.getSource()).getScene().getRoot();
            ((Node) event.getSource()).getScene().setRoot(newRoot);
        } catch (IOException ex) {
            Logger.getLogger(FXMLDocumentController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
        public void checkEmptyEnter(TextField input,Label error ) throws Exception{
        if(input.getText().isEmpty()){
            error.setText("* field required");
             throw new Exception();
        }
    }

}
