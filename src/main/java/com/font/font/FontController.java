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
    public Map<String, Object> getFonts() {
        // Mapa de combinaciones de fuentes locales
        Map<String, String> fonts = new HashMap<>();
        
        // Añadir combinaciones de fuentes que desees
        fonts.put("Merriweather", "Open Sans");
        fonts.put("Playfair Display", "Montserrat");
        fonts.put("Lato", "Roboto");
        fonts.put("Raleway", "Roboto Slab");
        fonts.put("Amatic SC", "Josefin Sans");

        // Crear una lista para almacenar todas las fuentes
        List<Map<String, String>> allFonts = new ArrayList<>();

        // Añadir combinaciones al principio
        for (Map.Entry<String, String> entry : fonts.entrySet()) {
            Map<String, String> fontEntry = new HashMap<>();
            fontEntry.put("font", entry.getKey());
            fontEntry.put("pair", entry.getValue());
            allFonts.add(fontEntry);
        }

        // Verifica si la clave API está presente
        if (apiKey == null || apiKey.isEmpty()) {
            System.out.println("Error: API Key is missing");
            return Map.of("fonts", allFonts); // Devuelve las combinaciones locales si falta la clave API
        }

        // Lógica para obtener fuentes desde Google Fonts API
        try {
            RestTemplate restTemplate = new RestTemplate();
            String url = "https://www.googleapis.com/webfonts/v1/webfonts?key=" + apiKey;
            Map<String, Object> response = restTemplate.getForObject(url, Map.class);

            if (response != null && response.containsKey("items")) {
                // Procesa la respuesta para obtener todas las fuentes
                List<Map<String, Object>> items = (List<Map<String, Object>>) response.get("items");
                
                // Añadir un salto para "Resto de fuentes"
                Map<String, String> restOfFontsHeader = new HashMap<>();
                restOfFontsHeader.put("font", "Resto de fuentes");
                restOfFontsHeader.put("pair", ""); // Sin combinación
                allFonts.add(restOfFontsHeader);

                // Añadir las fuentes restantes
                for (Map<String, Object> item : items) {
                    String family = (String) item.get("family");
                    if (!fonts.containsKey(family)) { // Solo añadir si no es una combinación definida
                        Map<String, String> fontEntry = new HashMap<>();
                        fontEntry.put("font", family);
                        fontEntry.put("pair", ""); // Sin combinación
                        allFonts.add(fontEntry);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return Map.of("fonts", allFonts); // Devuelve la lista como JSON
    }
}
