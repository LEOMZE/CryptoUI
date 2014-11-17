package main;

import cast5.Cast_128;

import javax.xml.bind.DatatypeConverter;
import java.security.InvalidKeyException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CastWrapper{

    private Cast_128 cast_128 = new Cast_128();

    public String decrypt(String msg, String key) {
        System.out.println(" MSG: \n" + msg);
        for(byte b: msg.getBytes()){
            System.out.print(Integer.toHexString(b&0xff) + " ");
        }

        byte[] out = new byte[8];
        byte[] part;
        ArrayList<Byte> ar = new ArrayList<>();

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

                out = cast_128.decrypt(part, 0, out, 0, cast_128.makeKey(key.getBytes(), 8), 8);
                for(byte b: out){
                    if (((int) b & 0xff) < 0x10) {

                        stringBuffer.append("0");
                    }
                    ar.add(b);
                    stringBuffer.append(Integer.toHexString(b&0xff) + " ");
                }
            }
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        }
        System.out.print(" to decr ar: \n");
        for(byte b : ar){
            System.out.print(Integer.toHexString(b & 0xff) + " ");
        }
        System.out.println("Decrypt msg:\n" + stringBuffer.toString());

        return stringBuffer.toString().toUpperCase();

    }

    private static byte[] toByteArray(String s) {
        return DatatypeConverter.parseHexBinary(s);
    }

    public String encrypt(String dMsg, String key){
        ArrayList<Byte> ar = new ArrayList<>();
        System.out.println("Dcr msg: \n" + dMsg);
        byte[] out = new byte[8];
        byte[] part;
        String decryptMsg = "";
        System.out.println("without space:\n " + dMsg.replace(" ", ""));
        byte[] byteArray = toByteArray(dMsg.replace(" ", ""));
        System.out.print(" In arr: \n");
        for(byte b : byteArray){
            System.out.print(Integer.toHexString(b&0xff) + " ");
        }
        System.out.println();

        try {
            for(int i = 0; i < byteArray.length; i += 8){
                part = Arrays.copyOfRange(byteArray, i, i + 8);
//                decryptMsg += new String(cast_128.encrypt(part, 0, out, 0, cast_128.makeKey(key.getBytes(), 8), 8) /*"cp1251"*/);
                for(byte b : cast_128.encrypt(part, 0, out, 0, cast_128.makeKey(key.getBytes(), 8), 8) ){
                    ar.add(b);
                }
            }
//            System.out.println(decryptMsg);
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        }

        byte[] data = new byte[ar.size()];
        for (int i = 0; i < data.length; i++) {
            data[i] = (byte) ar.get(i);
        }
        decryptMsg = new String(data);

        System.out.println("decr msg to arr: \n");
        for(byte b: ar){
            System.out.print(Integer.toHexString(b & 0xff) + " ");
        }
        System.out.println();
        return decryptMsg;

    }

    private static String repeat(String string, int times) {
        StringBuilder out = new StringBuilder();
        while (times-- > 0) {
            out.append(string);
        }
        return out.toString();
    }

    public ArrayList<Integer> getAEffect(String msg, String key){
        byte[] b = Arrays.copyOfRange(msg.getBytes(), 0, 8);
        System.out.println(new String(b));
        ArrayList<byte[]> arrayOrig;
        ArrayList<byte[]> arrayEx;
        ArrayList<Integer> ddd = new ArrayList<>();
        ArrayList<ArrayList<Integer>> dots = new ArrayList<>();
        ArrayList<Integer> dotsD = new ArrayList<>();

        try {
            arrayOrig =  cast_128.avalanche_effect(b, 0, cast_128.makeKey(key.getBytes(), 8), 8);
            for(int i = 0; i < arrayOrig.size(); i++){
                for(int j = 0; j < 7; j++){
                    for(int k = 0; k < 7; k++){
                        arrayEx = cast_128.avalanche_effect(changeElement(b, j, k), 0, cast_128.makeKey(key.getBytes(), 8), 8);
                        dots.add(returnD(arrayOrig, arrayEx));
                    }
                }

            }
            for(int i = 0; i < dots.get(i).size(); i++ ){
                int m = 0;
                for(int l = 0; l < dots.size(); l++){
                    m += dots.get(l).get(i);
                }
                ddd.add(m/dots.get(i).size());
            }




        } catch (InvalidKeyException e) {
            e.printStackTrace();
        }

        return ddd;
    }

    private Double calculateAverage(List<Integer> marks) {
        Double sum = 0.0;
        if(!marks.isEmpty()) {
            for (Integer mark : marks) {
                sum += mark;
            }
            return sum.doubleValue() / marks.size();
        }
        return sum;
    }

    private byte[] changeElement(byte[] b, int index, int byteIndex){

        for(int i = 0; i < b.length; i++){
            if(i == byteIndex){
                if((b[i] & (1 << index)) == 1){
                    b[i] &=  1 << index;
                }else{
                    b[i] |= 1 << index;
                }
            }
        }

        return b;
    }

    private ArrayList<Integer> returnD(ArrayList<byte[]> a, ArrayList<byte[]> b){
        ArrayList<Integer> arrayD = new ArrayList<>();//cравнение двух строк

        String str = new String();
        int d;
        int s = 0;
        int aByte;
        int bByte;


        for(int i = 0; i < a.size(); i++){
            for(int j = 0; j < a.get(i).length; j++){
                aByte = a.get(i)[j] & 0xff;
                bByte = b.get(i)[j] & 0xff;
                str += Integer.toBinaryString(aByte ^ bByte);
                s = a.get(i)[j] ^ b.get(i)[j];
            }
            d = str.replace("0", "").length();
            arrayD.add(d);
            str = new String();
        }

        return arrayD;

    }
}




