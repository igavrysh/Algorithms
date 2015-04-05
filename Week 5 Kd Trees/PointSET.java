/*************************************************************************
 *  Compilation:  javac PointSET.java
 *  Execution:    java PointSET
 *  Dependencies: 
 *
 *  Implementation of PointSET.
 *
 *************************************************************************/

public class PointSET {
  // construct an empty set of points
  private SET<Point2D> set;
  public PointSET() {
    set = new SET<Point2D>();
  }
  // is the set empty? 
  public boolean isEmpty() {
    return set.isEmpty();
  }
  // number of points in the set
  public int size() {
    return set.size();
  }
  // add the point to the set (if it is not already in the set)
  public void insert(Point2D p) {
    if (p == null) throw new NullPointerException("Argument point has null value");
    if (!set.contains(p)) {
      set.add(p);
    }
  }
  // does the set contain point p?
  public boolean contains(Point2D p) {
    if (p == null) throw new NullPointerException("Argument point has null value");
    return set.contains(p);
  }
  
  // draw all points to standard draw
  public void draw() {
   //StdDraw.show(0);
   //StdDraw.setCanvasSize(1, 1);
   //StdDraw.clear();
   //StdDraw.setPenColor(StdDraw.BLACK);
   for (Point2D q : set) {
     StdDraw.point(q.x(), q.y());
   }
   //StdDraw.show(0);
  }
  
  // all points that are inside the rectangle
  public Iterable<Point2D> range(RectHV rect) {
    Queue<Point2D> queue = new Queue<Point2D>();
    for (Point2D point : set) {
      if (point.x() > rect.xmin() && point.x() < rect.xmax() 
            && point.y() > rect.ymin() && point.y() < rect.ymax()) {
        queue.enqueue(point);
      }
    }
    return queue;
  }
  // a nearest neighbor in the set to point p; null if the set is empty
  public Point2D nearest(Point2D p) {
    if (p == null) throw new NullPointerException("Argument point has null value");
    Point2D nearest = null;
    double minDistance = 0;
    for (Point2D point : set) {
      if (nearest == null) {
        nearest = point;
        minDistance = p.distanceTo(point);
      }
      else {
        if (minDistance > p.distanceTo(point)) {
          nearest = point;
          minDistance = p.distanceTo(point);
        }
      }
    }
    return nearest;
  }

  // unit testing of the methods (optional)
  public static void main(String[] args) {
      PointSET set = new PointSET();
      set.insert(new Point2D(1, 0.5));
      set.insert(new Point2D(0.5, 0.5));
      set.insert(new Point2D(0.3, 0.3));
      set.draw();
  } 
}