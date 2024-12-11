/*
 * Copyright (C) 2024 Miguel González García
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package utils;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import javax.swing.ImageIcon;

/**
 *
 * @author Miguel González García
 */
public class Idiomas {

    private final ArrayList<ArrayList<String>> idiomas;
    private final ArrayList<ArrayList<ImageIcon>> imagenes;
    private final int numIdiomas;

    /**
     * Lee y carga en memoria el fichero con los diferentes idiomas
     *
     * @param fichero ruta al fichero que contiene los idiomas
     * @throws FileNotFoundException
     * @throws IOException
     */
    public Idiomas(String fichero) throws FileNotFoundException, IOException {

        FileInputStream fis = new FileInputStream(fichero);
        InputStreamReader isr = new InputStreamReader(fis, "UTF-8");
        BufferedReader br = new BufferedReader(isr);

        numIdiomas = Integer.parseInt(br.readLine());
        idiomas = new ArrayList<>();
        imagenes = new ArrayList<>();
        for (int i = 0; i < numIdiomas; i++) {
            ArrayList<String> idioma = new ArrayList<>();
            idioma.add(br.readLine());
            int numPalabras = Integer.parseInt(br.readLine());
            for (int j = 0; j < numPalabras; j++) {
                idioma.add(br.readLine());
            }

            ArrayList<ImageIcon> imagenesAux = new ArrayList<>();
            int numImagenes = Integer.parseInt(br.readLine());
            for (int j = 0; j < numImagenes; j++) {
                imagenesAux.add(new ImageIcon(br.readLine()));
            }

            idiomas.add(idioma);
            imagenes.add(imagenesAux);
        }
    }

    /**
     *
     * @return Una lista de listas con los idiomas
     */
    public ArrayList<ArrayList<String>> getIdiomas() {
        return idiomas;
    }

    /**
     *
     * @param cual el idioma que se quiera obtener por orden según aparece en el
     * fichero
     * @return Una lista con los textos del idioma
     */
    public ArrayList<String> getIdioma(int cual) {
        return idiomas.get(cual);
    }

    /**
     *
     * @return una lista de listas con los diferentes idiomas
     */
    public ArrayList<ArrayList<ImageIcon>> getImagenes() {
        return imagenes;
    }

    /**
     *
     * @param cual el idioma que se quiera obtener por orden según aparece en el
     * fichero
     * @return Una lista con las imágenes del idioma
     */
    public ArrayList<ImageIcon> getImagenesIdioma(int cual) {
        return imagenes.get(cual);
    }

    /**
     *
     * @return el número de idiomas cargados
     */
    public int getNumIdiomas() {
        return numIdiomas;
    }

}
