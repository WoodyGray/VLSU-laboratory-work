package secondLab;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class Decoder {
    public static void decodeSimpleSabstitution(String whatDecodeFileName,
                                                String toCodeFileName, int[] asci){
        try(FileInputStream whatDecodeFile = new FileInputStream(whatDecodeFileName);
            FileOutputStream toCodeFile = new FileOutputStream(toCodeFileName)){
            int[] asciDecode = new int[256];
            for (int i = 0; i < 256; i++) {
                asciDecode[asci[i]] = i;
            }

            int bufferSize = 64000;
            byte[] bufferRead = new byte[bufferSize];
            byte[] bufferWrite = new byte[bufferSize];
            while (whatDecodeFile.available() > 0){
                if (whatDecodeFile.available() < bufferSize) bufferSize = whatDecodeFile.available();
                whatDecodeFile.read(bufferRead, 0, bufferSize);
                if (bufferSize != 64000) {
                    bufferWrite = new byte[bufferSize];
                }
                for (int i = 0; i < bufferSize; i++) {
                    bufferWrite[i] = (byte)asciDecode[bufferRead[i] & 0b11111111];
                }
                toCodeFile.write(bufferWrite);
                toCodeFile.flush();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void decodePermutation(String whatDecodeFile, String toDecodeFile,
                                         int[] key, int origFileSize){
        int whatCodeFileSize = 0;
        try(FileInputStream whatDecode = new FileInputStream(whatDecodeFile);
            FileOutputStream toDecode = new FileOutputStream(toDecodeFile)){
            int bufferSize = 64000 + key.length - (64000 % key.length);

            int[] keyDecode = new int[key.length];
            for (int i = 0; i < key.length; i++) {
                keyDecode[key[i]] = i;
            }
            int diffOfSize = whatDecode.available() - origFileSize;

            byte[] bufferRead = new byte[bufferSize];
            byte[] bufferWrite = new byte[bufferSize];

            while (whatDecode.available() > 0){
                if (whatDecode.available() < bufferSize) {
                    bufferSize = whatDecode.available();
                    bufferWrite = new byte[bufferSize];
                }
                whatDecode.read(bufferRead, 0, bufferSize);

                for (int i = 0; i < bufferSize / key.length; i++) {
                    for (int j = 0; j < key.length; j++) {
                        bufferWrite[i*key.length + keyDecode[j]] = bufferRead[i*key.length + j];
                    }
                }
                if (bufferSize < 64000){
                    byte[] lastBufferWrite = new byte[bufferSize-diffOfSize];
                    for (int i = 0; i < lastBufferWrite.length; i++) {
                        lastBufferWrite[i] = bufferWrite[i];
                    }
                    toDecode.write(lastBufferWrite);
                    toDecode.flush();
                }else {
                    toDecode.write(bufferWrite);
                    toDecode.flush();
                }
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public static void decodeGamming(String whatDecodeFileName, String toDecodeFileName, int[] key){
        try (FileInputStream whatDecodeFile = new FileInputStream(whatDecodeFileName);
             FileOutputStream toDecodeFile = new FileOutputStream(toDecodeFileName)){

            int bufferSize = 64000;
            byte[] bufferRead = new byte[bufferSize];
            byte[] bufferWrite = new byte[bufferSize];

            while (whatDecodeFile.available() > 0){
                if (whatDecodeFile.available() < bufferSize) {
                    bufferSize = whatDecodeFile.available();
                    bufferWrite = new byte[bufferSize];
                }

                whatDecodeFile.read(bufferRead, 0, bufferSize);

                for (int i = 0; i < bufferSize; i++) {
                    bufferWrite[i] = (byte) (bufferRead[i] - key[i % key.length]);
                }

                toDecodeFile.write(bufferWrite);
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void decodeGamming(String whatDecodeFileName, String toDecodeFileName, String notepadFileName){
        try (FileInputStream whatDecodeFile = new FileInputStream(whatDecodeFileName);
             FileOutputStream toDecodeFile = new FileOutputStream(toDecodeFileName);
             FileInputStream notepadFile = new FileInputStream(notepadFileName)){

            int bufferSize = 64000;
            byte[] bufferRead = new byte[bufferSize];
            byte[] bufferWrite = new byte[bufferSize];
            byte[] bufferNotepad = new byte[bufferSize];

            while (whatDecodeFile.available() > 0){
                if (whatDecodeFile.available() < bufferSize) {
                    bufferSize = whatDecodeFile.available();
                    bufferWrite = new byte[bufferSize];
                }

                whatDecodeFile.read(bufferRead, 0, bufferSize);
                notepadFile.read(bufferNotepad, 0, bufferSize);

                for (int i = 0; i < bufferSize; i++) {
                    bufferWrite[i] = (byte) (bufferRead[i] - bufferNotepad[i]);
                }

                toDecodeFile.write(bufferWrite);
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
