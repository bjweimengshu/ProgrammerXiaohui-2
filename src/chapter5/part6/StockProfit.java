package chapter5.part6;

/**
 * Created by bjwei on 2020/10/9.
 */
public class StockProfit {

    public static int maxProfitFor1Time(int prices[]) {
        if(prices==null || prices.length==0) {
            return 0;
        }
        int minPrice = prices[0];
        int maxProfit = 0;
        for (int i = 1; i < prices.length; i++) {
            int profit = prices[i] - minPrice;
            if(profit > maxProfit){
                maxProfit = profit;
            } else if (prices[i] < minPrice) {
                minPrice = prices[i];
            }
        }
        return maxProfit;
    }

    public static int maxProfitForAnyTime(int[] prices) {
        int maxProfit = 0;
        for (int i = 1; i < prices.length; i++) {
            if (prices[i] > prices[i-1])
                maxProfit += prices[i] - prices[i-1];
        }
        return maxProfit;
    }

    //最大买卖次数
    private static int MAX_DEAL_TIMES = 2;

    public static int maxProfitFor2Time(int[] prices) {
        if(prices==null || prices.length==0) {
            return 0;
        }
        //表格的最大行数
        int n = prices.length;
        //表格的最大列数
        int m = MAX_DEAL_TIMES*2+1;
        //使用二维数组记录数据
        int[][] resultTable = new int[n][m];
        //填充初始状态
        resultTable[0][1] = -prices[0];
        resultTable[0][3] = -prices[0];
        //自底向上，填充数据
        for(int i=1;i<n;++i) {
            for(int j=1;j<m;j++){
                if((j&1) == 1){
                    //j为奇数的情况
                    resultTable[i][j] = Math.max(resultTable[i-1][j], resultTable[i-1][j-1]-prices[i]);
                }else {
                    //j为偶数的情况
                    resultTable[i][j] = Math.max(resultTable[i-1][j], resultTable[i-1][j-1]+prices[i]);
                }
            }
        }
        //返回最终结果
        return resultTable[n-1][m-1];
    }

    public static int maxProfitFor2TimeV2(int[] prices) {
        if(prices==null || prices.length==0) {
            return 0;
        }
        //表格的最大行数
        int n = prices.length;
        //表格的最大列数
        int m = MAX_DEAL_TIMES*2+1;
        //使用一维数组记录数据
        int[] resultTable = new int[m];
        //填充初始状态
        resultTable[1] = -prices[0];
        resultTable[3] = -prices[0];
        //自底向上，填充数据
        for(int i=1;i<n;i++) {
            for(int j=1;j<m;j++){
                if((j&1) == 1){
                    //j为奇数的情况
                    resultTable[j] = Math.max(resultTable[j], resultTable[j-1]-prices[i]);
                }else {
                    //j为偶数的情况
                    resultTable[j] = Math.max(resultTable[j], resultTable[j-1]+prices[i]);
                }
            }
        }
        //返回最终结果
        return resultTable[m-1];
    }

    public static int maxProfitForKTime(int[] prices, int k) {
        if(prices==null || prices.length==0 || k<=0) {
            return 0;
        }
        //表格的最大行数
        int n = prices.length;
        //表格的最大列数
        int m = k*2+1;
        //使用一维数组记录数据
        int[] resultTable = new int[m];
        //填充初始状态
        for(int i=1;i<m;i+=2) {
            resultTable[i] = -prices[0];
        }
        //自底向上，填充数据
        for(int i=1;i<n;i++) {
            for(int j=1;j<m;j++){
                if((j&1) == 1){
                    //j为奇数的情况
                    resultTable[j] = Math.max(resultTable[j], resultTable[j-1]-prices[i]);
                }else {
                    //j为偶数的情况
                    resultTable[j] = Math.max(resultTable[j], resultTable[j-1]+prices[i]);
                }
            }
        }
        //返回最终结果
        return resultTable[m-1];
    }

    public static void main(String[] args) {
        int[] prices = {9,2,7,4,3,1,8,4};
        System.out.println(maxProfitFor1Time(prices));
        System.out.println(maxProfitForAnyTime(prices));
        System.out.println(maxProfitFor2Time(prices));
        System.out.println(maxProfitForKTime(prices, 3));
    }

}


