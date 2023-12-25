package thirdLab;

public class Main {
    public static void main(String[] args) {
        MultilevelEncryption encryption =
                new MultilevelEncryption(CntOfRounds.NINE_SU);
        encryption.encode("track6.fla", "second.fla");
        encryption.decode("second.fla", "third.fla");
    }
}
