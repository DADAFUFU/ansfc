package com.ansfc.sort;

import java.util.Arrays;

public class ArrSort {


    public static void main(String[] args){
        int[] arr = {2,4,3,1,5,6,9,8,7};
        ArrSort sort = new ArrSort();
//        sort.quickSort(arr,0,arr.length-1);
        sort.insertSort(arr);
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


