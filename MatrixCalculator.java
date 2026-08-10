import javax.swing.*;
import javax.swing.border.*;

import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;

import java.io.File;
import java.io.FileWriter;

public class MatrixCalculator {

    private JFrame frame;

    // Input
    private JPanel matrix1Panel;
    private JPanel matrix2Panel;

    private JTextField[][] matrix1Fields;
    private JTextField[][] matrix2Fields;

    // Output
    private JTextArea outputArea;
    private double[][] lastResultMatrix;

    // Matrix 1 buttons
    private RoundedButton scalarMultiply1Button;
    private RoundedButton scalarDivide1Button;
    private RoundedButton transpose1Button;
    private RoundedButton determinant1Button;
    private RoundedButton inverse1Button;
    private RoundedButton rref1Button;

    // Matrix 2 buttons
    private RoundedButton scalarMultiply2Button;
    private RoundedButton scalarDivide2Button;
    private RoundedButton transpose2Button;
    private RoundedButton determinant2Button;
    private RoundedButton inverse2Button;
    private RoundedButton rref2Button;

    // Both matrices buttons
    private RoundedButton addButton;
    private RoundedButton subtractButton;
    private RoundedButton multiplyButton;

    // Scalar fields
    private JTextField scalar1Field;
    private JTextField scalar2Field;

    public MatrixCalculator() {

        // Create calculator window frame
        frame = new JFrame("Matrix Calculator");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1100, 850);
        frame.setLocationRelativeTo(null); // Centre window

        // Set background colour
        JPanel background = new JPanel(new BorderLayout());
        background.setBackground(new Color(165, 197, 208));

        frame.setContentPane(background);

        background.add(createMainPanel(), BorderLayout.CENTER);
        background.add(createFooter(), BorderLayout.SOUTH);

        frame.setVisible(true);
    }

    // Create the layout for the UI, seperating the screen into inputs (left) and outputs (right)
    private JPanel createMainPanel() {

        // Create root panel for all inputs and outputs
        JPanel mainPanel = new JPanel(new BorderLayout(10, 0));
        mainPanel.setOpaque(false); // Make panel transparent
        mainPanel.setBorder(new EmptyBorder(5, 5, 5, 5)); // Add padding to panel

        // Create input panel
        JPanel inputWrapper = createInputPanel();
        inputWrapper.setPreferredSize(new Dimension(300, 0));
        mainPanel.add(inputWrapper, BorderLayout.WEST);

        // Create operations and output panel
        JPanel workspacePanel = new JPanel(new GridBagLayout());
        workspacePanel.setOpaque(false);

        // Set alignment constraints 
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.BOTH; // Expand components to fill assigned grid cells
        gbc.insets = new Insets(5, 5, 5, 5);

        // Top row (matrix operations)
        gbc.gridy = 0;
        gbc.gridheight = 1;
        gbc.weighty = 0.55;

        // Matrix 1 panel
        gbc.gridx = 0;
        gbc.weightx = 1.0;
        workspacePanel.add(createMatrix1Panel(), gbc);

        // Matrix 2 panel
        gbc.gridx = 1;
        gbc.weightx = 1.0;
        workspacePanel.add(createMatrix2Panel(), gbc);

        // Both panel
        gbc.gridx = 2;
        gbc.weightx = 1.0;
        workspacePanel.add(createBothPanel(), gbc);

        // Bottom row (output)
        gbc.gridy = 1;
        gbc.gridx = 0;
        gbc.gridwidth = 3;
        gbc.weightx = 3.0;
        gbc.weighty = 0.45;

        workspacePanel.add(createOutputPanel(), gbc);

        mainPanel.add(workspacePanel, BorderLayout.CENTER);

        return mainPanel;
    }

    // Create the sub-sections for operations 
    private JPanel createSection(String title) {

        // Create boxed section
        JPanel panel = new JPanel();
        panel.setOpaque(false);
        panel.setBorder(BorderFactory.createMatteBorder(2, 2, 2, 2, Color.WHITE));
        panel.setLayout(new BorderLayout());

        // Create section heading
        JLabel heading = new JLabel(title);
        heading.setForeground(Color.WHITE);
        heading.setFont(new Font("Monospaced", Font.BOLD, 24));
        heading.setBorder(new EmptyBorder(8, 8, 8, 8));

        panel.add(heading, BorderLayout.NORTH);

        return panel;
    }

    // Create footer for copyright and navigation buttons
    private JPanel createFooter() {

        // Create panel for footer
        JPanel bottomPanel = new JPanel(new BorderLayout());
        bottomPanel.setBackground(Color.WHITE);
        bottomPanel.setBorder(new EmptyBorder(5, 8, 5, 8));

        // Create label for copyright text (left)
        JLabel copyright = new JLabel("@2026 Melanie Pritchard. All Rights Reserved.");
        copyright.setFont(new Font("Monospaced", Font.PLAIN, 12));

        // Create sub-panel for buttons
        JPanel actionPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 0));
        actionPanel.setOpaque(false);

        // Create button to return to the home page
        RoundedButton backButton = new RoundedButton(
            "<-",
            Color.WHITE,
            Color.BLACK,
            new Color(240, 240, 245),
            new Color(220, 220, 225)
        );

        backButton.setPreferredSize(new Dimension(35, 30));

        // Set action when back button is clicked
        backButton.addActionListener(e -> {
            frame.dispose(); // Close and destroy current window
            new MatrixHome(); // Open new instance of home page
        });

        // Create button to direct to the help page
        RoundedButton helpButton = new RoundedButton(
            "?",
            Color.WHITE,
            Color.BLACK,
            new Color(40, 40, 45),
            new Color(60, 60, 65)     
        );

        helpButton.setPreferredSize(new Dimension(30, 30));
        helpButton.setBackground(Color.BLACK);
        helpButton.setForeground(Color.WHITE);

        // Set action when help button is clicked
        helpButton.addActionListener(e -> {
            frame.dispose(); // Close and destroy current window
            new MatrixHelp(); // Open new instance of help page
        });

        actionPanel.add(backButton);
        actionPanel.add(helpButton);

        bottomPanel.add(copyright, BorderLayout.WEST);
        bottomPanel.add(actionPanel, BorderLayout.EAST);

        return bottomPanel;
    }

    // Create left sidebar for user inputs
    private JPanel createInputPanel() {

        // Create boxed section titled 'Input'
        JPanel outer = createSection("Input");

        // Create inner panel with vertical layout
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBorder(new EmptyBorder(10, 15, 15, 15));
        panel.setOpaque(false);

        // Matrix 1 parameters
        JLabel matrix1Label = new JLabel("Matrix 1"); // Header
        matrix1Label.setForeground(Color.WHITE);
        matrix1Label.setFont(new Font("Monospaced", Font.BOLD, 18));
        matrix1Label.setAlignmentX(Component.LEFT_ALIGNMENT);

        // Position row input
        JPanel row1Panel = new JPanel(new FlowLayout(FlowLayout.LEFT, 5, 0));
        row1Panel.setOpaque(false);
        row1Panel.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel rows1Label = new JLabel("Rows:   ");
        rows1Label.setFont(new Font("Monospaced", Font.PLAIN, 15));
        rows1Label.setForeground(Color.WHITE);
        
        // Number selection for matrix rows
        JSpinner rows1Spinner = new JSpinner(new SpinnerNumberModel(2, 1, 10, 1));
        ((JSpinner.DefaultEditor) rows1Spinner.getEditor()).getTextField().setColumns(3); 

        row1Panel.add(rows1Label);
        row1Panel.add(rows1Spinner);

        // Position column input
        JPanel col1Panel = new JPanel(new FlowLayout(FlowLayout.LEFT, 5, 0));
        col1Panel.setOpaque(false);
        col1Panel.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel cols1Label = new JLabel("Columns:");
        cols1Label.setFont(new Font("Monospaced", Font.PLAIN, 15));
        cols1Label.setForeground(Color.WHITE);

        // Number selection for matrix columns
        JSpinner cols1Spinner = new JSpinner(new SpinnerNumberModel(2, 1, 10, 1));
        ((JSpinner.DefaultEditor) cols1Spinner.getEditor()).getTextField().setColumns(3);
        
        col1Panel.add(cols1Label);
        col1Panel.add(cols1Spinner);

        // Position button
        JPanel buttonWrapper1 = new JPanel(new GridLayout(2, 1, 0, 8));
        buttonWrapper1.setOpaque(false);
        buttonWrapper1.setAlignmentX(Component.LEFT_ALIGNMENT);

        // Create button for matrix grid generation
        RoundedButton createMatrix1Button = new RoundedButton(
            "Generate Matrix Grid", Color.BLACK, Color.WHITE,
            new Color(240, 240, 245), new Color(220, 220, 225)
        );

        createMatrix1Button.setPreferredSize(new Dimension(250, 30));
        Font smallerFont = new Font(createMatrix1Button.getFont().getName(), createMatrix1Button.getFont().getStyle(), 15);
        createMatrix1Button.setFont(smallerFont);

        // Create button for a random matrix between 1x1 and 10x10 dimensions to be generated
        RoundedButton randomMatrix1Button = new RoundedButton(
            "Generate Random Matrix", Color.BLACK, Color.WHITE,
            new Color(240, 240, 245), new Color(220, 220, 225)
        );

        randomMatrix1Button.setPreferredSize(new Dimension(250, 30));
        randomMatrix1Button.setFont(smallerFont);

        buttonWrapper1.add(createMatrix1Button);
        buttonWrapper1.add(randomMatrix1Button);

        // Create panel for the matrix grid to be displayed on
        matrix1Panel = new JPanel();
        matrix1Panel.setOpaque(false);
        matrix1Panel.setBorder(BorderFactory.createLineBorder(Color.WHITE));

        // Set action when creation button is clicked
        createMatrix1Button.addActionListener(e -> {
            boolean random = false;
            int rows = (int) rows1Spinner.getValue();
            int cols = (int) cols1Spinner.getValue();
            matrix1Fields = createMatrixGrid(matrix1Panel, rows, cols, random);
        });

        // Set action when random creation button is clicked
        randomMatrix1Button.addActionListener(e -> {
            boolean random = true;
            int rows = (int)(Math.random() * 10) + 1;
            int cols = (int)(Math.random() * 10) + 1;
            matrix1Fields = createMatrixGrid(matrix1Panel, rows, cols, random);
        });

        // Wrap scroll panes
        JScrollPane scrollMatrix1 = new JScrollPane(matrix1Panel);
        scrollMatrix1.setOpaque(false);
        scrollMatrix1.getViewport().setOpaque(false);
        scrollMatrix1.setBorder(BorderFactory.createLineBorder(Color.WHITE));
        scrollMatrix1.setAlignmentX(Component.LEFT_ALIGNMENT);
        scrollMatrix1.setMaximumSize(new Dimension(260, 160));
        scrollMatrix1.setPreferredSize(new Dimension(260, 160));

        // Matrix 2 parameters
        JLabel matrix2Label = new JLabel("Matrix 2"); // Header
        matrix2Label.setForeground(Color.WHITE);
        matrix2Label.setFont(new Font("Monospaced", Font.BOLD, 18));
        matrix2Label.setAlignmentX(Component.LEFT_ALIGNMENT);

        JPanel row2Panel = new JPanel(new FlowLayout(FlowLayout.LEFT, 5, 0));
        row2Panel.setOpaque(false);
        row2Panel.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel rows2Label = new JLabel("Rows:    ");
        rows2Label.setFont(new Font("Monospaced", Font.PLAIN, 14));
        rows2Label.setForeground(Color.WHITE);

        JSpinner rows2Spinner = new JSpinner(new SpinnerNumberModel(2, 1, 10, 1));
        ((JSpinner.DefaultEditor) rows2Spinner.getEditor()).getTextField().setColumns(3);
        
        row2Panel.add(rows2Label);
        row2Panel.add(rows2Spinner);
        
        JPanel col2Panel = new JPanel(new FlowLayout(FlowLayout.LEFT, 5, 0));
        col2Panel.setOpaque(false);
        col2Panel.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel cols2Label = new JLabel("Columns:");
        cols2Label.setFont(new Font("Monospaced", Font.PLAIN, 15));
        cols2Label.setForeground(Color.WHITE);

        JSpinner cols2Spinner = new JSpinner(new SpinnerNumberModel(2, 1, 10, 1));
        ((JSpinner.DefaultEditor) cols2Spinner.getEditor()).getTextField().setColumns(3);
        
        col2Panel.add(cols2Label);
        col2Panel.add(cols2Spinner);

        JPanel buttonWrapper2 = new JPanel(new GridLayout(2, 1, 0, 8));
        buttonWrapper2.setOpaque(false);
        buttonWrapper2.setAlignmentX(Component.LEFT_ALIGNMENT);
        
        RoundedButton createMatrix2Button = new RoundedButton(
            "Generate Matrix Grid", Color.BLACK, Color.WHITE,
            new Color(240, 240, 245), new Color(220, 220, 225)
        );

        createMatrix2Button.setPreferredSize(new Dimension(250, 30));
        createMatrix2Button.setFont(smallerFont);

        // Create button for a random matrix between 1x1 and 10x10 dimensions to be generated
        RoundedButton randomMatrix2Button = new RoundedButton(
            "Generate Random Matrix", Color.BLACK, Color.WHITE,
            new Color(240, 240, 245), new Color(220, 220, 225)
        );

        randomMatrix2Button.setPreferredSize(new Dimension(250, 30));
        randomMatrix2Button.setFont(smallerFont);

        buttonWrapper2.add(createMatrix2Button);
        buttonWrapper2.add(randomMatrix2Button);

        matrix2Panel = new JPanel();
        matrix2Panel.setOpaque(false);
        matrix2Panel.setBorder(BorderFactory.createLineBorder(Color.WHITE));

        createMatrix2Button.addActionListener(e -> {
            boolean random = false;
            int rows = (int) rows2Spinner.getValue();
            int cols = (int) cols2Spinner.getValue();
            matrix2Fields = createMatrixGrid(matrix2Panel, rows, cols, random);
        });

        randomMatrix2Button.addActionListener(e -> {
            boolean random = true;
            int rows = (int)(Math.random() * 10) + 1;
            int cols = (int)(Math.random() * 10) + 1;
            matrix2Fields = createMatrixGrid(matrix2Panel, rows, cols, random);
        });

        JScrollPane scrollMatrix2 = new JScrollPane(matrix2Panel);
        scrollMatrix2.setOpaque(false);
        scrollMatrix2.getViewport().setOpaque(false);
        scrollMatrix2.setBorder(BorderFactory.createLineBorder(Color.WHITE));
        scrollMatrix2.setAlignmentX(Component.LEFT_ALIGNMENT);
        scrollMatrix2.setMaximumSize(new Dimension(260, 160));
        scrollMatrix2.setPreferredSize(new Dimension(260, 160));
    
       // Stack components vertically in panel
        panel.add(matrix1Label);
        panel.add(Box.createVerticalStrut(8));
        panel.add(row1Panel);
        panel.add(Box.createVerticalStrut(4));
        panel.add(col1Panel);
        panel.add(Box.createVerticalStrut(10));
        panel.add(buttonWrapper1);
        panel.add(Box.createVerticalStrut(10));
        panel.add(scrollMatrix1);

        panel.add(Box.createVerticalStrut(25));

        panel.add(matrix2Label);
        panel.add(Box.createVerticalStrut(8));
        panel.add(row2Panel);
        panel.add(Box.createVerticalStrut(4));
        panel.add(col2Panel);
        panel.add(Box.createVerticalStrut(10));
        panel.add(buttonWrapper2);
        panel.add(Box.createVerticalStrut(10));
        panel.add(scrollMatrix2);

        panel.add(Box.createVerticalGlue()); // Remove empty screen height at the bottom
        outer.add(panel, BorderLayout.CENTER);

        return outer;
    }

    // Operation selection for single-matrix operations on Matrix 1
    private JPanel createMatrix1Panel() {

        // Create boxed section titled 'Matrix 1'
        JPanel outer = createSection("Matrix 1");

         // Create inner panel with vertical layout
        JPanel panel = new JPanel();
        panel.setOpaque(false);
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBorder(new EmptyBorder(10, 15, 15, 15));

        // Create label for input box
        JLabel scalarLabel = new JLabel("Scalar:");
        scalarLabel.setForeground(Color.WHITE);
        scalarLabel.setFont(new Font("Monospaced", Font.PLAIN, 14));
        scalarLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
 
        // Create field for user to enter scalar
        scalar1Field = new JTextField();
        scalar1Field.setMaximumSize(new Dimension(Integer.MAX_VALUE, 30));
        scalar1Field.setAlignmentX(Component.LEFT_ALIGNMENT);

        // Vertically stack label and text field
        panel.add(scalarLabel);
        panel.add(Box.createVerticalStrut(5));
        panel.add(scalar1Field);
        panel.add(Box.createVerticalStrut(15));

        // Generate operation buttons
        scalarMultiply1Button = createOperationButton("Multiply");
        scalarDivide1Button = createOperationButton("Divide");
        transpose1Button = createOperationButton("Transpose");
        determinant1Button = createOperationButton("Determinant");
        inverse1Button = createOperationButton("Inverse");
        rref1Button = createOperationButton("RREF");

        // Set action when 'multiply' button is clicked
        scalarMultiply1Button.addActionListener(e -> {
            try {
                // Check if matrix grid text fields have been generated
                if (matrix1Fields == null) { JOptionPane.showMessageDialog(frame, "Create Matrix 1 first."); return; }
                
                // Parse and extract entries
                double[][] m1 = getMatrix(matrix1Fields);
                if (m1 == null) return; // Exit if entries invalid

                // Parse and convert text from scalar input into a double
                double scalar = Double.parseDouble(scalar1Field.getText());
                
                // Execute math logic
                Calculations outcome = Matrix.scalarMultiplication(m1, scalar);
                displayResultWithSteps(outcome);

            } catch (NumberFormatException ex) {
                // Catch empty or invalid scalar inputs
                JOptionPane.showMessageDialog(frame, "Please enter a valid scalar numeric value.");
            }
        });

        // Set action when 'divide' button is clicked
        scalarDivide1Button.addActionListener(e -> {
            try {
                if (matrix1Fields == null) { JOptionPane.showMessageDialog(frame, "Create Matrix 1 first."); return; }
                
                double[][] m1 = getMatrix(matrix1Fields);
                if (m1 == null) return;

                double scalar = Double.parseDouble(scalar1Field.getText());

                Calculations outcome = Matrix.scalarDivision(m1, scalar);
                displayResultWithSteps(outcome);

            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(frame, "Please enter a valid scalar numeric value.");
            }
        });

        // Set action when 'transpose' button is clicked
        transpose1Button.addActionListener(e -> {
            if (matrix1Fields == null) { JOptionPane.showMessageDialog(frame, "Create Matrix 1 first."); return; }
            double[][] m1 = getMatrix(matrix1Fields);
            if (m1 != null) {
                Calculations outcome = Matrix.transpose(m1);
                displayResultWithSteps(outcome);
            }
        });

        // Set action when 'determinant' button is clicked
        determinant1Button.addActionListener(e -> {
            if (matrix1Fields == null) { JOptionPane.showMessageDialog(frame, "Create Matrix 1 first."); return; }
            double[][] m1 = getMatrix(matrix1Fields);
            if (m1 != null) {
                try {
                    Calculations outcome = Matrix.determinant(m1);
                    displayResultWithSteps(outcome);
                } catch (IllegalArgumentException ex) {
                    // Catch mathematical errors - non-square matrix
                    JOptionPane.showMessageDialog(frame, ex.getMessage());
                }
            }
        });
 
        // Set action when 'inverse' button is clicked
        inverse1Button.addActionListener(e -> {
            if (matrix1Fields == null) { JOptionPane.showMessageDialog(frame, "Create Matrix 1 first."); return; }
            double[][] m1 = getMatrix(matrix1Fields);
            if (m1 != null) {
                try {
                    Calculations outcome = Matrix.inverse(m1);
                    displayResultWithSteps(outcome);
                } catch(IllegalArgumentException ex) {
                    // Catch mathematical errors - non-square matrix
                    JOptionPane.showMessageDialog(frame, ex.getMessage());
                }
            }
        });

        // Set action when 'rref' button is clicked
        rref1Button.addActionListener(e -> {
            if (matrix1Fields == null) { JOptionPane.showMessageDialog(frame, "Create Matrix 1 first."); return; }
            double[][] m1 = getMatrix(matrix1Fields);
            if (m1 != null) {
                Calculations outcome = Matrix.reducedRowEchelonForm(m1);
                displayResultWithSteps(outcome);
            }
        });

        // Vertically stack all buttons to panel
        panel.add(scalarMultiply1Button); 
        panel.add(Box.createVerticalStrut(8));

        panel.add(scalarDivide1Button);    
        panel.add(Box.createVerticalStrut(8));

        panel.add(transpose1Button);       
        panel.add(Box.createVerticalStrut(8));

        panel.add(determinant1Button);     
        panel.add(Box.createVerticalStrut(8));

        panel.add(inverse1Button);         
        panel.add(Box.createVerticalStrut(8));

        panel.add(rref1Button);
        
        panel.add(Box.createVerticalGlue());
        outer.add(panel, BorderLayout.CENTER);

        return outer;
    }

    // Operation selection for single-matrix operations on Matrix 2
    private JPanel createMatrix2Panel() {

        JPanel outer = createSection("Matrix 2");

        JPanel panel = new JPanel();
        panel.setOpaque(false);
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBorder(new EmptyBorder(10, 15, 15, 15));

        JLabel scalarLabel = new JLabel("Scalar:");
        scalarLabel.setForeground(Color.WHITE);
        scalarLabel.setFont(new Font("Monospaced", Font.PLAIN, 14));
        scalarLabel.setAlignmentX(Component.LEFT_ALIGNMENT);

        scalar2Field = new JTextField();
        scalar2Field.setMaximumSize(new Dimension(180, 30));
        scalar2Field.setAlignmentX(Component.LEFT_ALIGNMENT);

        panel.add(scalarLabel);
        panel.add(Box.createVerticalStrut(5));
        panel.add(scalar2Field);
        panel.add(Box.createVerticalStrut(15));

        scalarMultiply2Button = createOperationButton("Multiply");
        scalarDivide2Button = createOperationButton("Divide");
        transpose2Button = createOperationButton("Transpose");
        determinant2Button = createOperationButton("Determinant");
        inverse2Button = createOperationButton("Inverse");
        rref2Button = createOperationButton("RREF");

        scalarMultiply2Button.addActionListener(e -> {
            try {
                if (matrix2Fields == null) { JOptionPane.showMessageDialog(frame, "Create Matrix 2 first."); return; }
                
                double[][] m2 = getMatrix(matrix2Fields);
                if (m2 == null) return;

                double scalar = Double.parseDouble(scalar2Field.getText());
                
                Calculations outcome = Matrix.scalarMultiplication(m2, scalar);
                displayResultWithSteps(outcome);

            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(frame, "Please enter a valid scalar numeric value.");
            }
        });

        scalarDivide2Button.addActionListener(e -> {
            try {
                if (matrix2Fields == null) { JOptionPane.showMessageDialog(frame, "Create Matrix 2 first."); return; }
                
                double[][] m2 = getMatrix(matrix2Fields);
                if (m2 == null) return;

                double scalar = Double.parseDouble(scalar2Field.getText());
                
                Calculations outcome = Matrix.scalarDivision(m2, scalar);
                displayResultWithSteps(outcome);

            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(frame, "Please enter a valid scalar numeric value.");
            }
        });

        transpose2Button.addActionListener(e -> {
            if (matrix2Fields == null) { JOptionPane.showMessageDialog(frame, "Create Matrix 2 first."); return; }
            double[][] m2 = getMatrix(matrix2Fields);
            if (m2 != null) {
                Calculations outcome = Matrix.transpose(m2);
                displayResultWithSteps(outcome);
            }
        });

        determinant2Button.addActionListener(e -> {
            if (matrix2Fields == null) { JOptionPane.showMessageDialog(frame, "Create Matrix 2 first."); return; }
            double[][] m2 = getMatrix(matrix2Fields);
            if (m2 != null) {
                try {
                    Calculations outcome = Matrix.determinant(m2);
                    displayResultWithSteps(outcome);
                } catch (IllegalArgumentException ex) {
                    JOptionPane.showMessageDialog(frame, ex.getMessage());
                }
            }
        });

        inverse2Button.addActionListener(e -> {
            if (matrix2Fields == null) { JOptionPane.showMessageDialog(frame, "Create Matrix 2 first."); return; }
            double[][] m2 = getMatrix(matrix2Fields);
            if (m2 != null) {
                try {
                    Calculations outcome = Matrix.inverse(m2);
                    displayResultWithSteps(outcome);
                } catch(IllegalArgumentException ex) {
                    JOptionPane.showMessageDialog(frame, ex.getMessage());
                }
            }
        });

        rref2Button.addActionListener(e -> {
            if (matrix2Fields == null) { JOptionPane.showMessageDialog(frame, "Create Matrix 2 first."); return; }
            double[][] m2 = getMatrix(matrix2Fields);
            if (m2 != null) {
                Calculations outcome = Matrix.reducedRowEchelonForm(m2);
                displayResultWithSteps(outcome);
            }
        });

        panel.add(scalarMultiply2Button); 
        panel.add(Box.createVerticalStrut(8));

        panel.add(scalarDivide2Button);    
        panel.add(Box.createVerticalStrut(8));

        panel.add(transpose2Button);       
        panel.add(Box.createVerticalStrut(8));

        panel.add(determinant2Button);     
        panel.add(Box.createVerticalStrut(8));

        panel.add(inverse2Button);         
        panel.add(Box.createVerticalStrut(8));

        panel.add(rref2Button);
        
        panel.add(Box.createVerticalGlue());
        outer.add(panel, BorderLayout.CENTER);

        return outer;
    }

    //Operation selection for matrix operations with Matrix 1 and Matrix 2
    private JPanel createBothPanel() {

        JPanel outer = createSection("Both");

        JPanel panel = new JPanel();
        panel.setOpaque(false);
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBorder(new EmptyBorder(10, 15, 15, 15));

        // Labels to show the order for each matrix
        JLabel formatLabel = new JLabel("Format:");
        formatLabel.setFont(new Font("Monospaced", Font.PLAIN, 14));
        formatLabel.setForeground(Color.WHITE);
        formatLabel.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel format1Label = new JLabel("Matrix 1 + Matrix 2");
        format1Label.setFont(new Font("Monospaced", Font.PLAIN, 14));
        format1Label.setForeground(Color.WHITE);
        format1Label.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel format2Label = new JLabel("Matrix 1 - Matrix 2");
        format2Label.setFont(new Font("Monospaced", Font.PLAIN, 14));
        format2Label.setForeground(Color.WHITE);
        format2Label.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel format3Label = new JLabel("Matrix 1 * Matrix 2");
        format3Label.setFont(new Font("Monospaced", Font.PLAIN, 14));
        format3Label.setForeground(Color.WHITE);
        format3Label.setAlignmentX(Component.LEFT_ALIGNMENT);

        panel.add(formatLabel);
        panel.add(format1Label);
        panel.add(format2Label);
        panel.add(format3Label);
        panel.add(Box.createVerticalStrut(20));

        addButton = createOperationButton("Add");
        // Set action when 'add' button is clicked
        addButton.addActionListener(e -> {
            try {
                // Check text grids exist for both matrices
                if (matrix1Fields == null || matrix2Fields == null) {
                    JOptionPane.showMessageDialog(frame, "Create both matrices first.");
                    return;
                }

                // Parse, extract, and convert values
                double[][] m1 = getMatrix(matrix1Fields);
                double[][] m2 = getMatrix(matrix2Fields);
                if (m1 == null || m2 == null) return; // Exit if entries invalid

                // Execute math logic
                Calculations outcome = Matrix.addition(m1, m2);
                displayResultWithSteps(outcome);

            } catch(Exception ex) {
                // Catch incomptability errors (dimension mismatch)
                JOptionPane.showMessageDialog(frame, ex.getMessage());
            }
        });

        subtractButton = createOperationButton("Subtract");
        // Set action when 'subtract' button is clicked
        subtractButton.addActionListener(e -> {
            try {
                if (matrix1Fields == null || matrix2Fields == null) {
                    JOptionPane.showMessageDialog(frame, "Create both matrices first.");
                    return;
                }

                double[][] m1 = getMatrix(matrix1Fields);
                double[][] m2 = getMatrix(matrix2Fields);
                if (m1 == null || m2 == null) return;

                Calculations outcome = Matrix.subtraction(m1, m2);
                displayResultWithSteps(outcome);

            } catch(Exception ex) {
                JOptionPane.showMessageDialog(frame, ex.getMessage());
            }
        });

        multiplyButton = createOperationButton("Multiply");
        // Set action when 'multiply' button is clicked
        multiplyButton.addActionListener(e -> {
            try {
                if (matrix1Fields == null || matrix2Fields == null) {
                    JOptionPane.showMessageDialog(frame, "Create both matrices first.");
                    return;
                }

                double[][] m1 = getMatrix(matrix1Fields);
                double[][] m2 = getMatrix(matrix2Fields);
                if (m1 == null || m2 == null) return;

                Calculations outcome = Matrix.matrixMultiplication(m1, m2);
                displayResultWithSteps(outcome);

            } catch(Exception ex) {
                JOptionPane.showMessageDialog(frame, ex.getMessage());
            }
        });

        panel.add(addButton);
        panel.add(Box.createVerticalStrut(8));
        panel.add(subtractButton);
        panel.add(Box.createVerticalStrut(8));
        panel.add(multiplyButton);
        
        panel.add(Box.createVerticalGlue());
        outer.add(panel, BorderLayout.CENTER);

        return outer;
    }

    // Template to create all operation buttons
    private RoundedButton createOperationButton(String text) {

        RoundedButton button = new RoundedButton( 
            text, 
            Color.BLACK,
            Color.WHITE,
            new Color(240, 240, 245),
            new Color(220, 220, 225)
        );

        button.setMaximumSize(new Dimension(Integer.MAX_VALUE, 35));
        button.setAlignmentX(Component.LEFT_ALIGNMENT);

        return button;    
    }

    // Create bottom-right output field
    private JPanel createOutputPanel() {

        JPanel outer = createSection("Output");
        outer.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createMatteBorder(2, 2, 2, 2, Color.WHITE),
            BorderFactory.createEmptyBorder(10, 10, 10, 10)
        ));
        
        outputArea = new JTextArea();
        outputArea.setEditable(false);
        outputArea.setFont(new Font("Monospaced", Font.PLAIN, 14));
        outputArea.setMargin(new Insets(10, 10, 10, 10));

        // Allow scrollable output
        JScrollPane scroll = new JScrollPane(outputArea);
        scroll.setBorder(BorderFactory.createLineBorder(Color.BLACK));

        // Create button to copy output text to clipboard
        JButton copyButton = new JButton("Copy to Clipboard");
        copyButton.setFont(new Font("Monospaced", Font.BOLD, 12));
        copyButton.setBackground(Color.WHITE);
        copyButton.setFocusable(false);
        
        // Link the button click directly to utility method
        copyButton.addActionListener(e -> copyOutputToClipboard());

        // Create button to copy output matrix to Matrix 1
        JButton copyToMatrix1Button = new JButton("Copy to Matrix 1");
        copyToMatrix1Button.setFont(new Font("Monospaced", Font.BOLD, 12));
        copyToMatrix1Button.setBackground(Color.WHITE);
        copyToMatrix1Button.setFocusable(false);

        copyToMatrix1Button.addActionListener(e -> copyOutputToInputGrid(1));

        // Create button to copy output matrix to Matrix 2
        JButton copyToMatrix2Button = new JButton("Copy to Matrix 2");
        copyToMatrix2Button.setFont(new Font("Monospaced", Font.BOLD, 12));
        copyToMatrix2Button.setBackground(Color.WHITE);
        copyToMatrix2Button.setFocusable(false);

        copyToMatrix2Button.addActionListener(e -> copyOutputToInputGrid(2));

        // Create button to export output matrix to CSV file
        JButton exportCSVButton = new JButton("Export CSV");
        exportCSVButton.setFont(new Font("Monospaced", Font.BOLD, 12));
        exportCSVButton.setBackground(Color.WHITE);
        exportCSVButton.setFocusable(false);

        exportCSVButton.addActionListener(e -> {

            if (lastResultMatrix == null) {
                JOptionPane.showMessageDialog(frame, "No result to export.");
                return;
            }

            String csv = MatrixExporter.toCSV(lastResultMatrix);

            JFileChooser chooser = new JFileChooser();
            chooser.setDialogTitle("Save CSV File");

            int choice = chooser.showSaveDialog(frame);

            if (choice == JFileChooser.APPROVE_OPTION) {
                try {
                    File file = chooser.getSelectedFile();

                    if (!file.getName().toLowerCase().endsWith(".csv")) {
                        file = new File(file.getAbsolutePath() + ".csv");
                    }

                    FileWriter writer = new FileWriter(file);
                    writer.write(csv);
                    writer.close();

                    JOptionPane.showMessageDialog(frame, "CSV exported successfully.");

                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(frame, "Error saving file " + ex.getMessage());
                }
            }
        });

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 5));
        buttonPanel.setOpaque(false);
        buttonPanel.add(copyButton);
        buttonPanel.add(copyToMatrix1Button);
        buttonPanel.add(copyToMatrix2Button);
        buttonPanel.add(exportCSVButton);
       
        outer.add(scroll, BorderLayout.CENTER); 
        outer.add(buttonPanel, BorderLayout.SOUTH); 

        return outer;
    }

    // Generate grid layout for matrix entry
    private JTextField[][] createMatrixGrid(JPanel panel, int rows, int cols, boolean random) {

        panel.removeAll(); // Reset grid
        panel.setLayout(new GridLayout(rows, cols, 5, 5)); // Create new grid

        JTextField[][] fields = new JTextField[rows][cols];

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                fields[i][j] = new JTextField(3);

                if (random) {
                    double randomEntry = (Math.random() * 18.0) - 9.0;
                    fields[i][j].setText(String.format("%.2f", randomEntry));
                }
                panel.add(fields[i][j]);
            }
        }

        panel.revalidate(); // Recalculate grid
        panel.repaint(); // Redraw changes

        return fields;
    }

    //Convert 2D array of text fields to 2D double array for mathematical operations
    private double[][] getMatrix(JTextField[][] fields) {

        // Cacuate dimensions
        int rows = fields.length;
        int cols = fields[0].length;

        // Initialise array
        double[][] matrix = new double[rows][cols];

        // Extract text and fill new array
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                try {
                    matrix[i][j] = Double.parseDouble(fields[i][j].getText());
                } catch (NumberFormatException ex) {
                    // Catch blank or invalid text boxes
                    JOptionPane.showMessageDialog(frame, "Please enter valid numbers in every matrix cell.");
                    return null;
                }
            }
        }
        return matrix;
    } 

    // Convert double array to formatted string block
    private String matrixToString(double[][] matrix) {

        // Check matrix exists
        if (matrix == null) return "";

        StringBuilder sb = new StringBuilder();

        for (double[] row : matrix) {
            for (double value : row) {
                // Check if value close enough to an integer
                if (Math.abs(value - Math.round(value)) < 1e-9) {
                    // Format value as an integer with padding to keep columns aligned
                    sb.append(String.format("%6d", (int) Math.round(value)));
                } else {
                    sb.append(String.format("%8.3f", value));
                }
            }
            sb.append("\n");
        }
        return sb.toString();
    }

    // Output calculation with steps taken to reach the result
    private void displayResultWithSteps(Calculations calc) {

        this.lastResultMatrix = calc.matrix;

        StringBuilder formatBuilder = new StringBuilder();
        formatBuilder.append("====================================================================\n");
        formatBuilder.append(" CALCULATION STEPS LOGS\n");
        formatBuilder.append(" (All decimals rounded to 3 d.p where necessary)\n");
        formatBuilder.append("====================================================================\n");
        formatBuilder.append(calc.steps).append("\n");
        formatBuilder.append("====================================================================\n");

        // If output is a number (1x1 matrix)
        if (calc.matrix.length == 1 && calc.matrix[0].length == 1) {
            formatBuilder.append(" FINAL OUTPUT ANSWER\n");
            formatBuilder.append("====================================================================\n");
            formatBuilder.append(String.format(" Determinant = %.0f\n", calc.matrix[0][0]));
        // For every other matrix
        } else {
            formatBuilder.append(" FINAL OUTPUT ANSWER GRID\n");
            formatBuilder.append("====================================================================\n");
            formatBuilder.append(matrixToString(calc.matrix));
        }

        formatBuilder.append("====================================================================\n");
        
        outputArea.setText(formatBuilder.toString());
        outputArea.setCaretPosition(0); // Return the scroll bar back to the top
    }

    // Copy output text to clipboard
    private void copyOutputToClipboard() {

        // Retrieve text
        String textToCopy = outputArea.getText();

        // Check if text null, empty, or only white space
        if (textToCopy == null || textToCopy.trim().isEmpty()) {
            JOptionPane.showMessageDialog(frame, "The output box is empty.");
            return;
        }

        Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
        StringSelection selection = new StringSelection(textToCopy); // Wrap raw string text
        clipboard.setContents(selection, selection); // Overwrite current clipboard contents

        // Success message to confirm text has been copied
        JOptionPane.showMessageDialog(frame, "Output successfully copied to clipboard.");
    }

    // Copy output result to chosen matrix grid
    private void copyOutputToInputGrid(int matrix) {

        // Check there is an output result to copy
        if (lastResultMatrix == null) {
            JOptionPane.showMessageDialog(frame, "There is no calculated matrix resut");
            return;
        }

        int rows = lastResultMatrix.length;
        int cols = lastResultMatrix[0].length;

        if (matrix == 1) {
            matrix1Fields = createMatrixGrid(matrix1Panel, rows, cols, false);

            for (int i = 0; i < rows; i++) {
                for (int j = 0; j < cols; j++) {
                    matrix1Fields[i][j].setText(String.format("%.3f", lastResultMatrix[i][j]));
                }
            }
        } else if (matrix == 2) {
            matrix2Fields = createMatrixGrid(matrix2Panel, rows, cols, false);

            for (int i = 0; i < rows; i++) {
                for (int j = 0; j < cols; j++) {
                    matrix2Fields[i][j].setText(String.format("%.3f", lastResultMatrix[i][j]));
                }
            }
        }
    }
}