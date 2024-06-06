package main.two_pointers.medium;

import java.util.Arrays;

import static java.lang.System.*;

/*
2563. Count the Number of Fair Pairs
Medium
Topics
Companies
Hint
Given a 0-indexed integer array nums of size n and two integers lower and upper, return the number of fair pairs.

A pair (i, j) is fair if:

0 <= i < j < n, and
lower <= nums[i] + nums[j] <= upper

Example 1:

Input: nums = [0,1,7,4,4,5], lower = 3, upper = 6
Output: 6
Explanation: There are 6 fair pairs: (0,3), (0,4), (0,5), (1,3), (1,4), and (1,5).
Example 2:

Input: nums = [1,7,9,2,5], lower = 11, upper = 11
Output: 1
Explanation: There is a single fair pair: (2,3).

Constraints:

1 <= nums.length <= 105
nums.length == n
-109 <= nums[i] <= 109
-109 <= lower <= upper <= 109
*/
public class CountTheNumberOfFairPairs {

	public static long countFairPairs(int[] nums, int lower, int upper) {
		Arrays.sort(nums);
		long count = 0;

		for (int i = 0; i < nums.length - 1; i++) {
			int lowIndex = findLowerBound(nums, i + 1, nums.length - 1, lower - nums[i]);
			int highIndex = findUpperBound(nums, i + 1, nums.length - 1, upper - nums[i]);
			out.println(
					"For nums[" + i + "] = " + nums[i] + ": lowIndex = " + lowIndex + ", highIndex = " + highIndex); // Debug print
			if (lowIndex <= highIndex) {
				count += (highIndex - lowIndex + 1);
			}
			out.println("Count after nums[" + i + "] = " + nums[i] + ": " + count); // Debug print
		}

		return count;
	}

	private static int findLowerBound(int[] nums, int start, int end, int target) {
		int left = start;
		int right = end;
		while (left <= right) {
			int mid = left + (right - left) / 2;
			if (nums[mid] < target) {
				left = mid + 1;
			} else {
				right = mid - 1;
			}
		}
		return left;
	}

	// Find the last index where nums[index] <= target
	private static int findUpperBound(int[] nums, int start, int end, int target) {
		int left = start;
		int right = end;
		while (left <= right) {
			int mid = left + (right - left) / 2;
			if (nums[mid] <= target) {
				left = mid + 1;
			} else {
				right = mid - 1;
			}
		}
		return right;
	}

	public static void main(String[] args) {

		int[] nums3 = { 0, 0, 0, 0, 0, 0 };
		int lower3 = 0;
		int upper3 = 0;
		out.println("Output: " + countFairPairs(nums3, lower3, upper3)); // Output: 15
	}
}
