package dungeonmania.helpers;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.*;

public class FileReader {
    public static String LoadFile(String FileName) throws IOException {
        try {
            Path absPath = searchFile(new File("./src/test"), FileName+".json").toPath();
            return new String(Files.readAllBytes(absPath));
        } catch (Exception nulException) {
            //TODO: handle exception
            throw new IOException("No such file");
        }
    }
    private static File searchFile(File file, String search) {
        // System.out.println(file.getAbsolutePath());
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
    
}
