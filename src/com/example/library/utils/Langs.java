package com.example.library.utils;

import javax.swing.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Vector;

public class Langs {

    private static int numLangs;
    private Vector<Vector<String>> langs;
    private Vector<Vector<ImageIcon>> images;

    public Langs(String file) {
        FileReader fr = null;
        String line = "";

        try {
            fr = new FileReader(file);
            BufferedReader br = new BufferedReader(fr);

            numLangs = Integer.parseInt(br.readLine());
            langs = new Vector<>();
            images = new Vector<>();

            for (int i = 0; i < numLangs; i++) {
                Vector<String> lang = new Vector<>();
                lang.add(br.readLine());
                int numWords = Integer.parseInt(br.readLine());
                for (int j = 0; j < numWords; j++) {
                    lang.add(br.readLine());
                }

                Vector<ImageIcon> imagesAux = new Vector<>();
                int numImages = Integer.parseInt(br.readLine());
                for (int j = 0; j < numImages; j++) {
                    imagesAux.add(new ImageIcon(br.readLine()));
                }

                langs.add(lang);
                images.add(imagesAux);
            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public Vector<Vector<String>> getLangs() {
        return langs;
    }

    public Vector<String> getLang(int index) {
        return langs.get(index);
    }

    public Vector<Vector<ImageIcon>> getImages() {
        return images;
    }

    public Vector<ImageIcon> getImagesLang(int index) {
        return images.get(index);
    }

    public int getNumLangs() {
        return numLangs;
    }
}
