# Matrix-Calculator
A desktop application built in Java Swing for performing a wide range of matrix operations. It allows users to generate matrices, apply mathematical transformations, view step-by-step solutions, and export results for further use.

The user can create custom matrix grids (1x1 to 10x10), or generate random matrices. 

Features:
- Matrix addition
- Matrix subtraction
- Matrix multiplication
- Scalar multiplication
- Scalar division
- Matrix transposition
- Matrix determinant
- Matrix inverse
- Reduced row echelon form (RREF)

The output is displayed as a step-by-step solution, with the final answer shown at the bottom. Users can copy the result to the clipboard, or back into Matrix 1/2. Additionally, results can be exported as a CSV file for external use.

Tech Stack:
- Java 17+
- Swing (UI)
- AWT (layout and rendering)
- Custom RoundedButton components
- Modular helper classes (e.g. MatrixExporter)

Project Structure:
- MatrixCalculator.java - main UI and application logic
- Matrix.java - mathematical operations
- Calculations.java - result and step tracking
- MatrixExporter.java - CSV export functionality
- RoundedButton.java - custom UI component
- MatrixHome.java / MatrixHelp.java - navigation pages

How to Run (options):
1. Clone the repository
   git clone https://github.com/yourusername/Matrix-Calculator.git
1. Download ZIP
   click the green 'Code' button, select 'Download ZIP', extract the folder
2. Open the project in your Java IDE.
3. Ensure you have Java 17+ installed.
4. Run the MatrixCalculator.java file.

Author:
Melanie Pritchard
BSc Computer Science Student
University of Sheffield
@ 2026

