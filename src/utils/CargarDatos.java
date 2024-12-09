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
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;
import java.util.Vector;

/**
 *
 * @author Miguel González García
 */
public class CargarDatos {

    private final Vector<Libro> datos;

    public CargarDatos(String fichero) throws FileNotFoundException, IOException {
        this.datos = new Vector<>();
        File archivo = new File(fichero);
        Scanner scn = new Scanner(archivo);
        while (scn.hasNext()) {
            String linea = scn.nextLine();
            String[] split = linea.split(";");
            String[] aux = new String[5];
//            for (String string : aux) {
//                string = " ";
//            }
            System.arraycopy(split, 0, aux, 0, split.length);
            Libro nuevoLibro = new Libro(aux[0], aux[1], aux[2], aux[3], aux[4]);
            datos.add(nuevoLibro);
        }
    }

    public Vector<Libro> getDatos() {
        return datos;
    }

}
