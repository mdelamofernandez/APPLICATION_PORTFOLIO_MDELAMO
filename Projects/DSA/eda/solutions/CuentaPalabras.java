package eda.solutions;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;


/**
 * La siguiente clase "CuentaPalabras" cuenta las ocurrencias de palabras en un archivo de texto y muestra sus posiciones.
 * Lee un archivo de texto línea por línea y divide cada línea en palabras.
 * Por cada palabra encontrada, se registra su posición en el formato (número de línea:número de columna).
 * Las palabras y sus posiciones se almacenan en un HashMap, donde la clave es la palabra y el valor es una lista de posiciones.
 * Al final, imprime las palabras y sus posiciones en la consola.
 *
 * Ejemplo de uso:
 * <pre>{@code
 * java CuentaPalabras text.txt
 * }</pre>
 *
 * Este comando procesará el archivo de texto llamado "text.txt" y mostrará las ocurrencias de palabras junto con sus posiciones.
 */
public class CuentaPalabras {
    public static void main(String[] args) {
        String filename = args.length > 0 ? args[0] : "src/text.txt";
        HashMap<String, List<String>> wordOccurrences = new HashMap<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            String line;
            int lineNum = 1;
            while ((line = reader.readLine()) != null) {
                String[] words = line.split("\\s+");
                int columnNum = 1;
                for (String word : words) {
                    if (!word.isEmpty()) {
                        String position = "(" + lineNum + ":" + columnNum + ")";
                        wordOccurrences.computeIfAbsent(word, k -> new ArrayList<>()).add(position);
                        columnNum += word.length() + 1;
                    }
                }
                lineNum++;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        wordOccurrences.forEach((word, positions) -> {
            System.out.println(word + ": " + positions);
        });
    }
}
