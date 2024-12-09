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

    public Libro(String nombre, String autor, String genero, String año, String rutaImagen) {
        this.nombre = nombre;
        this.autor = autor;
        this.genero = genero;
        this.año = año;
        this.rutaImagen = rutaImagen;
        this.tieneImagen = true;
    }

    public Libro() {
        this.nombre = " ";
        this.autor = " ";
        this.genero = " ";
        this.año = " ";
        this.tieneImagen = false;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setAutor(String autor) {
        this.autor = autor;
    }

    public void setGenero(String genero) {
        this.genero = genero;
    }

    public void setAño(String año) {
        this.año = año;
    }

    public String getNombre() {
        return nombre;
    }

    public String getAutor() {
        return autor;
    }

    public String getGenero() {
        return genero;
    }

    public String getAño() {
        return año;
    }

    public void setRutaImagen(String rutaImagen) {
        this.rutaImagen = rutaImagen;
        this.tieneImagen = true;
    }

    public String getRutaImagen() {
        return rutaImagen;
    }

    public boolean isTieneImagen() {
        return tieneImagen;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 71 * hash + Objects.hashCode(this.nombre);
        hash = 71 * hash + Objects.hashCode(this.autor);
        hash = 71 * hash + Objects.hashCode(this.genero);
        hash = 71 * hash + Objects.hashCode(this.año);
        hash = 71 * hash + Objects.hashCode(this.rutaImagen);
        hash = 71 * hash + (this.tieneImagen ? 1 : 0);
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
        if (!Objects.equals(this.nombre, other.nombre)) {
            return false;
        }
        if (!Objects.equals(this.autor, other.autor)) {
            return false;
        }
        if (!Objects.equals(this.genero, other.genero)) {
            return false;
        }
        return Objects.equals(this.año, other.año);
    }
    
    
    
}
