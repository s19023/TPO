package zad1;

import java.io.File;
import java.io.IOException;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;

public class Futil
{
    public static void processDir(String dirName, String resultFileName)
    {
        try
        {
            File resultFile = new File(resultFileName);
            resultFile.createNewFile();
            Path directoryPath = Paths.get(dirName);

            Files.walkFileTree(directoryPath, new SimpleFileVisitor<Path>()
            {
                @Override
                public FileVisitResult visitFile(Path path, BasicFileAttributes basicFileAttributes) throws IOException
                {
                    
                    return super.visitFile(path, basicFileAttributes);
                }
            });

        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }
}
