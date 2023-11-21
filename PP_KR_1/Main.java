import java.util.*;

class Point
{
     public double x;
     public double y;
     public Point(double x, double y)
     {
         this.x = x;
         this.y = y;
     }
     public double DistTo (Point p)
     {
         return Math.sqrt((p.x - x) * (p.x - x) + (p.y - y) * (p.y - y));
     }
     public static Point Parse(String str)
             throws MissingFormatArgumentException, NumberFormatException
     {
         if (str.charAt(0) != '(' || str.charAt(str.length() - 1) != ')')
             throw  new MissingFormatArgumentException("Point format must be: \"(X:Y)\"");

         StringTokenizer cord = new StringTokenizer(str.substring(1, str.length() - 1), ":");
         if (cord.countTokens() != 2)
             throw  new MissingFormatArgumentException("Point format must be: \"(X:Y)\"");

         double x = Double.parseDouble(cord.nextToken()),
                y = Double.parseDouble(cord.nextToken());

         return new Point(x, y);
     }
     public String ToString()
     {
         return "("+ x + ":" + y + ")";
     }
}

class Zigzag implements Comparable<Zigzag>
{
    int lineId;
    List<Point> points;
    public Zigzag(int lineId)
    {
        this.lineId = lineId;
        points = new ArrayList<>();
    }
    public void AddPoint (Point p)
    {
        points.add(p);
    }
    public double Length ()
    {
        if (points.size() <= 1)
            return  0d;

        double len = 0d;

        for(int i = 1; i < points.size(); ++i)
            len += points.get(i).DistTo(points.get(i - 1));

        return  len;
    }
    @Override
    public int compareTo(Zigzag o) {

        return -(int)Math.signum(this.Length() - o.Length());
    }
    public String toString()
    {
        StringBuilder ans = new StringBuilder();
        for (int i = 0; i < points.size(); i++) {
            ans.append(points.get(i).ToString());
            if (i != points.size() - 1)
                ans.append(",");
        }
        return ans.toString();
    }
}

public class Main
{
    public static void main(String[] args)
    {
        List<Zigzag> lines = new ArrayList<>();

        int lineNumber = 0;

        Scanner in = new Scanner(System.in);
        while (in.hasNextLine())
        {
            ++lineNumber;
            String line = in.nextLine().trim();
            if (line.isEmpty())
            {
                System.err.println("Line {" + lineNumber + "} exception: Empty line!");
                continue;
            }

            Zigzag zz = new Zigzag(lineNumber);
            boolean errFlag = false;
            StringTokenizer tokenizer = new StringTokenizer(line, ",", false);
            while (tokenizer.hasMoreTokens())
            {
                String token = tokenizer.nextToken().trim();
                if (token.trim().isEmpty())
                {
                    System.err.println("Line {" + lineNumber + "}: Point is empty!");
                    errFlag = true;
                    break;
                }
                try
                {
                    Point p = Point.Parse(token);
                    zz.AddPoint(p);
                }
                catch (Exception e)
                {
                    System.err.println("Line {" + lineNumber + "} exception: " + e.getMessage());
                    errFlag = true;
                    break;
                }
            }
            if (errFlag) continue;
            lines.add(zz);
        }

        lines.sort(Zigzag::compareTo);

        for (var l : lines)
        {
            System.out.println(
                    "Line " + l.lineId +
                    "; Length: " + l.Length() +
                    "; Zigzag: " + l.toString());
        }
    }
}