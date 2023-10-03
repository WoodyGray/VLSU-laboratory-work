package secondLab;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class Decoder {
    public static void decodeSimpleSabstitution(String whatDecodeFileName, String toCodeFileName, int[] asci){
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
}
