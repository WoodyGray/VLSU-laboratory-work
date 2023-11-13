package thirdLab;


import secondLab.Decoder;
import secondLab.Encoder;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Arrays;

public class MultilevelEncryption {
    private int ending;
    private CntOfRounds strategy;
    private final int blockSize = 32;

    private int[] permutationKey;
    private int[] substitutionKey;
    private int[] gummingKey;

    private byte[] previousEncodeBlock;
    private byte[] nowEncodeBlock;

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
                    ending = blockSize - (bufferSize % blockSize);
                    bufferSize += ending;
                    bufferWrite = new byte[bufferSize];
                }
                whatCodeFile.read(bufferRead, 0, bufferSize);

                for (int i = blockSize - 1; i < bufferSize; i+=blockSize) {
                    if (previousEncodeBlock == null){
                        block = choseEncodeMethod(
                                Arrays.copyOfRange(bufferRead, i-blockSize+1, i+1));
                        previousEncodeBlock = block;
                    }else{
                        block = Arrays.copyOfRange(bufferRead, i-blockSize+1, i+1);
                        block = xorBlocks(block, previousEncodeBlock);
                        block = choseEncodeMethod(block);
                        previousEncodeBlock = block;
                    }
                    System.arraycopy(block, 0, bufferWrite, i-blockSize+1, blockSize);
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
        previousEncodeBlock = null;

        try(FileInputStream whatDecodeFile = new FileInputStream(whatDecodeFileName);
            FileOutputStream toDecodeFile = new FileOutputStream(toDecodeFileName)){

            int bufferSize = 64000;
            byte[] bufferRead = new byte[bufferSize];
            byte[] bufferWrite = new byte[bufferSize];
            byte[] block;
            boolean flag = true;

            while(whatDecodeFile.available() > 0){
                if (whatDecodeFile.available() < bufferSize){
                    bufferSize = whatDecodeFile.available();
                    bufferWrite = new byte[bufferSize];
                    flag = false;
                }
                whatDecodeFile.read(bufferRead, 0, bufferSize);

                for (int i = blockSize - 1; i < bufferSize; i+=blockSize) {
                    if (previousEncodeBlock == null){
                        previousEncodeBlock = Arrays.copyOfRange(bufferRead, i-blockSize+1, i+1);
                        block = choseDecodeMethod(
                                previousEncodeBlock);
                    }else{
                        nowEncodeBlock = Arrays.copyOfRange(bufferRead, i-blockSize+1, i+1);
                        block = choseDecodeMethod(nowEncodeBlock);
                        block = xorBlocks(block, previousEncodeBlock);
                        previousEncodeBlock = nowEncodeBlock;
                    }
                    System.arraycopy(block, 0, bufferWrite, i-blockSize+1, blockSize);
                }
                if (!flag) {
                    toDecodeFile.write(
                            Arrays.copyOfRange(bufferWrite, 0, bufferSize - ending+1)
                    );
                    toDecodeFile.flush();
                }else {
                    toDecodeFile.write(bufferWrite);
                    toDecodeFile.flush();
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private byte[] choseDecodeMethod(byte[] block){
        byte[] resultBlock = new byte[blockSize];
        switch (strategy){
            case FIVE_PE:
                resultBlock = decodeFiveRndPe(block);
                break;
            case FIVE_SU:
                resultBlock = decodeFiveRndSu(block);
                break;
            case NINE_PE:
                resultBlock = decodeNineRndPe(block);
                break;
            case NINE_SU:
                resultBlock = decodeNineRndSu(block);
                break;
        }
        return resultBlock;
    }

    private byte[] xorBlocks(byte[] firstBlock, byte[] secondBlock){
        byte[] result = new byte[firstBlock.length];
        for (int i = 0; i < result.length; i++) {
            result[i] = (byte) (firstBlock[i] ^ secondBlock[i]);
        }

        return result;
    }

    private byte[] encodeFiveRndPe(byte[] block){
        block = Encoder.encodePermutation(block, permutationKey);
        block = Encoder.encodeGamming(block, gummingKey);
        block = Encoder.encodeSimpleSubstitution(block, substitutionKey);
        block = Encoder.encodeGamming(block, gummingKey);
        block = Encoder.encodePermutation(block, permutationKey);
        return block;
    }
    private byte[] encodeFiveRndSu(byte[] block){
        block = Encoder.encodeSimpleSubstitution(block, substitutionKey);
        block = Encoder.encodeGamming(block, gummingKey);
        block = Encoder.encodePermutation(block, permutationKey);
        block = Encoder.encodeGamming(block, gummingKey);
        block = Encoder.encodeSimpleSubstitution(block, substitutionKey);
        return block;
    }
    private byte[] encodeNineRndPe(byte[] block){
        block = Encoder.encodePermutation(block, permutationKey);
        block = Encoder.encodeGamming(block, gummingKey);
        block = Encoder.encodeSimpleSubstitution(block, substitutionKey);
        block = Encoder.encodeGamming(block, gummingKey);
        block = Encoder.encodePermutation(block, permutationKey);
        block = Encoder.encodeGamming(block, gummingKey);
        block = Encoder.encodeSimpleSubstitution(block, substitutionKey);
        block = Encoder.encodeGamming(block, gummingKey);
        block = Encoder.encodePermutation(block, permutationKey);
        return block;
    }
    private byte[] encodeNineRndSu(byte[] block){
        block = Encoder.encodeSimpleSubstitution(block, substitutionKey);
        block = Encoder.encodeGamming(block, gummingKey);
        block = Encoder.encodePermutation(block, permutationKey);
        block = Encoder.encodeGamming(block, gummingKey);
        block = Encoder.encodeSimpleSubstitution(block, substitutionKey);
        block = Encoder.encodeGamming(block, gummingKey);
        block = Encoder.encodePermutation(block, permutationKey);
        block = Encoder.encodeGamming(block, gummingKey);
        block = Encoder.encodeSimpleSubstitution(block, substitutionKey);
        return block;
    }

    private byte[] decodeFiveRndPe(byte[] block){
        block = Decoder.decodePermutation(block, permutationKey);
        block = Decoder.decodeGamming(block, gummingKey);
        block = Decoder.decodeSimpleSubstitution(block, substitutionKey);
        block = Decoder.decodeGamming(block, gummingKey);
        block = Decoder.decodePermutation(block, permutationKey);
        return block;
    };
    private byte[] decodeFiveRndSu(byte[] block){
        block = Decoder.decodeSimpleSubstitution(block, substitutionKey);
        block = Decoder.decodeGamming(block, gummingKey);
        block = Decoder.decodePermutation(block, permutationKey);
        block = Decoder.decodeGamming(block, gummingKey);
        block = Decoder.decodeSimpleSubstitution(block, substitutionKey);
        return block;
    };
    private byte[] decodeNineRndPe(byte[] block){
        block = Decoder.decodePermutation(block, permutationKey);
        block = Decoder.decodeGamming(block, gummingKey);
        block = Decoder.decodeSimpleSubstitution(block, substitutionKey);
        block = Decoder.decodeGamming(block, gummingKey);
        block = Decoder.decodePermutation(block, permutationKey);
        block = Decoder.decodeGamming(block, gummingKey);
        block = Decoder.decodeSimpleSubstitution(block, substitutionKey);
        block = Decoder.decodeGamming(block, gummingKey);
        block = Decoder.decodePermutation(block, permutationKey);
        return block;
    };
    private byte[] decodeNineRndSu(byte[] block){
        block = Decoder.decodeSimpleSubstitution(block, substitutionKey);
        block = Decoder.decodeGamming(block, gummingKey);
        block = Decoder.decodePermutation(block, permutationKey);
        block = Decoder.decodeGamming(block, gummingKey);
        block = Decoder.decodeSimpleSubstitution(block, substitutionKey);
        block = Decoder.decodeGamming(block, gummingKey);
        block = Decoder.decodePermutation(block, permutationKey);
        block = Decoder.decodeGamming(block, gummingKey);
        block = Decoder.decodeSimpleSubstitution(block, substitutionKey);
        return block;
    };

    private void initKeys(){
        substitutionKey = Encoder.mixArray(256);
        permutationKey = Encoder.mixArray(blockSize);
        gummingKey = Encoder.rndArrayOfByte(blockSize);
    }
}
