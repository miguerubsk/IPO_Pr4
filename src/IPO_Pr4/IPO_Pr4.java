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

import utils.Idiomas;
import interfaces.Inicio;
import interfaces.Nuevo;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;

/**
 *
 * @author Miguel González García
 */
public class IPO_Pr4 {

    static private String jMenuTextArchivo;
    static private String jMenuTextIdioma;
    static private String jMenuItemTextSalir;
    static private String jMenuTextAyuda;
    private static String jMenuItemTextAbrir;
    private static String jMenuItemTextGuardar;
    private static String jMenuItemTextAyuda;
    private static String jMenuItemTextOperaciones;
    private static String jMenuItemTextNuevo;
    private static String jMenuItemTextBorrar;
    private static String jMenuItemTextModificar;
    private static String jMenuItemTextConsultar;
    private static String textoAyuda;
    private static String tituloAyuda;

    static private Idiomas idiomas;

    static private javax.swing.JMenu menuArchivo;
    static private javax.swing.JMenu menuOperaciones;
    //static private javax.swing.JMenu menuAyuda;
    static private javax.swing.JMenuBar menuBar;
    static private javax.swing.JMenuItem menuItemSalir;
    static private javax.swing.JMenuItem menuItemIdioma;
    static private javax.swing.JMenuItem menuItemAbrir;
    static private javax.swing.JMenuItem menuItemGuardar;
    static private javax.swing.JMenuItem menuItemNuevo;
    static private javax.swing.JMenuItem menuItemBorrar;
    static private javax.swing.JMenuItem menuItemModificar;
    static private javax.swing.JMenuItem menuItemConsultar;
    static private javax.swing.JMenuItem menuItemAyuda;

    static private Inicio inicio;
    static private JFrame frame;

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        javax.swing.SwingUtilities.invokeLater(() -> {
            cargarIdiomas();
            crearVentana();
        });
    }

    /**
     *
     */
    private static void cargarIdiomas() {
        try {
            idiomas = new Idiomas("idiomas.txt");
        } catch (IOException ex) {
            Logger.getLogger(IPO_Pr4.class.getName()).log(Level.SEVERE, "Error al leer el fichero", ex);
        }

        jMenuTextArchivo = idiomas.getIdioma(0).get(1);
        jMenuTextIdioma = idiomas.getIdioma(0).get(2);
        jMenuItemTextSalir = idiomas.getIdioma(0).get(3);
        jMenuTextAyuda = idiomas.getIdioma(0).get(4);
        jMenuItemTextAbrir = idiomas.getIdioma(0).get(11);
        jMenuItemTextGuardar = idiomas.getIdioma(0).get(12);
        jMenuItemTextAyuda = idiomas.getIdioma(0).get(13);
        textoAyuda = idiomas.getIdioma(0).get(14);
        tituloAyuda = idiomas.getIdioma(0).get(21);
        jMenuItemTextOperaciones = idiomas.getIdioma(0).get(30);
        jMenuItemTextNuevo = idiomas.getIdioma(0).get(5);
        jMenuItemTextBorrar = idiomas.getIdioma(0).get(6);
        jMenuItemTextModificar = idiomas.getIdioma(0).get(20);
        jMenuItemTextConsultar = idiomas.getIdioma(0).get(35);
    }

    /**
     *
     */
    private static void crearVentana() {
        frame = new JFrame(idiomas.getIdioma(0).get(29));
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setMinimumSize(new Dimension(500, 300));
        inicio = new Inicio(frame, idiomas.getIdioma(0), idiomas.getImagenesIdioma(0));

        crearMenuBar(frame);

        frame.setJMenuBar(menuBar);

        frame.add(inicio, BorderLayout.PAGE_START);

        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    /**
     *
     * @param frame
     */
    private static void crearMenuBar(JFrame frame) {
        menuBar = new javax.swing.JMenuBar();
        //Menu archivo
        menuArchivo = new javax.swing.JMenu(); //Archivo
        menuArchivo.setText(jMenuTextArchivo); //Archivo
        menuItemAbrir = new javax.swing.JMenuItem(); //Abrir
        menuItemAbrir.setText(jMenuItemTextAbrir); //Abrir
        menuItemAbrir.addActionListener(new AbrirListener(frame));
        menuItemGuardar = new javax.swing.JMenuItem(); //Guardar
        menuItemGuardar.setText(jMenuItemTextGuardar); //Guardar
        menuItemGuardar.addActionListener(new GuardarListener(frame));
        menuItemIdioma = new javax.swing.JMenu(); //Menu Idioma
        menuItemIdioma.setText(jMenuTextIdioma); //Idioma
        for (int i = 0; i < idiomas.getNumIdiomas(); i++) {
            JMenuItem menuItemAux = new JMenuItem(idiomas.getIdioma(i).get(33));
            menuItemAux.addActionListener(new IdiomaListener(i));
            menuItemIdioma.add(menuItemAux);
            menuItemAux.setIcon(new ImageIcon(idiomas.getImagenesIdioma(i).get(1).getImage().getScaledInstance(20, 20, Image.SCALE_SMOOTH)));
        }
        menuItemSalir = new javax.swing.JMenuItem(); //Salir
        menuItemSalir.setText(jMenuItemTextSalir); //Salir
        menuItemSalir.addActionListener(new CloseListener()); //Salir

        //Menu operaciones
        menuOperaciones = new javax.swing.JMenu();
        menuOperaciones.setText(jMenuItemTextOperaciones);

        menuItemNuevo = new javax.swing.JMenuItem(); //Nuevo
        menuItemNuevo.setText(jMenuItemTextNuevo); //Nuevo
        menuItemNuevo.addActionListener(new NuevoListener(inicio)); //Nuevo

        menuItemBorrar = new javax.swing.JMenuItem(); //Borrar
        menuItemBorrar.setText(jMenuItemTextBorrar); //Borrar
        menuItemBorrar.addActionListener(new EliminarListener(inicio)); //Borrar

        menuItemModificar = new javax.swing.JMenuItem(); //Modificar
        menuItemModificar.setText(jMenuItemTextModificar); //Modificar
        menuItemModificar.addActionListener(new ModificarListener(inicio)); //modificar
        
        menuItemConsultar = new javax.swing.JMenuItem();
        menuItemConsultar.setText(jMenuItemTextConsultar);
        menuItemConsultar.addActionListener(new ConsultarListener(inicio));

        //Menu Ayuda
        menuItemAyuda = new javax.swing.JMenuItem();
        menuItemAyuda.setText(jMenuItemTextAyuda);
        menuItemAyuda.addActionListener(new AyudaListener(frame));

        menuArchivo.add(menuItemAbrir);
        menuArchivo.add(menuItemGuardar);
        menuArchivo.add(menuItemSalir);
        menuOperaciones.add(menuItemNuevo);
        menuOperaciones.add(menuItemBorrar);
        menuOperaciones.add(menuItemModificar);
        menuOperaciones.add(menuItemConsultar);
        
        //menuAyuda.add(menuItemAyuda);
        menuBar.add(menuArchivo);
        menuBar.add(menuOperaciones);
        menuBar.add(menuItemIdioma);
        menuBar.add(menuItemAyuda);
    }

    static class IdiomaListener implements ActionListener {

        private final int idioma;

        public IdiomaListener(int idioma) {
            this.idioma = idioma;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            inicio.setIdioma(idiomas.getIdioma(idioma), idiomas.getImagenesIdioma(idioma));
            cambiarIdioma(idioma);
        }
    }

    static class AbrirListener implements ActionListener {

        private final JFrame frame;

        public AbrirListener(JFrame frame) {
            this.frame = frame;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            JFileChooser selectorArchivos = new JFileChooser();
            selectorArchivos.setCurrentDirectory(new File(System.getProperty("user.dir")));
            selectorArchivos.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
            FileNameExtensionFilter filter = new FileNameExtensionFilter(
                    ".tsv", "tsv");
            selectorArchivos.setFileFilter(filter);
            int resultado = selectorArchivos.showOpenDialog(frame);

            if (resultado == JFileChooser.APPROVE_OPTION) {
                File archivo = selectorArchivos.getSelectedFile();
                System.out.println("Fichero cargado: " + archivo.getAbsolutePath());
                cargarDatos(archivo.getAbsolutePath());
            }
        }
    }

    static class GuardarListener implements ActionListener {

        private final JFrame frame;

        public GuardarListener(JFrame frame) {
            this.frame = frame;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            inicio.guardarDatos();
        }
    }

    static class ModificarListener implements ActionListener {

        Inicio inicio;

        public ModificarListener(Inicio JPanel) {
            this.inicio = JPanel;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            inicio.gestionarEdicion(inicio);
        }
    }
    
    static class ConsultarListener implements ActionListener {

        Inicio inicio;

        public ConsultarListener(Inicio JPanel) {
            this.inicio = JPanel;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            inicio.gestionarConsulta(inicio);
        }
    }

    static class NuevoListener implements ActionListener {

        Inicio inicio;

        public NuevoListener(Inicio JPanel) {
            this.inicio = JPanel;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            Libro libro = new Libro();
            inicio.nuevo = new Nuevo(inicio, libro, inicio.getIdioma(), inicio.getImagenes());

            inicio.dialogoEmergente = new JDialog(frame, inicio.getIdioma().get(5), true);
            inicio.dialogoEmergente.setSize(new Dimension(450, 325));
            inicio.dialogoEmergente.setLocationRelativeTo(frame);
            inicio.dialogoEmergente.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
            inicio.dialogoEmergente.add(inicio.nuevo);
            inicio.dialogoEmergente.getRootPane().setDefaultButton(inicio.nuevo.saveButton);

            inicio.dialogoEmergente.setVisible(true);
        }
    }

    static class EliminarListener implements ActionListener {

        Inicio inicio;

        public EliminarListener(Inicio JPanel) {
            this.inicio = JPanel;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            
            inicio.gestionarBorrado(inicio);

//            int elementoSeleccionado = inicio.cuadroBuscar();
//
//            switch (elementoSeleccionado) {
//                case -1 ->
//                    inicio.mostrarError(28, inicio);
//                case -2 ->
//                    System.out.println("El usuario ha cancelado la operación");
//                default -> {
//                    Object[] opciones = {inicio.getIdioma().get(24), inicio.getIdioma().get(25)};
//                    int confirm = JOptionPane.showOptionDialog(inicio, inicio.getIdioma().get(22) + " (" + inicio.getVectorLibros().get(elementoSeleccionado).getNombre() + ")", inicio.getIdioma().get(23), JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, opciones, opciones[1]);
//
//                    switch (confirm) {
//                        case JOptionPane.YES_OPTION -> {
//                            inicio.getListModel().removeElementAt(elementoSeleccionado);
//                            inicio.getVectorLibros().remove(elementoSeleccionado);
//                        }
//                        default -> {
//                        }
//                    }
//                }
//            }
        }
    }

    static class AyudaListener implements ActionListener {

        private final JFrame framePadre;

        public AyudaListener(JFrame frame) {
            this.framePadre = frame;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            textoAyuda = textoAyuda.replace("\\n", "\n");
            textoAyuda = textoAyuda.replace("\\", "");
            JOptionPane.showMessageDialog(framePadre, textoAyuda, tituloAyuda, JOptionPane.INFORMATION_MESSAGE);
        }
    }

    static private class CloseListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            System.exit(0);
        }
    }

    /**
     *
     * @param cual el idioma al que se quiera cambiar, por orden de aparición en
     * el fichero de idiomas
     */
    public static void cambiarIdioma(int cual) {
        jMenuTextArchivo = idiomas.getIdioma(cual).get(1);
        jMenuTextIdioma = idiomas.getIdioma(cual).get(2);
        jMenuItemTextSalir = idiomas.getIdioma(cual).get(3);
        jMenuItemTextOperaciones = idiomas.getIdioma(cual).get(30);
        jMenuItemTextNuevo = idiomas.getIdioma(cual).get(5);
        jMenuItemTextBorrar = idiomas.getIdioma(cual).get(6);
        jMenuItemTextModificar = idiomas.getIdioma(cual).get(20);
        jMenuTextAyuda = idiomas.getIdioma(cual).get(4);
        jMenuItemTextAbrir = idiomas.getIdioma(cual).get(11);
        jMenuItemTextGuardar = idiomas.getIdioma(cual).get(12);
        jMenuItemTextAyuda = idiomas.getIdioma(cual).get(13);
        textoAyuda = idiomas.getIdioma(cual).get(14);
        frame.setTitle(idiomas.getIdioma(cual).get(29));

        menuArchivo.setText(jMenuTextArchivo);//Archivo
        menuItemIdioma.setText(jMenuTextIdioma); //Idioma
        menuItemSalir.setText(jMenuItemTextSalir);//Salir
        menuItemAbrir.setText(jMenuItemTextAbrir);//Abrir
        menuItemGuardar.setText(jMenuItemTextGuardar);//Guardar
        menuOperaciones.setText(jMenuItemTextOperaciones);//Operaciones
        menuItemNuevo.setText(jMenuItemTextNuevo);//Nuevo
        menuItemBorrar.setText(jMenuItemTextBorrar);//Borrar
        menuItemModificar.setText(jMenuItemTextModificar);//Modificar
        menuItemAyuda.setText(jMenuItemTextAyuda);//Ayuda
    }

    /**
     *
     * @param ruta ruta donde se encuentra el archivo con los datos que se
     * quieran cargar
     */
    public static void cargarDatos(String ruta) {
        inicio.cargarDatos(ruta);
    }
}
