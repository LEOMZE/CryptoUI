package main;

import cast5.Cast_128;
import java.io.*;
import java.security.InvalidKeyException;

public class CastWrapper{

    private Cast_128 cast_128 = new Cast_128();

    public String decrypt(String msg, String key) {
        System.out.println(msg);

        String string = new String();
        for(byte b: msg.getBytes()){
            string += " " + Integer.toHexString(b&0xff);
        }
        System.out.println(string);

        byte[] out = new byte[8];
        byte[] part;

        StringBuffer stringBuffer = new StringBuffer();
        String crypt = new String();
        try {
            for(int i = 0; i < msg.getBytes().length; i += 8){
                if((i + 8) > msg.length()){
                    part = (msg.substring(i, msg.length()) + repeat(" " , msg.length() - i)).getBytes() ;
                }else{
                    part = msg.substring(i, i + 8).getBytes();
                }

                for (byte b: cast_128.decrypt(part, 0, out, 0, cast_128.makeKey(key.getBytes(), 8), 8)){
                    stringBuffer.append(" " + Integer.toHexString(b&0xff));
                }
                crypt += new String(cast_128.decrypt(part, 0, out, 0, cast_128.makeKey(key.getBytes(), 8), 8));

            }
            System.out.println(stringBuffer.toString());
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        }

        return crypt;

    }

    public String encrypt(String dMsg, String key){
        System.out.println(dMsg);
        StringBuffer stringBuffer = new StringBuffer();
        byte[] out = new byte[8];
        byte[] part;
        String msg = new String();
        try {
            for(int i = 0; i < dMsg.getBytes().length; i += 8){
                part = dMsg.substring(i, i + 8).getBytes();
                for (byte b: cast_128.encrypt(part, 0, out, 0, cast_128.makeKey(key.getBytes(), 8), 8)){
                    stringBuffer.append(" " + Integer.toHexString(b&0xff));
                }
                msg += new String(cast_128.encrypt(part, 0, out, 0, cast_128.makeKey(key.getBytes(), 8), 8));

            }
            System.out.println(stringBuffer.toString());
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        }
        return msg;

    }


    public static String repeat(String string, int times) {
        StringBuilder out = new StringBuilder();
        while (times-- > 0) {
            out.append(string);
        }
        return out.toString();
    }


}

//try {
//while(br.read(part) > -1){
//        msg += new String( cast_128.encrypt(part, 0, out, 0, cast_128.makeKey(key.getBytes(), 8), 8));
//        }
//        System.out.println(msg);
//        br.close();
//
//        } catch (InvalidKeyException e) {
//        e.printStackTrace();
//        } catch (IOException e) {
//        e.printStackTrace();
//        }
//
//try {
//        while (br.read(part) > -1){
//        for (byte b: cast_128.decrypt(part, 0, out, 0, cast_128.makeKey(key.getBytes(), 8), 8)){
//        stringBuffer.append(" " + Integer.toHexString(b&0xff));
//        }
//        crypt += new String(cast_128.decrypt(part, 0, out, 0, cast_128.makeKey(key.getBytes(), 8), 8));
//        part = new byte[8];
//        out = new byte[8];
//        }
//        System.out.println(crypt);
////            for(byte b:crypt.getBytes()){
////                stringBuffer.append(" " + Integer.toHexString(b&0xff));
////            }
//        System.out.println("Decrypt array from decryptFoo: \n" + stringBuffer);
//        br.close();
//        } catch (IOException e) {
//        e.printStackTrace();
//        } catch (InvalidKeyException e) {
//        e.printStackTrace();
//        }
