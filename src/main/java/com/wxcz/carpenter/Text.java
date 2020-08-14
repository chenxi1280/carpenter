package com.wxcz.carpenter;

/**
 * @author by cxd
 * @Classname Text
 * @Description TODO
 * @Date 2020/8/14 13:14
 */
public class Text {

    public static void main(String[] args) {
        int[] a = {1,1,2};
        removeDuplicates(a);

        for (int i : a) {
            System.out.println(i);

        }
    }

    public static int removeDuplicates(int[] nums) {
        if (nums == null ) {
            return 0;
        }
        int i = 0;
        for (int j = 0; j < nums.length; j++) {
            if (nums[i] != nums[j]) {
                i++;
                nums[i] = nums[j];
            }

        }

        return i+1;
    }
}
