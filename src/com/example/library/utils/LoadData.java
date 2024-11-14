package com.example.library.utils;

import com.example.library.Book;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;
import java.util.Vector;

public class LoadData {

    private final Vector<Book> books;


    public LoadData(String filename) {
        books = new Vector<>();
        File file = null;
        Scanner scanner = null;
        try {
            file = new File(filename);
            scanner = new Scanner(file);
            while (scanner.hasNext()) {
                String line = scanner.nextLine();
                String[] tokens = line.split(" {2}");
                books.add(new Book(tokens[0], tokens[1], tokens[2], tokens[3]));
            }
        } catch (NullPointerException | FileNotFoundException e) {
            //TODO lanzar ventana emergente
            e.printStackTrace();
        }
    }

    public Vector<Book> getBooks() {
        return this.books;
    }
}
