package secondLab;

import java.io.*;

public class Encoder {
    public static void encodeSimpleSubstitution(String whatCodeFile, String toCodeFile, int[] asci){
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
    public static int encodePermutation(String whatCodeFile, String toCodeFile, int[] key){
        int whatCodeFileSize = 0;
        try(FileInputStream whatCode = new FileInputStream(whatCodeFile);
            FileOutputStream toCode = new FileOutputStream(toCodeFile)){
            int bufferSize = 64000;
            while (bufferSize % key.length != 0){
                bufferSize++;
            }
            whatCodeFileSize = whatCode.available();

            byte[] bufferRead = new byte[bufferSize];
            byte[] bufferWrite = new byte[bufferSize];

            while (whatCode.available() > 0){
                if (whatCode.available() < bufferSize) {
                    bufferSize = whatCode.available();
                    bufferSize = bufferSize + key.length - (bufferSize % key.length);
                    bufferWrite = new byte[bufferSize];
                }
                whatCode.read(bufferRead, 0, bufferSize);

                for (int i = 0; i < bufferSize / key.length; i++) {
                    for (int j = 0; j < key.length; j++) {
                        bufferWrite[i*key.length + key[j]] = bufferRead[i*key.length + j];
                    }
                }
                toCode.write(bufferWrite);
                toCode.flush();
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return whatCodeFileSize;

    }
}
