package musiccenter;

import java.io.FileNotFoundException;
import java.io.IOException;
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
 *
 * @author Trang Nguyen
 */
public class FXMLDocumentController implements Initializable {

    @FXML
    private TextField lbUserId;
    @FXML
    private PasswordField lbPasswd;
    @FXML
    private Label lbErrUserID;
    @FXML
    private Label lbErrUserPass;

    private FXMLRegisterController FXMLRegister;
    private List_of_user userList;
    private handleUserEnter handleUserEnter;
    private static Parent root;

    // current login 
    private static int indexOnEditing = 0; // index of current user


    private static user currentUser;
    public static user getCurrentUser() {
        return currentUser;
    }

    // 
    private static Parent newRoot;
    private ArrayList<user> listOfUSer = new ArrayList<user>();
    private static FXMLDisplayController FXMLDisplay;

    @Override
    public void initialize(URL url, ResourceBundle rb) {

        try {
            userList.getListOfUser(listOfUSer);
        } catch (FileNotFoundException ex) {
            Logger.getLogger(FXMLDocumentController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void handleButtonLogin(ActionEvent event) {
        try {
            checkEmptyEnter(lbUserId, lbErrUserID);
            checkEmptyEnter(lbPasswd, lbErrUserPass);

            indexOnEditing = userList.checkUserValidation(lbUserId.getText());
            currentUser = listOfUSer.get(indexOnEditing);

            System.out.println(indexOnEditing);
            if (indexOnEditing < 0) {        
                lbErrUserID.setText("* wrong user name");
                lbUserId.clear();
                
                throw new Exception();
            }
            else {
                if (!lbPasswd.getText().equals(listOfUSer.get(indexOnEditing).getPasswd())) {
                    lbErrUserPass.setText("* wrong password");
                    throw new Exception();
                } else {
                    try {
                        FXMLLoader loader = new FXMLLoader();
                        loader.setLocation(getClass().getResource("FXMLDisplay.fxml"));
                        newRoot = FXMLLoader.load(getClass().getResource("FXMLDisplay.fxml"));
                        root = ((Node) event.getSource()).getScene().getRoot();
                        ((Node) event.getSource()).getScene().setRoot(newRoot);

                        FXMLDisplay = loader.getController();
                        FXMLDisplay.setFXMLDoc(this);

                    } catch (IOException ex) {
                        Logger.getLogger(FXMLDocumentController.class.getName()).log(Level.SEVERE, null, ex);
                    }

                }
            }
        } catch (Exception e) {
            System.out.println("User checking error");
        }
    }

    @FXML
    private void handleButtonRegister(ActionEvent event) {
        try {
            newRoot = FXMLLoader.load(getClass().getResource("FXMLRegister.fxml"));
            root = ((Node) event.getSource()).getScene().getRoot();
            ((Node) event.getSource()).getScene().setRoot(newRoot);
        } catch (IOException ex) {
            Logger.getLogger(FXMLDocumentController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void checkEmptyEnter(TextField input, Label error) throws Exception {
        if (input.getText().isEmpty()) {
            error.setText("* field required");
            throw new Exception();
        }
    }
}
