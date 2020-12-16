package chapter5.part3;

import java.util.*;

/**
 * Created by bjwei on 2020/9/21.
 */
public class TwoSum {

    public static List<List<Integer>> twoSum(int[] nums, int target) {
        Map<Integer, Integer> map = new HashMap<>();
        List<List<Integer>> resultList = new ArrayList<>();
        for (int i = 1; i < nums.length; i++) {
            map.put(nums[i], i);
        }
        for (int i = 0; i < nums.length; i++) {
            int d = target - nums[i];
            if (map.containsKey(d) && map.get(d) != i) {
                resultList.add(Arrays.asList(nums[i], d));
                //为防止找到重复的元素对，匹配后从哈希表删除对应元素
                map.remove(nums[i]);
            }
        }
        return resultList;
    }

    public static List<List<Integer>> twoSumV2(int[] nums, int target) {
        Map<Integer, Integer> map = new HashMap<>();
        List<List<Integer>> resultList = new ArrayList<>();
        for (int i = 0; i < nums.length; i++) {
            int d = target - nums[i];
            if (map.containsKey(d)) {
                resultList.add(Arrays.asList(nums[i], d));
            }
            map.put(nums[i], i);
        }
        return resultList;
    }

    public static void main(String[] args) {
        int[] nums = {5,12,6,3,9,2,1,7};
        List<List<Integer>> resultList = twoSumV2(nums, 12);
        for(List<Integer> list : resultList){
            System.out.println(Arrays.toString(list.toArray()));
        }
    }

}
