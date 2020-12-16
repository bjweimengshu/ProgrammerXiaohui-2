package chapter4.part4;


/**
 * Created by bjwei on 2020/9/10.
 */
public class KMP {
    // KMP算法主体逻辑。str是主串，pattern是模式串
    public static int kmp(String str, String pattern) {
        //预处理，生成next数组
        int[] next = getNexts(pattern);
        int j = 0;
        //主循环，遍历主串字符
        for (int i = 0; i < str.length(); i++) {
            while ((j > 0) && (str.charAt(i) != pattern.charAt(j))) {
                //遇到坏字符时，查询next数组并改变模式串的起点
                j = next[j];
            }
            if (str.charAt(i) == pattern.charAt(j)) {
                j++;
            }
            if (j == pattern.length()) {
                //匹配成功，返回下标
                return i - pattern.length() + 1;
            }
        }
        return -1;
    }

    // 生成Next数组
    private static int[] getNexts(String pattern) {
        int[] next = new int[pattern.length()];
        int j = 0;

        for (int i = 2; i < pattern.length(); i++) {
            while ((j != 0) && (pattern.charAt(j) != pattern.charAt(i - 1))) {
                //从next[i+1]的求解回溯到 next[j]
                j = next[j];
            }
            if (pattern.charAt(j) == pattern.charAt(i - 1)) {
                j++;
            }
            next[i] = j;
        }
        return next;
    }

    public static void main(String[] args) {
        String str = "ATGTGAGCTGGTGTGTGCFAA";
        String pattern = "GTGTGCF";
        int index = kmp(str, pattern);
        System.out.println("首次出现位置：" + index);
    }
}
