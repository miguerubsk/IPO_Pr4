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
import utils.GuardarDatos;
import utils.CargarDatos;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.util.Collections;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.DefaultListModel;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.ListSelectionModel;

/**
 *
 * @author Miguel González García
 */
public final class Inicio extends javax.swing.JPanel {

    private final JFrame framePadre;
    private Nuevo nuevo;
    private Edicion edicion;
    private CargarDatos cd;

    private final DefaultListModel listModel;

    Vector<String> idioma;

    private final Vector<Libro> vectorLibros;
    private Libro libroAnterior;
    private Vector<ImageIcon> imagenes;

    /**
     * Creates new form Inicio
     *
     * @param framePadre JFrame que lo crea
     * @param idioma vector con el idioma
     * @param imagenes vector con las imágenes
     */
    public Inicio(JFrame framePadre, Vector<String> idioma, Vector<ImageIcon> imagenes) {
        initComponents();

        this.framePadre = framePadre;
        this.vectorLibros = new Vector<>();
        this.idioma = idioma;
        this.imagenes = imagenes;
        cambiarIdioma();

        this.listModel = new DefaultListModel();
        addLibrosEjemplo();
        list.setModel(listModel);
        list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        list.setSelectedIndex(0);
        list.setVisibleRowCount(5);
        list.addMouseListener(new MouseListener(this));
        setMinimumSize(new Dimension(500, 400));
        addButton.addActionListener(new NuevoListener(this));
        deleteButton.addActionListener(new EliminarListener(this));
        modifyButton.addActionListener(new ModificarListener(this));
        Icon addIcon = new ImageIcon("images/icons/add.png");
        Icon deleteIcon = new ImageIcon("images/icons/delete.png");
        Icon modifyIcon = new ImageIcon("images/icons/edit.png");
        addButton.setIcon(addIcon);
        deleteButton.setIcon(deleteIcon);
        modifyButton.setIcon(modifyIcon);

        setVisible(true);
    }

    private void addLibrosEjemplo() {
        try {
            cd = new CargarDatos("libros.txt");
            vectorLibros.addAll(cd.getDatos());
        } catch (IOException ex) {
            Logger.getLogger(Inicio.class.getName()).log(Level.SEVERE, null, ex);
        }

        for (Libro libro : vectorLibros) {
            listModel.addElement(libro.getNombre() + " | " + libro.getAutor());
        }
    }

    public void guardarLibro(Libro libro) {
        if (vectorLibros.contains(libro)) {
            JOptionPane.showMessageDialog(framePadre, idioma.get(19), idioma.get(18), JOptionPane.WARNING_MESSAGE);
        } else {
            vectorLibros.add(libro);
            listModel.addElement(libro.getNombre() + " | " + libro.getAutor());
        }
    }

    public void restaurarLibro() {
        vectorLibros.add(libroAnterior);
        listModel.addElement(libroAnterior.getNombre() + " | " + libroAnterior.getAutor());
    }

    public void cambiarIdioma() {
        addButton.setText(idioma.get(5));
        deleteButton.setText(idioma.get(6));
        modifyButton.setText(idioma.get(20));
        jLabelLibros.setText(idioma.get(17));
    }

    public void setIdioma(Vector<String> idioma, Vector<ImageIcon> imagenes) {
        this.idioma = idioma;
        this.imagenes = imagenes;
        cambiarIdioma();
    }

    public void cargarDatos(String ruta) {

        try {
            CargarDatos cargaDatos = new CargarDatos(ruta);
            for (Libro dato : cargaDatos.getDatos()) {
                if (!vectorLibros.contains(dato)) {
                    vectorLibros.add(dato);
                }
            }
        } catch (IOException ex) {
            Logger.getLogger(Inicio.class.getName()).log(Level.SEVERE, null, ex);
        }

        Collections.sort(vectorLibros, (Libro p1, Libro p2) -> p1.getAutor().compareTo(p2.getAutor()));
        
        for (Libro libro : vectorLibros) {
            listModel.addElement(libro.getNombre() + " | " + libro.getAutor());
        }

    }

    public void guardarDatos() {
        GuardarDatos gd = new GuardarDatos(vectorLibros, "libros");
    }

    class MouseListener extends MouseAdapter {

        Inicio inicio;

        public MouseListener(Inicio JPanel) {
            this.inicio = JPanel;
        }

        @Override
        public void mouseClicked(MouseEvent evt) {
            if (evt.getClickCount() == 2) {
                int elementoSeleccionado = list.getSelectedIndex();
                list.setSelectedIndex(elementoSeleccionado);
                list.ensureIndexIsVisible(elementoSeleccionado);

                edicion = new Edicion(inicio, vectorLibros.get(elementoSeleccionado), idioma, imagenes);
                libroAnterior = vectorLibros.get(elementoSeleccionado);
                vectorLibros.remove(elementoSeleccionado);
                listModel.remove(elementoSeleccionado);

                framePadre.add(edicion, BorderLayout.PAGE_START);
                framePadre.pack();
                edicion.setVisible(true);
                inicio.setVisible(false);
            }
        }
    }

    class NuevoListener implements ActionListener {

        Inicio inicio;

        public NuevoListener(Inicio JPanel) {
            this.inicio = JPanel;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            Libro libro = new Libro();
            nuevo = new Nuevo(inicio, libro, idioma, imagenes);

            framePadre.add(nuevo, BorderLayout.PAGE_START);
            framePadre.pack();
            nuevo.setVisible(true);
            inicio.setVisible(false);
        }
    }

    class EliminarListener implements ActionListener {

        Inicio inicio;

        public EliminarListener(Inicio JPanel) {
            this.inicio = JPanel;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            int elementoSeleccionado = list.getSelectedIndex();
            list.setSelectedIndex(elementoSeleccionado);
            list.ensureIndexIsVisible(elementoSeleccionado);

            listModel.removeElementAt(elementoSeleccionado);
            vectorLibros.removeElementAt(elementoSeleccionado);
        }
    }

    class ModificarListener implements ActionListener {

        Inicio inicio;

        public ModificarListener(Inicio JPanel) {
            this.inicio = JPanel;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            int elementoSeleccionado = list.getSelectedIndex();
            list.setSelectedIndex(elementoSeleccionado);
            list.ensureIndexIsVisible(elementoSeleccionado);

            edicion = new Edicion(inicio, vectorLibros.get(elementoSeleccionado), idioma, imagenes);
            libroAnterior = vectorLibros.get(elementoSeleccionado);
            vectorLibros.remove(elementoSeleccionado);
            listModel.remove(elementoSeleccionado);

            framePadre.add(edicion, BorderLayout.PAGE_START);
            framePadre.pack();
            edicion.setVisible(true);
            inicio.setVisible(false);
        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        list = new javax.swing.JList<>();
        addButton = new javax.swing.JButton();
        deleteButton = new javax.swing.JButton();
        jLabelLibros = new javax.swing.JLabel();
        jSeparator1 = new javax.swing.JSeparator();
        modifyButton = new javax.swing.JButton();

        list.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N
        list.setModel(new javax.swing.AbstractListModel<String>() {
            String[] strings = { "Item 1", "Item 2", "Item 3", "Item 4", "Item 5" };
            public int getSize() { return strings.length; }
            public String getElementAt(int i) { return strings[i]; }
        });
        jScrollPane1.setViewportView(list);

        addButton.setText("Nuevo");
        addButton.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        deleteButton.setText("Eliminar");
        deleteButton.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        jLabelLibros.setFont(new java.awt.Font("Dialog", 1, 24)); // NOI18N
        jLabelLibros.setText("Libros");

        jSeparator1.setBackground(new java.awt.Color(0, 0, 0));
        jSeparator1.setForeground(new java.awt.Color(0, 0, 0));
        jSeparator1.setOrientation(javax.swing.SwingConstants.VERTICAL);

        modifyButton.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        modifyButton.setLabel("Modificar");
        modifyButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                modifyButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabelLibros)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(addButton, javax.swing.GroupLayout.PREFERRED_SIZE, 95, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(deleteButton, javax.swing.GroupLayout.PREFERRED_SIZE, 95, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(modifyButton, javax.swing.GroupLayout.PREFERRED_SIZE, 95, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 64, Short.MAX_VALUE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabelLibros, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jSeparator1)
                    .addComponent(addButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(deleteButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(modifyButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 206, Short.MAX_VALUE)
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents

    private void modifyButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_modifyButtonActionPerformed

    }//GEN-LAST:event_modifyButtonActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton addButton;
    private javax.swing.JButton deleteButton;
    private javax.swing.JLabel jLabelLibros;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JList<String> list;
    private javax.swing.JButton modifyButton;
    // End of variables declaration//GEN-END:variables

}
