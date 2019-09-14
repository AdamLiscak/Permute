import java.awt.*;
import java.util.*;

public class permute
{
    static int indicatornum=5;
    static int permutationCount=0;
    static ArrayList<Integer> forbiddenIndeces=new ArrayList<>();
    public static boolean[] allowed;
    static ArrayList<String> permutations= new ArrayList<>();
    static ArrayList<String> reversePermutations= new ArrayList<>();
    public static class Point
    {
        public Point(int x,int y)
        {
            this.x=x;
            this.y=y;
        }
        int x;
        int y;
        ArrayList<Point> subPoints;

        @Override
        public String toString() {
            String points="";
            for (int i=0;i<subPoints.size();i++)
            {
                points+=" ("+subPoints.get(i).x+","+subPoints.get(i).y+")";
            }
            return "("+x+","+y+")   "+points;
        }

        public boolean equals(Point obj)
        {
            return obj.x==x&&obj.y==y;
        }
    }
    public static class TableCol
    {
        public TableCol()
        {

        }
        public TableCol(String denominator, String column)
        {
            this.denominator=denominator;
            this.column=column;
        }
        String denominator;
        String column;

        @Override
        public String toString() {
            return denominator+"    "+column;
        }
    }
    public static void main(String[] args) {
        print();
        String a="(31,2) (2,3) (3,4) (4,5)";
        System.out.println(a.replaceAll("[^0-9]+","."));
        System.out.println(a.split("[^0-9]")[0]);
        Set setItems = new LinkedHashSet(permutations);
        permutations.clear();
        permutations.addAll(setItems);
        Set reverse= new LinkedHashSet(reversePermutations);
        reversePermutations.clear();
        reversePermutations.addAll(reverse);
        Collections.sort(reversePermutations);
        int m;
        for (m=0; m<permutations.size();m++)
        {
            System.out.println(permutations.get(m));
        }
        System.out.println(m+" unique combinations");
        permutationCount=m;
        allowed=new boolean[m];
        Arrays.fill(allowed,true);
        ArrayList<TableCol> permtable= aggregate(permutations);
        ArrayList<TableCol> reversePermtable= aggregate(reversePermutations);
        ArrayList<TableCol> fullTable=union(permtable,reversePermtable);
        ArrayList<TableCol> mapped=findSingle(fullTable);
        ArrayList<Point> graph = parseGraph(fullTable);
   //     System.out.println(graph.get(0).toString());
//        System.out.println(graph.get(7).toString());
        queryGraph(graph);
     // removepoints(graph,graph.get(0),graph.get(3));
        System.out.println(smallestPosition(graph));
        System.out.println(" ");

        for (int s=0;s<graph.size();s++)
        {
            System.out.println(graph.get(s).toString());
        }
        System.out.println(" ");

    }
    public static void print()
        {

            int i=0;
            for(int a=1;a<=indicatornum;a++)
            {
                for (int b=1;b<=indicatornum;b++)
                {
                    for (int c=1;c<=indicatornum;c++)
                    {
                        for (int d=1;d<=indicatornum;d++)
                        {
                            if(a!=b&&c!=d&&a!=c&&a!=d&&b!=c&&b!=d) {
                                int[] array1={a,b};
                                int[] array2={c,d};
                                int[] array=new int[4];
                                Arrays.sort(array1);
                                Arrays.sort(array2);
                                if(array1[0]>array2[0])
                                {
                                    for(int i1=0;i1<2;i1++)
                                    {
                                        array[i1]=array2[i1];
                                        array[i1+2]=array1[i1];
                                    }
                                }
                                else
                                {
                                    for(int i1=0;i1<2;i1++)
                                    {
                                        array[i1]=array1[i1];
                                        array[i1+2]=array2[i1];
                                    }
                                }
                                i++;
                                permutations.add("("+array[0]+","+array[1]+")"+","+"("+array[2]+","+array[3]+")");
                                reversePermutations.add("("+array[2]+","+array[3]+")"+","+"("+array[0]+","+array[1]+")");
                            }

                        }
                    }
                }
            }
            System.out.println(i);
        }
        public static ArrayList<TableCol> findSingle (ArrayList<TableCol> a)
        {
            ArrayList<TableCol> arr = new ArrayList<>();
            for (int j=1;j<=indicatornum;j++) {
                Integer input = j;
                String allies="";
                String enemies="";
                for (int i = 0; i < a.size(); i++)
                {
                    if(a.get(i).denominator.contains(input.toString()))
                    {
                        allies=" "+a.get(i).denominator.replaceAll(input.toString(),"").replaceAll("[^0-9]","")+" ";
                        enemies+=allies;
                        enemies+=a.get(i).column;

                    }
                }
                arr.add(new TableCol(input.toString(),enemies));
            }
            return arr;
        }
        public static ArrayList<TableCol> union(ArrayList<TableCol> a, ArrayList<TableCol> b)
        {
            for(int i=0;i<a.size();i++)
            {
                String denom=a.get(i).denominator;
                for(int j=0;j<b.size()-3;j++)
                {
                    String denom1=b.get(j).denominator;
                    if(denom.equals(denom1))
                    {
                        a.get(i).column+=b.get(j).column;
                    }
                }

            }
            int m=a.size();
            for(int k=3;k>0;k--)
            {
                a.add(b.get(b.size()-k));
            }
            return a;
        }
        public static ArrayList<TableCol> aggregate(ArrayList<String> permutations)
        {
            ArrayList<TableCol> table=new ArrayList<>();

            for(int i=0;i<permutations.size();i++)
            {
                String a=permutations.get(i).split(",")[0]+","+permutations.get(i).split(",")[1];
                String b=permutations.get(i).split(",")[2]+","+permutations.get(i).split(",")[3];

                if(i==0)
                {
                    TableCol col= new TableCol();
                    col.denominator=permutations.get(0).split(",")[0]+","+permutations.get(0).split(",")[1];
                    col.column=permutations.get(0).split(",")[2]+","+permutations.get(0).split(",")[3];
                    table.add(col);
                }
               else if(!a.equals(table.get(table.size()-1).denominator))
                {
                    TableCol col= new TableCol();
                    col.denominator=permutations.get(i).split(",")[0]+","+permutations.get(i).split(",")[1];
                    col.column=permutations.get(i).split(",")[2]+","+permutations.get(i).split(",")[3];
                    table.add(col);
                }
              else
                {
                    table.get(table.size()-1).column+=b;
                }
            }
         /*   for (int j=0;j<table.size();j++)
            {
                System.out.println(table.get(j).toString());
            }
            */
            return table;
        }
       public static ArrayList<Point> parseGraph(ArrayList<TableCol> permutations)
       {
        ArrayList<Point> points=new ArrayList<>();
           for (int i=0;i<permutations.size();i++)
           {
               String strRepresentant=permutations.get(i).denominator;
               String[] strRepresentants=strRepresentant.split("[^0-9]+");
               Point p= new Point(Integer.parseInt(strRepresentants[1]),Integer.parseInt(strRepresentants[2]));
               String strValue=permutations.get(i).column;
               String[] strValues= strValue.split("[^0-9]+");
               ArrayList<Point> subPoints=new ArrayList<>();
               for (int j=1;j<strValues.length-1;j+=2)
               {
                  Point q= new Point(Integer.parseInt(strValues[j]),Integer.parseInt(strValues[j+1]));
                  subPoints.add(q);
               }
               p.subPoints=subPoints;
               points.add(p);

           }
            return points;
       }
       public static ArrayList<Point> queryGraph(ArrayList<Point> graph) {
           int[][] Buckets = new int[indicatornum][indicatornum];
           Point oldPoint = new Point(0, 0);
           int i = 0;
           while (!ordered(graph)&&i<permutationCount) {
               Point point = graph.get(smallestPosition(graph));
               int coord1 = point.x;
               int coord2 = point.y;
               ArrayList<Point> subPoints = point.subPoints;
               int x = giveTicket(coord1 - 1, indicatornum + 1, Buckets) + 1;
               int y = giveTicket(coord1 - 1, x, Buckets) + 1;
               int z = giveTicket(coord2 - 1, 0, Buckets) + 1;
               int w=giveTicket(coord2-1,z,Buckets)+1;
              // System.out.println(x + " " + y);
               for (int j = 0; j < point.subPoints.size(); j++) {

                   Point subPoint = subPoints.get(j);
                   if (subPoint.x != oldPoint.x || subPoint.y != oldPoint.x)
                        {
                       if (Buckets[point.x - 1][subPoint.x - 1] + 1 <= 2 && Buckets[point.x - 1][subPoint.y - 1] + 1 <= 2 && Buckets[point.y - 1][subPoint.x - 1] + 1 <= 2 && Buckets[point.y - 1][subPoint.y - 1] + 1 <= 2 && Buckets[subPoint.x - 1][point.x - 1] + 1 <= 2 && Buckets[subPoint.x - 1][point.y - 1] + 1 <= 2 && Buckets[subPoint.y - 1][point.x - 1] <= 2 && Buckets[point.y - 1][subPoint.x - 1] <= 2) {
                           removepoints(graph, point, subPoint);
                   /*        Buckets[point.x - 1][subPoint.x - 1]++;
                           Buckets[point.x - 1][subPoint.y - 1]++;
                           Buckets[point.y - 1][subPoint.x - 1]++;
                           Buckets[point.y - 1][subPoint.y - 1]++;
                           Buckets[subPoint.x - 1][point.x - 1]++;
                           Buckets[subPoint.x - 1][point.y - 1]++;
                           Buckets[subPoint.y - 1][point.x - 1]++;
                           Buckets[subPoint.y - 1][point.y - 1]++; */
                           oldPoint = subPoint;
                           break;
                       }
               }


               }
               checkSize(graph,Buckets);

               // Buckets[point.x][subPoint.x];
               // Buckets[point.x][subPoint.y];
               // Buckets[point.y][subPoint.x];
               // Buckets[point.y][subPoint.y];

               i++;

           }
           for (int m=0;m<indicatornum;m++)
           {
               for (int s=0;s<indicatornum;s++)
               {
                   System.out.print(Buckets[m][s]);
               }
               System.out.print("\n");
           }
           return null;
       }
       public static void checkSize(ArrayList<Point> graph,int[][] Buckets)
       {
           for (int i=0;i<graph.size();i++)
           {
               Point point=graph.get(i);
                   if(graph.get(i).subPoints.size()==1&&allowed[i])
                   {
                       System.out.println("hello");
                       Point subPoint=graph.get(i).subPoints.get(0);
                       Buckets[point.x - 1][subPoint.x - 1]++;
                       Buckets[point.x - 1][subPoint.y - 1]++;
                       Buckets[point.y - 1][subPoint.x - 1]++;
                       Buckets[point.y - 1][subPoint.y - 1]++;
                       Buckets[subPoint.x - 1][point.x - 1]++;
                       Buckets[subPoint.x - 1][point.y - 1]++;
                       Buckets[subPoint.y - 1][point.x - 1]++;
                       Buckets[subPoint.y - 1][point.y - 1]++;
                       allowed[i]=false;
                       for (int k=0;k<graph.size();k++)
                       {
                           if(graph.get(k).equals(subPoint))
                           {
                                allowed[k]=false;
                           }
                       }
               }
           }
       }
       public static int smallestPosition (ArrayList<Point> graph)
       {
           int min=0;
           int minValue=permutationCount*2/graph.size();
           for(int i=0;i<graph.size();i++)
           {
               if(graph.get(i).subPoints.size()<=minValue&&graph.get(i).subPoints.size()!=1)
               {
                   minValue=graph.get(i).subPoints.size();
                   min=i;
               }
           }
           return min;
       }
    public static int giveTicket(int i,int x, int[][] Buckets)
    {
        int min=0;
        for (int k=0;k<Buckets[i].length-1;k++)
        {
            System.out.print(Buckets[i][k]);
            if (Buckets[i][k+1]>Buckets[i][k]&&Buckets[i][k]!=x)
            {
                System.out.print(Buckets[i][k]);
                min=k;
            }
        }
        System.out.println(min);
        return min;
    }
       public static boolean ordered(ArrayList<Point> graph)
    {
        int size=0;
        for (int i=0;i<graph.size();i++)
        {
            size+=graph.get(i).subPoints.size();
        }
        return size<=graph.size();
    }
       public  static  void removepoints(ArrayList<Point> graph, Point a, Point b)
       {
           for (int i=0;i<graph.size();i++)
           {
               Point point=graph.get(i);
               if(!point.equals(a)||!point.equals(b))
               {
                   for (int j = 0; j < point.subPoints.size(); j++) {
                       if(point.subPoints.get(j).equals(a)||point.subPoints.get(j).equals(b))
                       {
                           point.subPoints.remove(j);
                       }
                   }
               }
               if(point.equals(a))
               {
                   for (int j = 0; j < point.subPoints.size(); j++)
                   {

                           point.subPoints.clear();
                           point.subPoints.add(b);
                   }
               }
               else if(point.equals(b))
               {
                       point.subPoints.clear();
                       point.subPoints.add(a);
               }


           }
       }

}
