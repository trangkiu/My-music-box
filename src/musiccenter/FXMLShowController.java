package musiccenter;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.web.WebView;
import javafx.stage.Stage;

public class FXMLShowController implements Initializable {
    // Control

    @FXML
    private WebView wvScreen;
    @FXML
    private Label lbUploader;
    @FXML
    private Label lbName;
    @FXML
    private Label lbSinger;
    @FXML
    private Button btnAddFav;
    @FXML
    private Button btnEdit;
    @FXML
    private Label lbComment;
    @FXML
    private TextField tfComment;
    @FXML
    private Button btnComment;

    private static int indexOnEditing;

    public static int getIndexOnEditing() {
        return indexOnEditing;
    }

    //
    private FXMLDisplayController FXMLDisplay;

    public void setFXMLDisplayController(FXMLDisplayController FXMLDisplay) {
        this.FXMLDisplay = FXMLDisplay;
    }

    private FXMLEditController FXMLEdit;

    private FXMLDocumentController FXMLDoc;

    // 
    
    private static ArrayList<userFav> favorite = new ArrayList<>();

    @Override
    public void initialize(URL url, ResourceBundle rb) {

        indexOnEditing = FXMLDisplay.getIndexOnEditing();
        System.out.println("index in show" + indexOnEditing);

        wvScreen.getEngine().load(FXMLDisplay.getCurrentSong().getLink());

        lbName.setText(FXMLDisplay.getCurrentSong().getName());
        lbSinger.setText("- " + FXMLDisplay.getCurrentSong().getSinger());
        lbUploader.setText("uploaded by: " + FXMLDisplay.getCurrentSong().getUploader());

                try {
            // loop throught userFav, get the whole list
            read_and_write_FavFile.getListOfFav(favorite);
        } catch (FileNotFoundException ex) {
            Logger.getLogger(FXMLShowController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void handleButtonFav(ActionEvent event) {
        boolean songExist = false;
        for (int x = 0; x < FXMLDisplay.getFavoriteList().size(); x++) {
            if (FXMLDisplay.getCurrentSong().getId().equals(FXMLDisplay.getFavoriteList().get(x).getId())) {
                songExist = true;
                break;
            }
        }
        try{
        if (songExist == false) {
            FXMLDisplay.getFavoriteList().add(FXMLDisplay.getCurrentSong());
            userFav newRecord = new userFav(FXMLDoc.getCurrentUser().getUserId(), FXMLDisplay.getCurrentSong().getId());
            read_and_write_FavFile.appendUserFavFile(newRecord);
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setContentText("Adding successfully");
            alert.showAndWait();
        } else {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Infomation");
            Label lb = new Label("This song already in your list");
            alert.getDialogPane().setContent(lb);
            ButtonType delete = new ButtonType("Delete");
            ButtonType ok = new ButtonType("ok");
            alert.getButtonTypes().clear();
            alert.getButtonTypes().addAll(delete, ok);
            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == delete) {
                
                FXMLDisplay.getFavoriteList().remove(FXMLDisplay.getCurrentSong());
                
                int index =0;
                for(int x =0; x< favorite.size();x++){
                    System.out.println("index fav :" + index);
                    if(favorite.get(x).getMusicId().equals(FXMLDisplay.getCurrentSong().getId())
                            && favorite.get(x).getUserName().equals(FXMLDoc.getCurrentUser().getUserId())){
                        index =x;
                        System.out.println("index fav :" + index);
                        break;
                    }               
                        }              
                favorite.remove(index);
                
                read_and_write_FavFile.overwriteUserFavFile(favorite);
                     alert.onCloseRequestProperty();          
            } else {
                alert.onCloseRequestProperty();
            }
        }
        }catch (Exception e){
            System.out.println(e);
        }

    }

    @FXML
    private void handleButtonEdit(ActionEvent event) throws IOException {
        if (FXMLDoc.getCurrentUser().getUserId().equals(FXMLDisplay.getCurrentSong().getUploader())) {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("FXMLEdit.fxml"));
            Parent root = (Parent) loader.load();
            Scene scene = new Scene(root, 450, 580);
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.setResizable(false);
            stage.show();

            FXMLEdit = loader.getController();
            FXMLEdit.setFXMLShowController(this);

            btnEdit.setDisable(true);
            stage.setOnCloseRequest(e -> {
                btnEdit.setDisable(false);
            });
        } else {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setContentText("No permission");
            alert.showAndWait();
        }
    }



}
