package thirdLab;


import secondLab.Encoder;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Arrays;

public class MultilevelEncryption {
    private CntOfRounds strategy;
    private final int blockSize = 32;

    private int[] permutationKey;
    private int[] substitutionKey;
    private int[] gummingKey;

    private String whatCodeFileName;
    private String toCodeFileName;
    private String whatDecodeFileName;
    private String toDecodeFileName;

    public MultilevelEncryption(CntOfRounds strategy){
        this.strategy = strategy;
    }

    public void encode(String whatCodeFileName, String toCodeFileName){
        initKeys();


        try(FileInputStream whatCodeFile = new FileInputStream(whatCodeFileName);
            FileOutputStream toCodeFile = new FileOutputStream(toCodeFileName)){

            int bufferSize = 64000;
            byte[] bufferRead = new byte[bufferSize];
            byte[] bufferWrite = new byte[bufferSize];
            byte[] block;

            while(whatCodeFile.available() > 0){
                if (whatCodeFile.available() < bufferSize){
                    bufferSize = whatCodeFile.available();
                    bufferWrite = new byte[bufferSize
                            + blockSize
                            - (bufferSize % blockSize)];
                }
                whatCodeFile.read(bufferRead, 0, bufferSize);

                for (int i = blockSize - 1; i < bufferSize; i+=blockSize) {
                    block = choseEncodeMethod(
                            Arrays.copyOfRange(bufferRead, i-blockSize+1, i));
                    System.arraycopy(block, 0, bufferWrite, i-blockSize+1, i);
                }

                toCodeFile.write(bufferWrite);
                toCodeFile.flush();
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private byte[] choseEncodeMethod(byte[] block){
        byte[] resultBlock = new byte[blockSize];
        switch (strategy){
            case FIVE_PE:
                resultBlock = encodeFiveRndPe(block);
                break;
            case FIVE_SU:
                resultBlock = encodeFiveRndSu(block);
                break;
            case NINE_PE:
                resultBlock = encodeNineRndPe(block);
                break;
            case NINE_SU:
                resultBlock = encodeNineRndSu(block);
                break;
        }
        return resultBlock;
    }

    public void decode(String whatDecodeFileName, String toDecodeFileName){
        this.whatDecodeFileName = whatDecodeFileName;
        this.toDecodeFileName = toDecodeFileName;
        switch (strategy){
            case FIVE_PE -> decodeFiveRndPe();
            case FIVE_SU -> decodeFiveRndSu();
            case NINE_PE -> decodeNineRndPe();
            case NINE_SU -> decodeNineRndSu();
        }
    }

    private byte[] encodeFiveRndPe(byte[] block){
        block = Encoder.encodePermutation(block, )
    }
    private byte[] encodeFiveRndSu(byte[] block){}
    private byte[] encodeNineRndPe(byte[] block){}
    private byte[] encodeNineRndSu(byte[] block){}

    private void decodeFiveRndPe(){};
    private void decodeFiveRndSu(){};
    private void decodeNineRndPe(){};
    private void decodeNineRndSu(){};

    private void initKeys(){
        substitutionKey = Encoder.mixArray(256);
        permutationKey = Encoder.mixArray(blockSize);
        gummingKey = Encoder.rndArrayOfByte(blockSize);
    }
}
