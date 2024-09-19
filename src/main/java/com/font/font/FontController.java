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

import java.util.Map;
import java.util.HashMap;

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
    public Map<String, Map<String, String>> getFonts() {
        // Mapa de combinaciones de fuentes locales
        Map<String, Map<String, String>> fonts = new HashMap<>();

        // Añadir combinaciones de fuentes que desees con sus enlaces
        fonts.put("Merriweather", Map.of("pair", "Open Sans", "link", "https://fonts.googleapis.com/css2?family=Merriweather&family=Open+Sans&display=swap"));
        fonts.put("Playfair Display", Map.of("pair", "Montserrat", "link", "https://fonts.googleapis.com/css2?family=Playfair+Display&family=Montserrat&display=swap"));
        fonts.put("Lato", Map.of("pair", "Roboto", "link", "https://fonts.googleapis.com/css2?family=Lato&family=Roboto&display=swap"));
        fonts.put("Raleway", Map.of("pair", "Roboto Slab", "link", "https://fonts.googleapis.com/css2?family=Raleway&family=Roboto+Slab&display=swap"));
        fonts.put("Amatic SC", Map.of("pair", "Josefin Sans", "link", "https://fonts.googleapis.com/css2?family=Amatic+SC&family=Josefin+Sans&display=swap"));

        // Verifica si la clave API está presente
        if (apiKey == null || apiKey.isEmpty()) {
            System.out.println("Error: API Key is missing");
            return fonts; // Devuelve las combinaciones locales si falta la clave API
        }

        // Lógica para obtener fuentes desde Google Fonts API se ha eliminado, ya que no es necesaria

        return fonts; // Devuelve el mapa como JSON
    }
}
