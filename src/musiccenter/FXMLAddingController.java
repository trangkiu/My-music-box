package musiccenter;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;
import javafx.stage.FileChooser;
import javax.imageio.ImageIO;

public class FXMLAddingController implements Initializable {

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
    private Label lbCateErr;
    @FXML
    private Label lbSingerErr;
    @FXML
    private ComboBox<GENRES> cbCate;
    @FXML
    private TextField tfId;
    @FXML
    private TextField tfName;
    @FXML
    private Button btnCreate;
    @FXML
    private TextField tfSinger;
    @FXML
    private TextField tfLink;

    // controller
    private FXMLDisplayController FXMLDisplay;

    public void setFXMLDisplayController(FXMLDisplayController FXMLDisplay) {
        this.FXMLDisplay = FXMLDisplay;
    }
    //

    private static ArrayList<music> listOfMusic = new ArrayList<>();
    private static ArrayList<String> listOfId = new ArrayList<>();

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        cbCate.getItems().setAll(GENRES.values());
        lblUser.setText("Uploaded by : " + FXMLDisplay.getUserName());
        try {
            // get the list of music
            read_and_write_musicFile.getListOfMusic(listOfMusic);
        } catch (FileNotFoundException ex) {
            Logger.getLogger(FXMLAddingController.class.getName()).log(Level.SEVERE, null, ex);
        }

        // get list of ID
        for (int x = 0; x < listOfMusic.size(); x++) {
            listOfId.add(listOfMusic.get(x).getId());
        }

    }

    File picture = null;
    Image importImage;

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
    private void handleButtonCreate(ActionEvent event) {
        try {
            checkEmptyEnter(tfId, lbIdErr);
            checkEmptyEnter(tfName, lbNameErr);
            checkEmptyEnter(tfSinger, lbSingerErr);
            if (cbCate.getSelectionModel().getSelectedIndex() < 0) {
                lbCateErr.setText("* Category required");
                throw new Exception();
            }

            // check duplicated id
            for (int x = 0; x < listOfId.size(); x++) {
                if (tfId.getText().equals(listOfId.get(x))) {
                    lbIdErr.setText("Id already exist ");
                    throw new Exception();
                }
            }
            // end check id
            // create new music object

            music newSong = new music(tfId.getText(), tfName.getText(), tfSinger.getText(),
                    cbCate.getValue(), picture, tfLink.getText(), FXMLDisplay.getUserName());
            

            // add into file and Display controller
            // add into current id List because if user add two song at the same time, it couldn't check the newest song
            listOfId.add(tfId.getText());
            // add into file
            read_and_write_musicFile.appendMusicFile(newSong);
            // add into Display controller
            FXMLDisplay.getDisplayAllList().add(newSong);
//             add into coresponding category 
            switch (cbCate.getValue()) {
                case ROCK:
                    FXMLDisplay.getRockList().add(newSong);
                    break;
                case FOLK:
                    FXMLDisplay.getFolkList().add(newSong);
                    break;
                case COUNTRY:
                    FXMLDisplay.getCountryList().add(newSong);
                    break;
                case POP:
                    FXMLDisplay.getPopList().add(newSong);
                    break;
                case CLASSICAL:
                    FXMLDisplay.getClassicalList().add(newSong);
                    break;
                case ORCHESTA:
                    FXMLDisplay.getOrchestaList().add(newSong);
                    break;
            }
            //


            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setContentText("Upload successfully");
            alert.showAndWait();

        } catch (Exception e) {
            e.getCause();

        }
            tfId.setText(null);
            tfName.setText(null);
            tfSinger.setText(null);
            tfLink.setText(null);
    }

    // check invalid input
    public void checkEmptyEnter(TextField input, Label error) throws Exception {
        if (input.getText().isEmpty()) {
            error.setText("* field required");
            throw new Exception();
        }
    }
}
