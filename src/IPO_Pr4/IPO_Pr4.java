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

    static private String jMenu1Text;
    static private String jMenu3Text;
    static private String jMenuItem2Text;
    static private String jMenu2Text;
    private static String jMenuItem3Text;
    private static String jMenuItem4Text;
    private static String jMenuItem5Text;
    private static String textoAyuda;
    private static String tituloAyuda;

    static private Idiomas idiomas;

    static private javax.swing.JMenu menuArchivo;
    static private javax.swing.JMenu menuAyuda;
    static private javax.swing.JMenuBar menuBar;
    static private javax.swing.JMenuItem menuItemSalir;
    static private javax.swing.JMenuItem menuItemIdioma;
    static private javax.swing.JMenuItem menuItemAbrir;
    static private javax.swing.JMenuItem menuItemGuardar;
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

        jMenu1Text = idiomas.getIdioma(0).get(1);
        jMenu3Text = idiomas.getIdioma(0).get(2);
        jMenuItem2Text = idiomas.getIdioma(0).get(3);
        jMenu2Text = idiomas.getIdioma(0).get(4);
        jMenuItem3Text = idiomas.getIdioma(0).get(11);
        jMenuItem4Text = idiomas.getIdioma(0).get(12);
        jMenuItem5Text = idiomas.getIdioma(0).get(13);
        textoAyuda = idiomas.getIdioma(0).get(14);
        tituloAyuda = idiomas.getIdioma(0).get(21);
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
        menuArchivo = new javax.swing.JMenu();
        menuArchivo.setText(jMenu1Text);//Archivo
        menuItemAbrir = new javax.swing.JMenuItem();
        menuItemAbrir.setText(jMenuItem3Text);
        menuItemAbrir.addActionListener(new AbrirListener(frame));
        menuItemGuardar = new javax.swing.JMenuItem();
        menuItemGuardar.setText(jMenuItem4Text);
        menuItemGuardar.addActionListener(new GuardarListener(frame));
        menuItemIdioma = new javax.swing.JMenu();
        menuItemIdioma.setText(jMenu3Text); //Idioma
        for (int i = 0; i < idiomas.getNumIdiomas(); i++) {
            JMenuItem menuItemAux = new JMenuItem(idiomas.getIdioma(i).getFirst());
            menuItemAux.addActionListener(new IdiomaListener(i));
            menuItemIdioma.add(menuItemAux);
            menuItemAux.setIcon(new ImageIcon(new ImageIcon("images/" + idiomas.getIdioma(i).getFirst() + ".png").getImage().getScaledInstance(20, 20, Image.SCALE_SMOOTH)));
        }
        menuItemSalir = new javax.swing.JMenuItem();
        menuItemSalir.setText(jMenuItem2Text);//Salir
        menuItemSalir.addActionListener(new CloseListener());
        menuAyuda = new javax.swing.JMenu();
        menuAyuda.setText(jMenu2Text); //Ayuda
        menuItemAyuda = new javax.swing.JMenuItem();
        menuItemAyuda.setText(jMenuItem5Text);
        menuItemAyuda.addActionListener(new AyudaListener(frame));

        menuArchivo.add(menuItemAbrir);
        menuArchivo.add(menuItemGuardar);
        menuArchivo.add(menuItemIdioma);
        menuArchivo.add(menuItemSalir);
        menuAyuda.add(menuItemAyuda);
        menuBar.add(menuArchivo);
        menuBar.add(menuAyuda);
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
            selectorArchivos.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
            FileNameExtensionFilter filter = new FileNameExtensionFilter(
                    ".txt", "txt");
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
        jMenu1Text = idiomas.getIdioma(cual).get(1);
        jMenu3Text = idiomas.getIdioma(cual).get(2);
        jMenuItem2Text = idiomas.getIdioma(cual).get(3);
        jMenu2Text = idiomas.getIdioma(cual).get(4);
        jMenuItem3Text = idiomas.getIdioma(cual).get(11);
        jMenuItem4Text = idiomas.getIdioma(cual).get(12);
        jMenuItem5Text = idiomas.getIdioma(cual).get(13);
        textoAyuda = idiomas.getIdioma(cual).get(14);
        frame.setTitle(idiomas.getIdioma(cual).get(29));

        menuArchivo.setText(jMenu1Text);//Archivo
        menuItemIdioma.setText(jMenu3Text); //Idioma
        menuItemSalir.setText(jMenuItem2Text);//Salir
        menuAyuda.setText(jMenu2Text);
        menuItemAbrir.setText(jMenuItem3Text);
        menuItemGuardar.setText(jMenuItem4Text);
        menuItemAyuda.setText(jMenuItem5Text);
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
