package musiccenter;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;
import javafx.scene.control.Hyperlink;

public class read_and_write_musicFile {

    private static File musicFile = new File("music.csv");
    private static PrintWriter output = null;

    public static File getFile() {
        return musicFile;
    }

    // read file into listOfMusic
    public static void getListOfMusic(ArrayList<music> listOfMusic) throws FileNotFoundException {
        Scanner fileInput = null;
        String id ="";
        String name="";
        String singer="";
        GENRES genres =null;
        File poster;
        String link;
        String uploader ="";
        String record ="";
        try {
            fileInput = new Scanner(musicFile);
            fileInput.useDelimiter("[\n]");
            while (fileInput.hasNextLine()) {
                record = fileInput.next();
                String[] field = record.split(",");
                id = field[0];
                name = field[1];
                singer = field[2];
                genres = GENRES.valueOf(field[3]);
                poster = new File(field[4]);
                link = field[5];
                uploader = field[6];
                
                listOfMusic.add(new music(id,name ,singer, genres, poster,
                        link,uploader));
            }
        } catch (FileNotFoundException e) {
            System.out.println("File cant be readed");
        } catch (Exception ex){
            System.out.println(record);
        }
        finally {
            if (fileInput != null) {
                fileInput.close();
            }
        }
    }

    // write into file
    // append into file
    public static void appendMusicFile(music musicObj) {
        FileWriter fw = null;
        try {
            fw = new FileWriter(musicFile, true);
            BufferedWriter bf = new BufferedWriter(fw);
            output = new PrintWriter(bf);
            output.append(musicObj.getId() + "," + musicObj.getName() + ","
                    + musicObj.getSinger() + "," + musicObj.getGenres() + "," + musicObj.getPoster()
                    + "," + musicObj.getLink() + "," + musicObj.getUploader() + "\n");
        } catch (IOException e) {
            System.out.println("Error in save user to file" + e);
        } finally {
            if (output != null) {
                output.close();
            }
        }
    }

    // overwrite a list into file
    public static void overwriteUserFile(ArrayList<music> listMusic) {
        try {
            output = new PrintWriter(musicFile);
            for (music musicObj : listMusic) {
                output.append(musicObj.getId() + "," + musicObj.getName() + ","
                        + musicObj.getSinger() + "," + musicObj.getGenres() + "," + musicObj.getPoster()
                        + "," + musicObj.getLink() + "," + musicObj.getUploader() + "\n");
            }
        } catch (IOException e) {
            System.out.println("Error" + e);
        } finally {
            if (output != null) {
                output.close();
            }
        }
    }

}
