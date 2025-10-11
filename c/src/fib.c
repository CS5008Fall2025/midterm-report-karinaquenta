#include <stdio.h>
#include <stdlib.h>
#include <time.h>

//Function Declarations
int iterative_fib(int n);
int recursive_fib(int n);
int dynamic_fib(int n);

//Created a counted wrapper for recursion to report "ops"
static unsigned long long rec_ops = 0;
static int recursive_fib_counted(int n) {
    if (n <= 1) return n;
    rec_ops++;     // count one combination/addition
    return recursive_fib_counted(n-1) + recursive_fib_counted(n-2);
}

//Iterative : prints the series 0..N and returns F(N)

int iterative_fib(int n){
    if (n <= 1){
        // Print series for small n
        printf("Iterative Series: ");
        if (n == 0) { printf("0\n"); return 0; }
        else { printf("0 1\n"); return 1; }
    }

    int prev_num_1 = 0;   // F(0)
    int prev_num_2 = 1;   // F(1)

    // Print the first two numbers
    printf("Iterative Series: %d %d ", prev_num_1, prev_num_2);

    // Build and print up to F(n)
    for (int i = 2; i <= n; i++){
        int current_num = prev_num_1 + prev_num_2;   // next Fib number
        printf("%d%s", current_num, (i == n ? "\n" : " ")); // added space-separater and newline at end to look clean
        prev_num_1 = prev_num_2;
        prev_num_2 = current_num;
    }

    return prev_num_2;  // F(n)
}

//Recursive 

int recursive_fib(int n){
    if (n <= 1) return n;    // base case
    return recursive_fib(n-1) + recursive_fib(n-2);
}

//Dynamic programming used tabulation: prints the series 0..N (inclusive & returns F(N)

int dynamic_fib(int n){
    if (n <= 1){
        // Print series for small n
        printf("Dynamic Series: ");
        if (n == 0) { printf("0\n"); return 0; }
        else { printf("0 1\n"); return 1; }
    }

    int *fib = (int *)malloc((n+1) * sizeof(int));
    if (!fib) { printf("Memory allocation failed!\n"); exit(1); }

    fib[0] = 0;
    fib[1] = 1;

    printf("Dynamic Series: %d %d ", fib[0], fib[1]);

    for (int i = 2; i <= n; i++){
        fib[i] = fib[i-1] + fib[i-2]; // builds up from previous
        printf("%d%s", fib[i], (i == n ? "\n" : " "));
    }

    int results = fib[n];    // F(n)
    free(fib);         // preventing memory leak
    return results;
}

//Main:prints time + ops for each method

int main(void) {
    int n;
    printf("Please enter which Fibonacci number to find: ");
    if (scanf("%d", &n) != 1) {
        printf("Invalid input.\n");
        return 1;
    }
    if (n < 0) {
        printf("Please enter a non-negative N.\n");
        return 1;
    }

    // Print the series 1..N inclusive (N+1 terms) via a tiny stream
    printf("\nFibonacci series (1..%d): ", n);
    if (n <= 0) {
        printf("<none>\n");
    } else if (n == 1) {
        printf("0\n"); //F(0)
    } else {
        int a = 0, b = 1;
        printf("%d %d", a, b);      // F(0), F(1)
        for (int i = 2; i < n; i++) {  // stop before n
          int c = a+b;
            printf(" %d", c);
            a = b;  b = c;
        }
        printf("\n");
    }

    // The timing vars to calculate speed and ops for each
    clock_t start_clock, end_clock;
    double elapsed_time_seconds;
    unsigned long long operation_count;

    // Iterative: ops ≈ (n-1) additions for n>=2
    operation_count = (n >= 2) ? (unsigned long long)(n - 1) : 0ULL;
    start_clock = clock();
    int iterative_value = iterative_fib(n);   // also prints its own series
    end_clock = clock();
    elapsed_time_seconds = (double)(end_clock - start_clock) / CLOCKS_PER_SEC;

    printf("\nIterative Approach:\n");
    printf("  F(%d) = %d\n", n, iterative_value);
    printf("  Operations performed: %llu\n", operation_count);
    printf("  Time taken: %.6f seconds\n", elapsed_time_seconds);

    //Recursive: count ops via wrapper (exponential) 
    rec_ops = 0;
    start_clock = clock();
    int recursive_value = recursive_fib_counted(n);
    end_clock = clock();
    elapsed_time_seconds = (double)(end_clock - start_clock) / CLOCKS_PER_SEC;

    printf("\nRecursive Approach:\n");
    printf("  F(%d) = %d\n", n, recursive_value);
    printf("  Operations performed: %llu\n", rec_ops);
    printf("  Time taken: %.6f seconds\n", elapsed_time_seconds);

    // Dynamic programming: ops ≈ (n-1) additions for n>=2
    operation_count = (n >= 2) ? (unsigned long long)(n - 1) : 0ULL;
    start_clock = clock();
    int dp_value = dynamic_fib(n);// also prints its own series
    end_clock = clock();
    elapsed_time_seconds = (double)(end_clock - start_clock) / CLOCKS_PER_SEC;

    printf("\nDynamic Programming Approach:\n");
    printf("  F(%d) = %d\n", n, dp_value);
    printf("  Operations performed: %llu\n", operation_count);
    printf("  Time taken: %.6f seconds\n\n", elapsed_time_seconds);

    return 0;
}
