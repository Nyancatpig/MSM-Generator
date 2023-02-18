package com.ncpbails.dndGenerator;

import java.awt.*;
import java.awt.*;
import javax.swing.*;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.nio.file.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.List;

public class Main {
    static Color backgroundColour = new Color(58, 60, 66);
    static Color topColour = new Color(52, 54, 59);
    static Color textBoxColour = new Color(66, 69, 76);
    static Color titleColour = new Color(164, 165, 169);
    static Color optionColour = new Color(192, 193, 194);
    static Color buttonColour = new Color(51, 55, 60);

    public static void main(String[] args) {
        // Get absolute path of the files directory
        File filesDir = new File("files");
        String absPath = filesDir.getAbsolutePath();

        // Create array from file
        ArrayList<String> defaultElements = new ArrayList<String>();
        try {
            FileReader fileReader = new FileReader(absPath + "/filesMonster Array.txt");
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                defaultElements.add(line);
            }
            bufferedReader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        String[] allDefaultElements = defaultElements.toArray(new String[defaultElements.size()]);

        //Create Window
        JFrame mainWindow = new JFrame("MSM Generator");
        mainWindow.setSize(600, 400);
        mainWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainWindow.getContentPane().setBackground(backgroundColour);
        mainWindow.setVisible(true);
        mainWindow.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 10));

        //Create Nested Panel
        JPanel nestedPanel = new JPanel();
        nestedPanel.setLayout(new BoxLayout(nestedPanel, BoxLayout.PAGE_AXIS));
        nestedPanel.setBackground(backgroundColour);
        mainWindow.add(nestedPanel);

        //Create Blank Array Button
        JButton saveButton = new JButton("Create Blank Monster Arrays");
        saveButton.setBackground(buttonColour);
        saveButton.setForeground(titleColour);
        saveButton.setBorderPainted(false);
        saveButton.setHorizontalAlignment(SwingConstants.CENTER);
        saveButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        mainWindow.add(saveButton);
        nestedPanel.add(saveButton, BorderLayout.NORTH);
        nestedPanel.add(Box.createRigidArea(new Dimension(0, 10)));

        //Listen to Blank Array Button
        saveButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent saveClick) {
                String[] elements = {"A", "B", "C", "D", "E", "F", "G", "H"};
                int len = elements.length;
                List<String> combos = new ArrayList<>();
                for (int i = 1; i <= len; i++) {
                    generateCombinations(elements, i, 0, new StringBuilder(), combos);
                }

                // Generate a timestamp to use as the filename suffix
                DateFormat dateFormat = new SimpleDateFormat("yyyyMMdd_HHmmss");
                String filenameSuffix = dateFormat.format(new Date());

                //Create the filename using the original filename and the timestamp suffix
                //String newFilename = absPath + "/files" + "Monster Array" + filenameSuffix + ".txt";
                String newFilename = (absPath + "Monster Array" + ".txt");

                // Create a new thread to handle the file writing
                new Thread(new Runnable() {
                    public void run() {
                        //Open a new PrintWriter with the new filename
                        try (PrintWriter writer = new PrintWriter(newFilename)) {
                            //Split the text of the label into an array of strings
                            //Write each element of the array to the file
                            for (String combos : combos) {
                                writer.println(combos);
                            }
                        } catch (FileNotFoundException e) {
                            e.printStackTrace();
                        }
                    }
                }).start();

                for (String combo : combos) {
                    //Create the filename using the original filename, the element in the array, and the timestamp suffix
                    String comboFileName = absPath + "/combinations/" + combo + ".txt";
                    //Open a new PrintWriter with the new filename
                    try (PrintWriter writer = new PrintWriter(comboFileName)) {
                        //Write the current element to the file
                        writer.println(combo);
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }
                }

            }
        });

        //Create Buttons for each 5 Element Combination
        JPanel buttonPanel = new JPanel(new GridLayout(0, 8, 10, 10));
        buttonPanel.setBackground(backgroundColour);
        for (String combo : allDefaultElements) {
            if (combo.length() == 5) {
                JButton comboButton = new JButton(combo);
                comboButton.setBackground(buttonColour);
                comboButton.setForeground(titleColour);
                comboButton.setBorderPainted(false);
                comboButton.setHorizontalAlignment(SwingConstants.CENTER);
                comboButton.setAlignmentX(Component.CENTER_ALIGNMENT);
                comboButton.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10)); // add padding
                buttonPanel.add(comboButton);
                //Listen to 5 Element Combo Button
                comboButton.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent saveClick) {
                        //Create 5 Elem Combo Window
                        JFrame comboWindow = new JFrame(combo);
                        comboWindow.setSize(600, 400);
                        comboWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                        comboWindow.getContentPane().setBackground(backgroundColour);
                        comboWindow.setVisible(true);
                        comboWindow.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 10));
                        final boolean[] canMakeTextbox = {true};

                        //Create 5 Elem Combo Nested Panel
                        JPanel comboNestedPanel = new JPanel();
                        comboNestedPanel.setLayout(new BoxLayout(comboNestedPanel, BoxLayout.PAGE_AXIS));
                        comboNestedPanel.setBackground(backgroundColour);
                        comboWindow.add(comboNestedPanel);

                        //Create Buttons for each Element Combo in 5 Elem Combination
                        JPanel comboButtonPanel = new JPanel(new GridLayout(0, 8, 10, 10));
                        comboButtonPanel.setBackground(backgroundColour);
                        char[] charArray = combo.toCharArray();
                        String[] fiveElements = new String[charArray.length];
                        for (int i = 0; i < charArray.length; i++) {
                            fiveElements[i] = Character.toString(charArray[i]);
                        }
                        int len = fiveElements.length;
                        List<String> fives = new ArrayList<>();
                        for (int i = 1; i <= len; i++) {
                            generateCombinations(fiveElements, i, 0, new StringBuilder(), fives);
                        }

                        for (String fivo : fives) {
                            JButton fivoButton = new JButton(fivo);
                            fivoButton.setBackground(buttonColour);
                            fivoButton.setForeground(titleColour);
                            fivoButton.setBorderPainted(false);
                            fivoButton.setHorizontalAlignment(SwingConstants.CENTER);
                            fivoButton.setAlignmentX(Component.CENTER_ALIGNMENT);
                            fivoButton.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10)); // add padding
                            comboButtonPanel.add(fivoButton);
                            //Listen to Fivo Array Button
                            fivoButton.addActionListener(new ActionListener() {
                                public void actionPerformed(ActionEvent saveClick) {

                                }
                            });
                            //Create Text Boxes for the text relating to each Element Combo in 5 Elem Combination
                            Path path = Paths.get(absPath + "/combinations/" + fivo + ".txt");
                            try {
                                // create watch service and register the path to watch for changes
                                WatchService watchService = FileSystems.getDefault().newWatchService();
                                path.getParent().register(watchService, StandardWatchEventKinds.ENTRY_MODIFY);

                                //Create Text Box
                                String textName = new String(Files.readAllBytes(path));
                                JTextField textField = new JTextField(textName, 2);
                                textField.setBackground(textBoxColour);
                                textField.setForeground(titleColour);
                                textField.setCaretColor(optionColour);
                                textField.setBorder(BorderFactory.createLineBorder(textBoxColour));
                                textField.setHorizontalAlignment(SwingConstants.CENTER);
                                textField.setAlignmentX(Component.CENTER_ALIGNMENT);
                                textField.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10)); // add padding
                                comboWindow.add(textField);
                                comboButtonPanel.add(textField);
                                comboWindow.revalidate();
                                comboWindow.repaint();

                                //Listen to Text Box
                                textField.addActionListener(event -> {
                                    if (textField.getText().chars().allMatch(Character::isDigit)) {
                                        int itemTextNum = Integer.parseInt(textField.getText());
                                        if (itemTextNum > 0 && itemTextNum < 9) {
                                            try (BufferedWriter writer = new BufferedWriter(new FileWriter(path.toFile()))) {
                                                writer.write(textField.getText());
                                                comboWindow.revalidate();
                                                comboWindow.repaint();
                                            } catch (IOException e) {
                                                e.printStackTrace();
                                            }
                                        } else {
                                            textField.setText(textName);
                                        }
                                    } else {
                                        textField.setText(textName);
                                    }
                                });

                                //Update Text when File is Changed
                                new Thread(() -> {
                                    try {
                                        while (true) {
                                            WatchKey key = watchService.take(); // wait for a file change
                                            for (WatchEvent<?> event : key.pollEvents()) {
                                                Path changedPath = (Path) event.context();
                                                if (changedPath.getFileName().equals(path.getFileName())) {
                                                    textField.setText(new String(Files.readAllBytes(path)));
                                                }
                                            }
                                            key.reset();
                                        }
                                    } catch (InterruptedException | IOException e) {
                                        throw new RuntimeException(e);
                                    }
                                }).start();
                            } catch (IOException e) {
                                throw new RuntimeException(e);
                            }
                        }
                        comboWindow.add(comboButtonPanel);
                        comboNestedPanel.add(comboButtonPanel, BorderLayout.NORTH);
                        comboWindow.revalidate();
                        comboWindow.repaint();
                    }
                });
            }
        mainWindow.add(buttonPanel);
        nestedPanel.add(buttonPanel, BorderLayout.NORTH);
        mainWindow.revalidate();
        mainWindow.repaint();
        }
    }
    //Generate Combinations for Array Maker
    private static void generateCombinations(String[] elements, int len, int start, StringBuilder sb, List<String> combos) {
        if (sb.length() == len) {
            combos.add(sb.toString());
            return;
        }
        for (int i = start; i < elements.length; i++) {
            sb.append(elements[i]);
            generateCombinations(elements, len, i + 1, sb, combos);
            sb.deleteCharAt(sb.length() - 1);
        }
    }
}