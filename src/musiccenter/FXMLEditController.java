package musiccenter;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URL;
import java.util.ArrayList;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;
import javafx.stage.FileChooser;
import javax.imageio.ImageIO;

public class FXMLEditController implements Initializable {

    @FXML
    private VBox btnUpload;
    @FXML
    private Rectangle recPoster;
    @FXML
    private Button btnUpPoster;
    @FXML
    private Label lbIdErr;
    @FXML
    private Label lblUser;
    @FXML
    private Label lbNameErr;
    @FXML
    private Label lbSingerErr;
    @FXML
    private ComboBox<GENRES> cbCate;
    @FXML
    private TextField tfId;
    @FXML
    private TextField tfName;
    @FXML
    private TextField tfSinger;
    @FXML
    private TextField tfLink;
    @FXML
    private Button btnEdit;
    @FXML
    private Label lbCateErr;
    @FXML
    private Button btnDelete;
    File picture = null;
    Image importImage;

    private static ArrayList<music> listOfAll = new ArrayList<>();
    ;
    @FXML
    private static int indexOnEditing;
    //Controller
    private static FXMLShowController FXMLShow;

    public void setFXMLShowController(FXMLShowController FXMLShow) {
        this.FXMLShow = FXMLShow;
    }
    private static FXMLDisplayController FXMLdisplayFromEdit;

    public void setFXMLDisplayController(FXMLDisplayController FXMLdisplayFromEdit) {
        this.FXMLdisplayFromEdit = FXMLdisplayFromEdit;
    }
    //

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            // get List of all Song
            read_and_write_musicFile.getListOfMusic(listOfAll);
        } catch (FileNotFoundException ex) {
            Logger.getLogger(FXMLEditController.class.getName()).log(Level.SEVERE, null, ex);
        }

        indexOnEditing = FXMLShow.getIndexOnEditing();

        // fill up with current informations
        tfId.setText(FXMLdisplayFromEdit.getCurrentSong().getId());
        tfId.setDisable(true);
        cbCate.getSelectionModel().select(FXMLdisplayFromEdit.getCurrentSong().getGenres());
        cbCate.setDisable(true);
        tfName.setText(FXMLdisplayFromEdit.getCurrentSong().getName());
        tfSinger.setText(FXMLdisplayFromEdit.getCurrentSong().getSinger());
        tfLink.setText(FXMLdisplayFromEdit.getCurrentSong().getLink());
        lblUser.setText("uploaded by :" + FXMLdisplayFromEdit.getCurrentSong().getUploader());
        cbCate.getItems().setAll(GENRES.values());
        picture = FXMLdisplayFromEdit.getCurrentSong().getPoster();
        ImageView myImage = new ImageView();
        try {
            BufferedImage folderImage = ImageIO.read(FXMLdisplayFromEdit.getCurrentSong().getPoster());
            Image importImage = SwingFXUtils.toFXImage(folderImage, null);
            myImage.setImage(importImage);
            recPoster.setFill(new ImagePattern(importImage));
        } catch (IOException ex) {
            System.out.println(ex);
        }
        // 

    }

    @FXML
    private void handleButtonUpload(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        picture = fileChooser.showOpenDialog(null);

        ImageView myImageView = new ImageView();
        try {
            BufferedImage bufferedImage = ImageIO.read(picture);
            importImage = SwingFXUtils.toFXImage(bufferedImage, null);
            myImageView.setImage(importImage);
            recPoster.setFill(new ImagePattern(importImage));

        } catch (IOException ex) {
            System.out.println(ex);
        }
    }

    @FXML
    private void handleButtonEdit(ActionEvent event) {
        try {
            checkEmptyEnter(tfName, lbNameErr);
            checkEmptyEnter(tfSinger, lbSingerErr);

            music newMusic = new music(FXMLdisplayFromEdit.getCurrentSong().getId(),tfName.getText(),
            tfSinger.getText(),cbCate.getValue(),picture,tfLink.getText(),FXMLdisplayFromEdit.getCurrentSong().getUploader());
            // replace with new object           
            FXMLdisplayFromEdit.getDisplayAllList().set(indexOnEditing,newMusic);                    
            // overwrite the file
            read_and_write_musicFile.overwriteUserFile(FXMLdisplayFromEdit.getDisplayAllList());
            
            int index =0;
            switch (FXMLdisplayFromEdit.getCurrentSong().getGenres()) {
                case ROCK:
                    index = checkIndex(FXMLdisplayFromEdit.getRockList(),tfId.getText());
                    FXMLdisplayFromEdit.getRockList().remove(index);
                    FXMLdisplayFromEdit.getRockList().add(newMusic);
                    break;
                case FOLK:
                    index = checkIndex(FXMLdisplayFromEdit.getFolkList(),tfId.getText());
                    FXMLdisplayFromEdit.getFolkList().remove(index);
                    FXMLdisplayFromEdit.getFolkList().add(newMusic);
                    break;
                case COUNTRY:
                    index = checkIndex(FXMLdisplayFromEdit.getCountryList(),tfId.getText());
                    FXMLdisplayFromEdit.getCountryList().remove(index);
                    FXMLdisplayFromEdit.getCountryList().add(newMusic);
                    break;
                case POP:
                    index = checkIndex(FXMLdisplayFromEdit.getPopList(),tfId.getText());
                    FXMLdisplayFromEdit.getPopList().remove(index);
                    FXMLdisplayFromEdit.getPopList().add(newMusic);
                    break;
                case CLASSICAL:
                    index = checkIndex(FXMLdisplayFromEdit.getClassicalList(),tfId.getText());
                    FXMLdisplayFromEdit.getClassicalList().remove(index);
                    FXMLdisplayFromEdit.getClassicalList().add(newMusic);
                    break;
                case ORCHESTA:
                    index = checkIndex(FXMLdisplayFromEdit.getOrchestaList(),tfId.getText());
                    FXMLdisplayFromEdit.getOrchestaList().remove(index);
                    FXMLdisplayFromEdit.getOrchestaList().add(newMusic);
                    break;
            }
            
            
            
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setContentText("Update successfully");
            alert.showAndWait();
            throw new Exception();

        } catch (Exception e) {
            e.getCause();
        }

    }

    @FXML
    private void handleButtonDelete(ActionEvent event) {
        Alert alertDel = new Alert(Alert.AlertType.CONFIRMATION);
        alertDel.setContentText("Are you sure you want to delete?");
        Optional<ButtonType> resultDel = alertDel.showAndWait();
        if (resultDel.get() == ButtonType.OK) {
            FXMLdisplayFromEdit.getDisplayAllList().remove(indexOnEditing);
            int index =0;
            switch (FXMLdisplayFromEdit.getCurrentSong().getGenres()) {
                case ROCK:
                    index = checkIndex(FXMLdisplayFromEdit.getRockList(),tfId.getText());
                    FXMLdisplayFromEdit.getRockList().remove(index);
                    break;
                case FOLK:
                    index = checkIndex(FXMLdisplayFromEdit.getFolkList(),tfId.getText());
                    FXMLdisplayFromEdit.getFolkList().remove(index);
                    break;
                case COUNTRY:
                    index = checkIndex(FXMLdisplayFromEdit.getCountryList(),tfId.getText());
                    FXMLdisplayFromEdit.getCountryList().remove(index);
                    break;
                case POP:
                    index = checkIndex(FXMLdisplayFromEdit.getPopList(),tfId.getText());
                    FXMLdisplayFromEdit.getPopList().remove(index);
                    break;
                case CLASSICAL:
                    index = checkIndex(FXMLdisplayFromEdit.getClassicalList(),tfId.getText());
                    FXMLdisplayFromEdit.getClassicalList().remove(index);
                    break;
                case ORCHESTA:
                    index = checkIndex(FXMLdisplayFromEdit.getOrchestaList(),tfId.getText());
                    FXMLdisplayFromEdit.getOrchestaList().remove(index);
                    break;
            }
            
            // overwrite the file
            read_and_write_musicFile.overwriteUserFile(FXMLdisplayFromEdit.getDisplayAllList());
            
            
            
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setContentText("Update successfully");
            alert.showAndWait();
            btnDelete.setDisable(true);
     
        } else if (resultDel.get() == ButtonType.CANCEL) {
            alertDel.onCloseRequestProperty();
        }

    }

    // check invalid input
    public void checkEmptyEnter(TextField input, Label error) throws Exception {
        if (input.getText().isEmpty()) {
            error.setText("* field required");
            throw new Exception();
        }
    }
    
    private static int checkIndex(ArrayList<music> list, String id){
        int index = 0;
        for(int x =0; x < list.size(); x++){
            if(list.get(x).getId().equals(id)){
                index = x ;
            }
        }
               return index;
    }
}
