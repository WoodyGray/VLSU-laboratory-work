package thirdLab;

public class Main {
    public static void main(String[] args) {
        MultilevelEncryption encryption =
                new MultilevelEncryption(CntOfRounds.NINE_SU);
        encryption.encode("firstFile.txt", "secondFile.txt");
//        encryption.decode("second.exe", "third.exe");
    }
}
