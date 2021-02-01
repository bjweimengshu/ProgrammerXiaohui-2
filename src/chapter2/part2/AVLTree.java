package chapter2.part2;

import java.util.LinkedList;
import java.util.Queue;

/**
 * Created by bjwei on 2020/7/29.
 */
public class AVLTree {
    private TreeNode root;
    /*
     * 获取树的高度
     */
    private int height(TreeNode node) {
        if (node != null)
            return node.height;

        return 0;
    }

    public int height() {
        return height(root);
    }

    //查找结点
    public TreeNode search(TreeNode node, int data) {
        while (node!=null) {
            if (data < node.data)
                node = node.left;
            else if (data > node.data)
                node = node.right;
            else
                return node;
        }
        return node;
    }

    //左左局面旋转
    private TreeNode leftLeftRotation(TreeNode node) {
        //leftChildNode 对应示意图中的结点B
        TreeNode leftChildNode = node.left;
        node.left = leftChildNode.right;
        leftChildNode.right = node;
        //刷新结点A和结点B的高度
        node.height = Math.max(height(node.left), height(node.right)) + 1;
        leftChildNode.height = Math.max(height(leftChildNode.left), node.height) + 1;
        //返回旋转后的父结点
        return leftChildNode;
    }

    //右右局面旋转
    private TreeNode rightRightRotation(TreeNode node) {
        //rightChildNode 对应示意图中的结点B
        TreeNode rightChildNode = node.right;
        node.right = rightChildNode.left;
        rightChildNode.left = node;
        //刷新结点A和结点B的高度
        node.height = Math.max(height(node.left), height(node.right)) + 1;
        rightChildNode.height = Math.max(height(rightChildNode.right), node.height) + 1;
        //返回旋转后的父结点
        return rightChildNode;
    }

    //左右局面旋转
    private TreeNode leftRightRotation(TreeNode node) {
        //先做左旋
        node.left = rightRightRotation(node.left);
        //再做右旋
        return leftLeftRotation(node);
    }

    //右左局面旋转
    private TreeNode rightLeftRotation(TreeNode node) {
        //先做右旋
        node.right = leftLeftRotation(node.right);
        //再做左旋
        return rightRightRotation(node);
    }

    //插入结点
    public void insert(int data) {
        root = insert(root, data);
    }

    //插入结点详细过程（递归）
    private TreeNode insert(TreeNode node, int data) {
        if (node == null) {
            node = new TreeNode(data);
        } else {
            if (data < node.data) {
                //新结点小于当前结点，选择当前结点的左子树插入
                node.left = insert(node.left, data);
                // 插入节点后，若AVL树失去平衡，则进行相应的调节。
                if (node.getBalance() == 2) {
                    if (data < node.left.data) {
                        node = leftLeftRotation(node);
                    } else {
                        node = leftRightRotation(node);
                    }
                }
            } else if (data > node.data)  {
                //新结点大于当前结点，选择当前结点的右子树插入
                node.right = insert(node.right, data);
                // 插入节点后，若AVL树失去平衡，则进行相应的调节。
                if (node.getBalance() == -2) {
                    if (data > node.right.data) {
                        node = rightRightRotation(node);
                    } else {
                        node = rightLeftRotation(node);
                    }
                }
            } else {
                System.out.println("AVL树中已有重复的结点！");
            }
        }
        //刷新结点的高度
        node.height = Math.max(height(node.left), height(node.right)) + 1;
        return node;
    }

    //删除结点
    public void remove(int data) {
        TreeNode deletedNode;
        if ((deletedNode = search(root, data)) != null)
            root = remove(root, deletedNode);
    }

    //删除结点详细过程（递归）
    private TreeNode remove(TreeNode node, TreeNode deletedNode) {
        // 根为空 或者 没有要删除的节点，直接返回null。
        if (node==null || deletedNode==null)
            return null;
        if (deletedNode.data < node.data){
            //待删除结点小于当前结点，在当前结点的左子树继续执行
            node.left = remove(node.left, deletedNode);
            // 删除节点后，若AVL树失去平衡，则进行相应的调节。
            if (height(node.right) - height(node.left) == 2) {
                TreeNode r =  node.right;
                if (height(r.left) > height(r.right))
                    node = rightLeftRotation(node);
                else
                    node = rightRightRotation(node);
            }
        } else  if (deletedNode.data > node.data) {
            //待删除结点大于当前结点，在当前结点的右子树继续执行
            node.right = remove(node.right, deletedNode);
            // 删除节点后，若AVL树失去平衡，则进行相应的调节。
            if (height(node.left) - height(node.right) == 2) {
                TreeNode l =  node.left;
                if (height(l.right) > height(l.left))
                    node = leftRightRotation(node);
                else
                    node = leftLeftRotation(node);
            }
        } else {
            // tree的左右孩子都非空
            if ((node.left!=null) && (node.right!=null)) {
                if (height(node.left) > height(node.right)) {
                    // 如果node的左子树比右子树高，找出左子树最大结点赋值给Node，并删除最小结点
                    TreeNode max = maximum(node.left);
                    node.data = max.data;
                    node.left = remove(node.left, max);
                } else {
                    // 如果node的右子树比左子树高，找出右子树最小结点赋值给Node，并删除最小结点
                    TreeNode min = minimum(node.right);
                    node.data = min.data;
                    node.right = remove(node.right, min);
                }
            } else {
                node = (node.left!=null) ? node.left : node.right;
            }
        }
        node.height = Math.max(height(node.left), height(node.right)) + 1;
        return node;
    }

    //找出结点node为根的子树的最大节点
    private TreeNode maximum(TreeNode node) {
        if (node == null)
            return null;
        while(node.right != null)
            node = node.right;
        return node;
    }

    //找出结点node为根的子树的最小节点
    private TreeNode minimum(TreeNode node) {
        if (node == null)
            return null;
        while(node.left != null)
            node = node.left;
        return node;
    }

    //中序遍历
    public static void inOrderTraversal(TreeNode node) {
        if(node != null) {
            inOrderTraversal(node.left);
            System.out.print(node.data+" ");
            inOrderTraversal(node.right);
        }
    }

    //层序遍历
    public static void levelOrderTraversal(TreeNode root){
        Queue<TreeNode> queue = new LinkedList<>();
        queue.offer(root);
        while(!queue.isEmpty()){
            TreeNode node = queue.poll();
            System.out.print(node.data+" ");
            if(node.left != null){
                queue.offer(node.left);
            }
            if(node.right != null){
                queue.offer(node.right);
            }
        }
    }

    class TreeNode {
        int data;
        int height;
        TreeNode left;
        TreeNode right;

        public TreeNode(int data) {
            this.data = data;
            this.height = 0;
        }

        //获得结点的平衡因子
        public int getBalance(){
            int left =  (this.left==null ? 0:this.left.height);
            int right = (this.right==null ? 0:this.right.height);
            return left - right;
        }
    }

    public static void main(String[] args) {
        AVLTree tree = new AVLTree();
        int input[]= {5,3,7,2,4,6,9,1};
        for(int i=0; i<input.length; i++) {
            tree.insert(input[i]);
        }
        System.out.println("中序遍历: ");
        inOrderTraversal(tree.root);
        System.out.println();
        System.out.println("层序遍历: ");
        levelOrderTraversal(tree.root);
        System.out.println();
        System.out.printf("高度: %d\n", tree.height());
        int deletedData = 3;

        System.out.printf("删除根节点: %d\n", deletedData);
        tree.remove(deletedData);

        System.out.println("中序遍历: ");
        inOrderTraversal(tree.root);
        System.out.println();
        System.out.println("层序遍历: ");
        levelOrderTraversal(tree.root);
        System.out.println();
        System.out.printf("高度: %d\n", tree.height());
    }
}
