
import java.util.Scanner;        // Using for user input
import java.text.DecimalFormat;  // For organized formatting

public class FibonacciMethods {

    // === Global counter for recursive ops (like static in C) ===
    private static long recOps = 0;  // counts how many "additions/combinations" we do in recursion

    public static void main(String[] args) {
        // Create a Scanner to read from standard input (keyboard)
        Scanner sc = new Scanner(System.in);

        // Ask the user for N
        System.out.print("Please enter which Fibonacci number to find: ");
        // Read an integer N (we'll treat it like C code: allow 0 and up)
        if (!sc.hasNextInt()) {
            System.out.println("Invalid input.");
            sc.close();
            return;  // stop program if input isn't an int
        }
        int n = sc.nextInt();
        sc.close(); // close scanner (good practice)

        //Validating N (non-negative)
        if (n < 0) {
            System.out.println("Please enter a non-negative N.");
            return; // stop if invalid
        }

        //Printing N terms: F(0), F(1), ..., F(N-1)
        System.out.print("\nFibonacci series (1.." + n + "): ");
        if (n <= 0) {
            System.out.println("<none>");
        } else if (n == 1) {
            //Only one term: F(0)
            System.out.println("0");
        } else {
            //Prints first two terms, then loop
            int a = 0;  // F(0)
            int b = 1;  // F(1)
            System.out.print(a + " " + b);
            for (int i = 2; i < n; i++) {  // print until we have N terms total
                int c = a + b;             // next term
                System.out.print(" " + c); // print with a leading space
                a = b;                     // slide the window forward
                b = c;
            }
            System.out.println(); // newline at end
        }

        //Using System.nanoTime() to measure durations and formatting seconds
        DecimalFormat timeFmt = new DecimalFormat("0.000000");

        // Iteratively: prints its own series 0..N and returns F(N)
        
        // For iterative, ops ≈ (n - 1) additions when n >= 2, else 0 
        long iterativeOps = (n >= 2) ? (n - 1L) : 0L;

        long start = System.nanoTime();           // start timing
        int iterativeValue = iterativeFib(n);     // also prints its own series
        long end = System.nanoTime();             // end timing
        double iterativeSeconds = (end - start) / 1_000_000_000.0;

        System.out.println("\nIteratively:");
        System.out.println("  F(" + n + ") = " + iterativeValue);
        System.out.println("  Operations performed: " + iterativeOps);
        System.out.println("  Time taken: " + timeFmt.format(iterativeSeconds) + " seconds");


        // Recursively: uses operation counter (counts "combination" steps)
    
        // Reset global counter
        recOps = 0;
        start = System.nanoTime();
        int recursiveValue = recursiveFib(n);  // wrapper counts ops in a global long
        end = System.nanoTime();
        double recursiveSeconds = (end - start) / 1_000_000_000.0;

        System.out.println("\nRecursively:");
        System.out.println("  F(" + n + ") = " + recursiveValue);
        System.out.println("  Operations performed: " + recOps);
        System.out.println("  Time taken: " + timeFmt.format(recursiveSeconds) + " seconds");

      
        // Using Dynamic Programming

        // ops ≈ (n - 1) additions when n >= 2, else 0 (same as iterative)
        long dpOps = (n >= 2) ? (n - 1L) : 0L;

        start = System.nanoTime();
        int dpValue = dynamicFib(n); // also prints its own series
        end = System.nanoTime();
        double dpSeconds = (end - start) / 1_000_000_000.0;

        System.out.println("\nDynamic Programming Approach:");
        System.out.println("  F(" + n + ") = " + dpValue);
        System.out.println("  Operations performed: " + dpOps);
        System.out.println("  Time taken: " + timeFmt.format(dpSeconds) + " seconds\n");
    }

    // Iteratively: prints series 0..N and returns F(N) 
  
    public static int iterativeFib(int n) {
        // Handle tiny n (print series appropriately and return)
        if (n <= 1) {
            System.out.print("Iterative Series: ");
            if (n == 0) { 
                System.out.println("0");
                return 0;
            } else {
                System.out.println("0 1");
                return 1;
            }
        }

        int prev1 = 0; // F(0)
        int prev2 = 1; // F(1)

        // Print first two numbers
        System.out.print("Iterative Series: " + prev1 + " " + prev2 + " ");

        // Build and print up to F(n)
        for (int i = 2; i <= n; i++) {
            int current = prev1 + prev2;           // next Fibonacci number
            // print number, then either space or newline at the end
            if (i == n) {
                System.out.println(current);       // print last one with newline
            } else {
                System.out.print(current + " ");   // print with space
            }
            prev1 = prev2;                         // move forward
            prev2 = current;
        }

        return prev2; // F(n)
    }

    // Recursive: counted wrapper (counts one "combination/addition" per non-base call) 

    public static int recursiveFib(int n) {
        // Base case: F(0)=0, F(1)=1
        if (n <= 1) return n;

        // Count a combination/addition at this node
        recOps++;

        // Recurse on subproblems
        return recursiveFib(n - 1) + recursiveFib(n - 2);
    }

    // Dynamic Programming using tabulation: prints its own series 0..N and returns F(N))

    public static int dynamicFib(int n) {
        // Handle tiny n (print series appropriately and return)
        if (n <= 1) {
            System.out.print("Dynamic Series: ");
            if (n == 0) { 
                System.out.println("0");
                return 0;
            } else {
                System.out.println("0 1");
                return 1;
            }
        }

        // Create an array to store all values up to F(n)
        int[] fib = new int[n + 1];  // Java auto-inits to 0
        fib[0] = 0;                   // F(0)
        fib[1] = 1;                   // F(1)

        // Print the first two
        System.out.print("Dynamic Series: " + fib[0] + " " + fib[1] + " ");

        // Fill the table and print as we go
        for (int i = 2; i <= n; i++) {
            fib[i] = fib[i - 1] + fib[i - 2];     // tabulation step
            if (i == n) {
                System.out.println(fib[i]);        // last term + newline
            } else {
                System.out.print(fib[i] + " ");    // space between terms
            }
        }

        // Return F(n)
        return fib[n];
    }
}
