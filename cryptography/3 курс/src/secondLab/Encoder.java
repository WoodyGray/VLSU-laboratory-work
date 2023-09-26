package secondLab;

import java.io.*;

public class Encoder {
    public static void encode(String whatCodeFile, String toCodeFile, int[] asci){
        try(FileInputStream whatCode = new FileInputStream(whatCodeFile);
            FileOutputStream toCode = new FileOutputStream(toCodeFile)){
            int bufferSize = 64000;
            byte[] bufferRead = new byte[bufferSize];
            byte[] bufferWrite = new byte[bufferSize];
            while (whatCode.available() > 0){
                if (whatCode.available() < bufferSize) bufferSize = whatCode.available();
                whatCode.read(bufferRead, 0, bufferSize);
                if (bufferSize != 64000) {
                    bufferWrite = new byte[bufferSize];
                }
                for (int i = 0; i < bufferSize; i++) {
                    bufferWrite[i] = (byte) asci[bufferRead[i] & 0b11111111];
                    //System.out.print(bufferWrite[i] + " ");
                }

                toCode.write(bufferWrite);
                toCode.flush();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
