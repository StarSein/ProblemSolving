class Solution {
    fun solution(message: String, spoiler_ranges: Array<IntArray>): Int {
        // 스포 방지 여부를 기준으로 단어 분류
        val spoilers = mutableListOf<String>()
        val openWords = hashSetOf<String>()
        val vipWords = hashSetOf<String>()
        var srIdx = 0
        var ws = message.indexOfFirst { it != ' ' }
        val words = message.trim().split(" ")
        words.forEach { word ->
            val we = ws + word.length - 1
            while (srIdx < spoiler_ranges.size && spoiler_ranges[srIdx][1] < ws) {
                srIdx++
            }
            if (srIdx >= spoiler_ranges.size) {
                openWords.add(word)
            } else {
                val (rs, re) = spoiler_ranges[srIdx]
                if (we < rs) {
                    openWords.add(word)
                } else {
                    spoilers.add(word)
                }
                ws = we + 2
            }
        }
        
        // 스포 방지 단어를 차례대로 중요한 단어인지 확인
        spoilers.forEach { word ->
            if (!openWords.contains(word)) {
                vipWords.add(word)    
            }
        }
        
        // 중요한 단어의 개수를 정답으로 반환
        return vipWords.size
    }
}