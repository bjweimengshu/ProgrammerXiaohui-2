package chapter3.part4;

public class Floyd {
    final static int INF = Integer.MAX_VALUE;

    public static void floyd(int[][] matrix) {
        //循环更新矩阵的值
        for (int k = 0; k < matrix.length; k++) {
            for (int i = 0; i < matrix.length; i++) {
                for (int j = 0; j < matrix.length; j++) {
                    if ((matrix[i][k] == INF) || (matrix[k][j] == INF)) {
                        continue;
                    }
                    matrix[i][j] = Math.min(matrix[i][j], matrix[i][k] + matrix[k][j]);
                }
            }
        }

        // 打印floyd最短路径的结果
        System.out.printf("最短路径矩阵: \n");

        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix.length; j++) {
                System.out.printf("%3d  ", matrix[i][j]);
            }
            System.out.printf("\n");
        }
    }

    public static void main(String[] args) {
        int[][] matrix = {
                { 0, 5, 2, INF, INF, INF, INF },
                { 5, 0, INF, 1, 6, INF, INF },
                { 2, INF, 0, 6, INF, 8, INF },
                { INF, 1, 6, 0, 1, 2, INF },
                { INF, 6, INF, 1, 0, INF, 7 },
                { INF, INF, 8, 2, INF, 0, 3 },
                { INF, INF, INF, INF, 7, 3, 0 }
        };
        floyd(matrix);
    }
}
