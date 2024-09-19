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
    public Map<String, String> getFonts() {
        // Mapa de combinaciones de fuentes locales
        Map<String, String> fonts = new HashMap<>();

        // Añadir combinaciones de fuentes que desees
        fonts.put("Merriweather", "Open Sans");
        fonts.put("Playfair Display", "Montserrat");
        fonts.put("Lato", "Roboto");
        fonts.put("Raleway", "Roboto Slab");
        fonts.put("Amatic SC", "Josefin Sans");

        // Verifica si la clave API está presente
        if (apiKey == null || apiKey.isEmpty()) {
            System.out.println("Error: API Key is missing");
            return fonts; // Devuelve las combinaciones locales si falta la clave API
        }

        // Lógica para obtener fuentes desde Google Fonts API
        try {
            RestTemplate restTemplate = new RestTemplate();
            String url = "https://www.googleapis.com/webfonts/v1/webfonts?key=" + apiKey;
            Map<String, Object> response = restTemplate.getForObject(url, Map.class);

            if (response != null && response.containsKey("items")) {
                // Procesa la respuesta para obtener todas las fuentes
                List<Map<String, Object>> items = (List<Map<String, Object>>) response.get("items");
                for (Map<String, Object> item : items) {
                    String family = (String) item.get("family");
                    fonts.putIfAbsent(family, ""); // Añade la fuente a la lista sin combinación si no existe ya
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return fonts; // Devuelve el mapa como JSON
    }
}
