package chapter2.part3;

import java.util.LinkedList;
import java.util.Queue;

/**
 * Created by bjwei on 2020/7/29.
 */

public class RedBlackTree {
    TreeNode root;
    final static boolean RED = true;
    final static boolean BLACK = false;

    //查找结点
    public TreeNode search(int data) {
        TreeNode tmp = root;
        while (tmp != null) {
            if (tmp.data == data)
                return tmp;
            else if (tmp.data > data)
                tmp = tmp.left;
            else
                tmp = tmp.right;
        }
        return null;
    }

    //插入结点
    public boolean insert(int data) {
        TreeNode node = new TreeNode(data);
        //局面1：新结点位于树根，没有父结点。
        if (root == null) {
            root = node;
            node.color = BLACK;
            return true;
        }
        TreeNode targetNode = root;
        while (targetNode != null) {
            if( data == targetNode.data){
                System.out.println("红黑树中已有重复的结点：" + data);
                return false;
            } else if (data > targetNode.data) {
                if(targetNode.right == null){
                    targetNode.right = node;
                    node.parent = targetNode;
                    insertAdjust(node);
                    return true;
                }
                targetNode = targetNode.right;
            } else {
                if(targetNode.left == null){
                    targetNode.left = node;
                    node.parent = targetNode;
                    insertAdjust(node);
                    return true;
                }
                targetNode = targetNode.left;
            }
        }
        return true;
    }

    //插入后自我调整
    private void insertAdjust(TreeNode node) {
        //创建父结点和祖父结点指针
        TreeNode parent, grandParent;
        //局面3的调整有可能引发后续的一系列调整，所以使用while循环。
        while (node.parent != null && node.parent.color == RED) {
            parent = node.parent;
            grandParent = parent.parent;
            if (grandParent.left == parent) {
                TreeNode uncle = grandParent.right;
                //局面3：新结点的父结点和叔叔结点都是红色。
                if (uncle != null && uncle.color == RED) {
                    parent.color = BLACK;
                    uncle.color = BLACK;
                    grandParent.color = RED;
                    node = grandParent;
                    continue;
                }
                //局面4：新结点的父结点是红色，叔叔结点是黑色或者没有叔叔，且新结点是父结点的右孩子，父结点是祖父结点的左孩子。
                if (node == parent.right) {
                    leftRotate(parent);
                    TreeNode tmp = node;
                    node = parent;
                    parent = tmp;
                }
                //局面5：新结点的父结点是红色，叔叔结点是黑色或者没有叔叔，且新结点是父结点的左孩子，父结点是祖父结点的左孩子。
                parent.color = BLACK;
                grandParent.color = RED;
                rightRotate(grandParent);
            } else {
                TreeNode uncle = grandParent.left;
                //局面3（镜像）：新结点的父结点和叔叔结点都是红色。
                if (uncle != null && uncle.color == RED) {
                    parent.color = BLACK;
                    uncle.color = BLACK;
                    grandParent.color = RED;
                    node = grandParent;
                    continue;
                }
                //局面4（镜像）：新结点的父结点是红色，叔叔结点是黑色或者没有叔叔，且新结点是父结点的左孩子，父结点是祖父结点的右孩子。
                if (node == parent.left) {
                    rightRotate(parent);
                    TreeNode tmp = node;
                    node = parent;
                    parent = tmp;
                }
                //局面5（镜像）：新结点的父结点是红色，叔叔结点是黑色或者没有叔叔，且新结点是父结点的右孩子，父结点是祖父结点的右孩子。
                parent.color = BLACK;
                grandParent.color = RED;
                leftRotate(grandParent);
            }
        }
        //经过局面3的调整，有可能把根结点变为红色，此时再变回黑色即可。
        if(root.color == RED){
            root.color = BLACK;
        }
    }

    //删除节点
    public void remove(int key) {
        remove(search(key));
    }

    //删除节点详细逻辑
    private void remove(TreeNode node) {
        TreeNode targetNode = node;
        if (node == null)
            return;
        //第一步：如果待删除结点有两个非空的孩子结点，转化成待删除结点只有一个孩子（或没有孩子）的情况。
        if (node.left != null && node.right != null) {
            //待删除结点的后继结点
            TreeNode successNode = targetNode.right;
            while(successNode.left != null) {
                successNode = successNode.left;
            }
            if(targetNode == root) {
                root = successNode;
            }
            //把后继结点复制到待删除结点位置
            targetNode.data = successNode.data;
            remove(successNode);
            return;
        }
        //第二步：根据待删除结点和其唯一子结点的颜色，分情况处理。
        TreeNode successNode = node.right; //node只可能拥有右孩子或没有孩子
        TreeNode parent = node.parent;
        if (parent == null) {
            //子情况1，被删除结点是红黑树的根结点：
            root = successNode;
            if (successNode != null)
                successNode.parent = null;
        } else {
            //无论何种情况，都需要先删除node结点
            if (successNode != null)
                successNode.parent = parent;
            if (parent.left == node)
                parent.left = successNode;
            else {
                parent.right = successNode;
            }
        }
        //此时情况1已处理完毕，如果父结点是黑色结点，则增加额外处理
        if (node.color == BLACK)
            if(successNode!=null && successNode.color == RED ){
                //情况2：父结点是黑，子节点是红
                successNode.color = BLACK;
            }else {
                //情况3：遇到双黑结点。此时进入第三步，在子结点顶替父结点之后，分成6种子情况处理。
                removeAdjust(parent, successNode);
            }
    }

    //删除结点后的自我调整
    private void removeAdjust(TreeNode parent, TreeNode node) {
        while ((node == null || node.color == BLACK) && node != root) {
            if (parent.left == node) {
                //node的兄弟节点
                TreeNode sibling = parent.right;
                //子情况3，node的兄弟结点是红色：
                if (sibling != null && sibling.color == RED) {
                    parent.color = RED;
                    sibling.color = BLACK;
                    leftRotate(parent);
                    sibling = parent.right;
                }
                if (sibling == null || ((sibling.left == null || sibling.left.color == BLACK) && (sibling.right == null || sibling.right.color == BLACK))) {
                    //子情况2（镜像），node的父结点是黑色，兄弟和侄子结点是黑色：
                    if(parent.color == BLACK){
                        sibling.color = RED;
                        node = parent;
                        parent = node.parent;
                        continue;
                    }
                    //子情况4（镜像），node的父结点是红色，兄弟和侄子结点是黑色：
                    else {
                        sibling.color = RED;
                        break;
                    }
                }
                //子情况5，node的父结点随意，兄弟结点是黑色右孩子，左侄子结点是红色：
                if (sibling.color == BLACK && sibling.left!=null && sibling.left.color == RED) {
                    rightRotate(sibling);
                    sibling.parent.color = BLACK;
                    sibling.color = RED;
                    sibling = sibling.parent;
                }
                //子情况6，node的父结点随意，兄弟结点是黑色右孩子，右侄子结点是红色：
                if (sibling.color == BLACK && sibling.right!=null && sibling.right.color == RED) {
                    leftRotate(sibling.parent);
                    sibling.color = sibling.left.color;
                    sibling.left.color = BLACK;
                    sibling.right.color = BLACK;
                }
                node = root; //跳出循环
            } else {
                //node的兄弟节点
                TreeNode sibling = parent.left;
                //子情况3（镜像），node的兄弟结点是红色：
                if (sibling != null && sibling.color == RED) {
                    parent.color = RED;
                    sibling.color = BLACK;
                    rightRotate(parent);
                    sibling = parent.left;
                }
                if (sibling == null || ((sibling.left == null || sibling.left.color == BLACK) && (sibling.right == null || sibling.right.color == BLACK))) {
                    //子情况2（镜像），node的父结点是黑色，兄弟和侄子结点是黑色：
                    if(parent.color == BLACK){
                        sibling.color = RED;
                        node = parent;
                        parent = node.parent;
                        continue;
                    }
                    //子情况4（镜像），node的父结点是红色，兄弟和侄子结点是黑色：
                    else {
                        sibling.color = RED;
                        break;
                    }
                }
                //子情况5（镜像），node的父结点随意，兄弟结点是黑色左孩子，右侄子结点是红色：
                if (sibling.color == BLACK && sibling.right!=null && sibling.right.color == RED) {
                    leftRotate(sibling);
                    sibling.parent.color = BLACK;
                    sibling.color = RED;
                    sibling = sibling.parent;
                }
                //子情况6（镜像），node的父结点随意，兄弟结点是黑色左孩子，左侄子结点是红色：
                if (sibling.color == BLACK && sibling.left!=null && sibling.left.color == RED) {
                    rightRotate(sibling.parent);
                    sibling.color = sibling.right.color;
                    sibling.right.color = BLACK;
                    sibling.left.color = BLACK;
                }
                node = root; //跳出循环
            }
        }
        if (node != null) {
            node.color = BLACK;
        }
    }

    //左旋转
    private void leftRotate(TreeNode node) {
        TreeNode right = node.right;
        TreeNode parent = node.parent;
        if (parent == null) {
            root = right;
            right.parent = null;
        } else {
            if (parent.left != null && parent.left == node) {
                parent.left = right;
            } else {
                parent.right = right;
            }
            right.parent = parent;
        }
        node.parent = right;
        node.right = right.left;
        if (right.left != null) {
            right.left.parent = node;
        }
        right.left = node;
    }

    //右旋转
    private void rightRotate(TreeNode node) {
        TreeNode left = node.left;
        TreeNode parent = node.parent;
        if (parent == null) {
            root = left;
            left.parent = null;
        } else {
            if (parent.left != null && parent.left == node) {
                parent.left = left;
            } else {
                parent.right = left;
            }
            left.parent = parent;
        }
        node.parent = left;
        node.left = left.right;
        if (left.right != null) {
            left.right.parent = node;
        }
        left.right = node;
    }

    //中序遍历
    public static void inOrderTraversal(TreeNode node) {
        if(node != null) {
            inOrderTraversal(node.left);
            System.out.print(node);
            inOrderTraversal(node.right);
        }
    }

    //层序遍历
    public static void levelOrderTraversal(TreeNode root){
        Queue<TreeNode> queue = new LinkedList<>();
        queue.offer(root);
        while(!queue.isEmpty()){
            TreeNode node = queue.poll();
            System.out.print(node);
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
        boolean color;
        TreeNode left;
        TreeNode right;
        TreeNode parent;

        public TreeNode(int data) {
            this.data = data;
            this.color = RED;
        }

        @Override
        public String toString() {
            return  data + (color?"(red)":"(black)") + "  " ;
        }
    }

    public static void main(String[] args) {
        //case 1:
        RedBlackTree rbTree = new RedBlackTree();
        int input[]= {13,8,17,1,11,15,25,6,22,27};
        for(int i=0; i<10; i++) {
            rbTree.insert(input[i]);
        }
        rbTree.remove(25);
        System.out.println("中序遍历: ");
        inOrderTraversal(rbTree.root);
        System.out.println();
        System.out.println("层序遍历: ");
        levelOrderTraversal(rbTree.root);
        System.out.println();

        //case 2:
        rbTree = new RedBlackTree();
        for(int i=0; i<1000; i++) {
            rbTree.insert(i);
        }
        for(int i=100; i<1000; i+=100) {
            rbTree.remove(i);
        }
        System.out.println("中序遍历: ");
        inOrderTraversal(rbTree.root);
        System.out.println();
        System.out.println("层序遍历: ");
        levelOrderTraversal(rbTree.root);
        System.out.println();

    }
}

