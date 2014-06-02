package Utils;

import com.json.parsers.JSONParser;
import com.json.parsers.JsonParserFactory;
import gestionnaires.GestionnaireArtistes;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Map;
import javax.ejb.EJB;
import javax.servlet.ServletContext;

public class JSON {

    private static Map Json;

    @EJB
    GestionnaireArtistes gestionnaireArtistes;

    public JSON() {
        System.out.println("JSON CREATED");
    }

    public void ParseJSON(ServletContext context) throws IOException {
        JsonParserFactory factory = JsonParserFactory.getInstance();
        JSONParser parser = factory.newJsonParser();

        System.out.println("ABOUT TO GET FIlE");
        File f = new File(context.getRealPath("/WEB-INF/musique.json"));
        System.out.println("ABOUT TO PARSE");
        Json = parser.parseJson(deserializeString(f));
        System.out.println("ABOUT TO FILL !");
        fillDB();
    }

    private void fillDB() {
        System.out.println("ABOUT TO GET ROOT MAP");
        Map rootMap = (Map) Json.get("root");
        System.out.println("JSON SIZE : " + rootMap.get("nom"));
        
        // for (int i = 0; i < rootMap.size(); i++) {
        System.out.println("JSON : " + Json.get("root"));
        System.out.println("JSON NOM : "+ Json.get(1));
          //  Map musique = (Map) Json.get(i);
        //System.out.println("MUSIQUE : "+musique.toString());
        //gestionnaireArtistes.setArtiste(musique.get("nom").toString(), "", "");
        //}
    }

    /**
     * From
     * http://www.java2s.com/Tutorial/Java/0180__File/LoadatextfilecontentsasaString.htm
     *
     * @param file
     * @return
     * @throws IOException
     */
    public static String deserializeString(File file) throws FileNotFoundException, IOException {
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
