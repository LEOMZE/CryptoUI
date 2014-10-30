package main;

import cast5.Cast_128;
import java.io.*;
import java.security.InvalidKeyException;
import java.util.Arrays;

public class CastWrapper{

    private Cast_128 cast_128 = new Cast_128();

    public String decrypt(String msg, String key) {
        System.out.println(msg);

        byte[] out = new byte[8];
        byte[] part;

        StringBuilder stringBuffer = new StringBuilder();
        byte[] msgArray = msg.getBytes();

        try {
            for(int i = 0; i < msgArray.length; i += 8){
                if((i + 8) > msgArray.length){
                    part = new byte[8];
                    int offset = Arrays.copyOfRange(msgArray, i, msgArray.length).length;
                    System.arraycopy( Arrays.copyOfRange(msgArray, i, msgArray.length), 0 , part, 0, Arrays.copyOfRange(msgArray, i, msgArray.length).length);
                    System.arraycopy( repeat(" " , (i + 8) - msgArray.length).getBytes(), 0, part, offset, repeat(" " , (i + 8) - msgArray.length).getBytes().length);
                }else{
                    part = Arrays.copyOfRange(msgArray, i, i + 8);
                }
                for(byte b: cast_128.decrypt(part, 0, out, 0, cast_128.makeKey(key.getBytes(), 8), 8)){
                    stringBuffer.append((char)(b&0xff));
                }
            }
//            System.out.println(stringBuffer.toString());
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        }

        return stringBuffer.toString();

    }

    public String encrypt(String dMsg, String key){
        System.out.println(dMsg);
        byte[] out = new byte[8];
        byte[] part;
        String decryptMsg = "";
        byte[] byteArray = new byte[dMsg.length()];
        for(int i = 0; i < byteArray.length; i++){
            byteArray[i] = (byte) dMsg.charAt(i);
        }

        try {
            for(int i = 0; i < byteArray.length; i += 8){
                part = Arrays.copyOfRange(byteArray, i, i + 8);
                decryptMsg += new String(cast_128.encrypt(part, 0, out, 0, cast_128.makeKey(key.getBytes(), 8), 8));
            }
//            System.out.println(decryptMsg);
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        }
        return decryptMsg;

    }


    public static String repeat(String string, int times) {
        StringBuilder out = new StringBuilder();
        while (times-- > 0) {
            out.append(string);
        }
        return out.toString();
    }


}
