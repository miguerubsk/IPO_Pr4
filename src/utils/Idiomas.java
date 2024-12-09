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
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Vector;
import javax.swing.ImageIcon;

/**
 *
 * @author Miguel González García
 */
public class Idiomas {

    private final Vector<Vector<String>> idiomas;
    private final Vector<Vector<ImageIcon>> imagenes;
    private final int numIdiomas;

    public Idiomas(String fichero) throws FileNotFoundException, IOException {
        FileReader f = new FileReader(fichero);
        BufferedReader b = new BufferedReader(f);

        numIdiomas = Integer.parseInt(b.readLine());
        idiomas = new Vector<>();
        imagenes = new Vector<>();
        for (int i = 0; i < numIdiomas; i++) {
            Vector<String> idioma = new Vector<>();
            idioma.add(b.readLine());
            int numPalabras = Integer.parseInt(b.readLine());
            for (int j = 0; j < numPalabras; j++) {
                idioma.add(b.readLine());
            }

            Vector<ImageIcon> imagenesAux = new Vector<>();
            int numImagenes = Integer.parseInt(b.readLine());
            for (int j = 0; j < numImagenes; j++) {
                imagenesAux.add(new ImageIcon(b.readLine()));
            }

            idiomas.add(idioma);
            imagenes.add(imagenesAux);
        }
    }

    public Vector<Vector<String>> getIdiomas() {
        return idiomas;
    }

    public Vector<String> getIdioma(int cual) {
        return idiomas.get(cual);
    }

    public Vector<Vector<ImageIcon>> getImagenes() {
        return imagenes;
    }

    public Vector<ImageIcon> getImagenesIdioma(int cual) {
        return imagenes.get(cual);
    }

    public int getNumIdiomas() {
        return numIdiomas;
    }

}
