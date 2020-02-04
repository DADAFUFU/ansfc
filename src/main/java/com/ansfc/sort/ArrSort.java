package com.ansfc.sort;

import java.util.Arrays;

public class ArrSort {


    public static void main(String[] args){
        int[] arr = {2,4,3,1,5,6,9,8,7};
        ArrSort sort = new ArrSort();
        sort.quickSort(arr,0,arr.length-1);
        System.out.println(Arrays.toString(arr));
    }

    int mpartition(int a[], int l, int r) {
        int pivot = a[l];

        while (l < r) {
            while (l < r && pivot <= a[r]) r--;
            if (l < r) a[l++] = a[r];
            while (l < r && pivot > a[l]) l++;
            if (l < r) a[r--] = a[l];
        }
        a[l] = pivot;
        return l;
    }

    /**
     * 快排
     * @param a
     * @param l
     * @param r
     */
    void quick_sort(int a[], int l, int r) {

        if (l < r) {
            int q = mpartition(a, l, r);
            quick_sort(a, l, q - 1);
            quick_sort(a, q + 1, r);
        }
    }


    /**
     * 选择排序
     * @param a
     * @param n
     */
    void selection_sort(int a[], int n) {
        int i, j, pos, tmp;
        for (i = 0; i < n - 1; i++) {
            for (pos = i, j = i + 1; j < n; j++)
                if (a[pos] > a[j])
                    pos = j;
            if (pos != i) {
                tmp = a[i];
                a[i] = a[pos];
                a[pos] = tmp;
            }
        }
    }


    /**
     * 冒泡排序
     * @param a
     * @param n
     */
    void bubble_sort (int a[], int n) {
        int i, j, lastSwap, tmp;
        for (j=n-1; j>0; j=lastSwap) {
            lastSwap=0;     //每一轮要初始化为0，防止某一轮未发生交换，lastSwap保留上一轮的值进入死循环
            for (i=0; i<j; i++) {
                if (a[i] > a[i+1]) {
                    tmp=a[i];
                    a[i]=a[i+1];
                    a[i+1]=tmp;
                    lastSwap = i;
                }
            }
        }
    }


    /**
     * 插入排序
     * @param a
     * @param n
     */
    void insertion_sort (int a[], int n) {
        int i,j,v;
        for (i=1; i<n; i++) {
            for (v=a[i], j=i-1; j>=0&&v<a[j]; j--)
                a[j+1]=a[j];
            a[j+1]=v;
        }
    }


    void selectSort(int[] arr){
        int n = arr.length;
        for(int i=0;i<n;i++){
            for(int j=0;j<n;j++){
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


    void insertSort(int[] arr){
        int n = arr.length;
        for(int i=0;i<arr.length;i++){
            for(int j=i;j>0;j--){
                if(arr[j] > arr[j-1]){
                    swap(arr,j,j-1);
                }
            }
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


    void swap(int[] arr,int i,int j){
        int temp = arr[i];
        arr[i] = arr[j];
        arr[j] = temp;
    }
}


