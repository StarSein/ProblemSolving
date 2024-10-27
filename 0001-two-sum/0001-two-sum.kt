/**
Figure out an algorithm less than O(N^2) time complexity.
1. O(N^2)
    - Outer Brute-force & Inner Brute-force
    - Each Brute-force has O(N) time complexity
2. Optimization
    - Reducing the number of searching
3. O(NlogN)
    - valid: Sorting & Outer Brute-force & Inner Binary-search
        - If a number is not the target of the binary search, the range which should be excluded from the search is obvious.
    - invalid: Outer Binary-search, Inner Brute-force
        - Even if a number is not able to be a pair with other number to make the target, the range of exclusion is obscure.
        - So, using binary search doubly is also impossible.
 */

class Solution {
    fun twoSum(nums: IntArray, target: Int): IntArray {
        lateinit var answer: IntArray

        // make a list of pair(pos, number)
        // and sort the list by the number in ascending order
        val pairs = nums.mapIndexed { index, i -> index to i}
            .sortedBy { (_, number) -> number }

        // find the pair to make the target
        pairs.forEachIndexed { index1, (pos1, number1) ->
            val diff = target - number1
            val index2 = pairs.binarySearch(
                fromIndex = index1 + 1,
                comparison = { (_, number2) -> number2 - diff }
            )
            if (index2 >= 0) {
                val pos2 = pairs[index2].first
                answer = intArrayOf(pos1, pos2)
                return@forEachIndexed
            }
        }

        // return the pair as an IntArray type
        return answer
    }
}