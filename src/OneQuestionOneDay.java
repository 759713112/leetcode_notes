import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class OneQuestionOneDay {
    public static void main(String[] args) {
        int validBit = (Integer.MAX_VALUE >> 11);
        System.out.println(Math.pow(2, 20) - 1);
    }

    public List<String> findRepeatedDnaSequences(String s) {
        List<String> res = new LinkedList<>();
        if (s.length() <= 10) return res;
        Map<Integer, Integer> count = new HashMap<>();
        Map<Character, Integer> translate = new HashMap<>(){{
           put('A', 0); put('C', 1);
           put('G', 2); put('T', 3);
        }};
        int window = 0;
        int validBit = (Integer.MAX_VALUE >> 11);
        for (int i=0; i<10; i++) {
            window = (window<<2) | translate.get(s.charAt(i));
        }
        count.computeIfAbsent(window & validBit, k->1);
        for (int i=1; i<s.length()-9; i++) {
            window = (window<<2) | translate.get(s.charAt(i+9));
            Integer temp = count.computeIfAbsent(window & validBit, k->0);
            if (temp == 1) {
                res.add(s.substring(i, i+10));
                count.put(window & validBit, ++temp);
            } else{
                count.put(window & validBit, ++temp);
            }
        }
        return res;
    }
}
