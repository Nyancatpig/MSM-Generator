package com.ncpbails.dndGenerator;

import java.awt.*;
import java.awt.*;
import javax.swing.*;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
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
    static Color textBoxColour1 = new Color(89, 111, 163);
    static Color textBoxColour2 = new Color(118, 92, 150);
    static Color textBoxColour3 = new Color(163, 132, 152);
    static Color textBoxColour4 = new Color(142, 87, 101);
    static Color textBoxColour5 = new Color(147, 121, 102);
    static Color textBoxColour6 = new Color(147, 143, 92);
    static Color textBoxColour7 = new Color(84, 134, 110);
    static Color textBoxColour8 = new Color(117, 146, 157);

    static int islandElemCount = 4;

    public static void main(String[] args) throws IOException {
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
        mainWindow.setSize(750, 725);
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
        saveButton.setBackground(topColour);
        saveButton.setForeground(titleColour);
        saveButton.setBorderPainted(false);
        saveButton.setHorizontalAlignment(SwingConstants.CENTER);
        saveButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        mainWindow.add(saveButton);
        nestedPanel.add(saveButton, BorderLayout.NORTH);
        nestedPanel.add(Box.createRigidArea(new Dimension(0, 10)));

        //Listen to Blank Array Button, Open Double Check Window
        saveButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent saveClick) {
                //Create Elem Combo Window
                JFrame remakeWindow = new JFrame("Double Check");
                remakeWindow.setSize(300, 100);
                remakeWindow.getContentPane().setBackground(backgroundColour);
                remakeWindow.setVisible(true);
                remakeWindow.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 10));

                //Create Elem Combo Nested Panel
                JPanel remakeNestedPanel = new JPanel();
                remakeNestedPanel.setLayout(new BoxLayout(remakeNestedPanel, BoxLayout.PAGE_AXIS));
                remakeNestedPanel.setBackground(backgroundColour);
                remakeWindow.add(remakeNestedPanel);

                //Create Yes Button
                JButton yesButton = new JButton("Yes, Create New Array");
                yesButton.setBackground(buttonColour);
                yesButton.setForeground(titleColour);
                yesButton.setBorderPainted(false);
                yesButton.setHorizontalAlignment(SwingConstants.CENTER);
                yesButton.setAlignmentX(Component.CENTER_ALIGNMENT);
                remakeWindow.add(yesButton);
                remakeNestedPanel.add(yesButton, BorderLayout.NORTH);
                remakeNestedPanel.add(Box.createRigidArea(new Dimension(0, 10)));

                //Listen to Yes Button, Clear Array
                yesButton.addActionListener((new ActionListener() {
                    public void actionPerformed(ActionEvent actionEvent) {
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

                        //Create a new thread to handle the file writing
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
                        remakeWindow.dispose();
                    }
                }));
            }
        });

        //Create Buttons for each Element Combination
        JPanel buttonPanel = new JPanel(new GridLayout(0, 8, 10, 10));
        buttonPanel.setBackground(backgroundColour);
        for (String combo : allDefaultElements) {
            if (combo.length() == islandElemCount) {
                JButton comboButton = new JButton(combo);
                comboButton.setBackground(buttonColour);
                comboButton.setForeground(titleColour);
                comboButton.setBorderPainted(false);
                comboButton.setHorizontalAlignment(SwingConstants.CENTER);
                comboButton.setAlignmentX(Component.CENTER_ALIGNMENT);
                comboButton.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10)); // add padding
                buttonPanel.add(comboButton);

                //Create Combo Panel
                JPanel comboDisplay = new JPanel(new GridLayout(0, 8));
                comboDisplay.setBackground(textBoxColour);
                comboDisplay.setForeground(titleColour);
                comboDisplay.setAlignmentX(Component.CENTER_ALIGNMENT);
                comboDisplay.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10)); // add padding
                buttonPanel.add(comboDisplay);

                //Create Combo Panel Info
                char[] charaArray = combo.toCharArray();
                String[] fiveElement = new String[charaArray.length]; //Create array of every element in combo
                for (int i = 0; i < charaArray.length; i++) {
                    fiveElement[i] = Character.toString(charaArray[i]);
                }
                int len = fiveElement.length;
                List<String> fivez = new ArrayList<>(); //Create all combos elements
                for (int i = 1; i <= len; i++) {
                    generateCombinations(fiveElement, i, 0, new StringBuilder(), fivez);
                }

                List<String> arrayOf1s = new ArrayList<>();
                List<String> arrayOf2s = new ArrayList<>();
                List<String> arrayOf3s = new ArrayList<>();
                List<String> arrayOf4s = new ArrayList<>();
                List<String> arrayOf5s = new ArrayList<>();
                List<String> arrayOf6s = new ArrayList<>();
                List<String> arrayOf7s = new ArrayList<>();
                List<String> arrayOf8s = new ArrayList<>();

                for (String fivoz : fivez) {
                    Path path5 = Paths.get(absPath + "/combinations/" + fivoz + ".txt");
                    if (Files.readString(path5).equals("1")) {
                        arrayOf1s.add(fivoz);
                    } else if (Files.readString(path5).equals("2")) {
                        arrayOf2s.add(fivoz);
                    } else if (Files.readString(path5).equals("3")) {
                        arrayOf3s.add(fivoz);
                    } else if (Files.readString(path5).equals("4")) {
                        arrayOf4s.add(fivoz);
                    } else if (Files.readString(path5).equals("5")) {
                        arrayOf5s.add(fivoz);
                    } else if (Files.readString(path5).equals("6")) {
                        arrayOf6s.add(fivoz);
                    } else if (Files.readString(path5).equals("7")) {
                        arrayOf7s.add(fivoz);
                    } else if (Files.readString(path5).equals("8")) {
                        arrayOf8s.add(fivoz);
                    }
                }

                JLabel comboDisplayInfo1 = new JLabel(String.valueOf(arrayOf1s.toArray().length));
                comboDisplayInfo1.setForeground(textBoxColour1);
                comboDisplayInfo1.setHorizontalAlignment(SwingConstants.CENTER);
                comboDisplayInfo1.setAlignmentX(Component.CENTER_ALIGNMENT);
                comboDisplay.add(comboDisplayInfo1);
                JLabel comboDisplayInfo2 = new JLabel(String.valueOf(arrayOf2s.toArray().length));
                comboDisplayInfo2.setForeground(textBoxColour2);
                comboDisplayInfo2.setHorizontalAlignment(SwingConstants.CENTER);
                comboDisplayInfo2.setAlignmentX(Component.CENTER_ALIGNMENT);
                comboDisplay.add(comboDisplayInfo2);
                JLabel comboDisplayInfo3 = new JLabel(String.valueOf(arrayOf3s.toArray().length));
                comboDisplayInfo3.setForeground(textBoxColour3);
                comboDisplayInfo3.setHorizontalAlignment(SwingConstants.CENTER);
                comboDisplayInfo3.setAlignmentX(Component.CENTER_ALIGNMENT);
                comboDisplay.add(comboDisplayInfo3);
                JLabel comboDisplayInfo4 = new JLabel(String.valueOf(arrayOf4s.toArray().length));
                comboDisplayInfo4.setForeground(textBoxColour4);
                comboDisplayInfo4.setHorizontalAlignment(SwingConstants.CENTER);
                comboDisplayInfo4.setAlignmentX(Component.CENTER_ALIGNMENT);
                comboDisplay.add(comboDisplayInfo4);
                JLabel comboDisplayInfo5 = new JLabel(String.valueOf(arrayOf5s.toArray().length));
                comboDisplayInfo5.setForeground(textBoxColour5);
                comboDisplayInfo5.setHorizontalAlignment(SwingConstants.CENTER);
                comboDisplayInfo5.setAlignmentX(Component.CENTER_ALIGNMENT);
                comboDisplay.add(comboDisplayInfo5);
                JLabel comboDisplayInfo6 = new JLabel(String.valueOf(arrayOf6s.toArray().length));
                comboDisplayInfo6.setForeground(textBoxColour6);
                comboDisplayInfo6.setHorizontalAlignment(SwingConstants.CENTER);
                comboDisplayInfo6.setAlignmentX(Component.CENTER_ALIGNMENT);
                comboDisplay.add(comboDisplayInfo6);
                JLabel comboDisplayInfo7 = new JLabel(String.valueOf(arrayOf7s.toArray().length));
                comboDisplayInfo7.setForeground(textBoxColour7);
                comboDisplayInfo7.setHorizontalAlignment(SwingConstants.CENTER);
                comboDisplayInfo7.setAlignmentX(Component.CENTER_ALIGNMENT);
                comboDisplay.add(comboDisplayInfo7);
                JLabel comboDisplayInfo8 = new JLabel(String.valueOf(arrayOf8s.toArray().length));
                comboDisplayInfo8.setForeground(textBoxColour8);
                comboDisplayInfo8.setHorizontalAlignment(SwingConstants.CENTER);
                comboDisplayInfo8.setAlignmentX(Component.CENTER_ALIGNMENT);
                comboDisplay.add(comboDisplayInfo8);

                for (String fiveez : fivez) {
                    Path path = Paths.get(absPath + "/combinations/" + fiveez +".txt");

                    // create watch service and register the path to watch for changes
                    WatchService watchService = FileSystems.getDefault().newWatchService();
                    path.getParent().register(watchService, StandardWatchEventKinds.ENTRY_MODIFY);

                    //Update Text when File is Changed
                    new Thread(() -> {
                        try {
                            while (true) {
                                WatchKey key = watchService.take(); // wait for a file change
                                for (WatchEvent<?> event : key.pollEvents()) {
                                    Path changedPath = (Path) event.context();
                                    if (changedPath.getFileName().equals(path.getFileName())) {
                                        if (arrayOf1s.contains(fiveez)) {
                                            arrayOf1s.remove(fiveez);
                                        } else if (arrayOf2s.contains(fiveez)) {
                                            arrayOf2s.remove(fiveez);
                                        } else if (arrayOf3s.contains(fiveez)) {
                                            arrayOf3s.remove(fiveez);
                                        } else if (arrayOf4s.contains(fiveez)) {
                                            arrayOf4s.remove(fiveez);
                                        } else if (arrayOf5s.contains(fiveez)) {
                                            arrayOf5s.remove(fiveez);
                                        } else if (arrayOf6s.contains(fiveez)) {
                                            arrayOf6s.remove(fiveez);
                                        } else if (arrayOf7s.contains(fiveez)) {
                                            arrayOf7s.remove(fiveez);
                                        } else if (arrayOf8s.contains(fiveez)) {
                                            arrayOf8s.remove(fiveez);
                                        }

                                        if (Files.readString(path).equals("1")) {
                                            arrayOf1s.add(fiveez);
                                        } else if (Files.readString(path).equals("2")) {
                                            arrayOf2s.add(fiveez);
                                        } else if (Files.readString(path).equals("3")) {
                                            arrayOf3s.add(fiveez);
                                        } else if (Files.readString(path).equals("4")) {
                                            arrayOf4s.add(fiveez);
                                        } else if (Files.readString(path).equals("5")) {
                                            arrayOf5s.add(fiveez);
                                        } else if (Files.readString(path).equals("6")) {
                                            arrayOf6s.add(fiveez);
                                        } else if (Files.readString(path).equals("7")) {
                                            arrayOf7s.add(fiveez);
                                        } else if (Files.readString(path).equals("8")) {
                                            arrayOf8s.add(fiveez);
                                        }

                                        comboDisplayInfo1.setText(String.valueOf(arrayOf1s.toArray().length));
                                        comboDisplayInfo2.setText(String.valueOf(arrayOf2s.toArray().length));
                                        comboDisplayInfo3.setText(String.valueOf(arrayOf3s.toArray().length));
                                        comboDisplayInfo4.setText(String.valueOf(arrayOf4s.toArray().length));
                                        comboDisplayInfo5.setText(String.valueOf(arrayOf5s.toArray().length));
                                        comboDisplayInfo6.setText(String.valueOf(arrayOf6s.toArray().length));
                                        comboDisplayInfo7.setText(String.valueOf(arrayOf7s.toArray().length));
                                        comboDisplayInfo8.setText(String.valueOf(arrayOf8s.toArray().length));

                                        if (arrayOf1s.toArray().length > 4) {
                                            comboButton.setBackground(textBoxColour1);
                                        } else if (arrayOf2s.toArray().length > 4) {
                                            comboButton.setBackground(textBoxColour2);
                                        } else if (arrayOf3s.toArray().length > 4) {
                                            comboButton.setBackground(textBoxColour3);
                                        } else if (arrayOf4s.toArray().length > 4) {
                                            comboButton.setBackground(textBoxColour4);
                                        } else if (arrayOf5s.toArray().length > 4) {
                                            comboButton.setBackground(textBoxColour5);
                                        } else if (arrayOf6s.toArray().length > 4) {
                                            comboButton.setBackground(textBoxColour6);
                                        } else if (arrayOf7s.toArray().length > 4) {
                                            comboButton.setBackground(textBoxColour7);
                                        } else if (arrayOf8s.toArray().length > 4) {
                                            comboButton.setBackground(textBoxColour8);
                                        } else {
                                            comboButton.setBackground(buttonColour);
                                        }
                                    }
                                }
                                key.reset();
                            }
                        } catch (InterruptedException e) {
                            throw new RuntimeException(e);
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                    }).start();
                }
                //Listen to Element Combo Info Being Clicked
                comboDisplay.addMouseListener(new MouseAdapter() {
                    @Override
                    public void mouseClicked(MouseEvent e) {
                        // Handle click event here
                        Random random = new Random();
                        String removedCombo = "Null Element";
                        int islandMax = 0;
                        if (islandElemCount == 5) {
                            islandMax = 4;
                        } else if (islandElemCount == 4) {
                            islandMax = 2;
                        } else if (islandElemCount <= 3) {
                            islandMax = 1;
                        } else if (islandElemCount == 6) {
                            islandMax = 6;
                        } else if (islandElemCount == 7) {
                            islandMax = 8;
                        } else if (islandElemCount == 8) {
                            islandMax = 10;
                        }

                        if (arrayOf1s.toArray().length > islandMax) {
                            int randomIndex = random.nextInt(arrayOf1s.size());
                            removedCombo = arrayOf1s.get(randomIndex);
                            arrayOf1s.remove(arrayOf1s.get(randomIndex));
                            resetNum(random, absPath, removedCombo, mainWindow, comboButton,
                                    arrayOf1s, arrayOf2s, arrayOf3s, arrayOf4s, arrayOf5s, arrayOf6s, arrayOf7s, arrayOf8s);
                        }
                        else if (arrayOf2s.toArray().length > islandMax) {
                            int randomIndex = random.nextInt(arrayOf2s.size());
                            removedCombo = arrayOf2s.get(randomIndex);
                            arrayOf2s.remove(arrayOf2s.get(randomIndex));
                            resetNum(random, absPath, removedCombo, mainWindow, comboButton,
                                    arrayOf1s, arrayOf2s, arrayOf3s, arrayOf4s, arrayOf5s, arrayOf6s, arrayOf7s, arrayOf8s);
                        }
                        else if (arrayOf3s.toArray().length > islandMax) {
                            int randomIndex = random.nextInt(arrayOf3s.size());
                            removedCombo = arrayOf3s.get(randomIndex);
                            arrayOf3s.remove(arrayOf3s.get(randomIndex));
                            resetNum(random, absPath, removedCombo, mainWindow, comboButton,
                                    arrayOf1s, arrayOf2s, arrayOf3s, arrayOf4s, arrayOf5s, arrayOf6s, arrayOf7s, arrayOf8s);
                        }
                        else if (arrayOf4s.toArray().length > islandMax) {
                            int randomIndex = random.nextInt(arrayOf4s.size());
                            removedCombo = arrayOf4s.get(randomIndex);
                            arrayOf4s.remove(arrayOf4s.get(randomIndex));
                            resetNum(random, absPath, removedCombo, mainWindow, comboButton,
                                    arrayOf1s, arrayOf2s, arrayOf3s, arrayOf4s, arrayOf5s, arrayOf6s, arrayOf7s, arrayOf8s);
                        }
                        else if (arrayOf5s.toArray().length > islandMax) {
                            int randomIndex = random.nextInt(arrayOf5s.size());
                            removedCombo = arrayOf5s.get(randomIndex);
                            arrayOf5s.remove(arrayOf5s.get(randomIndex));
                            resetNum(random, absPath, removedCombo, mainWindow, comboButton,
                                    arrayOf1s, arrayOf2s, arrayOf3s, arrayOf4s, arrayOf5s, arrayOf6s, arrayOf7s, arrayOf8s);
                        }
                        else if (arrayOf6s.toArray().length > islandMax) {
                            int randomIndex = random.nextInt(arrayOf6s.size());
                            removedCombo = arrayOf6s.get(randomIndex);
                            arrayOf6s.remove(arrayOf6s.get(randomIndex));
                            resetNum(random, absPath, removedCombo, mainWindow, comboButton,
                                    arrayOf1s, arrayOf2s, arrayOf3s, arrayOf4s, arrayOf5s, arrayOf6s, arrayOf7s, arrayOf8s);
                        }
                        else if (arrayOf7s.toArray().length > islandMax) {
                            int randomIndex = random.nextInt(arrayOf7s.size());
                            removedCombo = arrayOf7s.get(randomIndex);
                            arrayOf7s.remove(arrayOf7s.get(randomIndex));
                            resetNum(random, absPath, removedCombo, mainWindow, comboButton,
                                    arrayOf1s, arrayOf2s, arrayOf3s, arrayOf4s, arrayOf5s, arrayOf6s, arrayOf7s, arrayOf8s);
                        }
                        else if (arrayOf8s.toArray().length > islandMax) {
                            int randomIndex = random.nextInt(arrayOf8s.size());
                            removedCombo = arrayOf8s.get(randomIndex);
                            arrayOf8s.remove(arrayOf8s.get(randomIndex));
                            resetNum(random, absPath, removedCombo, mainWindow, comboButton,
                                    arrayOf1s, arrayOf2s, arrayOf3s, arrayOf4s, arrayOf5s, arrayOf6s, arrayOf7s, arrayOf8s);
                        }
                        mainWindow.pack();
                        mainWindow.revalidate();
                        mainWindow.repaint();
                        comboDisplayInfo1.setText(String.valueOf(arrayOf1s.toArray().length));
                        comboDisplayInfo2.setText(String.valueOf(arrayOf2s.toArray().length));
                        comboDisplayInfo3.setText(String.valueOf(arrayOf3s.toArray().length));
                        comboDisplayInfo4.setText(String.valueOf(arrayOf4s.toArray().length));
                        comboDisplayInfo5.setText(String.valueOf(arrayOf5s.toArray().length));
                        comboDisplayInfo6.setText(String.valueOf(arrayOf6s.toArray().length));
                        comboDisplayInfo7.setText(String.valueOf(arrayOf7s.toArray().length));
                        comboDisplayInfo8.setText(String.valueOf(arrayOf8s.toArray().length));
                    }
                });

                //Listen to Element Combo Button
                comboButton.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent saveClick) {
                        //Create Elem Combo Window
                        JFrame comboWindow = new JFrame(combo);
                        comboWindow.setSize(600, 400);
                        comboWindow.getContentPane().setBackground(backgroundColour);
                        comboWindow.setVisible(true);
                        comboWindow.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 10));

                        //Create Elem Combo Nested Panel
                        JPanel comboNestedPanel = new JPanel();
                        comboNestedPanel.setLayout(new BoxLayout(comboNestedPanel, BoxLayout.PAGE_AXIS));
                        comboNestedPanel.setBackground(backgroundColour);
                        comboWindow.add(comboNestedPanel);

                        //Create Buttons for each Element Combo in Elem Combination
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

                            //Create Text Boxes for the text relating to each Element Combo in Elem Combination
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
                                setTextColour(textField, new String(Files.readAllBytes(path)));
                                //Listen to Text Box
                                textField.addActionListener(event -> {
                                    if (textField.getText().chars().allMatch(Character::isDigit)) {
                                        int itemTextNum = Integer.parseInt(textField.getText());
                                        if (itemTextNum > 0 && itemTextNum < 9) {
                                            try (BufferedWriter writer = new BufferedWriter(new FileWriter(path.toFile()))) {
                                                writer.write(textField.getText());
                                                setTextColour(textField, new String(Files.readAllBytes(path)));
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
                                                    setTextColour(textField, new String(Files.readAllBytes(path)));
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
                        comboWindow.pack();
                        comboWindow.revalidate();
                        comboWindow.repaint();
                        mainWindow.pack();
                        mainWindow.revalidate();
                        mainWindow.repaint();
                    }
                });
            }
        mainWindow.add(buttonPanel);
        nestedPanel.add(buttonPanel, BorderLayout.NORTH);
        mainWindow.pack();
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
    //Set Text Colour Based on Value
    private static void setTextColour(JTextField textBox, String string) {
        Color textColour = null;
        if (Objects.equals(string, "1")) {
            textBox.setBackground(textBoxColour1);
        } else if (Objects.equals(string, "2")) {
            textBox.setBackground(textBoxColour2);
        } else if (Objects.equals(string, "3")) {
            textBox.setBackground(textBoxColour3);
        } else if (Objects.equals(string, "4")) {
            textBox.setBackground(textBoxColour4);
        } else if (Objects.equals(string, "5")) {
            textBox.setBackground(textBoxColour5);
        } else if (Objects.equals(string, "6")) {
            textBox.setBackground(textBoxColour6);
        } else if (Objects.equals(string, "7")) {
            textBox.setBackground(textBoxColour7);
        } else if (Objects.equals(string, "8")) {
            textBox.setBackground(textBoxColour8);
        }else {
            textBox.setBackground(textBoxColour);
        }
    }
    private static void resetNum(Random rand, String absPathy, String comboRemoved, JFrame windowMain, JButton button, List<String> array1, List<String> array2,
                             List<String> array3, List<String> array4, List<String> array5, List<String> array6, List<String> array7, List<String> array8) {
        int randomNum = rand.nextInt(8) + 1;
        Path pathy = Paths.get(absPathy + "/combinations/" + comboRemoved + ".txt");
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(pathy.toFile()))) {
            writer.write(Integer.toString(randomNum));
            windowMain.revalidate();
            windowMain.repaint();
        } catch (IOException f) {
            f.printStackTrace();
        }

        if (randomNum == 1) {
            array1.add(comboRemoved);
        } else if (randomNum == 2) {
            array2.add(comboRemoved);
        } else if (randomNum == 3) {
            array3.add(comboRemoved);
        } else if (randomNum == 4) {
            array4.add(comboRemoved);
        } else if (randomNum == 5) {
            array5.add(comboRemoved);
        } else if (randomNum == 6) {
            array6.add(comboRemoved);
        } else if (randomNum == 7) {
            array7.add(comboRemoved);
        } else if (randomNum == 8) {
            array8.add(comboRemoved);
        }
        if (array1.toArray().length > 4) {
            button.setBackground(textBoxColour1);
        } else if (array2.toArray().length > 4) {
            button.setBackground(textBoxColour2);
        } else if (array3.toArray().length > 4) {
            button.setBackground(textBoxColour3);
        } else if (array4.toArray().length > 4) {
            button.setBackground(textBoxColour4);
        } else if (array5.toArray().length > 4) {
            button.setBackground(textBoxColour5);
        } else if (array6.toArray().length > 4) {
            button.setBackground(textBoxColour6);
        } else if (array7.toArray().length > 4) {
            button.setBackground(textBoxColour7);
        } else if (array8.toArray().length > 4) {
            button.setBackground(textBoxColour8);
        } else {
            button.setBackground(buttonColour);
        }
    }
}