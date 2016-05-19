package me.qunwei.other;

/**
 * Created by qunwei on 16-5-9.
 */
class Node {
    public final static Boolean BLACK = false;
    public final static Boolean RED = true;
    public Node parent;
    public Node lchild;
    public Node rchild;
    public Boolean color;
    public long value;
}
public class RedBlackTree {

    public static final Boolean BLACK = Node.BLACK;
    public static final Boolean RED = Node.RED;

    public Node grandparent(Node node){
        return node.parent.parent;
    }

    public Node uncle(Node node){

        if (node.parent == grandparent(node).lchild){
            return grandparent(node).rchild;
        }else {
            return grandparent(node).lchild;
        }

    }

    public void insert_case1(Node node){
        if (node.parent == null){//root node
            node.color = BLACK;
        }else{
            insert_case2(node);
        }
    }

    private void insert_case2(Node node) {
        if (node.parent.color == BLACK){
            return;
        }else {
            insert_case3(node);
        }
    }

    private void insert_case3(Node node) {
        Node uncle = uncle(node);
        if (uncle != null && uncle.color == RED){
            node.parent.color = BLACK;
            uncle.color = BLACK;
            grandparent(node).color = RED;
            insert_case1(grandparent(node));
        }else{
            insert_case4(node);
        }
    }

    private void insert_case4(Node node) {

        if (node == node.parent.rchild && node.parent == grandparent(node).lchild ){
//            rotateLeft(node.parent);
            node = node.lchild;
        }else if ( node == node.parent.lchild && node.parent == grandparent(node).rchild ){
//            rotateRight(node.parent);
            node = node.rchild;
        }

        insert_case5(node);

    }

    private void insert_case5(Node node) {
        node.parent.color = BLACK;
        grandparent(node).color = RED;
        if (node == node.parent.lchild && node.parent == grandparent(node).lchild){

        }
    }


}
