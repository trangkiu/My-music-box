package musiccenter;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;

/**
 *
 * @author nguye
 */
public class List_of_user {

    private static File userFile = new File("user.csv");
    private static PrintWriter output = null;
    
    public File getFile() {
        return userFile;
    }
    // read file into listOfUSer
    public static void getListOfUser(ArrayList<user> listOfUSer) throws FileNotFoundException {
        Scanner fileInput = null;
        try {
            fileInput = new Scanner(userFile);
            while (fileInput.hasNext()) {
                String record = fileInput.next();
                String[] field = record.split(",");
                listOfUSer.add(new user(field[0], field[1]));
            }
        } catch (FileNotFoundException e) {
            System.out.println("File cant be readed");
        } finally {
            if (fileInput != null) {
                fileInput.close();
            }
        }
    }
    // end read file
    
    
    // append into file
    public static void appendUserFile(String name, String passwd) {
        FileWriter fw = null;
       try {
            fw = new FileWriter(userFile,true);
            BufferedWriter bf = new BufferedWriter(fw);
            output = new PrintWriter(bf);
            output.println(name + "," + passwd);
        } catch (IOException e) {
            System.out.println("Error in save user to file" + e);
        } finally {
            if (output != null) {
                output.close();
            }
        }
    }
    
    
    // overwrite a list into file
    public static void overwriteUserFile(ArrayList<user> listUser) {
            try {               
                output = new PrintWriter(userFile);                
                for(user user : listUser){
           output.append(user.getUserId()+","+user.getPasswd()+"\n");
                }
            } catch (IOException e) {
                System.out.println("Error" + e);
            } finally {
                if (output != null) {
                    output.close();
                }
            }          
    }
    
    // check name validation return index
    public static int checkUserValidation(String name) throws FileNotFoundException{
        int userIndex = -1;
         ArrayList<user> listOfUSer = new ArrayList<>();
         getListOfUser(listOfUSer);
        for(int x =0; x < listOfUSer.size(); x++){
            if (listOfUSer.get(x).getUserId().equals(name)){
                userIndex = x; // user exists
                break;
            }
        }
        return userIndex;
    }
}// end class
