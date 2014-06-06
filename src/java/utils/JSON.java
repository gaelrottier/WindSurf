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

    /**
     * Initialise l'objet JSON
     *
     * @param sc Le contexte de l'application
     */
    public JSON(ServletContext sc) {
        this.sc = sc;
    }

    /**
     * Désérialise le contenu du fichier "/WEB-INF/musique.json" et le retourne
     * Ce fichier est une adaptation du fichier musique.txt fourni par Mr Buffa,
     * et transformé en Json par Guillaume Golfieri.
     *
     * @return Le contenu du fichier "/WEB-INF/musique.json" sous forme de
     * JSONArray
     * @throws IOException Si le fichier n'existe pas
     */
    public JSONArray ParseJSON() throws IOException {

        File f = new File(sc.getRealPath("/WEB-INF/musique.json"));

        String deserializedJson = deserializeString(f);

        Object obj = JSONValue.parse(deserializedJson);
        Json = (JSONArray) obj;

        return Json;
    }

    /**
     * Désérialise le Fichier fourni en paramètre et le retourne sous forme de
     * <String>
     * Cette fonction a été récupérée sur :
     * http://www.java2s.com/Tutorial/Java/0180__File/LoadatextfilecontentsasaString.htm
     *
     * @param file Le fichier à désérialiser
     * @return Le contenu du fichier désérialisé
     * @throws IOException Si le fichier n'existe pas
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
