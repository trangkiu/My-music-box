
package musiccenter;

import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;


public class handleUserEnter {
        // check empty input
        public void checkEmptyEnter(TextField input,Label error ) throws Exception{
        if(input.getText().isEmpty()){
            error.setText("* field required");
             throw new Exception();
        }
    }
        public void checkEmptyEnter(PasswordField input,Label error ) throws Exception{
        if(input.getText().isEmpty()){
            error.setText("* field required");
             throw new Exception();
        }
    }
}
