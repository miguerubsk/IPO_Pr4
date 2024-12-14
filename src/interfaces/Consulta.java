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
package interfaces;

import IPO_Pr4.Libro;
import java.awt.Dimension;
import java.awt.FontMetrics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.filechooser.FileNameExtensionFilter;

/**
 *
 * @author Miguel González García
 */
public final class Consulta extends javax.swing.JPanel {

    private final Inicio panelAnterior;
    private final Libro libro;

    private final ArrayList<String> idioma;
    private final ArrayList<ImageIcon> imagenes;

    /**
     * Creates new form Edicion
     *
     * @param panelAnterior panel desde el que se está llamando a la fonción
     * @param libro que se va a editar
     * @param idioma idioma seleccionado
     * @param imagenes imágenes según el idioma
     */
    public Consulta(Inicio panelAnterior, Libro libro, ArrayList<String> idioma, ArrayList<ImageIcon> imagenes) {
        initComponents();
//        this.saveButton.requestFocusInWindow();
        this.panelAnterior = panelAnterior;
        this.libro = libro;
        this.idioma = idioma;
        this.imagenes = imagenes;
        cambiarIdioma();

        SwingUtilities.invokeLater(() -> {
            jTextFieldNombre.setText(libro.getNombre());
            ajustarTam(jTextFieldNombre);
            jTextFieldAutor.setText(libro.getAutor());
            ajustarTam(jTextFieldAutor);
            jTextFieldGenero.setText(libro.getGenero());
            ajustarTam(jTextFieldGenero);
            jTextFieldAnio.setText(libro.getAño());
            ajustarTam(jTextFieldAnio);
        });

        if (libro.tieneImagen()) {
            ImageIcon imageIcon = new ImageIcon(new ImageIcon(libro.getRutaImagen()).getImage().getScaledInstance(82, 125, Image.SCALE_SMOOTH));
            imagenLibro.setIcon(imageIcon);
        } else {
            imagenLibro.setIcon(imagenes.get(0));
        }

//        saveButton.addActionListener(new SaveListener(this));
        volverButton.addActionListener(new VolverListener(this));
//        addImage.addActionListener(new AddImageListener(this));
    }

    /**
     *
     */
    public void guardarLibro() {
        libro.setNombre(jTextFieldNombre.getText());
        libro.setAutor(jTextFieldAutor.getText());
        libro.setGenero(jTextFieldGenero.getText());
        libro.setAño(jTextFieldAnio.getText());
        panelAnterior.guardarLibro(libro);
    }

    /**
     *
     */
    public void cambiarIdioma() {
        jLabelNombre.setText(idioma.get(7));
        jLabelAutor.setText(idioma.get(8));
        jLabelGenero.setText(idioma.get(9));
        jLabelAnio.setText(idioma.get(10));
//        saveButton.setText(idioma.get(16));
        volverButton.setText(idioma.get(15));
//        addImage.setText(idioma.get(27));
        this.revalidate();
        this.repaint();
    }

    private static void ajustarTam(JTextField textField) {
        FontMetrics metrics = textField.getFontMetrics(textField.getFont());
        int anchoTexto = metrics.stringWidth(textField.getText());

        // Ajustar el ancho con un pequeño margen
        textField.setPreferredSize(new Dimension(anchoTexto + 10, textField.getPreferredSize().height));
        textField.revalidate(); // Recalcular el diseño
    }

    /**
     *
     * @param ruta relativa
     */
    public void setImagenLibro(String ruta) {
        libro.setRutaImagen(ruta);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jTextFieldNombre = new javax.swing.JTextField();
        jLabelNombre = new javax.swing.JLabel();
        jLabelAutor = new javax.swing.JLabel();
        jTextFieldAutor = new javax.swing.JTextField();
        jLabelGenero = new javax.swing.JLabel();
        jTextFieldGenero = new javax.swing.JTextField();
        jLabelAnio = new javax.swing.JLabel();
        jTextFieldAnio = new javax.swing.JTextField();
        volverButton = new javax.swing.JButton();
        imagenLibro = new javax.swing.JLabel();

        setFocusable(false);

        jTextFieldNombre.setEditable(false);
        jTextFieldNombre.setText("jTextField1");

        jLabelNombre.setText("Nombre");

        jLabelAutor.setText("Autor");

        jTextFieldAutor.setEditable(false);
        jTextFieldAutor.setText("jTextField2");

        jLabelGenero.setText("Genero");

        jTextFieldGenero.setEditable(false);
        jTextFieldGenero.setText("jTextField3");

        jLabelAnio.setText("Año");

        jTextFieldAnio.setEditable(false);
        jTextFieldAnio.setText("jTextField4");

        volverButton.setText("Volver");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(volverButton, javax.swing.GroupLayout.PREFERRED_SIZE, 77, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jTextFieldAnio, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 222, Short.MAX_VALUE)
                            .addComponent(jTextFieldGenero, javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabelNombre, javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabelAutor, javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabelGenero, javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabelAnio, javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jTextFieldAutor, javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jTextFieldNombre, javax.swing.GroupLayout.Alignment.LEADING))
                        .addGap(33, 33, 33)
                        .addComponent(imagenLibro, javax.swing.GroupLayout.PREFERRED_SIZE, 107, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(29, 29, 29))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(13, 13, 13)
                .addComponent(jLabelNombre)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jTextFieldNombre, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jLabelAutor)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jTextFieldAutor, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jLabelGenero)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jTextFieldGenero, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(imagenLibro, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabelAnio)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jTextFieldAnio, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(volverButton)
                .addContainerGap(25, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel imagenLibro;
    private javax.swing.JLabel jLabelAnio;
    private javax.swing.JLabel jLabelAutor;
    private javax.swing.JLabel jLabelGenero;
    private javax.swing.JLabel jLabelNombre;
    private javax.swing.JTextField jTextFieldAnio;
    private javax.swing.JTextField jTextFieldAutor;
    private javax.swing.JTextField jTextFieldGenero;
    private javax.swing.JTextField jTextFieldNombre;
    public javax.swing.JButton volverButton;
    // End of variables declaration//GEN-END:variables

    class VolverListener implements ActionListener {

        Consulta panelEdicion;

        public VolverListener(Consulta panelEdicion) {
            this.panelEdicion = panelEdicion;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
//            panelAnterior.restaurarLibro();
            panelAnterior.getDialogoEmergente().dispose();
        }
    }

    static class AddImageListener implements ActionListener {

        private final Consulta nuevo;

        public AddImageListener(Consulta nuevo) {
            this.nuevo = nuevo;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            JFileChooser selectorArchivos = new JFileChooser();
            selectorArchivos.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
            FileNameExtensionFilter filter = new FileNameExtensionFilter(".jpg", "jpg");
            selectorArchivos.setFileFilter(filter);
            int resultado = selectorArchivos.showOpenDialog(null);

            if (resultado == JFileChooser.APPROVE_OPTION) {
                File archivo = selectorArchivos.getSelectedFile();

                String directorioBase = System.getProperty("user.dir");
                Path rutaAbsoluta = Paths.get(archivo.getAbsolutePath());
                Path rutaBase = Paths.get(directorioBase);
                Path rutaRelativa = rutaBase.relativize(rutaAbsoluta);

                System.out.println("Fichero cargado: " + rutaRelativa.toString());
                cargarImagen(rutaRelativa.toString());
            }
        }

        private void cargarImagen(String ruta) {
            ImageIcon imageIcon = new ImageIcon(new ImageIcon(ruta).getImage().getScaledInstance(82, 125, Image.SCALE_SMOOTH));
            nuevo.imagenLibro.setIcon(imageIcon);
            nuevo.setImagenLibro(ruta);
        }
    }
}