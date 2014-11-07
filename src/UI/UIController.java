package UI;

import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import main.CastWrapper;


import java.net.URL;
import java.util.ResourceBundle;

public class UIController implements Initializable {

    @FXML
    private Button decryptButton;

    @FXML
    private TextArea encryptDText;

    @FXML
    private TextArea decryptDText;

    @FXML
    private TextField keyDText;


    @FXML
    private Button encryptButton;

    @FXML
    private TextArea encryptEText;

    @FXML
    private TextArea decryptEText;

    @FXML
    private TextField keyEText;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        keyDText.addEventFilter(KeyEvent.KEY_TYPED , letter_Validation(8));
        keyEText.addEventFilter(KeyEvent.KEY_TYPED , letter_Validation(8));

    }


    @FXML
    private void btnEncrypt(ActionEvent event){
        String key = keyEText.getText();
        String text = decryptEText.getText();

        if(key.length() != 0 && text.length() != 0){
            CastWrapper castWrapper = new CastWrapper();
            String decrText = castWrapper.encrypt(text, key);
            System.out.println(decrText);
            encryptEText.setText(decrText);
        }
    }

    @FXML
    private void btnDecrypt(ActionEvent event){
        String key = keyDText.getText();
        String text = encryptDText.getText();

        if(key.length() != 0 && text.length() != 0){
            CastWrapper castWrapper = new CastWrapper();
            String encrText = castWrapper.decrypt(text, key);
            System.out.println(encrText);
            decryptDText.setText(encrText);
        }
    }




    public EventHandler letter_Validation(final Integer max_Lengh) {
        return new EventHandler() {
            @Override
            public void handle(Event e) {
                KeyEvent keyEvent = (KeyEvent) e;
                TextField txt_TextField = (TextField) e.getSource();
                if (txt_TextField.getText().length() >= max_Lengh) {
                    keyEvent.consume();
                }
                if(keyEvent.getCharacter().matches("[A-Za-zА-Яа-я0-9]")){
                }else{
                    keyEvent.consume();
                }
            }
        };
    }



}
