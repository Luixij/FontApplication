/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.font.font;

/**
 *
 * @author Luis Imaicela
 */
import io.github.cdimascio.dotenv.Dotenv;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.Map;
import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;

@RestController
public class FontController {

    private final Dotenv dotenv;
    private final String apiKey;

    public FontController() {
        this.dotenv = Dotenv.configure().ignoreIfMissing().load();
        this.apiKey = dotenv.get("API_KEY");
    }

    @GetMapping("/fonts")
    @ResponseBody
    public Map<String, List<Map<String, String>>> getFonts() {
        List<Map<String, String>> allFonts = new ArrayList<>();
        Map<String, String> combinedFonts = new HashMap<>();

        // Añadir combinaciones de fuentes
        combinedFonts.put("Merriweather", "Open Sans");
        combinedFonts.put("Playfair Display", "Montserrat");
        combinedFonts.put("Lato", "Roboto");
        combinedFonts.put("Raleway", "Roboto Slab");
        combinedFonts.put("Amatic SC", "Josefin Sans");

        // Añadir las combinaciones al principio
        for (Map.Entry<String, String> entry : combinedFonts.entrySet()) {
            Map<String, String> fontEntry = new HashMap<>();
            fontEntry.put("font", entry.getKey());
            fontEntry.put("pair", entry.getValue());
            allFonts.add(fontEntry);
        }

        // Añadir un separador para "Resto de fuentes"
        Map<String, String> separator = new HashMap<>();
        separator.put("font", "Resto de fuentes");
        separator.put("pair", "");
        allFonts.add(separator);

        // Verifica si la clave API está presente
        if (apiKey != null && !apiKey.isEmpty()) {
            try {
                RestTemplate restTemplate = new RestTemplate();
                String url = "https://www.googleapis.com/webfonts/v1/webfonts?key=" + apiKey;
                Map<String, Object> response = restTemplate.getForObject(url, Map.class);

                if (response != null && response.containsKey("items")) {
                    List<Map<String, Object>> items = (List<Map<String, Object>>) response.get("items");

                    for (Map<String, Object> item : items) {
                        String family = (String) item.get("family");
                        if (!combinedFonts.containsKey(family)) { // Añadir solo si no es parte de las combinaciones
                            Map<String, String> fontEntry = new HashMap<>();
                            fontEntry.put("font", family);
                            fontEntry.put("pair", "");
                            allFonts.add(fontEntry);
                        }
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return Map.of("fonts", allFonts);
    }
}
