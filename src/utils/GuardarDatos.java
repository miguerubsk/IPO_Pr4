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

import IPO_Pr4.Libro;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Vector;

/**
 *
 * @author Miguel González García
 */
public class GuardarDatos {

    private FileWriter fichero = null;
    private PrintWriter pw = null;

    public GuardarDatos(Vector<Libro> libros, String nombreArchivo) {
        try {
            File file = new File(nombreArchivo + ".txt");
            if (!file.exists()) {
                file.createNewFile();
            } else {
                file.delete();
                file.createNewFile();
            }
            fichero = new FileWriter(nombreArchivo + ".txt");
            pw = new PrintWriter(fichero);

            for (Libro libro : libros) {
                pw.write(libro.getNombre() + ";" + libro.getAutor() + ";" + libro.getGenero() + ";" + libro.getAño() + ";" + libro.getRutaImagen() + ";\n");
            }
            fichero.close();
        } catch (IOException e) {
            System.err.println(e.toString());
        }

    }

}
