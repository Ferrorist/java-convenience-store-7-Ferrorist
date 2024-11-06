package utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MarkDownUtils {
    public static List<String> readMarkDownFile(String filePath) {
        return readMarkDownFile(new File(filePath));
    }
    public static List<String> readMarkDownFile(File file) {
        try {
            return readFile(file);
        } catch (FileNotFoundException exception) {
            System.out.println("[ERROR] 파일을 찾을 수 없습니다.");
        } catch (IOException exception) {
            System.out.println("[ERROR] 파일을 읽는 도중 에러가 발생했습니다.");
        }

        return null;
    }

    private static List<String> readFile(File file) throws IOException {
        List<String> markdownLines = new ArrayList<>();
        BufferedReader br = new BufferedReader(new FileReader(file));
        String line;
        while ((line = br.readLine()) != null) {
            markdownLines.add(line);
        }
        return markdownLines.subList(1, markdownLines.size());
    }
}
