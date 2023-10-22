import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Scanner;


public class Main {

    static final String SALES = "data/sales.csv"; 

    static double furnitureSales = 0;
    static double technologySales = 0;
    static double officeSales = 0;
    static double totalAverage = 0;

    public static void main(String[] args) {
        
        try {
            Path path = Paths.get(Thread.currentThread().getContextClassLoader().getResource(SALES).toURI());
            Thread thread2 = new Thread(() -> furnitureSales = average(path, "Furniture"));  
            Thread thread3 = new Thread(() -> technologySales = average(path, "technology"));
            Thread thread4 = new Thread(() -> officeSales = average(path, "office supplies"));
            Thread thread5 = new Thread(() -> totalAverage = totalAverage(path));

            thread2.start();
            thread3.start();
            thread4.start();
            thread5.start();

            Scanner scan = new Scanner(System.in);
            System.out.print("Please enter your name to access the Global Superstore dataset: ");
            String name = scan.nextLine();
            scan.close();

            try {
                thread2.join();
                thread3.join();
                thread4.join();
                thread5.join();
                
            } catch (InterruptedException e) {
                System.out.println(e.getMessage());
            }

            System.out.println("\nThank you " + name + ". The average sales for Global Superstore are:\n");
            System.out.println("Average Furniture Sales: " + furnitureSales);
            System.out.println("Average Technology Sales: " + technologySales);
            System.out.println("Average Office Supplies Sales: " + officeSales);
            System.out.println("Total Average: " + totalAverage);


        } catch (URISyntaxException e) {
            System.out.println(e.getMessage());
        }

        

    }

    /**
     * Function name: average
     * @param path (Path)
     * @param category (String)
     * @return Double
     * 
     * Inside the function:
     *   1. Runs through every line from the CSV file as a stream.
     *   2. Maps every element in the stream to an array of three String values.
     *   3. Filters every value by the @param category
     *   4. Maps every element in the stream to a double (price * quantity).
     *   5. Applies the terminal operation average.
     *   6. Returns the average as double.
     * 
     */
    public static Double average(Path path, String category) {
        try {
            Double average = Files.lines(path)
                .skip(1)
                .map(line -> line.split(","))
                .filter(line -> line[0].equalsIgnoreCase(category))
                .mapToDouble(line -> Double.parseDouble(line[1]) * Double.parseDouble(line[2]))
                .average().orElse(0.0);

            return average;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return 0.0;
        }
    }



    /**
     * Function name: totalAverage
     * @param path (Path)
     * @return Double
     * 
     * Inside the function:
     *   1. Runs through every line from the CSV file as a stream.
     *   2. Maps every element in the stream to an array of three values.
     *   3. Maps every element in the stream to a double: (price * quantity).
     *   4. Applies the terminal operation average.
     *   5. Returns the average as double.
     */
    public static Double totalAverage(Path path) {
        try {
            Double average = Files.lines(path)
                .skip(1)
                .map(line -> line.split(","))
                .mapToDouble(line -> Double.parseDouble(line[1]) * Double.parseDouble(line[2]))
                .average().orElse(0.0);
            return average;
        } catch (IOException e){
            System.out.println("Error reading file");
            return 0.0;
        }
    }
  

}
