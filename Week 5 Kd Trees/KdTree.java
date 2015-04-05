/*************************************************************************
 *  Compilation:  javac KdTree.java
 *  Execution:    java KdTree
 *  Dependencies: StdIn.java StdOut.java Queue.java
 *
 *  A symbol table implemented with a Kd search tree.
 * 
 *  % more tinyST.txt
 *  S E A R C H E X A M P L E
 *  
 *  % java KdTree < tinyST.txt
 *
 *************************************************************************/
import java.util.NoSuchElementException;

public class KdTree {
    private Node root;             // root of BST 
    
    private static class Node {
        private Point2D p;      // the point
        private RectHV rect;    // the axis-aligned rectangle corresponding to this node
        private Node lb;        // the left/bottom subtree
        private Node rt;        // the right/top subtree
        private int N;
        public Node(Point2D p, RectHV rect, Node lb, Node rt, int N) {
            this.p = p;
            this.rect = rect;
            this.lb = lb;
            this.rt = rt;
            this.N = N;
        }
    }
    
    // construct an empty set of points
    public KdTree() {
    }
    
    // is the symbol table empty?
    public boolean isEmpty() {
        return size() == 0;
    }
    
    // number of points in the set
    public int size() {
        return size(root);
    }
    
    // return number of key-value pairs in BST rooted at x
    private int size(Node x) {
        if (x == null) return 0;
        else return x.N;
    }
    
/*                         
   public void insert(Point2D p)              // add the point to the set (if it is not already in the set)
   public boolean contains(Point2D p)            // does the set contain point p? 
   public void draw()                         // draw all points to standard draw 
   public Iterable<Point2D> range(RectHV rect)             // all points that are inside the rectangle 
   public Point2D nearest(Point2D p)             // a nearest neighbor in the set to point p; null if the set is empty */
   // unit testing of the methods (optional) 
    public static void main(String[] args) {
    }
}