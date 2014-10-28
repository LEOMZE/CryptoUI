package main;

import cast5.Cast_128;
import com.sun.deploy.util.StringUtils;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.security.InvalidKeyException;
import java.util.Scanner;

public class Main extends Application{

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));
        primaryStage.setTitle("Hello World");
        primaryStage.setWidth(630);
        primaryStage.setHeight(430);
        primaryStage.setResizable(false);
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
    }

    public static void main(String[] args) {
        //        launch(args);
        String str = "Hello wolrd!";
        String key = "peeepeeepeeeepee";
        CastWrapper castWrapper = new CastWrapper();
        String decrypt = castWrapper.decrypt(str, key);
        castWrapper.encrypt(decrypt, key);



    }
//    public static String repeat(String string, int times) {
//        StringBuilder out = new StringBuilder();
//        while (times-- > 0) {
//            out.append(string);
//        }
//        return out.toString();
//    }
//
//
//    static {
//        Cast_128 cast_128 = new Cast_128();
//        Scanner scanner = new Scanner(System.in);
//        String string = scanner.nextLine();
//        String key = scanner.nextLine();
//        byte[] out;
//        if(string.length() >= 8){
//            out = new byte[string.length()];
//        }else{
//            out = new byte[string.length() + (8 - string.length())];
//            string += repeat(" ", string.length() + (8 - string.length()));
//        }
//
//        StringBuffer stringBuffer = new StringBuffer();
//        try {
//            byte[] decryptArray = cast_128.decrypt(string.getBytes(), 0, out, 0, cast_128.makeKey(key.getBytes(), 8), 8);
//            for(byte b: decryptArray){
//                stringBuffer.append(" " + Integer.toHexString(b&0xff));
//            }
//            System.out.println(new String(decryptArray));
//            System.out.println(stringBuffer.toString());
//            stringBuffer = new StringBuffer();
//
//            byte[] encryptArray = cast_128.encrypt(decryptArray, 0, out, 0, cast_128.makeKey(key.getBytes(), 8), 8);
//            System.out.println(new String(encryptArray).trim());
//            for(byte b: encryptArray){
//                stringBuffer.append(" " + Integer.toHexString(b&0xff));
//            }
//            System.out.println(stringBuffer.toString());
//            stringBuffer = new StringBuffer();
//
//        } catch (InvalidKeyException e) {
//            e.printStackTrace();
//        }
//
//    }
}
