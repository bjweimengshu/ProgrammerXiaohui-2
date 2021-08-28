package chapter2.part1;

public class BinarySearchTree {
    private Node root;

    //查找结点
    public Node search(int data) {
        Node targetNode = root;
        while (targetNode!=null && targetNode.data != data) {
            if (data > targetNode.data) {
                targetNode = targetNode.right;
            } else {
                targetNode = targetNode.left;
            }
        }
        if(targetNode == null){
            System.out.println("未找到结点：" + data);
        } else {
            System.out.println("已找到结点：" + data);
        }
        return targetNode;
    }

    //中序遍历
    public static void inOrderTraversal(Node node){
        if(node == null){
            return;
        }
        inOrderTraversal(node.left);

        System.out.print(node.data + " ");
        inOrderTraversal(node.right);
    }

    //插入结点
    public boolean insert(int data) {
        Node node = new Node(data);
        if(root == null){
            root = node;
            return true;
        }
        Node targetNode  = root;
        while (targetNode != null) {
            if( data == targetNode.data){
                System.out.println("二叉查找树中已有重复的结点：" + data);
                return false;
            }
            else if (data > targetNode.data) {
                if(targetNode.right == null){
                    targetNode.right = node;
                    return true;
                }
                targetNode = targetNode.right;
            }
            else {
                if(targetNode.left == null){
                    targetNode.left = node;
                    return true;
                }
                targetNode = targetNode.left;
            }
        }
        return true;
    }

    //删除结点
    public boolean delete(int data) {
        Node targetNode = root;
        Node parentNode = new Node(data);
        //判断待删除结点是否存在
        while (targetNode.data != data) {
            parentNode = targetNode;
            if (data > targetNode.data) {
                targetNode = targetNode.right;
            } else {
                targetNode = targetNode.left;
            }
            if (targetNode == null) {
                // 没有找到待删除结点
                return false;
            }
        }
        // 待删除结点没有子节点
        if (targetNode.right==null && targetNode.left==null) {
            if (targetNode == root) {
                //待删除结点是根结点
                root = null;
            } else {
                if (parentNode.right == targetNode) {
                    parentNode.right = null;
                } else {
                    parentNode.left = null;
                }
            }
        }
        //待删除结点有一个子结点（右）
        else if(targetNode.left == null) {
            if(targetNode == root) {
                root = targetNode.right;
            } else if(parentNode.right == targetNode) {
                parentNode.right = targetNode.right;
            } else {
                parentNode.left = targetNode.right;
            }
        }
        //待删除结点有一个子结点（左）
        else if(targetNode.right == null) {
            if(targetNode == root) {
                root = targetNode.left;
            } else if(parentNode.right == targetNode) {
                parentNode.right = targetNode.left;
            } else {
                parentNode.left = targetNode.left;
            }
        }
        //待删除结点有两个子结点
        else {
            //待删除结点的后继结点的父结点
            Node successParentNode = targetNode;
            //待删除结点的后继结点
            Node successNode = targetNode.right;
            while(successNode.left != null)
            {
                successParentNode = successNode;
                successNode = successNode.left;
            }
            //把后继结点复制到待删除结点位置
            targetNode.data = successNode.data;
            //删除后继结点
            if(successParentNode.right == successNode) {
                successParentNode.right = successNode.right;
            } else {
                successParentNode.left = successNode.right;
            }
        }
        return true;
    }

    // 结点类
    private class Node {
        int data;
        Node right;
        Node left;

        Node(int data){
            this.data = data;
        }
    }

    public static void main(String[] args) {
        BinarySearchTree tree = new BinarySearchTree();
        int input[]= {6,3,8,2,5,7,9,1,4};
        for(int i=0; i<input.length; i++) {
            tree.insert(input[i]);
        }
        inOrderTraversal(tree.root);
        System.out.println();
        tree.search(3);
        tree.delete(3);
        tree.search(3);
        tree.delete(6);
        inOrderTraversal(tree.root);
    }
}
