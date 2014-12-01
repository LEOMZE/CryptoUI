package cast5;

import cast5.Cast_128;

import javax.xml.bind.DatatypeConverter;
import java.security.InvalidKeyException;
import java.util.ArrayList;
import java.util.Arrays;

public class CastWrapper{

    private Cast_128 cast_128 = new Cast_128();

    public String decrypt(String msg, String key) {

        ArrayList<Byte> ar = new ArrayList<>();
        byte[] out = new byte[8];
        byte[] part;
        String decryptMsg;
        byte[] byteArray = toByteArray(msg.replace(" ", ""));


        try {
            for(int i = 0; i < byteArray.length; i += 8){
                part = Arrays.copyOfRange(byteArray, i, i + 8);
                for(byte b : cast_128.decrypt(part, 0, out, 0, cast_128.makeKey(key.getBytes(), 8), 8) ){
                    ar.add(b);
                }
            }
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        }

        byte[] data = new byte[ar.size()];
        for (int i = 0; i < data.length; i++) {
            data[i] = ar.get(i);
        }
        decryptMsg = new String(data);
        return decryptMsg;
    }

    private static byte[] toByteArray(String s) {
        return DatatypeConverter.parseHexBinary(s);
    }

    public String encrypt(String msg, String key){

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

                out = cast_128.encrypt(part, 0, out, 0, cast_128.makeKey(key.getBytes(), 8), 8);
                for(byte b: out){
                    if (((int) b & 0xff) < 0x10) {

                        stringBuffer.append("0");
                    }
                    stringBuffer.append(Integer.toHexString(b&0xff) + " ");
                }
            }
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        }
        return stringBuffer.toString().toUpperCase();

    }

    private static String repeat(String string, int times) {
        StringBuilder out = new StringBuilder();
        while (times-- > 0) {
            out.append(string);
        }
        return out.toString();
    }

    public ArrayList<Integer> getAEffectKey(String msg, String key){
        byte[] b = Arrays.copyOfRange(msg.getBytes(), 0, 8);
        byte[] keyArray = Arrays.copyOfRange(key.getBytes(), 0, 8);
        ArrayList<byte[]> arrayOrig;
        ArrayList<byte[]> arrayEx;
        ArrayList<Integer> ddd = new ArrayList<>();
        ArrayList<ArrayList<Integer>> dots = new ArrayList<>();

        try {
            arrayOrig =  cast_128.avalanche_effect(b, 0, cast_128.makeKey(keyArray, 8), 8);
            for(int i = 0; i < arrayOrig.size(); i++){
                for(int j = 0; j < 7/*key.getBytes().length*/; j++){
                    for(int k = 0; k < 7; k++){
                        arrayEx = cast_128.avalanche_effect(b, 0, cast_128.makeKey(changeElement(keyArray, j, k), 8), 8);
                        dots.add(returnD(arrayOrig, arrayEx));
                    }
                }

            }
            for(int i = 0; i < dots.get(i).size(); i++ ){
                int m = 0;
                for(int l = 0; l < dots.size(); l++){
                    m += dots.get(l).get(i);
                }

                ddd.add( m / dots.size() );
            }
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        }

        return ddd;
    }

    public ArrayList<Integer> getAEffectMsg(String msg, String key){
        byte[] b = Arrays.copyOfRange(msg.getBytes(), 0, 8);
        byte[] keyArray = Arrays.copyOfRange(key.getBytes(), 0, 8);
        ArrayList<byte[]> arrayOrig;
        ArrayList<byte[]> arrayEx;
        ArrayList<Integer> ddd = new ArrayList<>();
        ArrayList<ArrayList<Integer>> dots = new ArrayList<>();

        try {
            arrayOrig =  cast_128.avalanche_effect(b, 0, cast_128.makeKey(keyArray, 8), 8);
            for(int i = 0; i < arrayOrig.size(); i++){
                for(int j = 0; j < 7; j++){
                    for(int k = 0; k < 7; k++){
                        arrayEx = cast_128.avalanche_effect(changeElement(b, j, k), 0, cast_128.makeKey(keyArray, 8), 8);
                        dots.add(returnD(arrayOrig, arrayEx));
                    }
                }

            }
            for(int i = 0; i < dots.get(i).size(); i++ ){
                int m = 0;
                for(int l = 0; l < dots.size(); l++){
                    m += dots.get(l).get(i);
                }

                ddd.add(m / dots.size());
            }

        } catch (InvalidKeyException e) {
            e.printStackTrace();
        }

        return ddd;
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
        ArrayList<Integer> arrayD = new ArrayList<>();

        String str = new String();
        int d;
        int aByte;
        int bByte;

        for(int i = 0; i < a.size(); i++){
            for(int j = 0; j < a.get(i).length; j++){
                aByte = a.get(i)[j] & 0xff;
                bByte = b.get(i)[j] & 0xff;
                str += Integer.toBinaryString(aByte ^ bByte);
            }
            d = str.replace("0", "").length();
            arrayD.add(d);
            str = new String();
        }

        return arrayD;

    }
}




