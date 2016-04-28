package wordleGenerator;/* Authored by Kushagra on 4/25/2016. */

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.PriorityQueue;

import static wordleGenerator.TopKWordFeatureList.getTopKWords;

public class WordCoeffMapper {

    public static void main(String[] args) throws IOException {

        String[] topKWords = getTopKWords();
        ArrayList<Double> coefficients = getCeofficients();

        PriorityQueue<WordRank> minheap = new PriorityQueue<>();
        int wordCount = topKWords.length;



        for (int i=0; i<wordCount; i++) {
            minheap.add(new WordRank(topKWords[i], coefficients.get(i)));
        }

//        System.out.println(minheap.poll());
//        System.out.println(maxheap.poll());



        // Positive Words
//        double max = 0.11776617793442551;
//        double min = 0.007901117180840593;
//        double delta = max - min;
//        double z = (double) 20 / delta;


        // Negative Words
        double max = 0.06960256661443118;
        double min = 0.012667575800771025;
        double delta = min - max;
        double z = (double) 10 / delta;


        for (int i=0; i<80; i++) {

//            WordRank wr = maxheap.poll();
            WordRank wr = minheap.poll();
//            System.out.println(wr);

            double coeff = wr.coefficient;
            double normalizedCoeff = coeff * z;
            int integerScores = (int) normalizedCoeff;
            System.out.println(integerScores + "\t" + wr.word);
        }

    }

    private static ArrayList<Double> getCeofficients() throws IOException {

        System.out.println("Getting top k words from cache...");

        String filePath = "data/wordle/coeff.txt";

        String fileContents = new String(Files.readAllBytes(Paths.get(filePath)));

        String regex = "\\[|\\]";
        fileContents = fileContents.replaceAll(regex, "");

        String[] tokens = fileContents.split(",");

        ArrayList<Double> coefficients = new ArrayList<>();

        for (String token : tokens) {
            coefficients.add(Double.parseDouble(token.trim()));
        }


        System.out.println(coefficients.toString());

        return coefficients;
    }

    public static class WordRank implements Comparable<WordRank> {

        String word;
        double coefficient;

        public WordRank(String w, double c) {
            this.word = w;
            this.coefficient = c;
        }


        @Override
        public int compareTo(WordRank other) {
            return Double.compare(this.coefficient, other.coefficient);
        }


        public String toString() {
            String w = this.word;
            String c = String.valueOf(this.coefficient);
            return  c + "\t" + w;
        }
    }

}
