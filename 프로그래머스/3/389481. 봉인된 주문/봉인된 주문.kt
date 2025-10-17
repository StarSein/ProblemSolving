/**

# 분석
1. n이 10^15까지 가능하므로, 그만큼의 개수를 자료 구조로 관리하는 건 불가능하다.
2. 삭제된 주문이 없다고 할 때 n번째 주문을 찾는 작업은 비교적 간단하다.
   1) n번째 주문의 글자 수 찾기
     if (n <= 26^1) : 1자리
     else if (n <= 26^1 + 26^2) : 2자리
     else if (n <= 26^1 + ... + 26^t) : t자리
   2) t자리라는 것을 알았으면 m = (n - 26^1 - ... - 26^(t-1)) 이라고 할 때,
     계산의 편의를 위해 m -= 1 을 추가로 해 주고,
     a = 0, b = 1, c = 2, ... , z = 25 로 표현되는 26진법으로 간주한다.
   예) 30번째 주문 찾기
     (1) 2자리 주문
     (2) m = 30 - 26^1 = 4
         m-1 = 3
         30번째 주문은 ad
3. 서로 다른 두 주문 사이의 선후 관계를 따지는 작업은 비교적 간단하다.
   삭제된 주문을 A, 삭제 없는 주문들 중 n번째 주문을 B라고 하자.
   1) A가 B보다 짧을 경우, A가 B의 앞에 있었던 것이므로, B보다 한 칸 뒤에 있는 주문이 새로운 B가 되어야 한다.
   2) A와 B의 길이가 같은데, 사전 순으로 A가 B보다 앞에 있는 경우, 마찬가지로 B보다 한 칸 뒤에 있는 주문이 새로운 B가 되어야 한다.
   3) 단, B보다 한 칸 뒤에 있는 주문이 이미 삭제된 주문일 수 있다. 
      삭제에 따라 B가 밀려나는 작업이 누락되거나, 불필요하게 중복될 위험이 있다.
   4) 따라서 '삭제된 주문' 관련하여 3가지 작업을 해야 한다.
      (1) '삭제된 주문'과 B의 대조를 반복(iteration)해야 함
      (2) B 다음의 주문이 삭제되었는지 확인해야 함
      (3) '삭제된 주문'이 B의 밀려남에 이미 기여했는지 여부를 확인하기 위함
   5) 각각 (1) Array, (2) Set, (3) Set 으로 3개의 자료 구조를 이용할 수도 있겠지만,   
      '삭제된 주문'의 Map을 이용하면 1개의 자료 구조로 (1), (2), (3) 모두 처리 가능하다.
      (key: 삭제된 주문, value: B 밀려남에 기여 여부)
   예) 30번째 주문 찾기 (B = "ad")
      keys = ["d", "e", "bb", "aa", "ae"]
      values = [F, F, F, F, F]
      "d" 를 고려 -> B = "ae" -> B = "af" [T, F, F, F, T]
      "e" 를 고려 -> B = "ag" [T, T, F, F, T]
      "bb" 를 고려 (A가 B보다 뒤에 있음)
      "aa" 를 고려 -> B = "ah" [T, T, F, T, T]
      "ae" 를 고려 (A가 B보다 앞에 있으나, "ae"는 이미 B의 밀려남에 기여한 바 있음)

# 접근 - O(log_{26}(N) + M) (N은 n, M은 bans의 길이) (문자열의 길이는 최대 11이므로, 고려하지 않아도 되는 상수로 간주함)
1. 아무것도 삭제되지 않았을 때 n번째 주문, (B)을 찾는다.
  1) 글자 수 t 찾기
  2) m = (n - 26^1 - ... - 26^(t-1)) 이라고 할 때,
     계산의 편의를 위해 m -= 1 을 추가로 해 주고,
     a = 0, b = 1, c = 2, ... , z = 25 로 표현되는 26진법으로 간주한다.
  - n을 26^1, 26^2, 26^3, ..., 26^(i-1) 만큼 빼는데, 26^i >= n 이 되는 순간 멈춘다
    그때의 i가 바로 t이다.
2. { key: 삭제된 주문, value: B 밀려남에 기여 여부 } 인 hashMap 을 이용해서 아래 작업을 처리한다.
   (1) '삭제된 주문'과 B의 대조를 반복(iteration)해야 함
   (2) B 다음의 주문이 삭제되었는지 확인해야 함
   (3) '삭제된 주문'이 B의 밀려남에 이미 기여했는지 여부를 확인하기 위함
2-1. bans를 (원소의 길이, 사전 순) 기준으로 오름차순 정렬하여 순차적으로 (1) 작업을 수행하기만 해도 중복, 누락 문제가 해소된다. 
     다만, 시간 복잡도를 고려했을 때 다소 빠듯하다.
2-2. 기존의 bans 배열과 단 하나의 HashSet 으로 처리가 가능하다. B가 밀려나면서 중복 적용이 발생할 수는 없다.
     다만, iteration 과정에서 이미 B가 밀려오면서 거쳐 온 ban과 다시 한 번 대조하는 경우가 생길 수 있으므로,
     이 경우만 집합에서의 제거를 통해 처리해 주면 된다.
**/

class Solution {
    fun solution(n: Long, bans: Array<String>): String {
        // B 찾기
        var b = orderToSpell(n)
        println(b)
        
        // 삭제된 주문 처리하기
        var newN = n
        val banSet = bans.toHashSet()
        bans.forEach { ban ->
            if (ban.isBefore(b) && banSet.contains(ban)) {
                banSet.remove(ban)
                while (true) {
                    b = orderToSpell(++newN)
                    if (banSet.contains(b)) {
                        banSet.remove(b)
                    } else {
                        break
                    }
                }
            }
        }
        
        var answer: String = b
        return answer
    }
    
    fun String.isBefore(other: String): Boolean {
        if (this.length != other.length) return this.length < other.length
        return this <= other
    }
    
    fun orderToSpell(order: Long): String {
        var m = order
        var pow = 26L
        while (m > pow) {
            m -= pow
            pow *= 26L
        }
        pow /= 26L
        
        m--
        val sb = StringBuilder()
        while (pow >= 1L) {
            val digit = m / pow
            m -= digit * pow
            sb.append('a' + digit.toInt())
            pow /= 26L
        }
        return sb.toString()
    }
}