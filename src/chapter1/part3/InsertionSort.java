package chapter1.part3;

import java.util.Arrays;

public class InsertionSort {
    public static void insertSort(int[] array) {
        for (int i = 1; i < array.length; i++) {
            int insertValue = array[i];
            int j = i - 1;
            //从右向左比较元素的同时，进行元素复制
            for (; (j >= 0) && (insertValue < array[j]); j--) {
                array[j + 1] = array[j];
            }
            //insertValue的值插入适当位置
            array[j + 1] = insertValue;
        }
    }

    public static void main(String[] args) {
        int[] array = { 12, 1, 3, 46, 5, 0, -3, 12, 35, 16 };
        insertSort(array);
        System.out.println(Arrays.toString(array));
    }
}
