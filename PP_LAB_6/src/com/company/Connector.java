package com.company;

import java.io.*;

public class Connector
{
    private File file;

    public File getFile() {
        return file;
    }

    public Connector(String filename) {
        this.file = new File(filename);
    }

    public Connector(File file) {
        this.file = file;
    }

    public void write(OnlineStore onlineStore) throws IOException {
        FileOutputStream fos = new FileOutputStream(file);
        try (ObjectOutputStream oos = new ObjectOutputStream(fos)) {
            oos.writeObject(onlineStore);
            oos.flush();
        }
    }

    public OnlineStore read() throws IOException, ClassNotFoundException {
        FileInputStream fis = new FileInputStream(file);
        try (ObjectInputStream oin = new ObjectInputStream(fis)) {
            OnlineStore store = (OnlineStore) oin.readObject();
            return store;
        }
    }
}
