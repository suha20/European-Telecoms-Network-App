import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;

import java.util.List;

//importing Graph and Edge classes
import MST.Graph;
import MST.Edge;

public class mainPanel {
    //private JTextField textField1;
    //private JTextField textField2;
    private JButton button1;
    private JPanel XYZ;
    //private JTextField textField3;
    private JTextArea resultAreaTextArea;
    private JButton button2;
    //private JScrollPane mstScrollPane;

    public static void main(String[] args) {
        JFrame frame = new JFrame("mainPanel");
        frame.setContentPane(new mainPanel().XYZ);
        //frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }
    public mainPanel() {
        button1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {


                JFileChooser fileChooser = new JFileChooser();
                int result = fileChooser.showOpenDialog(null);

                if (result == JFileChooser.APPROVE_OPTION){
                    File selectedFile = fileChooser.getSelectedFile();
                    System.out.println("Selected file" + selectedFile.getAbsolutePath());

                    File destFile = new File("src/dataset"+ selectedFile.getName());
                    try {
                        Files.copy(selectedFile.toPath(), destFile.toPath(),
                                StandardCopyOption.REPLACE_EXISTING);
                        System.out.println("file successfully copied to:" + destFile.getAbsolutePath());

                        //SwingUtilities.getWindowAncestor(button1).dispose();

                      }catch (IOException ex){
                        ex.printStackTrace();

                    }
                }


                //String name = textField2.getText();
               // Integer age = Integer.valueOf(textField1.getText());



               // Float height = Float.valueOf(textField3.getText());
                //Double weight = Double.valueOf(textField3.getText());


                //String email = String.valueOf(textField3.getText());
               // Person person = new Person(name,age,height,weight,email);
                //System.out.println(person);

            }
        });
        //button2.addActionListener(new ActionListener() {
        //});
        button2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                File inputFile = new File("src/datasetCapitals.txt"); // Adjust this path as needed
                calculateAndDisplayMST(inputFile);

            }
        });
    }

    private void calculateAndDisplayMST(File file) {
        try {
            // Use the existing Kotlin companion object's fromFile method
            Graph graph = Graph.Companion.fromFile(file.getAbsolutePath());
            List<Edge> mstEdges = graph.kruskalMST();

            StringBuilder result = new StringBuilder();
            result.append("Minimum Spanning Tree (MST):\n\n");

            int totalDistance = 0;
            for (Edge edge : mstEdges) {
                result.append(String.format("%s -- %s: %d km\n",
                        edge.getSource(),
                        edge.getDestination(),
                        edge.getDistance()));
                totalDistance += edge.getDistance();
            }

            result.append("\nTotal Distance of MST: ").append(totalDistance).append(" km");
            resultAreaTextArea.setText(result.toString());

        } catch (Exception ex) {
            resultAreaTextArea.setText("Error calculating MST: " + ex.getMessage());
            ex.printStackTrace();
        }
    }
}
