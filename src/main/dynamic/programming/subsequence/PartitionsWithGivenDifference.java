package main.dynamic.programming.subsequence;

import java.util.Arrays;

/*
Problem statement
Given an array ‘ARR’, partition it into two subsets (possibly empty) such that their union is the original array. Let the sum of the elements of these two subsets be ‘S1’ and ‘S2’.

Given a difference ‘D’, count the number of partitions in which ‘S1’ is greater than or equal to ‘S2’ and the difference between ‘S1’ and ‘S2’ is equal to ‘D’. Since the answer may be too large, return it modulo ‘10^9 + 7’.

If ‘Pi_Sj’ denotes the Subset ‘j’ for Partition ‘i’. Then, two partitions P1 and P2 are considered different if:

1) P1_S1 != P2_S1 i.e, at least one of the elements of P1_S1 is different from P2_S2.
2) P1_S1 == P2_S2, but the indices set represented by P1_S1 is not equal to the indices set of P2_S2. Here, the indices set of P1_S1 is formed by taking the indices of the elements from which the subset is formed.
Refer to the example below for clarification.
Note that the sum of the elements of an empty subset is 0.

For example :
If N = 4, D = 3, ARR = {5, 2, 5, 1}
There are only two possible partitions of this array.
Partition 1: {5, 2, 1}, {5}. The subset difference between subset sum is: (5 + 2 + 1) - (5) = 3
Partition 2: {5, 2, 1}, {5}. The subset difference between subset sum is: (5 + 2 + 1) - (5) = 3
These two partitions are different because, in the 1st partition, S1 contains 5 from index 0, and in the 2nd partition, S1 contains 5 from index 2.
Input Format :
The first line contains a single integer ‘T’ denoting the number of test cases, then each test case follows:

The first line of each test case contains two space-separated integers, ‘N’ and ‘D,’ denoting the number of elements in the array and the desired difference.

The following line contains N integers denoting the space-separated integers ‘ARR’.
Output Format :
For each test case, find the number of partitions satisfying the above conditions modulo 10^9 + 7.
Output for each test case will be printed on a separate line.
Note :
You are not required to print anything; it has already been taken care of. Just implement the function.
Constraints :
1 ≤ T ≤ 10
1 ≤ N ≤ 50
0 ≤ D ≤ 2500
0 ≤ ARR[i] ≤ 50

Time limit: 1 sec
Sample Input 1 :
2
4 3
5 2 6 4
4 0
1 1 1 1
Sample Output 1 :
1
6
Explanation For Sample Input 1 :
For test case 1:
We will print 1 because :
There is only one possible partition of this array.
Partition : {6, 4}, {5, 2}. The subset difference between subset sum is: (6 + 4) - (5 + 2) = 3

For test case 2:
We will print 6 because :
The partition {1, 1}, {1, 1} is repeated 6 times:
Partition 1 : {ARR[0], ARR[1]}, {ARR[2], ARR[3]}
Partition 2 : {ARR[0], ARR[2]}, {ARR[1], ARR[3]}
Partition 3 : {ARR[0], ARR[3]}, {ARR[1], ARR[2]}
Partition 4 : {ARR[1], ARR[2]}, {ARR[0], ARR[3]}
Partition 5 : {ARR[1], ARR[3]}, {ARR[0], ARR[2]}
Partition 6 : {ARR[2], ARR[3]}, {ARR[0], ARR[1]}
The difference is in the indices chosen for the subset S1(or S2).
Sample Input 2 :
3
3 1
4 6 3
5 0
3 1 1 2 1
5 1
3 2 2 5 1
Sample Output 2 :
1
6
3
*/
public class PartitionsWithGivenDifference {

	static int mod = 1000000007;

	public static int countWaysUsingMemoization(int n, int totalSum, int[] arr, int[][] dp) {
		// Base case: if we've considered all elements
		if (n == 0) {
			if (totalSum == 0 && arr[0] == 0)
				return 2;
			if (totalSum == 0 || totalSum == arr[0])
				return 1;
			return 0;
		}

		// Check if this problem has already been solved
		if (dp[n][totalSum] != -1) {
			return dp[n][totalSum];
		}

		// Recursive cases:
		// 1. Include the current element in subset 1
		int include = 0;
		if (totalSum >= arr[n])
			include = countWaysUsingMemoization(n - 1, totalSum - arr[n], arr, dp);

		// 2. Exclude the current element from subset 1 (so it's in subset 2)
		int exclude = countWaysUsingMemoization(n - 1, totalSum, arr, dp);

		// Return the count of the two possibilities
		dp[n][totalSum] = (include + exclude) % mod;
		return dp[n][totalSum];
	}

	public static int countWaysUsingRecursion(int ind, int target, int[] arr) {
		// Base cases
		if (ind == 0) {
			if (target == 0 && arr[0] == 0)
				return 2;
			if (target == 0 || target == arr[0])
				return 1;
			return 0;
		}

		// Recursive cases
		int notTaken = countWaysUsingRecursion(ind - 1, target, arr);

		int taken = 0;
		if (arr[ind] <= target) {
			taken = countWaysUsingRecursion(ind - 1, target - arr[ind], arr);
		}

		// Return the sum of both cases (taking modulo)
		return (notTaken + taken) % mod;
	}

	public static int countPartitionsUsingRecursion(int n, int d, int[] arr, String ways) {
		// Calculate total sum of the array
		int totalSum = 0;
		for (int i = 0; i < n; i++) {
			totalSum += arr[i];
		}

		// Check if the partition is possible
		if ((totalSum - d) < 0 || (totalSum - d) % 2 != 0) {
			return 0;
		}

		int newTotalSum = (totalSum - d) / 2;

		// Memoization array to store intermediate results
		if (ways.equals("recursion")) {
			return countWaysUsingRecursion(n - 1, newTotalSum, arr);
		} else if (ways.equals("memoization")) {
			// Memoization table for storing results (initialize to -1)
			int[][] dp = new int[n + 1][newTotalSum + 1];
			for (int[] row : dp) {
				Arrays.fill(row, -1);
			}

			return countWaysUsingMemoization(n, newTotalSum, arr, dp);
		}
		return -1;
	}

	public static void main(String[] args) {
		int[] arr = new int[] { 5, 2, 6, 4 };
		int n = arr.length;
		int d = 3;


		System.out.println(countPartitionsUsingRecursion(n - 1, d, arr, "recursion"));
		System.out.println(countPartitionsUsingRecursion(n - 1, d, arr, "memoization"));


	}

}