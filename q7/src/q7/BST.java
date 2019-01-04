package q7;

import java.util.Iterator;
import java.util.NoSuchElementException;


public class BST implements Iterable<String> {

    private BSTNode root;

    /**
     * Constructs an empty binary search tree with an initial root of null.
     * Remember, there should be no dummy root. In other words size()==0 iff root=null.
     */
    BST() {
        root = null;
    }

    /**
     * Returns the number of elements in this binary search tree
     *
     * @return the number of elements in this binary search tree
     */
    public int size() {
        return sizehelper(root);
    }
    private int sizehelper(BSTNode in){
        if(in==null){return 0;}
        return 1+sizehelper(in.getLeft())+sizehelper(in.getRight());
    }

    /**
     * Checks if a node with the value of data is in the tree
     * The complexity of this method should be O(h) where h is
     * the height of the tree
     *
     * @param  data  the searching data.
     * @return true iff for a node n in the tree, n.getData().equals(data).
     */
    public boolean contains(String data) {
        return containsHelper(root,data);
    }
    private boolean containsHelper(BSTNode node, String data){
        if(node==null){
            return false;
        }else if(node.getData().equals(data)){
            return true;
        }else if(node.getData().compareTo(data)<0){
            return containsHelper(node.getLeft(),data);
        }else{
            return containsHelper(node.getRight(),data);
        }
    }

    /**
     * Inserts a new node with the value data in the tree iff the value
     * does not already exist in the tree (no node n s.t. n.getData().equals(data))
     * The complexity of this method should be O(h) where h is
     * the height of the tree
     *
     * @param  data  the inserting data. Not null.
     * @return true iff a new node with value of data is inserted to the tree
     */
    public boolean insert(String data) {
        return insertHelper(root,data);
    }
    private boolean insertHelper(BSTNode node, String data){
        if(node.getData().compareTo(data)>0){
            if(node.getRight().getData()==null){
                node.setRight(new BSTNode(data, node));
                return true;
            }else if(node.getData().compareTo(data)==0){
                return false;
            }else{
                return insertHelper(node.getRight(),data);
            }
        }else if(node.getData().compareTo(data)<0){
            if(node.getLeft().getData()==null){
                node.setLeft(new BSTNode(data, node));
                return true;
            }else if(node.getData().compareTo(data)==0){
                return false;
            }else{
                return insertHelper(node.getLeft(),data);
            }
        }else return false;
    }
    /**
     * Deletes the node with the value of data from the tree iff
     * there is a node n which n.getData().equals(data)
     * The complexity of this method should be O(h) where h is
     * the height of the tree
     *
     * @param  data  the deleting data.
     * @return true iff a the node with value of data is deleted from the tree
     */
    public boolean delete(String data) {
        if(this.contains(data)==false){
            return false;
        }
        BSTNode curr = root;
        while(curr.getData().equals(data)==false){
            if(curr.getData().compareTo(data)>0){
                curr=curr.getRight();
            }else{
                curr=curr.getLeft();
            }
        }
        if(curr.getLeft()==null && curr.getRight()==null){
            if(curr.getParent().getData().compareTo(curr.getData())<0){
                curr.getParent().setLeft(null);
            }else{
                curr.getParent().setRight(null);
            }
        }else if(curr.getLeft()==null){
            if(curr.getParent().getData().compareTo(curr.getData())<0){
                curr.getParent().setLeft(curr.getRight());
            }else{
                curr.getParent().setRight(curr.getRight());
            }
        }else if(curr.getRight()==null){
            if(curr.getParent().getData().compareTo(curr.getData())<0){
                curr.getParent().setLeft(curr.getLeft());
            }else{
                curr.getParent().setRight(curr.getLeft());
            }
        }
        BSTNode rep = curr.getLeft();
        while(rep.getRight()!=null){
            rep=rep.getRight();
        }
        String repString = rep.getData();
        delete(repString);
        curr.setData(repString);
        return true;
    }

    /**
     * Returns a new in-order iterator on this binary search tree.
     * Do not change this method.
     *
     * @return a new in-order iterator on this binary search tree
     */
    public final Iterator<String> iterator() {
        return new BSTInOrderIterator(root);
    }

    /**
     * This method is going to be used by your TA to verify the underlying
     * structure of your binary search tree implementation.
     * Do not change this method at all.
     *
     * @return true iff the binary search tree instance is internally ok
     */
    public final boolean correctInternalStructure() {
        return BSTWhiteBoxVerfier.repOk(this, this.root);
    }
}

/**
 * This is an in-order iterator implementation for your binary search class.
 * The memory size of your iterator should remain O(1).
 * Do not worry about any concurrent modification.
 */
class BSTInOrderIterator implements Iterator<String> {
    static BSTNode current;
    BSTInOrderIterator(BSTNode root) {
        current=root;
        while(current.getLeft()!=null){
            current=current.getLeft();
        }
    }
    private BSTNode successiveNode(BSTNode node){
        if (node.getRight() != null) {
            BSTNode next=node.getRight();
            while(next.getLeft()!=null){
                next=node.getLeft();
            }
            return node;
        }else {
            BSTNode parent = node.getParent();
            while (parent != null && node == parent.getRight()) {
                node = parent;
                parent = parent.getParent();
            }
            return parent;
        }
    }
    /**
     * This methods determines whether or not there is more element
     * The complexity of this method should be O(1).
     * You do not have to worry about concurrent modification.
     *
     * @return true iff there are more elements to go.
     */
    public boolean hasNext() {
        if(successiveNode(current)==null) return false;
        return true;
    }

    /**
     * This methods retrieves the next element.
     * The complexity of this method should be O(1) on average; it means if
     * your next method is called n times, the overall complexity should be O(n)
     * which means O(1) per call on average.
     * It means you have to return
     * the next element based on the current location of the iterator. Some possible
     * wrong approaches are storing the number of returned elements somewhere and
     * doing the in-order traversal again until we reach the index.
     * You must throw NoSuchElementException if there is no more element.
     * You do not have to worry about concurrent modification.
     *
     * @throws NoSuchElementException if there is no more element.
     * @return the next value of next element in the in-order traversal.
     */
    public String next() {
        current=successiveNode(current);
        return current.getData();
    }

    /**
     * This method is just here to implement Iterator interface. You should not
     * implement this method as its meaning is vague in this context.
     * Do not change this method at all.
     *
     * @throws UnsupportedOperationException this method should not be implemented.
     */
    public void remove() {
        throw new UnsupportedOperationException();
    }
}
