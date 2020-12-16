package chapter1.part6;

import java.util.Arrays;


public class RadixSort {
    //ascii码的取值范围
    public static final int ASCII_RANGE = 128;

    public static String[] radixSort(String[] array, int maxLength) {
        //排序结果数组，用于存储每一次按位排序的临时结果
        String[] sortedArray = new String[array.length];
        //从个位开始比较，一直比较到最高位
        for (int k = maxLength - 1; k >= 0; k--) {
            //计数排序的过程，分成三步：
            //1.创建辅助排序的统计数组，并把待排序的字符对号入座，
            //这里为了代码简洁，直接使用ascii码范围作为数组长度
            int[] count = new int[ASCII_RANGE];
            for (int i = 0; i < array.length; i++) {
                int index = getCharIndex(array[i], k);
                count[index]++;
            }
            //2.统计数组做变形，后面的元素等于前面的元素之和
            for (int i = 1; i < count.length; i++) {
                count[i] = count[i] + count[i - 1];
            }
            //3.倒序遍历原始数列，从统计数组找到正确位置，输出到结果数组
            for (int i = array.length - 1; i >= 0; i--) {
                int index = getCharIndex(array[i], k);
                int sortedIndex = count[index] - 1;
                sortedArray[sortedIndex] = array[i];
                count[index]--;
            }
            //下一轮排序需要以上一轮的排序结果为基础，因此把结果复制给array
            array = sortedArray.clone();
        }
        return array;
    }

    //获取字符串第k位字符所对应的ascii码序号
    private static int getCharIndex(String str, int k) {
        //如果字符串长度小于k，直接返回0，相当于给不存在的位置补0
        if (str.length() < (k + 1)) {
            return 0;
        }
        return str.charAt(k);
    }

    public static void main(String[] args) {
        String[] array = { "qd", "abc", "qwe", "hhh", "a", "cws", "ope" };
        System.out.println(Arrays.toString(radixSort(array, 3)));
    }
}
