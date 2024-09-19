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

    // Aquí defines las combinaciones de fuentes que deseas
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

    // Añadir otras fuentes desde Google Fonts API aquí si es necesario

    return Map.of("fonts", allFonts);
}
}
