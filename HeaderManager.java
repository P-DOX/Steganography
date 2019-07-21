import java.io.File;

public class HeaderManager
{
    public static final int HEADER_LENGTH = 25;
    private static final char SEPARATOR = '~';

    public static String FormHeader(File file)
    {
        String fName = file.getName();
        String fs = String.valueOf(file.length());

        int forSize = 9;
        int forName = HEADER_LENGTH - forSize - 1;

        while (fs.length() < forSize)
            fs = "#" + fs;

        if (fName.length() > forName)
        {
            //trim
            int start = fName.length() - forName;
            fName = fName.substring(start);
        }
        else
        {
            while (fName.length() < forName)
                fName = "#" + fName;
        }

        return fName + SEPARATOR + fs;
    }//FormHeader

    public static long GetSize(String header)
    {
        int start = header.indexOf(SEPARATOR);
        String size = header.substring(start + 1);
        size = size.replaceAll("#", "");
        size = size.trim();
        return Long.parseLong(size);
    }//GetSize

    public static String GetName(String header)
    {
        int end = header.indexOf(SEPARATOR);
        String name = header.substring(0, end);
        name = name.replaceAll("#","");
        name = name.trim();
        return name;
    }
}
