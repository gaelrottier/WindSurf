package utils;

import gestionnaires.GestionnaireArtistes;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import javax.ejb.EJB;
import javax.servlet.ServletContext;
import org.json.simple.JSONArray;
import org.json.simple.JSONValue;

public class JSON {

    private static JSONArray Json;
    private final ServletContext sc;
    
    @EJB
    GestionnaireArtistes gestionnaireArtistes;

    public JSON(ServletContext sc) {
        this.sc = sc;
    }

    public JSONArray ParseJSON() throws IOException {

        File f = new File(sc.getRealPath("/WEB-INF/musique.json"));

        String parsedJson = deserializeString(f);

        Object obj = JSONValue.parse(parsedJson);
        Json = (JSONArray) obj;

        return Json;
    }

    /*
     * From
     * http://www.java2s.com/Tutorial/Java/0180__File/LoadatextfilecontentsasaString.htm
     */
    public static String deserializeString(File file) throws IOException {
        int len;
        char[] chr = new char[4096];
        final StringBuffer buffer = new StringBuffer();
        final FileReader reader = new FileReader(file);
        try {
            while ((len = reader.read(chr)) > 0) {
                buffer.append(chr, 0, len);
            }
        } finally {
            reader.close();
        }
        return buffer.toString();
    }

}
