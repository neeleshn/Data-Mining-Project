package utils;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class FileIO {
    public static int countLines(String filePath) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(filePath));

        int lines = 0;
        while (reader.readLine() != null) lines++;

        reader.close();
        return lines;
    }
}
