import java.io.*;
import java.util.HashMap;

public class Main {
    private static HashMap<Double, String> rusSlovar = new HashMap<>();
    static {
        rusSlovar.put(0.175, " " );
        rusSlovar.put(0.089, "O" );
        rusSlovar.put(0.072, "Е" );
        rusSlovar.put(0.062, "АИ");
        rusSlovar.put(0.053, "НТ");
        rusSlovar.put(0.045, "С" );
        rusSlovar.put(0.040, "Р" );
        rusSlovar.put(0.038, "В" );
        rusSlovar.put(0.035, "Л" );
        rusSlovar.put(0.028, "К" );
        rusSlovar.put(0.026, "М" );
        rusSlovar.put(0.025, "Д" );
        rusSlovar.put(0.023, "П" );
        rusSlovar.put(0.021, "У" );
        rusSlovar.put(0.018, "Я" );
        rusSlovar.put(0.016, "ЫЗ");
        rusSlovar.put(0.014, "ЬБ");
        rusSlovar.put(0.013, "Г" );
        rusSlovar.put(0.012, "Ч" );
        rusSlovar.put(0.010, "Й" );
        rusSlovar.put(0.009, "Х" );
        rusSlovar.put(0.007, "Ж" );
        rusSlovar.put(0.006, "ЮШ");
        rusSlovar.put(0.004, "Ц" );
        rusSlovar.put(0.003, "ЩЭ");
        rusSlovar.put(0.002, "Ф" );
    }
    public static void main(String[] args) throws IOException {
        String path = "D:\\study\\ВлГУ\\прога\\крипта\\1 лаба\\Новый текстовый документ.txt";
        String answerPath = "D:\\study\\ВлГУ\\прога\\крипта\\1 лаба\\answer.txt";
        BufferedReader reader = new BufferedReader(new FileReader("ответ.txt"));
        int cntS = 0;
        HashMap<String, Double> slovar = new HashMap<>();
        while (reader.ready()){
            String[] line = reader.readLine().replaceAll("\\n", "").split("");
            for (String s: line
                 ) {
                System.out.println(s);
                if (slovar.containsKey(s)){
                    slovar.put(s, slovar.get(s)+1);
                }else {
                    slovar.put(s, 1.0);
                }
                cntS+=1;
            }
        }

        reader.close();
        BufferedWriter writer = new BufferedWriter(new FileWriter("kripta.csv"));
        for (String symbol: slovar.keySet()
             ) {
            writer.write(symbol+";"+slovar.get(symbol)+";"+slovar.get(symbol)/cntS+"\n");
        }
        writer.close();
        /*
        for (Character symbol: slovar.keySet()
             ) {
            double scale = Math.pow(10, 3);
            double result = Math.round((slovar.get(symbol)/cntS) * scale) / scale;
            slovar.put(symbol, result);
            System.out.println(symbol + ": "+ slovar.get(symbol));
        }
        FileReader finishFile = new FileReader(path);
        FileWriter answer = new FileWriter(answerPath);
        while (finishFile.ready()){
            Character symbol = (char) finishFile.read();
            Double probability = slovar.get(symbol);
            Double minDiff = 1.000;
            String rusSymbol = "";
            for (Double rusProb: rusSlovar.keySet()
                 ) {
                if (Math.abs(probability - rusProb) < minDiff){
                    minDiff = Math.abs(probability - rusProb);
                    rusSymbol = rusSlovar.get(rusProb);
                }
            }
            answer.write(rusSymbol);
        }
        finishFile.close();
        answer.close();

         */
    }
}