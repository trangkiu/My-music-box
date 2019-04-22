package musiccenter;

import java.awt.image.BufferedImage;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import javax.imageio.ImageIO;
import static musiccenter.GENRES.*;

public class FXMLDisplayController implements Initializable {

    @FXML
    private Button btnAdd;
    @FXML
    private Label lbUser;
    @FXML
    private Rectangle recPoster;
    @FXML
    private Label lbName;
    @FXML
    private Label lbSinger;
    @FXML
    private Label lbMyList;
    @FXML
    private Label lbViewAll;
    @FXML
    private Label lbCountry;
    @FXML
    private Label lbClassical;
    @FXML
    private Label lbPop;
    @FXML
    private Label lbFolk;
    @FXML
    private Label lbRock;
    @FXML
    private Label lbOrchesta;
    @FXML
    private TableView<music> tbView;

    @FXML
    private TableColumn<music, String> colName;
    @FXML
    private TableColumn<music, String> colSinger;
    @FXML
    private TableColumn<music, GENRES> colGenre;

    private static Parent newRoot;
    private static Parent root;

    private static int indexOnEditing; // current song on editing

    public static int getIndexOnEditing() {
        return indexOnEditing;
    }
    //
    private static String userName = "";

    public static String getUserName() {
        return userName;
    }
    //
    private static music currentSong;

    public static music getCurrentSong() {
        return currentSong;
    }

    // controller variable 
    private static FXMLAddingController FXMLAdd;
    private static FXMLDocumentController FXMLdoc;
    private static FXMLShowController FXMLshow;
    private static FXMLEditController FXMLedit;

    @FXML
    private Button btnView;

    public void setFXMLDoc(FXMLDocumentController FXMLDoc) {
        this.FXMLdoc = FXMLDoc;
    }
    // 
    // ArrayList of all category and favorite
    private static ArrayList<userFav> favorite = new ArrayList<>();
    
    public static ArrayList<userFav> getFavorite() {
        return favorite;
    }
    private static ArrayList<userFav> favoriteOfCurrentUSer = new ArrayList<>();
    
    public static ArrayList<userFav> getFavoriteOfCurrentUSer() {
        return favoriteOfCurrentUSer;
    }
    
    
    private static ArrayList<music> favoriteList = new ArrayList<>();

    public static ArrayList<music> getFavoriteList() {
        return favoriteList;
    }
    //
    private static ArrayList<music> displayAll = new ArrayList<>();

    public static ArrayList<music> getDisplayAllList() {
        return displayAll;
    }
    //
    private static ArrayList<music> countryList = new ArrayList<>();

    public static ArrayList<music> getCountryList() {
        return countryList;
    }
    //
    private static ArrayList<music> classicalList = new ArrayList<>();

    public static ArrayList<music> getClassicalList() {
        return classicalList;
    }
    //
    private static ArrayList<music> popList = new ArrayList<>();

    public static ArrayList<music> getPopList() {
        return popList;
    }
    //
    private static ArrayList<music> folkList = new ArrayList<>();

    public static ArrayList<music> getFolkList() {
        return folkList;
    }
    //
    private static ArrayList<music> rockList = new ArrayList<>();

    public static ArrayList<music> getRockList() {
        return rockList;
    }
    //
    private static ArrayList<music> orchestaList = new ArrayList<>();

    public static ArrayList<music> getOrchestaList() {
        return orchestaList;
    }
    // end arraylist

    // ObservableList
    private ObservableList<music> oListMusic;

    // method to read an arraylist of song into observable list
    public ObservableList<music> getObservableList(ArrayList<music> data) {
        oListMusic = FXCollections.observableArrayList(data);
        return oListMusic;
    }

    // Initialize
    @Override
    public void initialize(URL url, ResourceBundle rb) {

        System.out.println("index in Display" + indexOnEditing);

        // pass user login name from DocumentController       
        userName = FXMLdoc.getCurrentUser().getUserId();
        lbUser.setText("Welcome " + userName + " !");

        try {
            // get List of all Song
            read_and_write_musicFile.getListOfMusic(displayAll);
        } catch (FileNotFoundException ex) {
            Logger.getLogger(FXMLDisplayController.class.getName()).log(Level.SEVERE, null, ex);
        }
        // get list of each category song
        getListForEachGenre(countryList, COUNTRY);
        getListForEachGenre(classicalList, CLASSICAL);
        getListForEachGenre(popList, POP);
        getListForEachGenre(folkList, FOLK);
        getListForEachGenre(rockList, ROCK);
        getListForEachGenre(orchestaList, ORCHESTA);
        // 

        //
        colName.setCellValueFactory(new PropertyValueFactory<music, String>("name"));
        colSinger.setCellValueFactory(new PropertyValueFactory<music, String>("singer"));
        colGenre.setCellValueFactory(new PropertyValueFactory<music, GENRES>("genres"));
        // end table collumn.
        tbView.setItems(getObservableList(displayAll));

        // Add event handle for each label to show 
        //
        EventHandler<MouseEvent> eventHandlerAll = new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent e1) {
                tbView.setItems(getObservableList(displayAll));
            }
        };
        lbViewAll.addEventFilter(MouseEvent.MOUSE_CLICKED, eventHandlerAll);
        //
        EventHandler<MouseEvent> eventHandlerCountry = new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent e1) {
                tbView.setItems(getObservableList(countryList));
            }
        };
        lbCountry.addEventFilter(MouseEvent.MOUSE_CLICKED, eventHandlerCountry);
        //
        EventHandler<MouseEvent> eventHandlerClassical = new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent e1) {
                tbView.setItems(getObservableList(classicalList));
            }
        };
        lbClassical.addEventFilter(MouseEvent.MOUSE_CLICKED, eventHandlerClassical);
        //
        EventHandler<MouseEvent> eventHandlerPop = new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent e1) {
                tbView.setItems(getObservableList(popList));
            }
        };
        lbPop.addEventFilter(MouseEvent.MOUSE_CLICKED, eventHandlerPop);
        //
        EventHandler<MouseEvent> eventHandlerFolk = new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent e1) {
                tbView.setItems(getObservableList(folkList));
            }
        };
        lbFolk.addEventFilter(MouseEvent.MOUSE_CLICKED, eventHandlerFolk);
        //
        EventHandler<MouseEvent> eventHandlerRock = new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent e1) {
                tbView.setItems(getObservableList(rockList));
            }
        };
        lbRock.addEventFilter(MouseEvent.MOUSE_CLICKED, eventHandlerRock);
        //trang
        EventHandler<MouseEvent> eventHandlerOrchesta = new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent e1) {
                tbView.setItems(getObservableList(orchestaList));
            }
        };
        lbOrchesta.addEventFilter(MouseEvent.MOUSE_CLICKED, eventHandlerOrchesta);
        // End event handle for each label

        // on mouse event for display at top
        tbView.setOnMouseClicked(e -> {
            currentSong = tbView.getSelectionModel().getSelectedItem();
            for (int x = 0; x < displayAll.size(); x++) {
                if (displayAll.get(x).getId().equals(currentSong.getId())) {
                    indexOnEditing = x;
                }
            }
            displayOnTop(currentSong);

        });
        
        
        
            // user favorite 
        try {
            // loop throught userFav, get the whole list
            read_and_write_FavFile.getListOfFav(favorite);
        } catch (FileNotFoundException ex) {
            Logger.getLogger(FXMLDisplayController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
            //filter to take only the list for current user
//         for(int x =favorite.size() -1; x>=0; x--){
//             if(!favorite.get(x).getUserName().equals(FXMLdoc.getCurrentUser().getUserId())){
//                 favorite.remove(x);
//             }
//         }   
            
            for(int x = 0; x< favorite.size(); x++){
                if (favorite.get(x).getUserName().equals(FXMLdoc.getCurrentUser().getUserId())){
                    favoriteOfCurrentUSer.add(favorite.get(x));
                }
            }

            // get the songID list of current user
//            for(int x =0; x <favorite.size();x++ ){
//                for( int y =0; y<displayAll.size();y++){
//                    if(favorite.get(x).getMusicId().equals(displayAll.get(y).getId())){
//                        favoriteList.add(displayAll.get(y));
//                    }
//                }               
//            }
       for(int x =0; x <favoriteOfCurrentUSer.size();x++ ){
                for( int y =0; y<displayAll.size();y++){
                    if(favoriteOfCurrentUSer.get(x).getMusicId().equals(displayAll.get(y).getId())){
                        favoriteList.add(displayAll.get(y));
                    }
                }               
            }

        EventHandler<MouseEvent> eventHandlerMyList = new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent e1) {
                tbView.setItems(getObservableList(favoriteList));
            }
        };
        lbMyList.addEventFilter(MouseEvent.MOUSE_CLICKED, eventHandlerMyList);
            

    } // end inutialize

    @FXML
    private void handleButtonAdd(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("FXMLAdding.fxml"));
        Parent root = (Parent) loader.load();
        Scene scene = new Scene(root, 450, 550);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.setResizable(false);
        stage.setTitle("ADD YOUR NEW SONG");
        stage.show();

        FXMLAdd = loader.getController();
        FXMLAdd.setFXMLDisplayController(this);

        btnAdd.setDisable(true);
        stage.setOnCloseRequest(e -> {
            btnAdd.setDisable(false);
        });
    }// end handleButtonAdd

    @FXML
    private void handleButtonShow(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("FXMLShow.fxml"));
        Parent root = (Parent) loader.load();
        Scene scene = new Scene(root, 450, 500);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();

        FXMLshow = loader.getController();
        FXMLshow.setFXMLDisplayController(this);

        btnView.setDisable(true);
        stage.setOnCloseRequest(e -> {
            btnView.setDisable(false);
        });

    }// end handleButtonShow 

    // customize method
    private static ArrayList<music> getListForEachGenre(ArrayList<music> objList, GENRES genres) {
        for (int x = 0; x < displayAll.size(); x++) {
            if (displayAll.get(x).getGenres() == genres) {
                objList.add(displayAll.get(x));
            }
        }
        return objList;
    }

    private void displayOnTop(music music) {
        ImageView myImage = new ImageView();
        try {
            BufferedImage folderImage = ImageIO.read(music.getPoster());
            Image importImage = SwingFXUtils.toFXImage(folderImage, null);
            myImage.setImage(importImage);
            recPoster.setFill(new ImagePattern(importImage));
        } catch (IOException ex) {
            System.out.println(ex);
        }

        lbName.setText(music.getName());
        lbSinger.setText(music.getSinger());
    }

    @FXML
    private void handleButtonLogOut(ActionEvent event) {
         try {
            newRoot = FXMLLoader.load(getClass().getResource("FXMLDocument.fxml"));
            root = ((Node) event.getSource()).getScene().getRoot();
            ((Node) event.getSource()).getScene().setRoot(newRoot);
        } catch (IOException ex) {
            Logger.getLogger(FXMLDisplayController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        
    }

}
