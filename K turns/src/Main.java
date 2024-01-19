// Java program to count number of paths
// with maximum k turns allowed
import java.util.*;
class GFG
{
    static int MAX = 100;

    // table to store results of subproblems
    static int [][][][]dp = new int[MAX][MAX][MAX][2];

    // Returns count of paths to reach (i, j) from (0, 0)
// using at-most k turns. d is current direction
// d = 0 indicates along row, d = 1 indicates along
// column.
    static int countPathsUtil(int i, int j, int k, int d)
    {
        // If invalid row or column indexes
        if (i < 0 || j < 0)
            return 0;

        // If current cell is top left itself
        if (i == 0 && j == 0)
            return 1;

        // If 0 turns left
        if (k == 0)
        {
            // If direction is row, then we can reach here
            // only if direction is row and row is 0.
            if (d == 0 && i == 0) return 1;

            // If direction is column, then we can reach here
            // only if direction is column and column is 0.
            if (d == 1 && j == 0) return 1;

            return 0;
        }

        // If this subproblem is already evaluated
        if (dp[i][j][k][d] != -1)
            return dp[i][j][k][d];

        // If current direction is row,
        // then count paths for two cases
        // 1) We reach here through previous row.
        // 2) We reach here through previous column,
        // so number of turns k reduce by 1.
        if (d == 0)
            return dp[i][j][k][d] = countPathsUtil(i, j - 1, k, d) +
                    countPathsUtil(i - 1, j, k - 1, d == 1 ? 0 : 1);

        // Similar to above if direction is column
        return dp[i][j][k][d] = countPathsUtil(i - 1, j, k, d) +
                countPathsUtil(i, j - 1, k - 1, d == 1 ? 0 : 1);
    }

    // This function mainly initializes 'dp' array
// as -1 and calls countPathsUtil()
    static int countPaths(int i, int j, int k)
    {
        // If (0, 0) is target itself
        if (i == 0 && j == 0)
            return 1;

        // Initialize 'dp' array
        for(int p = 0; p < MAX; p++)
        {
            for(int q = 0; q < MAX; q++)
            {
                for(int r = 0; r < MAX; r++)
                    for(int s = 0; s < 2; s++)
                        dp[p][q][r][s] = -1;
            }
        }

        // Recur for two cases: moving along row and along column
        return countPathsUtil(i - 1, j, k, 1) + // Moving along row
                countPathsUtil(i, j - 1, k, 0); // Moving along column
    }

    // Driver Code
    public static void main(String[] args)
    {
        int m = 3, n = 3, k = 2;
        System.out.println("Number of paths is " +
                countPaths(m - 1, n - 1, k));
    }
}
