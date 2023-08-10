package com.bingbaihanji.utils.math.familiar;

public class Sort {
    private static Comparable[] assist;

    /**
     * 判断v元素是否大于w元素
     */
    public static boolean greater(Comparable v, Comparable w) {
        return v.compareTo(w) > 0;
    }

    /**
     * 交换arr数组中元素i与j的位置
     */
    public static void exch(Comparable[] arr, int i, int j) {
        Comparable temp = arr[i];
        arr[i] = arr[j];
        arr[j] = temp;
    }

    /**
     * @param arr 待排数组
     * @return void
     * @MethodName: bubbleSort
     * @Author 冰白寒祭
     * @Description: 冒泡排序
     */
    public static void bubbleSort(Comparable[] arr) {
        if (arr == null || arr.length < 2) {
            return;
        } else {
            for (int i = arr.length - 1; i > 0; i--) {
                for (int j = 0; j < i; j++) {
                    if (greater(arr[j], arr[j + 1])) {
                        exch(arr, j, j + 1);
                    }
                }
            }
        }
    }

    /**
     * @param arr 待排数组
     * @return void
     * @MethodName: insertionSort
     * @Author 冰白寒祭
     * @Description: 选择排序
     */
    public static void selectSort(Comparable[] arr) {
        if (arr == null || arr.length < 2) {
            return;
        } else {
            for (int i = 0; i < arr.length - 1; i++) {
                for (int j = i; j <= arr.length - 1; j++) {
                    if (greater(arr[i], arr[j])) {
                        exch(arr, i, j);
                    }
                }
            }
        }
    }

    /**
     * @param arr 待排数组
     * @return void
     * @MethodName: insertionSort
     * @Author 冰白寒祭
     * @Description: 插入排序
     */
    public static void insertionSort(Comparable[] arr) {
        if (arr == null || arr.length < 2) {
            return;
        } else {
            for (int i = 1; i <= arr.length; i++) {
                for (int j = i - 1; j > 0; j--) {
                    if (greater(arr[j - 1], arr[j])) {
                        exch(arr, j - 1, j);
                    }
                }
            }
        }
    }

    /**
     * @param arr 待排数组
     * @return void
     * @MethodName: insertionSort
     * @Author 冰白寒祭
     * @Description: 希尔排序
     */

    public static void shellSort(Comparable[] arr) {
        if (arr == null || arr.length < 2) {
            return;
        } else {
            // 根据数组arr的长度确定步长h的增长值
            int h = 1;
            while (h < arr.length) {
                h = 2 * h + 1;
            }
            // 希尔排序
            while (h >= 1) {
                // 排序
                // 第一个待插入元素为h+1
                for (int i = h; i < arr.length; i++) {
                    // 开始将待插入元素插入到有序数列中
                    for (int j = i; j >= h; j -= h) {
                        // 待插入元素为arr[j]
                        if (greater(arr[j - h], arr[j])) {
                            // 交换元素
                            exch(arr, j - h, j);
                        } else {
                            // 待插入元素已经找到合适位置
                            break;
                        }
                    }
                }
                // 减少h的值
                h = h / 2;
            }
        }
    }


    /**
     * @param arr 待排数组
     * @return void
     * @MethodName: mergeSort
     * @Author 冰白寒祭
     * @Description: 归并排序(整个数组)
     */

    public static void mergeSort(Comparable[] arr) {
        if (arr == null || arr.length < 2) {
            return;
        } else {
            // 初始化辅助数组
            assist = new Comparable[arr.length];
            // 定义left与right变量,分别记录数组arr中最小索引与最大索引
            int left = 0;
            int right = arr.length - 1;
            // 调用mergeSort重载方法，完成数组arr中从left到right的排序
            mergeSort(arr, left, right);
        }

    }


    /**
     * @param arr   待排数组
     * @param left  左区间
     * @param right 右区间
     * @return void
     * @MethodName: mergeSort
     * @Author 冰白寒祭
     * @Description: 归并排序(指定数组区间)
     */
    public static void mergeSort(Comparable[] arr, int left, int right) {
        if (left >= right) {
            return;
        } else {
            // 对从left到right的元素进行分为两组组
            int mid = left + (right - left) / 2;
            // 分别对每一组数据进行排序
            mergeSort(arr, left, mid);
            mergeSort(arr, mid + 1, right);
            // 将两组数据进行归并
            merge(arr, left, mid, right);
        }
    }


    /**
     * @return void
     * @MethodName: merge
     * @Author 冰白寒祭
     * @Description: 归并算法
     */
    public static void merge(Comparable[] arr, int left, int mid, int right) {
        // 定义三个指针
        int i = left;
        int p1 = left;
        int p2 = mid + 1;
        // 遍历,移动p1和p2指针,比较索引处的值，将小的值放到辅助数组的对应索引处;
        while (p1 <= mid && p2 <= right) {
            // 比较索引处的值
            if (greater(arr[p1], arr[p2])) {
                assist[i++] = arr[p1++];
            } else {
                assist[i++] = arr[p2++];
            }
        }
        // 遍历,如果p1的指针没有走完,那么顺序移动p1指针,把对应的元素放到辅助数组的对应索引处
        while (p1 <= mid) {
            assist[i++] = arr[p1++];
        }
        // 遍历,如果p2的指针没有走完,那么顺序移动p2指针,把对应的元素放到辅助数组的对应索引处
        while (p2 <= right) {
            assist[i++] = arr[p2++];
        }
        // 把辅助数组中的元素拷贝到原数组中
        for (int index = left; index <= right; index++) {
            arr[index] = assist[index];
        }
    }

    /**
     * @param arr 待排数组
     * @return void
     * @MethodName: quickSort
     * @Author 冰白寒祭
     * @Description: 快排序
     */

    public static void quickSort(Comparable[] arr) {
        if (arr == null || arr.length < 2) {
            return;
        } else {
            int left = 0;
            int right = arr.length - 1;
            quickSort(arr, left, right);
        }
    }

    /**
     * @param arr   待排数组
     * @param left  左区间
     * @param right 右区间
     * @return void
     * @MethodName: quickSort
     * @Author 冰白寒祭
     * @Description: 快排序(指定数组区间)
     */
    public static void quickSort(Comparable[] arr, int left, int right) {
        if (left >= right) {
            return;
        } else {
            // 需要对数组中left索引到right索引处的元素进行分组（左子组和右子组）
            int partition = partition(arr, left, right);
            // 让左子组有序
            quickSort(arr, left, partition - 1);
            // 让右子组有序
            quickSort(arr, partition + 1, right);
        }
    }

    /**
     * 对数组arr中，让在left到right之间的元素进行分组
     */
    public static int partition(Comparable[] arr, int left, int right) {
        // 确定分解值
        Comparable key = arr[left];
        // 定义两个指针分别指向待切分元素的最小索引处和最大索引处的下一个位置
        int p1 = left;
        int p2 = right + 1;
        // 切分
        while (true) {
            // 先从右往左扫描，移动p2指针找到一个比分界值小的元素停止
            while (greater(key, arr[--p2])) {
                if (p2 == left) {
                    break;
                }
            }
            // 再从左往右扫描，移动p1指针找到一个比分界值大的元素停止
            while (greater(arr[++p1], key)) {
                if (p1 == right) {
                    break;
                }
            }
            // 判断p1>=p2，如果是，则证明元素扫描完毕，结束循环，如果不是，则交换元素即可
            if (p1 >= p2) {
                break;
            } else {
                exch(arr, p1, p2);
            }
        }
        exch(arr, left, p2);
        return p2;
    }

}
