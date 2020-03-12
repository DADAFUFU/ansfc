package com.ansfc.sort;

import java.util.Arrays;

public class ArrSort {


    public static void main(String[] args){
        int[] arr = {2,4,3,1,5,6,9,8,7};
        ArrSort sort = new ArrSort();
//        sort.quickSort(arr,0,arr.length-1);
        sort.heapSort(arr);
        System.out.println(Arrays.toString(arr));
    }

    /**
     * 插入排序
     * @param a
     */
    void insertSort (int a[]) {
        int n = a.length;
        for (int i=1; i<n; i++) {
            for(int j=i;j>0;j--){
                if(a[j-1]>a[j]){
                    swap(a,j-1,j);
                }
            }
        }
    }


    void selectSort(int[] arr){
        int n = arr.length;
        for(int i=0;i<n-1;i++){
            for(int j=i+1;j<n;j++){
                if(arr[i]>arr[j]){
                    swap(arr,i,j);
                }
            }
        }
    }

    void bubbleSort(int[] arr){
        int n = arr.length;
        for(int i=0;i<n;i++){
            for(int j=0;j<n-1;j++){
                if(arr[j]>arr[j+1]){
                    swap(arr,j,j+1);
                }
            }
        }
    }

    /**
     * 堆排序 O(nlogn)
     * arr[i] >= arr[2*i+1] && arr[i] >= arr[2*i+2]
     * @param arr
     */
    void headSort(int[] arr){
        int len = arr.length-1;
        int beginIndex = len / 2 -1;
        /**
         * 第一步： 构造初始堆。将给定无序序列构造成一个大顶堆（一般升序采用大顶堆，降序采用小顶堆)
         * beginIndex = 第一个非叶子节点
         */
        for(int i=beginIndex;i>=0;i--){
            adjustHeap(arr,i,len);
        }
        /**
         * 第二步：对堆化数据排序
         * 每次都是移出最顶层的根节点A[0]，与最尾部节点调换位置，同时遍历长度 -1
         * 然后从新整理被换到根节点的末尾元素，使其符合堆的特性
         * 直至未排序的堆长度为0
         */
        for(int i=len;i>0;i--){
            swap(arr,0,i);
            adjustHeap(arr,0,i-1);
        }
    }

    private void adjustHeap(int[] arr, int i, int length) {
        int temp = arr[i];
        int left = i*2+1;
        int right = left+1;
        int cMax = left;
        if(left>length) return;
        if(right <= length && arr[right] > arr[left]){
            cMax = right;
        }
        if(arr[cMax] > temp){
            swap(arr,cMax,i);
            adjustHeap(arr,cMax,length);
        }
    }


    /**
     * 快速排序
     * @param arr
     * @param left
     * @param right
     */
    void quickSort(int[] arr,int left,int right){
        if(left < right){
            int middle = middle(arr,left,right);
            quickSort(arr,left,middle-1);
            quickSort(arr,middle+1,right);
        }
    }

    /**
     * 算出每次快速排序的中间位
     * @param arr
     * @param left
     * @param right
     * @return
     */
    private int middle(int[] arr, int left, int right) {
        int index = arr[left];
        while(left < right){

            while(left<right && arr[right]>index){
                right--;
            }
            if(left<right){
                arr[left] = arr[right];
                left++;
            }
            while(left< right && arr[left]< index){
                left++;
            }
            if(left<right){
                arr[right] = arr[left];
                right--;
            }
        }
        arr[left] = index;
        return left;
    }

    /**
     * a[i] >= a[2*i+1] && a[i] >= a[a*2+2]
     * 堆排序
     * @param arr
     */
    void heapSort(int[] arr){

        //构造大顶堆
        int len = arr.length-1;
        int index = len / 2 -1;
        for(int i=index ; i>=0 ;i--){
            buildHeap(arr,i,len);
        }

        //第二部 交换第一个跟最后一个，然后再进行排序
        for(int i=len;i>=0;i--){
            swap(arr,0,i);
            buildHeap(arr,0,i-1);
        }
    }

    void buildHeap(int[] arr,int i,int length){
        int temp = arr[i];
        int left = 2*i + 1;
        int right = 2*i + 2;
        int cMax = left;
        if(left > length){
            return;
        }
        if(right <= length && arr[right] > arr[left]){
            cMax = right;
        }
        if(arr[cMax] > temp){
            swap(arr,cMax,i);
            buildHeap(arr,cMax,length);
        }
    }


    void swap(int[] arr,int i,int j){
        int temp = arr[i];
        arr[i] = arr[j];
        arr[j] = temp;
    }
}


