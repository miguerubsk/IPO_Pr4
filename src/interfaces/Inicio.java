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
import java.awt.BorderLayout;
import utils.GuardarDatos;
import utils.CargarDatos;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Frame;
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
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.border.EmptyBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

/**
 *
 * @author Miguel González García
 */
public final class Inicio extends javax.swing.JPanel {

    private final JFrame framePadre;
    public Nuevo nuevo;
    private Edicion edicion;
    private Consulta consulta;
    private Borrar borrado;
    private CargarDatos cd;
    private final String separador = " | ";
    public JDialog dialogoEmergente;

    private final DefaultListModel listModel;
//    private final DefaultListModel filteredListModel;

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
//        this.filteredListModel = new DefaultListModel();

        addLibrosInicio();

        list.setModel(listModel);
//        campoBuscar.getDocument().addDocumentListener(new BuscarListener());

        list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        list.setSelectedIndex(0);
        list.setVisibleRowCount(5);
        list.addMouseListener(new MouseListener(this));
        setMinimumSize(new Dimension(500, 400));
//        addButton.addActionListener(new NuevoListener(this));
//        deleteButton.addActionListener(new EliminarListener(this));
//        modifyButton.addActionListener(new ModificarListener(this));
        Icon addIcon = new ImageIcon(new ImageIcon("images/icons/add.png").getImage().getScaledInstance(16, 16, Image.SCALE_DEFAULT));
        Icon deleteIcon = new ImageIcon(new ImageIcon("images/icons/delete.png").getImage().getScaledInstance(16, 16, Image.SCALE_DEFAULT));
        Icon modifyIcon = new ImageIcon(new ImageIcon("images/icons/edit.png").getImage().getScaledInstance(16, 16, Image.SCALE_DEFAULT));
//        addButton.setIcon(addIcon);
//        deleteButton.setIcon(deleteIcon);
//        modifyButton.setIcon(modifyIcon);
        setVisible(true);
//        addButton.setVisible(false);
//        deleteButton.setVisible(false);
//        modifyButton.setVisible(false);
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

        updateFilteredListModel("");
    }

    private void updateFilteredListModel(String filterText) {
//        filteredListModel.clear();
        for (int i = 0; i < listModel.getSize(); i++) {
            String item = listModel.getElementAt(i).toString();
            if (item.toLowerCase().contains(filterText.toLowerCase())) {
//                filteredListModel.addElement(item);
            }
        }
    }

    public void mostrarError(int texto, JPanel padre) {
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
        dialogo.getRootPane().setDefaultButton(botonAceptar);
        dialogo.setVisible(true);
    }

    /**
     *
     * @param inicio panel de inicio
     */
    public void gestionarEdicion(JPanel inicio) {

//        String userInput = JOptionPane.showInputDialog(null,
//                idioma.get(32),
//                idioma.get(31), JOptionPane.PLAIN_MESSAGE);
        int elementoSeleccionado = cuadroBuscar();

        switch (elementoSeleccionado) {
            case -1 ->
                mostrarError(28, inicio);
            case -2 ->
                System.out.println("El usuario ha cancelado la operación");
            default -> {
                edicion = new Edicion((Inicio) inicio, vectorLibros.get(elementoSeleccionado), idioma, imagenes);
                libroAnterior = vectorLibros.get(elementoSeleccionado);
                vectorLibros.remove(elementoSeleccionado);
                listModel.remove(elementoSeleccionado);
//            filteredListModel.remove(elementoSeleccionado);

                dialogoEmergente = new JDialog(framePadre, idioma.get(20), true);
                dialogoEmergente.setSize(new Dimension(450, 325));
                dialogoEmergente.setLocationRelativeTo(framePadre);
                dialogoEmergente.setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);
                dialogoEmergente.add(edicion);
                
                dialogoEmergente.getRootPane().setDefaultButton(edicion.saveButton);

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
        
//        if (elementoSeleccionado == -1) {
////            mostrarError(28, inicio);
//        } else {
////            list.setSelectedIndex(elementoSeleccionado);
////            list.ensureIndexIsVisible(elementoSeleccionado);
//
//        }

    }
    
    
    public void gestionarConsulta(JPanel inicio) {

//        String userInput = JOptionPane.showInputDialog(null,
//                idioma.get(32),
//                idioma.get(31), JOptionPane.PLAIN_MESSAGE);
        int elementoSeleccionado = cuadroBuscar();

        switch (elementoSeleccionado) {
            case -1 ->
                mostrarError(28, inicio);
            case -2 ->
                System.out.println("El usuario ha cancelado la operación");
            default -> {
                consulta = new Consulta((Inicio) inicio, vectorLibros.get(elementoSeleccionado), idioma, imagenes);
//                libroAnterior = vectorLibros.get(elementoSeleccionado);
//                vectorLibros.remove(elementoSeleccionado);
//                listModel.remove(elementoSeleccionado);
//            filteredListModel.remove(elementoSeleccionado);

                dialogoEmergente = new JDialog(framePadre, idioma.get(35), true);
                dialogoEmergente.setSize(new Dimension(450, 325));
                dialogoEmergente.setLocationRelativeTo(framePadre);
//                dialogoEmergente.setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);
                dialogoEmergente.add(consulta);
                
                dialogoEmergente.getRootPane().setDefaultButton(consulta.volverButton);

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
        
//        if (elementoSeleccionado == -1) {
////            mostrarError(28, inicio);
//        } else {
////            list.setSelectedIndex(elementoSeleccionado);
////            list.ensureIndexIsVisible(elementoSeleccionado);
//
//        }

    }
    
    public void gestionarBorrado(JPanel inicio) {

//        String userInput = JOptionPane.showInputDialog(null,
//                idioma.get(32),
//                idioma.get(31), JOptionPane.PLAIN_MESSAGE);
        int elementoSeleccionado = cuadroBuscar();

        switch (elementoSeleccionado) {
            case -1 ->
                mostrarError(28, inicio);
            case -2 ->
                System.out.println("El usuario ha cancelado la operación");
            default -> {
                borrado = new Borrar((Inicio) inicio, vectorLibros.get(elementoSeleccionado), idioma, imagenes);
                libroAnterior = vectorLibros.get(elementoSeleccionado);
                vectorLibros.remove(elementoSeleccionado);
                listModel.remove(elementoSeleccionado);
//            filteredListModel.remove(elementoSeleccionado);

                dialogoEmergente = new JDialog(framePadre, idioma.get(6), true);
                dialogoEmergente.setSize(new Dimension(450, 325));
                dialogoEmergente.setLocationRelativeTo(framePadre);
                dialogoEmergente.setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);
                dialogoEmergente.add(borrado);
                
                dialogoEmergente.getRootPane().setDefaultButton(borrado.volverButton);

                dialogoEmergente.setVisible(true);

            }
        }
        
//        if (elementoSeleccionado == -1) {
////            mostrarError(28, inicio);
//        } else {
////            list.setSelectedIndex(elementoSeleccionado);
////            list.ensureIndexIsVisible(elementoSeleccionado);
//
//        }

    }

    public int cuadroBuscar() {
//        String userInput = JOptionPane.showInputDialog(null,
//                idioma.get(32),
//                idioma.get(31), JOptionPane.PLAIN_MESSAGE);
        JPanel panel = new JPanel(new BorderLayout(10, 15));
        panel.setBorder(new EmptyBorder(10, 10, 10, 10));
        JLabel label = new JLabel(idioma.get(32));
        panel.add(label, BorderLayout.NORTH);

        JTextField textField = new JTextField(20);
        panel.add(textField, BorderLayout.CENTER);

        JButton okButton = new JButton(idioma.get(31));
        JButton cancelButton = new JButton(idioma.get(34));

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        buttonPanel.add(okButton);
        buttonPanel.add(cancelButton);

        panel.add(buttonPanel, BorderLayout.SOUTH);

        JDialog dialog = new JDialog((Frame) null, idioma.get(31), true);
        dialog.getContentPane().add(panel);
        dialog.pack();
        dialog.setLocationRelativeTo(this);
        
        dialog.getRootPane().setDefaultButton(okButton);

        final int resultado[] = {-2};

        okButton.addActionListener(e -> {
            resultado[0] = -1;
            for (Libro lib : vectorLibros) {
                if (lib.getNombre().toLowerCase().equals(textField.getText().toLowerCase())) {
                    resultado[0] = vectorLibros.indexOf(lib);
                }
            }
            dialog.dispose(); // Cerrar el cuadro de diálogo
        });

        cancelButton.addActionListener(e -> {
            resultado[0] = -2; // No devolver nada
            dialog.dispose();
        });

        dialog.setVisible(true);

        
        return resultado[0];
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
//            filteredListModel.addElement(libro.getNombre() + separador + libro.getAutor() + separador + libro.getGenero());
        }
    }
   
    /**
     *
     */
    public void restaurarLibro() {
        vectorLibros.add(libroAnterior);
        listModel.addElement(libroAnterior.getNombre() + separador + libroAnterior.getAutor() + separador + libroAnterior.getGenero());
//        filteredListModel.addElement(libroAnterior.getNombre() + separador + libroAnterior.getAutor() + separador + libroAnterior.getGenero());
    }

    /**
     *
     */
    public void cambiarIdioma() {
//        addButton.setText(idioma.get(5));
//        deleteButton.setText(idioma.get(6));
//        modifyButton.setText(idioma.get(20));
        jLabelLibros.setText(idioma.get(17));
//        campoBuscar.setText(idioma.get(31));
//        if (filteredListModel != null) {
//            updateFilteredListModel("");
//        }
        imagenLang.setIcon(new ImageIcon(imagenes.get(1).getImage().getScaledInstance(32, 32, Image.SCALE_SMOOTH)));

    }

    /**
     *
     * @param idioma lista con los textos del idioma al que se quiere cambiar
     * @param imagenes lista con las imágenes del idioma al que se quiere
     * cambiar
     */
    public void setIdioma(ArrayList<String> idioma, ArrayList<ImageIcon> imagenes) {
        this.idioma = idioma;
        this.imagenes = imagenes;
        cambiarIdioma();
    }

    public JFrame getFramePadre() {
        return framePadre;
    }

    public Nuevo getNuevo() {
        return nuevo;
    }

    public ArrayList<String> getIdioma() {
        return idioma;
    }

    public ArrayList<ImageIcon> getImagenes() {
        return imagenes;
    }

    public JList<String> getList() {
        return list;
    }

    /**
     *
     * @param ruta relativa al archivo con los datos
     */
    public void cargarDatos(String ruta) {

        try {
            CargarDatos cargaDatos = new CargarDatos(ruta);
            for (Libro libro : cargaDatos.getDatos()) {
                if (vectorLibros.contains(libro)) {
                    System.out.println("Libro duplicado:\n" + libro.toString());

                } else {
                    vectorLibros.add(libro);
                }
            }
        } catch (IOException ex) {
            Logger.getLogger(Inicio.class.getName()).log(Level.SEVERE, null, ex);
        }

        Collections.sort(vectorLibros, (Libro p1, Libro p2) -> p1.getAutor().compareTo(p2.getAutor()));

        listModel.clear();
//        filteredListModel.clear();
        vectorLibros.forEach(libro -> {
            listModel.addElement(libro.getNombre() + separador + libro.getAutor() + separador + libro.getGenero());
//            filteredListModel.addElement(libro.getNombre() + separador + libro.getAutor() + separador + libro.getGenero());
        });
        updateFilteredListModel("");
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

    public DefaultListModel getListModel() {
        return listModel;
    }

    public ArrayList<Libro> getVectorLibros() {
        return vectorLibros;
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
            dialogoEmergente.setSize(new Dimension(500, 325));
            dialogoEmergente.setLocationRelativeTo(framePadre);
            dialogoEmergente.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
            dialogoEmergente.add(nuevo);
            dialogoEmergente.getRootPane().setDefaultButton(nuevo.saveButton);

            dialogoEmergente.setVisible(true);
            updateFilteredListModel("");
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
//                    filteredListModel.removeElementAt(elementoSeleccionado);
                    vectorLibros.remove(elementoSeleccionado);
                    updateFilteredListModel("");
                }
                default -> {
                }
            }
        }
    }

    class BuscarListener implements DocumentListener {

        @Override
        public void changedUpdate(DocumentEvent e) {
            filterList();
        }

        @Override
        public void insertUpdate(DocumentEvent e) {
            filterList();
        }

        @Override
        public void removeUpdate(DocumentEvent e) {
            filterList();
        }

        private void filterList() {
//            String searchText = campoBuscar.getText();
//            updateFilteredListModel(searchText);
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
        jLabelLibros = new javax.swing.JLabel();
        imagenLang = new javax.swing.JLabel();

        list.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N
        list.setModel(new javax.swing.AbstractListModel<String>() {
            String[] strings = { "Item 1", "Item 2", "Item 3", "Item 4", "Item 5" };
            public int getSize() { return strings.length; }
            public String getElementAt(int i) { return strings[i]; }
        });
        jScrollPane1.setViewportView(list);

        jLabelLibros.setFont(new java.awt.Font("Dialog", 1, 24)); // NOI18N
        jLabelLibros.setText("Libros");

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
                        .addGap(329, 329, 329)
                        .addComponent(imagenLang, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 14, Short.MAX_VALUE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabelLibros)
                    .addComponent(imagenLang, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 218, Short.MAX_VALUE)
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel imagenLang;
    private javax.swing.JLabel jLabelLibros;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JList<String> list;
    // End of variables declaration//GEN-END:variables

}
