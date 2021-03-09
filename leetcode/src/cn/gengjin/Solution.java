package cn.gengjin;

public class Solution {

    public static void main(String[] args) {

        System.out.println(isPalindrome(101));
    }

    /**
     * 首先负数和0结尾的数直接排除
     *
     * 将数字的前半部分或者后半部分反转，再与另一半比较
     * 可以用除10和模10的方法反转
     * 用剩下的部分的值 < 反转好的部分的值 判断反转到了一半的位置
     * 由于要使用剩下的部分进行比较，因此反转后半部分，保留前半部分
     *
     * 还有考虑奇数长度的数，反转时如何解决最中间的那个（后退）
     *
     */
    public static boolean isPalindrome(int x) {

        if (x >=0 && x < 10) return true;
        if (x < 0 || x % 10 == 0) return false;

        int right = 0;

        while (x > right) {
            right = right * 10 + x % 10;
            x = x / 10;
        }
        if (x == right) {
            return true;
        } else return x == right / 10;// 应对101 -> 1和10的情况
    }
}
