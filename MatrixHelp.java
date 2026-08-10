import javax.swing.*;
import javax.swing.border.EmptyBorder;

import java.awt.*;

public class MatrixHelp {
    
    private JFrame frame;

    public MatrixHelp() {

        // Create help window frame
        frame = new JFrame("Matrix Help");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1100, 850);
        frame.setLocationRelativeTo(null); // Centre window

        // Create container panel for entire window
        JPanel container = new JPanel(new BorderLayout());
        frame.setContentPane(container);

        // Create wrapper
        JPanel wrapper = new JPanel(new BorderLayout());
        wrapper.setBackground(new Color(165, 197, 208));
        wrapper.setBorder(new EmptyBorder(40, 50, 40, 50));

        // Create white canvas for text
        JPanel canvas = new JPanel(new BorderLayout());
        canvas.setBackground(Color.WHITE);
        canvas.setBorder(new EmptyBorder(40, 40, 40, 40));

        // Create non-editable text area for help content
        JTextArea textArea = new JTextArea();
        textArea.setEditable(false);
        textArea.setFocusable(false);
        textArea.setOpaque(false);
        textArea.setFont(new Font("monospaced", Font.PLAIN, 15));
        textArea.setForeground(Color.BLACK);

        textArea.setLineWrap(true);
        textArea.setWrapStyleWord(true);

        // Formatted text
        String text = 
            "Welcome\n" +
            "Matrix Calculator allows you to perform matrix operations quickly and accurately.\n\n" +
            "Supported operations:\n" +
            " • Matrix addition\n" +
            " • Matrix subtraction\n" +
            " • Matrix multiplication\n" +
            " • Scalar multiplication\n" +
            " • Scalar division\n" +
            " • Matrix transposition\n" +
            " • Matrix determinant\n" +
            " • Matrix inverse\n" +
            " • Reduced-row-echelon-form (RREF)\n\n" +
            "How to Use\n" +
            "1. Create two matrices:\n" +
            "   a. Manually:\n" +
            "      i. Enter the matrix dimensions (between 1x1 to 10x10)\n" +
            "     ii. Generate the matrix grid\n" +
            "    iii. Fill in the generated grid with values\n" +
            "   b. Automatically:\n" +
            "      i. Generate a random matrix\n" +
            "2. Select a mathematical operation:\n" +
            "   a. Fill in the scalar box if required\n" +
            "3. The result with steps will be displayed in the output area:\n" +
            "   a. Copy the output to your system's clipboard\n" +
            "   b. Copy the result matrix to either Matrix 1 or Matrix 2\n\n" +
            "Input Rules\n" +
            " • Leave no matrix grid boxes empty.\n" +
            " • Matrix dimensions must be compatible for the chosen operation.\n\n" +
            "About\n" +
            "Matrix Calculator v1.0\n" +
            "Created by Melanie Pritchard\n" +
            "2026";

        textArea.setText(text);

        JScrollPane scrollPane = new JScrollPane(textArea);
        scrollPane.setBorder(null);
        scrollPane.setOpaque(false);
        scrollPane.getViewport().setOpaque(false);

        canvas.add(scrollPane, BorderLayout.CENTER);
        wrapper.add(canvas, BorderLayout.CENTER);

        container.add(wrapper, BorderLayout.CENTER);
        container.add(createFooter(), BorderLayout.SOUTH);
   

        frame.setVisible(true);
    }

      // Create footer for copyright and navigation buttons
    private JPanel createFooter() {

        // Create panel for footer
        JPanel bottomPanel = new JPanel(new BorderLayout());
        bottomPanel.setBackground(Color.WHITE);
        bottomPanel.setBorder(new EmptyBorder(5, 8, 5, 8));

        // Create label for copyright text (left)
        JLabel copyright = new JLabel("@2026 Melanie Pritchard. All Rights Reserved.");
        copyright.setFont(new Font("SansSerif", Font.PLAIN, 12));

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

}
