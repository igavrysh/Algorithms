/*************************************************************************
 * Name: Floyd Taylor   
 *
 * Compilation:  javac TwoTreeVisualizer
 * Execution:    java TwoTreeVisualizer <args>
 * Arguments: (all optional)
 * args[0] - Number of Points for each loop (default 200)
 * args[1] - Number of Loops (default 8)
 * args[2] - Loop Delay (default 2000 msec)
 * args[3] - Number of Nearest Points (Default 5)
 * args[4] - Minimum Box areasize (default .2)
 * 
 * Dependencies: StdLib, java.util, PointSET, KdTree
 *
 * Description: An Side-by-Side Visualizer for both KdTree and PointSET
 *
 *************************************************************************/
import java.awt.Font;

/**
 * Draw Visualization of 200 Random Points in Range(0:1), in two separate
 * frames (KdTree on left, PointSET on right) , with each frame showing:
 * - All points
 * - Rectangle for range search (RED)
 * - Points in the range (RED)
 * - Nearest test points (BLUE point, GREEN nearest, MAGENTA line, GRAY zone)
 * 
 * KdTree uses KdTree.draw(), and shows the partition lines
 * 
 * @author Floyd Taylor
 *
 */
public class TwoTreeVisualizer {

    /**
     * Not used
     */
    public TwoTreeVisualizer() {
        //
    }

    /*
     * Private methods for testing ...
     */
    private static boolean inRect(Point2D p, RectHV r) {
        if (p.y() > r.ymax()) return false;
        if (p.y() < r.ymin()) return false;
        if (p.x() < r.xmin()) return false;
        if (p.x() > r.xmax()) return false;
        return true;
    }

    /*
     * generate some random points
     */
    private static double[] getRandomPoints(int number, double maxSize) {
        double[] d = new double[number * 2];
        for (int i = 0; i < number * 2; i = i + 2) {
            d[i] = StdRandom.uniform();
            d[i + 1] = StdRandom.uniform();
        }
        return d;
    }

    /**
     * Main method, unit testing
     * 
     * @param args
     */
    public static void main(String[] args) {
        PointSET ps;
        //KdTree kd;

        int numPoints = 200;
        int loopTo = 8;
        int loopDelay = 4000;
        int numTestPoints = 5;
        double minBoxArea = .2;
        // Look for overrides on defaults
        if (args.length > 0) {
            numPoints = Integer.parseInt(args[0]);
            if (args.length > 1) loopTo = Integer.parseInt(args[1]);
            if (args.length > 2) loopDelay = Integer.parseInt(args[2]);
            if (args.length > 3) numTestPoints = Integer.parseInt(args[3]);
            if (args.length > 4) minBoxArea = Double.parseDouble(args[4]);
            numTestPoints = Math.min(numPoints, numTestPoints);
        }

        int loopCount = 0;
        double[] l;
        double maxsize = 1.0;
        double xoff = 1.1;

        Point2D[] tPoints = new Point2D[numTestPoints];

        while (loopCount < loopTo) {
            loopCount++;
            ps = new PointSET();
            //kd = new KdTree();
            StdDraw.show(0);
            StdDraw.setCanvasSize(1024, 512);
            StdDraw.clear();
            StdDraw.setPenColor(StdDraw.BLACK);
            StdDraw.setXscale(-.05 * maxsize, 2.2 * maxsize);
            StdDraw.setYscale(-.05 * maxsize, 1.05 * maxsize);
            StdDraw.setFont(new Font("SansSerif", Font.BOLD, 16));
            StdDraw.line(0, 0, 0, 1);
            StdDraw.line(0, 0, 1, 0);
            StdDraw.line(0, 1, 1, 1);
            StdDraw.line(1, 0, 1, 1);
            StdDraw.line(xoff, 0, xoff, 1);
            StdDraw.line(xoff, 0, xoff + 1, 0);
            StdDraw.line(xoff, 1, xoff + 1, 1);
            StdDraw.line(xoff + 1, 0, xoff + 1, 1);
            StdDraw.setPenColor(StdDraw.BLACK);
            StdDraw.text(.5 * maxsize * 2.2, maxsize * 1.025, "Testing "
                    + numPoints + " Random Points");
            StdDraw.textLeft(0, maxsize * 1.025, "KdTree");
            StdDraw.textRight(2.1, maxsize * 1.025, "PointSET");

            // generate and draw points
            StdOut.println("Add " + numPoints + " Random points");
            l = getRandomPoints(numPoints, maxsize);
            int i = 0;
            Point2D pt;
            while (i < l.length) {
                pt = new Point2D(l[i], l[i + 1]);
                //kd.insert(pt);
                ps.insert(pt);
                i += 2;
            }
            //kd.draw();
            // Draw Point set points with x-offset
            for (Point2D q : ps.range(new RectHV(0, 0, 1, 1))) {
                StdDraw.point(q.x() + xoff, q.y());
            }
            StdDraw.show(0);

            // get random test points
            l = getRandomPoints(numTestPoints, maxsize);
            i = 0;
            while (i < l.length) {
                pt = new Point2D(l[i], l[i + 1]);
                tPoints[i / 2] = pt;
                i += 2;
            }

            // setup the random Range Box
            StdDraw.setPenRadius(.01);
            StdDraw.setPenColor(StdDraw.RED);
            double xmin = StdRandom.uniform();
            double ymin = StdRandom.uniform();
            double width = 0;
            double height = 0;
            while (width * height < minBoxArea) {
                width = StdRandom.uniform();
                height = StdRandom.uniform();
            }
            xmin = Math.min(xmin, 1. - width);
            ymin = Math.min(ymin, 1. - height);
            RectHV rect = new RectHV(xmin, ymin, xmin + width, ymin + height);
            rect.draw();
            StdDraw.rectangle(xmin + width / 2. + xoff, ymin + height / 2.,
                    width / 2., height / 2.);
            StdDraw.show(0);

            // Highlight points in Box
            StdDraw.setPenRadius(.01);
            StdDraw.setPenColor(StdDraw.RED);
            int kdCount = 0;
            //for (Point2D p : kd.range(rect)) {
            //    p.draw();
            //    kdCount++;
            //}
            int psCount = 0;
            for (Point2D p : ps.range(rect)) {
                StdDraw.point(p.x() + xoff, p.y());
                psCount++;
            }
            StdDraw.show(0);
            StdOut.println("Found " + kdCount + " in the kdTree Rectangle");
            StdOut.println("Found " + psCount + " in the PointSet Rectangle");

            // Mark nearest test points
            StdOut.println("Nearest Test Points");
            Point2D npt;
            for (int t = 0; t < numTestPoints; t++) {
                StdDraw.setPenRadius(.02);
                StdDraw.setPenColor(StdDraw.BLUE);
                tPoints[t].draw();
                StdDraw.setPenColor(StdDraw.GREEN);
                npt = ps.nearest(tPoints[t]);
                npt.draw();
                StdDraw.setPenRadius(.005);
                StdDraw.setPenColor(StdDraw.MAGENTA);
                StdDraw.line(tPoints[t].x(), tPoints[t].y(), npt.x(), npt.y());
                StdDraw.setPenRadius(.002);
                StdDraw.setPenColor(StdDraw.GRAY);
                StdDraw.circle(tPoints[t].x(), tPoints[t].y(),
                        tPoints[t].distanceTo(npt));
                StdOut.println("#" + (t + 1) + " PointSET Nearest "
                        + tPoints[t] + " is " + npt);
            }
            for (int t = 0; t < numTestPoints; t++) {
                StdDraw.setPenRadius(.02);
                StdDraw.setPenColor(StdDraw.BLUE);
                StdDraw.point(tPoints[t].x() + xoff, tPoints[t].y());
                StdDraw.setPenColor(StdDraw.GREEN);
                //npt = kd.nearest(tPoints[t]);
                //StdDraw.point(npt.x() + xoff, npt.y());
                StdDraw.setPenRadius(.005);
                StdDraw.setPenColor(StdDraw.MAGENTA);
                //StdDraw.line(tPoints[t].x() + xoff, tPoints[t].y(), npt.x()
                //        + xoff, npt.y());
                StdDraw.setPenRadius(.002);
                StdDraw.setPenColor(StdDraw.GRAY);
                //StdDraw.circle(tPoints[t].x() + xoff, tPoints[t].y(),
                //        tPoints[t].distanceTo(npt));
                //StdOut.println("#" + (t + 1) + " KdTree Nearest " + tPoints[t]
                //        + " is " + npt);
            }
            StdDraw.setFont(new Font("SansSerif", Font.BOLD, 14));
            StdDraw.setPenColor(StdDraw.BLACK);
            StdDraw.text(.5 * maxsize * 2.2, -maxsize * .025, "Loop "
                    + loopCount + " of " + loopTo);
            StdDraw.setFont(new Font("SansSerif", Font.BOLD, 12));
            StdDraw.textLeft(0, -maxsize * .025, "(" + kdCount + " in Range)");
            StdDraw.textRight(2.1, -maxsize * .025, "(" + psCount
                    + " in Range)");
            StdOut.println("Loop complete");
            StdDraw.show(loopDelay);
        }
        StdOut.println("All Loops Complete");
        StdDraw.show(loopDelay * 2);
        System.exit(0);
    }
}