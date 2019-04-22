
package musiccenter;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;

public class read_and_write_FavFile {
    private static File favFile = new File("fav.csv");
    private static PrintWriter output = null;
    
    public File getFile() {
        return favFile;
    }
    
    // read file into listOfFav
        public static void getListOfFav(ArrayList<userFav> listOfFav) throws FileNotFoundException {
        Scanner fileInput = null;
        try {
            fileInput = new Scanner(favFile);
            while (fileInput.hasNext()) {
                String record = fileInput.next();
                String[] field = record.split(",");
                listOfFav.add(new userFav(field[0], field[1]));
            }
        } catch (FileNotFoundException e) {
            System.out.println("File cant be readed");
        } finally {
            if (fileInput != null) {
                fileInput.close();
            }
        }
    }
        
        
    // append into file

       public static void appendUserFavFile(userFav userFav) {
        FileWriter fw = null;
       try {
            fw = new FileWriter(favFile,true);
            BufferedWriter bf = new BufferedWriter(fw);
            output = new PrintWriter(bf);
            output.println(userFav.getUserName() + "," + userFav.getMusicId());
        } catch (IOException e) {
            System.out.println("Error in save user to file" + e);
        } finally {
            if (output != null) {
                output.close();
            }
        }
    }
    
    
    // overwrite a list into file
    public static void overwriteUserFavFile(ArrayList<userFav> listUserFav) {
            try {               
                output = new PrintWriter(favFile);                
                for(userFav user : listUserFav){
           output.append(user.getUserName()+","+user.getMusicId()+"\n");
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
