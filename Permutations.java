import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;
import java.util.TreeSet;

public class Permutations {
    static final int indicator=8;
    static HashSet<Point>[] trace=new HashSet[indicator];
    static ArrayList<Point> points= new ArrayList<>();
    public static boolean[] forbidden;
    public static void main(String[] args) {
        generatePoints();
        findSubPoints();
        formClasses();
        printPoints(points);
      // trace[0].remove(points.get(6));
       // trace[0].remove(points.get(7));
      // reduceColumn(0);
      // reduceCombinations();
       // choosePath();
       // trace[0].retainAll(trace[1]);
        printSets();
       // printPoints(findMatches());


    }
    public static class Point
    {
        public Point(int x, int y)
        {
            this.x=x;
            this.y=y;
        }
        int x;
        int y;
        ArrayList<Point> subPoints =new ArrayList<>();

        @Override
        public String toString()
        {
            String points="";
            for (int i=0;i<subPoints.size();i++)
            {
                points+=" ("+subPoints.get(i).x+","+subPoints.get(i).y+")";
            }
            return "("+x+","+y+")   "+points;
        }
        public String smallString()
        {
            return "("+x+","+y+")   ";
        }
        public Boolean equals(Point point)
        {
            return point.x==x&&point.y==y;
        }
    }
    public static void generatePoints()
    {
        ArrayList<Point> pointArrayList=new ArrayList<>();
        for (int i = 1; i < indicator+1; i++)
        {
            for (int j=1+i;j<indicator+1;j++)
            {
                pointArrayList.add(new Point(i,j));
            }
        }
        points=pointArrayList;
        forbidden=new boolean[points.size()];
    }
    public static void findSubPoints()
    {
        for (int i=0;i<points.size();i++)
        {
            for (int j=0;j<points.size();j++)
            {
                if(!partialmatch(points.get(j),points.get(i)))
                {
                    points.get(j).subPoints.add(points.get(i));
                }
            }

        }
    }
    public static boolean partialmatch(Point a,Point b)
    {
        return a.x==b.y||a.y==b.x||a.y==b.y||a.x==b.x;
    }
    public static boolean partialmatch(Point a,int x, int y)
    {
        return a.x==y||a.y==x||a.y==y||a.x==x;
    }
    public static void printPoints(ArrayList<Point> points)
    {
        for (int i=0;i<points.size();i++)
        {
            System.out.println(points.get(i).toString());
        }
    }
    public static void printSets()
    {
        for (int i=0;i<trace.length;i++)
        {
            System.out.print((i+1)+": ");
            for (int j=0;j<trace[i].size();j++)
            {
                Object[] points=trace[i].toArray();

                    System.out.print(((Point)points[j]).smallString());
            }
            System.out.println();
        }
    }
    public static void formClasses()
    {
        for (int m=0;m<trace.length;m++)
        {
            trace[m]=new HashSet<>();
        }
        for (int i=0;i<points.size();i++)
        {
            for (int j=0;j<points.size();j++)
            {
                if(points.get(j).x!=points.get(i).x&&points.get(j).y!=points.get(i).x)
                trace[points.get(i).x-1].add(points.get(j));
            }
        }
        for (int k=0;k<points.size();k++)
        {
            if(points.get(k).y!=points.get(points.size()-1).y&&points.get(k).x!=points.get(points.size()-1).y)
            trace[indicator-1].add(points.get(k));
        }

    }
    public static void reduceSet(int where)
    {
        reduceColumn(where);

    }
    public static void reduceColumn(int where)
    {
        int[] buckets = new int[indicator];
        Object[] points = trace[where].toArray();
        trace[where].clear();
        if (indicator % 2 == 0)
        {
            int cursor = 0;

            for (int i = 0; i < points.length; i++) {
                Point p = (Point) points[i];

                if ((buckets[p.x - 1] != 1 && buckets[p.y - 1] != 1)) {
                    trace[where].add(p);
                    buckets[p.x - 1]++;
                    buckets[p.y - 1]++;
                    cursor++;
                }
                if (cursor == indicator / 2) {
                    break;
                }
            }
            for (int i = cursor; i < cursor + 1; i++) {
                Point p = (Point) points[i];
                if ((p.x == indexof(0, buckets) || p.y == indexof(0, buckets))) {
                    trace[where].add(p);
                    buckets[p.x - 1]++;
                    buckets[p.y - 1]++;
                    cursor++;
                }
            }

            for (int i = cursor; i < points.length; i++) {
                Point p = (Point) points[i];
                if ((buckets[p.x - 1] != 2 && buckets[p.y - 1] != 2)) {
                   trace[where].add(p);
                    buckets[p.x - 1]++;
                    buckets[p.y - 1]++;
                }
            }
        }
        else
        {
            for (int i = 0; i < points.length; i++)
            {
                Point p = (Point) points[i];
                if ((buckets[p.x - 1] != 2 && buckets[p.y - 1] != 2)) {
                   trace[where].add(p);
                    buckets[p.x - 1]++;
                    buckets[p.y - 1]++;
                }

            }
        }
    }
    public static int findsmallest(int[] array)
    {
        int minValue=array[0];
        int min=0;
        for (int i = 1; i <array.length ; i++)
        {
            if(minValue<array[i])
            {
                minValue=array[i];
                min=i;
            }
        }
        return min;
    }
   /* public static ArrayList<Point> findMatches()
    {
        ArrayList<Point> matches=new ArrayList<>();
        int[] buckets=new int[indicator];
        for (int i=0;i<indicator-1;i++)
        {
            for (int j=0;j<points.get(i).subPoints.size();j++)
            {
                if(buckets[points.get(i).subPoints.get(j).x-1]!=1&&buckets[points.get(i).subPoints.get(j).y-1]!=1)
                {
                    Point point=points.get(i).subPoints.get(j);
                    matches.add(point);
                    buckets[points.get(i).subPoints.get(j).x-1]++;
                    buckets[points.get(i).subPoints.get(j).y-1]++;
                    break;
                }
            }
        }
        return matches;
    } */
    public static int indexof(int m,int[] buckets)
    {
        for (int i=0;i<buckets.length;i++)
        {
            if (buckets[i]==m) return i;
        }
        return -1;
    }
    static int pointArrayMatch(Object[] points,Point point)
    {
        int counter=0;
        for(int i=0;i<points.length;i++)
        {
            if (partialmatch(point,(Point)points[i]))
            {
                counter++;
            }
        }
        return counter;
    }
    static void reduceCombinations()
    {
        for (int i=0;i<trace.length;i++)
        {
            trace[i].retainAll(trace[0]);
        }
    }
    static void choosePath()
    {
        for (int i = 1; i <trace.length-3 ; i++)
        {
            Point point=(Point)trace[i].toArray()[0];
            trace[i].clear();
            trace[i].add(point);
            for (int j = 1; j <trace.length ; j++)
            {
                if(trace[j].size()>1)
                trace[j].remove(point);
            }

        }
    }
}
