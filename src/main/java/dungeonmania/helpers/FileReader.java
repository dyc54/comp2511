package dungeonmania.helpers;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
/**
 * Read file at path ./src/test
 */
public class FileReader {
    public static String LoadFile(String FileName, int branch) throws IOException {
        return FileReader.LoadFile(String.format("%s[%s]", FileName, branch));
    }
    public static String LoadFile(String FileName) throws IOException {
        try {
            File test = searchFile(new File("./src/test"), FileName+".json");
            File frontEnd = searchFile(new File("./src/main/resources"), FileName+".json");
            Path absPath = (test == null ? frontEnd: test).toPath();
            return new String(Files.readAllBytes(absPath));
        } catch (Exception nulException) {
            throw new IOException("No such file");
        }
    }
    private static File searchFile(File file, String search) {
        if (file.isDirectory()) {
            File[] arr = file.listFiles();
            for (File f : arr) {
                File found = searchFile(f, search);
                if (found != null)
                    return found;
                
            }
        } else {
            if (file.getName().equals(search)) {
                System.out.println("FILE FOUND\n->"+file.getAbsolutePath());
                return file;
            }
        }
        return null;
    }
    public static List<String> listAllGamesArchives() {
        ArrayList<String> list = new ArrayList<>();
        File file = new File(FileSaver.SAVED_PATH);
        File[] arr = file.listFiles();
        for (File f : arr) {
            String fileName = f.getName().replaceAll("\\[.*?\\].json", "");
            list.add(fileName);
        }
        return list;
    }
    
}
