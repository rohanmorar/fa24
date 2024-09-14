public class StarTriangle {
    public static void main(String[] args) {
        int noRows = 5;
        for (int r = 0; r < noRows; r++) {
            for (int c = 0; c < r; c++) {
                System.out.print("*");
            }
            System.out.println("*");
        }
    }
}
