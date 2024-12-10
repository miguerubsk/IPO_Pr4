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
package IPO_Pr4;

import java.util.Objects;

/**
 *
 * @author Miguel González García
 */
public class Libro {

    private String nombre;
    private String autor;
    private String genero;
    private String año;
    private String rutaImagen;
    private boolean tieneImagen;

    /**
     *
     * @param nombre
     * @param autor
     * @param genero
     * @param año
     * @param rutaImagen
     */
    public Libro(String nombre, String autor, String genero, String año, String rutaImagen) {
        this.nombre = nombre;
        this.autor = autor;
        this.genero = genero;
        this.año = año;
        this.rutaImagen = rutaImagen;
        this.tieneImagen = true;
    }

    /**
     *
     */
    public Libro() {
        this.nombre = " ";
        this.autor = " ";
        this.genero = " ";
        this.año = " ";
        this.tieneImagen = false;
    }

    /**
     *
     * @param nombre
     */
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    /**
     *
     * @param autor
     */
    public void setAutor(String autor) {
        this.autor = autor;
    }

    /**
     *
     * @param genero
     */
    public void setGenero(String genero) {
        this.genero = genero;
    }

    /**
     *
     * @param año
     */
    public void setAño(String año) {
        this.año = año;
    }

    /**
     *
     * @return el nombre del libro
     */
    public String getNombre() {
        return nombre;
    }

    /**
     *
     * @return el autor del libro
     */
    public String getAutor() {
        return autor;
    }

    /**
     *
     * @return el género del libro
     */
    public String getGenero() {
        return genero;
    }

    /**
     *
     * @return el año de publicación del libro
     */
    public String getAño() {
        return año;
    }

    /**
     *
     * @param rutaImagen la ruta relativa de la imagen
     */
    public void setRutaImagen(String rutaImagen) {
        this.rutaImagen = rutaImagen;
        this.tieneImagen = true;
    }

    /**
     *
     * @return la ruta relativa de la imagen
     */
    public String getRutaImagen() {
        return rutaImagen;
    }

    /**
     *
     * @return true si tiene imagen false en caso contratrio
     */
    public boolean tieneImagen() {
        return tieneImagen;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 29 * hash + Objects.hashCode(this.nombre);
        hash = 29 * hash + Objects.hashCode(this.autor);
        hash = 29 * hash + Objects.hashCode(this.genero);
        hash = 29 * hash + Objects.hashCode(this.año);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Libro other = (Libro) obj;
        return Objects.equals(this.nombre, other.nombre);
    }
}
