package com.ansfc.algorithms;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;

/**
 * Created by dafu on 2018/4/5.
 */
public class TestTree {

    /**
     * 递归 先序遍历
     * @param root
     */
    public void order1(TreeNode root, ArrayList<Integer> result){
        if(root == null){
            return;
        }
        result.add(root.val);
        order1(root.left,result);
        order1(root.right,result);
    }
    /**
     * 递归 中序遍历
     * @param root
     */
    public void order2(TreeNode root, ArrayList<Integer> result){
        if(root == null){
            return;
        }
        order2(root.left,result);
        result.add(root.val);
        order2(root.right,result);
    }
    /**
     * 递归 后序遍历
     * @param root
     */
    public void order3(TreeNode root, ArrayList<Integer> result){
        if(root == null){
            return;
        }
        order3(root.left,result);
        order3(root.right,result);
        result.add(root.val);
    }

    /**
     * 非递归 先序遍历
     * @param root
     * @param result
     */
    public void order4(TreeNode root,ArrayList<TreeNode> result){
        if(root == null){
            return;
        }
        Stack<TreeNode> stack = new Stack<>();
        while(root!=null || !stack.isEmpty()){

            while(root!=null){
                result.add(root);
                stack.push(root);
                root = root.left;
            }
            if(!stack.isEmpty()){
                TreeNode node = stack.pop();
                root = node.right;
            }

        }
    }

    /**
     * 非递归 中序遍历
     * @param root
     * @param result
     */
    public void order5(TreeNode root,ArrayList<TreeNode> result){
        if(root == null){
            return;
        }
        Stack<TreeNode> stack = new Stack<>();
        while(root!=null || !stack.isEmpty()){
            while(root!=null){
                stack.push(root);
                root = root.left;
            }
            if (stack.size()>0){
                TreeNode node = stack.pop();
                result.add(node);
                root = node.right;
            }
        }
    }

    /**
     * 非递归 后序遍历
     * @param root
     * @param result
     */
    public void order6(TreeNode root,ArrayList<TreeNode> result){
        if(root == null){
            return;
        }
        Stack<TreeNode> stack = new Stack<>();
        TreeNode preNode = null;
        while(root!=null && !stack.isEmpty()){
            while (root!=null){
                stack.push(root);
                root = root.left;
            }
            if(!stack.isEmpty()){
                TreeNode cur = stack.pop();
                if(cur.right == null || cur.right == preNode){
                    result.add(cur);
                    preNode = cur;
                }else{
                    stack.push(cur);
                    root = cur.right;
                }
            }
        }
    }

    /**
     * 非递归 广度优先遍历
     * @param root
     * @param result
     */
    public void order7(TreeNode root,ArrayList<Integer> result){
        if(root == null){
            return;
        }
        Queue<TreeNode> queue = new LinkedList<>();
        queue.add(root);
        while(queue.size()>0){
            TreeNode node = queue.poll();
            result.add(node.val);
            if(node.left!=null){
                queue.add(node.left);
            }
            if(node.right!=null){
                queue.add(node.right);
            }
        }
    }


}

class TreeNode {
    int val;
    TreeNode left;
    TreeNode right;
    TreeNode(int x) { val = x; }
}