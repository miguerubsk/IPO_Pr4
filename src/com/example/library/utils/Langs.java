package com.example.library.utils;

import javax.swing.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Vector;

public class Langs {

    private static int numLangs;
    private Map<String, Vector<String>> langs;
    private Map<String, Vector<ImageIcon>> images;

    public Langs(String file) {
        FileReader fr = null;
        String line = "";

        try {
            fr = new FileReader(file);
            BufferedReader br = new BufferedReader(fr);

            numLangs = Integer.parseInt(br.readLine());
            langs = new HashMap<>();
            images = new HashMap<>();

            for (int i = 0; i < numLangs; i++) {
                Vector<String> lang = new Vector<>();
                String langAux = br.readLine();
                int numWords = Integer.parseInt(br.readLine());
                for (int j = 0; j < numWords; j++) {
                    lang.add(br.readLine());
                }

                Vector<ImageIcon> imagesAux = new Vector<>();
                int numImages = Integer.parseInt(br.readLine());
                for (int j = 0; j < numImages; j++) {
                    imagesAux.add(new ImageIcon(br.readLine()));
                }

                langs.put(langAux, lang);
                images.put(langAux, imagesAux);
            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public Map<String, Vector<String>> getLangs() {
        return langs;
    }

    public Vector<String> getLang(String index) {
        return langs.get(index);
    }

    public Map<String, Vector<ImageIcon>> getImages() {
        return images;
    }

    public Vector<ImageIcon> getImagesLang(String index) {
        return images.get(index);
    }

    public int getNumLangs() {
        return numLangs;
    }
}
