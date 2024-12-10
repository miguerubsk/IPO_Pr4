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
import java.awt.Dimension;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.DefaultListModel;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
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
    private final String separador = " | ";
    private JDialog dialogoEmergente;

    private final DefaultListModel listModel;

    ArrayList<String> idioma;

    private final ArrayList<Libro> vectorLibros;
    private Libro libroAnterior;
    private ArrayList<ImageIcon> imagenes;

    /**
     * Creates new form Inicio
     *
     * @param framePadre JFrame que lo crea
     * @param idioma vector con el idioma
     * @param imagenes vector con las imágenes
     */
    public Inicio(JFrame framePadre, ArrayList<String> idioma, ArrayList<ImageIcon> imagenes) {
        initComponents();

        this.framePadre = framePadre;
        this.vectorLibros = new ArrayList<>();
        this.idioma = idioma;
        this.imagenes = imagenes;
        cambiarIdioma();

        this.listModel = new DefaultListModel();
        addLibrosInicio();
        list.setModel(listModel);
        list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        list.setSelectedIndex(0);
        list.setVisibleRowCount(5);
        list.addMouseListener(new MouseListener(this));
        setMinimumSize(new Dimension(500, 400));
        addButton.addActionListener(new NuevoListener(this));
        deleteButton.addActionListener(new EliminarListener(this));
        modifyButton.addActionListener(new ModificarListener(this));
        Icon addIcon = new ImageIcon(new ImageIcon("images/icons/add.png").getImage().getScaledInstance(16, 16, Image.SCALE_DEFAULT));
        Icon deleteIcon = new ImageIcon(new ImageIcon("images/icons/delete.png").getImage().getScaledInstance(16, 16, Image.SCALE_DEFAULT));
        Icon modifyIcon = new ImageIcon(new ImageIcon("images/icons/edit.png").getImage().getScaledInstance(16, 16, Image.SCALE_DEFAULT));
        addButton.setIcon(addIcon);
        deleteButton.setIcon(deleteIcon);
        modifyButton.setIcon(modifyIcon);
        setVisible(true);
    }

    /**
     *
     */
    private void addLibrosInicio() {
        try {
            cd = new CargarDatos("libros.tsv");
            vectorLibros.addAll(cd.getDatos());
        } catch (IOException ex) {
            Logger.getLogger(Inicio.class.getName()).log(Level.SEVERE, null, ex);
        }

        vectorLibros.forEach((var libro) -> {
            listModel.addElement(libro.getNombre() + separador + libro.getAutor() + separador + libro.getGenero());
        });
    }
    
    public void mostrarError(int texto, JPanel padre){
        JOptionPane optionPane = new JOptionPane(
                    idioma.get(texto),
                    JOptionPane.ERROR_MESSAGE,
                    JOptionPane.DEFAULT_OPTION,
                    null,
                    new Object[]{},
                    null
            );
            JDialog dialogo = optionPane.createDialog(padre, idioma.get(18));
            JButton botonAceptar = new JButton(idioma.get(3));
            botonAceptar.addActionListener(e2 -> dialogo.dispose());
            optionPane.setOptions(new Object[]{botonAceptar});
            dialogo.setVisible(true);
    }

    /**
     *
     * @param inicio panel de inicio
     */
    private void gestionarEdicion(JPanel inicio) {
        int elementoSeleccionado = list.getSelectedIndex();
        if (elementoSeleccionado < 0) {
            mostrarError(28, inicio);
        } else {
            list.setSelectedIndex(elementoSeleccionado);
            list.ensureIndexIsVisible(elementoSeleccionado);

            edicion = new Edicion((Inicio) inicio, vectorLibros.get(elementoSeleccionado), idioma, imagenes);
            libroAnterior = vectorLibros.get(elementoSeleccionado);
            vectorLibros.remove(elementoSeleccionado);
            listModel.remove(elementoSeleccionado);

            dialogoEmergente = new JDialog(framePadre, idioma.get(20), true);
            dialogoEmergente.setSize(new Dimension(500, 400));
            dialogoEmergente.setLocationRelativeTo(framePadre);
            dialogoEmergente.setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);
            dialogoEmergente.add(edicion);

            dialogoEmergente.setVisible(true);

            dialogoEmergente.addWindowListener(new WindowAdapter() {
                @Override
                public void windowClosing(WindowEvent e) {
                    restaurarLibro();
                    dialogoEmergente.dispose();
                }
            });
        }
    }

    /**
     *
     * @param libro que se va a guardar
     */
    public void guardarLibro(Libro libro) {
        if (vectorLibros.contains(libro)) {
            mostrarError(19, this);
        } else {
            vectorLibros.add(libro);
            listModel.addElement(libro.getNombre() + separador + libro.getAutor() + separador + libro.getGenero());
        }
    }

    /**
     *
     */
    public void restaurarLibro() {
        vectorLibros.add(libroAnterior);
        listModel.addElement(libroAnterior.getNombre() + separador + libroAnterior.getAutor() + separador + libroAnterior.getGenero());
    }

    /**
     *
     */
    public void cambiarIdioma() {
        addButton.setText(idioma.get(5));
        deleteButton.setText(idioma.get(6));
        modifyButton.setText(idioma.get(20));
        jLabelLibros.setText(idioma.get(17));
        imagenLang.setIcon(new ImageIcon(new ImageIcon("images/" + idioma.getFirst() + ".png").getImage().getScaledInstance(32, 32, Image.SCALE_SMOOTH)));
    }

    /**
     *
     * @param idioma lista con los textos del idioma al que se quiere cambiar
     * @param imagenes lista con las imágenes del idioma al que se quiere cambiar
     */
    public void setIdioma(ArrayList<String> idioma, ArrayList<ImageIcon> imagenes) {
        this.idioma = idioma;
        this.imagenes = imagenes;
        cambiarIdioma();
    }

    /**
     *
     * @param ruta relativa al archivo con los datos
     */
    public void cargarDatos(String ruta) {

        try {
            CargarDatos cargaDatos = new CargarDatos(ruta);
            cargaDatos.getDatos().stream().filter(dato -> (!vectorLibros.contains(dato))).forEachOrdered(dato -> {
                vectorLibros.add(dato);
            });
        } catch (IOException ex) {
            Logger.getLogger(Inicio.class.getName()).log(Level.SEVERE, null, ex);
        }

        Collections.sort(vectorLibros, (Libro p1, Libro p2) -> p1.getAutor().compareTo(p2.getAutor()));

        vectorLibros.forEach(libro -> {
            listModel.addElement(libro.getNombre() + separador + libro.getAutor() + separador + libro.getGenero());
        });

    }

    /**
     *
     */
    public void guardarDatos() {
        GuardarDatos gd = new GuardarDatos(vectorLibros, "libros");
    }

    /**
     *
     * @return un dialogo emergente sin inicializar
     */
    public JDialog getDialogoEmergente() {
        return dialogoEmergente;
    }

    class MouseListener extends MouseAdapter {

        Inicio inicio;

        public MouseListener(Inicio JPanel) {
            this.inicio = JPanel;
        }

        @Override
        public void mouseClicked(MouseEvent evt) {
            if (evt.getClickCount() == 2) {
                gestionarEdicion(inicio);
            }
        }
    }

    class ModificarListener implements ActionListener {

        Inicio inicio;

        public ModificarListener(Inicio JPanel) {
            this.inicio = JPanel;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            gestionarEdicion(inicio);
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

            dialogoEmergente = new JDialog(framePadre, idioma.get(5), true);
            dialogoEmergente.setSize(new Dimension(500, 400));
            dialogoEmergente.setLocationRelativeTo(framePadre);
            dialogoEmergente.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
            dialogoEmergente.add(nuevo);

            dialogoEmergente.setVisible(true);
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

            Object[] opciones = {idioma.get(24), idioma.get(25)};
            int confirm = JOptionPane.showOptionDialog(inicio, idioma.get(22), idioma.get(23), JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, opciones, opciones[1]);

            switch (confirm) {
                case JOptionPane.YES_OPTION -> {
                    listModel.removeElementAt(elementoSeleccionado);
                    vectorLibros.remove(elementoSeleccionado);
                }
                default -> {
                }
            }
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
        imagenLang = new javax.swing.JLabel();

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
                        .addGap(18, 18, 18)
                        .addComponent(imagenLang, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 14, Short.MAX_VALUE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addComponent(jLabelLibros, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jSeparator1)
                        .addComponent(addButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(deleteButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(modifyButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addComponent(imagenLang, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE))
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
    private javax.swing.JLabel imagenLang;
    private javax.swing.JLabel jLabelLibros;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JList<String> list;
    private javax.swing.JButton modifyButton;
    // End of variables declaration//GEN-END:variables

}
