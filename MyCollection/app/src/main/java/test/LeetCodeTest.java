package test;

/**
 * Description :leetCode刷题
 * Author :cgy
 * Date :2018/9/7
 */
public class LeetCodeTest {

    public static void main(String args[]) {
        System.out.println("Hello World!");

        System.out.println("result:" + twoSum(new int[]{1, 4, 5, 6, 7}, 6));
    }


    //    给定一个整数数组和一个目标值，找出数组中和为目标值的两个数。
//
//    你可以假设每个输入只对应一种答案，且同样的元素不能被重复利用。
//
//    示例:
//
//    给定 nums = [2, 7, 11, 15], target = 9
//
//    因为 nums[0] + nums[1] = 2 + 7 = 9
//    所以返回 [0, 1]
    public static int[] twoSum(int[] nums, int target) {
        int[] result = new int[2];
        if (nums == null || nums.length == 0) {
            return null;
        }

        for (int i = 0; i < nums.length; i++) {
            if (i >= nums.length - 1)
                break;

            for (int j = i + 1; j < nums.length; j++) {
                if (nums[i] + nums[j] == target) {
                    result[0] = i;
                    result[1] = j;
                    return result;
                }
            }
        }
        return null;
    }

}
