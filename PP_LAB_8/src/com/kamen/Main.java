package com.kamen;

import com.Lab7Engine.KeyNotUniqueException;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;

public class Main {
    static final String Append = "Append";
    static final String Delete = "Delete";
    static final String Print = "Print";
    static final String Find = "Find";

    static final String Name = "Name";
    static final String BusNumber = "Bus";
    static final String RouteNumber = "Route";

    static final String None = "None";
    static final String Ascending = "Ascending";
    static final String Descending = "Descending";

    static final String Equal = "Equal";
    static final String Less = "Less";
    static final String More = "More";


    private JPanel panelMain;

    private JComboBox<String> commandsComboBox;

    private JButton buttonExec;

    private JTextPane outputTextPane;

    //append
    private JPanel appendPanel;
    private JTextField filenameTextField;
    private JButton buttonFileChoose;
    private JCheckBox compressCheckBox;

    //delete
    private JPanel deletePanel;
    private JCheckBox delByKeyCheckBox;
    private JComboBox<String> delKeyComboBox;
    private JTextField delKeyTextField;

    //print
    private JPanel printPanel;
    private JComboBox<String> sortComboBox;
    private JComboBox<String> printKeyComboBox;

    //find
    private JPanel findPanel;
    private JComboBox comparisonComboBox;
    private JComboBox findKeyComboBox;
    private JTextField findKeyTextField;

    public Main() {
        //fill commands
        commandsComboBox.addItem(Append);
        commandsComboBox.addItem(Delete);
        commandsComboBox.addItem(Print);
        commandsComboBox.addItem(Find);

        //append
        appendPanel.setVisible(true);

        //delete
        deletePanel.setVisible(false);
        delKeyComboBox.addItem(Name);
        delKeyComboBox.addItem(BusNumber);
        delKeyComboBox.addItem(RouteNumber);

        //print
        printPanel.setVisible(false);
        printKeyComboBox.addItem(Name);
        printKeyComboBox.addItem(BusNumber);
        printKeyComboBox.addItem(RouteNumber);
        sortComboBox.addItem(None);
        sortComboBox.addItem(Ascending);
        sortComboBox.addItem(Descending);

        //find
        findPanel.setVisible(false);
        findKeyComboBox.addItem(Name);
        findKeyComboBox.addItem(BusNumber);
        findKeyComboBox.addItem(RouteNumber);
        comparisonComboBox.addItem(Equal);
        comparisonComboBox.addItem(Less);
        comparisonComboBox.addItem(More);

        //listeners
        buttonExec.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                String selected = (String) commandsComboBox.getSelectedItem();
                outputTextPane.setText("In process...");
                if (selected.equals(Append)) {
                    try {
                        Append();
                        outputTextPane.setText("Done!");
                    } catch (Exception e) {
                        JOptionPane.showMessageDialog(panelMain, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                        outputTextPane.setText(e.getMessage());
                    }
                }
                else if (selected.equals(Delete)) {
                    try {
                        Delete();
                        outputTextPane.setText("Done!");
                    } catch (Exception e) {
                        JOptionPane.showMessageDialog(panelMain, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                        outputTextPane.setText(e.getMessage());
                    }
                }
                else if (selected.equals(Print)) {
                    try {
                        Print();
                    } catch (Exception e) {
                        JOptionPane.showMessageDialog(panelMain, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                        outputTextPane.setText(e.getMessage());
                    }
                }
                else if (selected.equals(Find)) {
                    try {
                        Find();
                    } catch (Exception e) {
                        JOptionPane.showMessageDialog(panelMain, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                        outputTextPane.setText(e.getMessage());
                    }
                }
            }
        });
        buttonFileChoose.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                JFileChooser fileChooser = new JFileChooser();
                fileChooser.setCurrentDirectory(new File(System.getProperty("user.home")));
                int result = fileChooser.showOpenDialog(panelMain);
                if (result == JFileChooser.APPROVE_OPTION) {
                    File selectedFile = fileChooser.getSelectedFile();
                    filenameTextField.setText(selectedFile.getAbsolutePath());
                }
            }
        });
        commandsComboBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                String selected = (String) commandsComboBox.getSelectedItem();
                appendPanel.setVisible(false);
                deletePanel.setVisible(false);
                printPanel.setVisible(false);
                findPanel.setVisible(false);

                if (selected.equals(Append)) {
                    appendPanel.setVisible(true);
                }
                else if (selected.equals(Delete)) {
                    deletePanel.setVisible(true);
                }
                else if (selected.equals(Print)) {
                    printPanel.setVisible(true);
                }
                else if (selected.equals(Find)) {
                    findPanel.setVisible(true);
                }
            }
        });

    }

    private void Append() throws NullPointerException, IOException, ClassNotFoundException, KeyNotUniqueException {
        if (filenameTextField.getText().trim().isEmpty())
            throw new NullPointerException("Filename field is empty");
        String[] args = new String[]{"-a", filenameTextField.getText().trim()};
        com.Lab7Engine.Main.appendFile(args, compressCheckBox.isSelected());
    }

    private void Delete() throws IOException, ClassNotFoundException, KeyNotUniqueException {
        OutputStream os = new java.io.ByteArrayOutputStream();
        com.Lab7Engine.Main.setOut(os);
        if (!delByKeyCheckBox.isSelected()) {
            com.Lab7Engine.Main.deleteFile();
        }
        else {
            String delBy = Character.toString(((String)delKeyComboBox.getSelectedItem()).toLowerCase().charAt(0));
            String[] args = new String[]{"-dk", delBy, delKeyTextField.getText().trim()};
            com.Lab7Engine.Main.deleteFile(args);
        }
        outputTextPane.setText(os.toString());
    }

    private void Print() throws IOException, ClassNotFoundException {
        String sort = (String)sortComboBox.getSelectedItem();
        OutputStream os = new java.io.ByteArrayOutputStream();
        com.Lab7Engine.Main.setOut(os);
        if (sort.equals(None)) {
            com.Lab7Engine.Main.printFile();
        }
        else {
            String sortBy = Character.toString(((String)printKeyComboBox.getSelectedItem()).toLowerCase().charAt(0));
            String[] args = new String[]{"-p", sortBy};
            com.Lab7Engine.Main.printFile(args, sort.equals(Descending));
        }
        outputTextPane.setText(os.toString());
    }

    private void Find() throws IOException, ClassNotFoundException {
        OutputStream os = new java.io.ByteArrayOutputStream();
        com.Lab7Engine.Main.setOut(os);

        String selectedComp = (String)comparisonComboBox.getSelectedItem();

        String findBy = Character.toString(((String)findKeyComboBox.getSelectedItem()).toLowerCase().charAt(0));
        String[] args = new String[]{"-f", findBy, findKeyTextField.getText()};

        if (selectedComp.equals(Equal))
        {
            com.Lab7Engine.Main.findByKey(args);
        }
        else if (selectedComp.equals(More))
        {
            com.Lab7Engine.Main.findByKey(args, new com.Lab7Engine.KeyCompReverse());
        }
        else {
            com.Lab7Engine.Main.findByKey(args, new com.Lab7Engine.KeyComp());
        }

        outputTextPane.setText(os.toString());
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("Main");
        frame.setContentPane(new Main().panelMain);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocation(400, 400);
        frame.setResizable(false);
        frame.pack();
        frame.setSize(600, 400);
        frame.setVisible(true);
    }
}
