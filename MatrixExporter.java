public class MatrixExporter {
    
    // Convert matrix to CSV format for exporting
    // Each row becomes a comma-separated line
    public static String toCSV(double[][] matrix) {

        if (matrix == null) return "";

        StringBuilder sb = new StringBuilder();

        for (double[] row : matrix) {
            for (int i = 0; i < row.length; i++) {
                sb.append(row[i]);
                if (i < row.length - 1) sb.append(",");
            }
            sb.append("\n");
        }

        return sb.toString();
    }
}
