package UI;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import main.CastWrapper;

import java.net.URL;
import java.util.ArrayList;
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

    @FXML
    LineChart<Number, Double> graph;

    @FXML
    Button btnEffect;

    @FXML
    Button clearBtnD;

    @FXML
    Button clearBtnE;

    @FXML
    Button cte;




    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        keyDText.addEventFilter(KeyEvent.KEY_TYPED , letter_Validation(8));
        keyEText.addEventFilter(KeyEvent.KEY_TYPED , letter_Validation(8));
        encryptDText.setWrapText(true);
        encryptEText.setWrapText(true);
        decryptDText.setWrapText(true);
        decryptEText.setWrapText(true);
        graph.getStyleClass().add("thick-chart");
        graph.setCreateSymbols(false);

    }

    @FXML
    private void setBtnEffect(ActionEvent event){

        ArrayList<Integer> arrayMsg = new ArrayList<>();
        ArrayList<Integer> arrayKey = new ArrayList<>();

        ObservableList<XYChart.Series<Number, Double>> lineChartData = FXCollections.observableArrayList();

        LineChart.Series<Number, Double> series1 = new LineChart.Series<Number, Double>();
        LineChart.Series<Number, Double> series2 = new LineChart.Series<Number, Double>();
        series1.setName("Avalanche Effect of Message");
        series2.setName("Avalanche Effect of Key");



        if(encryptDText.getText().length() != 0 & keyDText.getText().length() != 0){
            arrayMsg =  new CastWrapper().getAEffectMsg(encryptDText.getText(), keyDText.getText());
            arrayKey = new CastWrapper().getAEffectKey(encryptDText.getText(), keyDText.getText());
            for(int i=0; i<arrayMsg.size(); i++){
                series1.getData().add(new XYChart.Data<Number, Double>(i, (double) arrayMsg.get(i).intValue()/64));
                System.out.print(arrayMsg.get(i).intValue() + " ");
            }
            for(int i = 0; i < arrayKey.size(); i++){
                series2.getData().add(new XYChart.Data<Number,Double>(i,(double) arrayKey.get(i).intValue()/64));
                System.out.print(arrayKey.get(i).intValue() + " ");
            }
        }


        lineChartData.add(series1);
        lineChartData.add(series2);

        graph.setData(lineChartData);
        graph.createSymbolsProperty();
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
    private void clearAllDBtn(ActionEvent event){
        encryptDText.setText("");
        keyDText.setText("");
    }

    @FXML
    private void clearAllEBtn(ActionEvent event){
        decryptEText.setText("");
        keyEText.setText("");
    }

    @FXML
    private void copyBtn(ActionEvent event){
        decryptEText.setText(decryptDText.getText());
        keyEText.setText(keyDText.getText());
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
