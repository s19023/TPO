package zad1;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
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
            Charset inCharset = Charset.forName("Cp1250"), outCharset = StandardCharsets.UTF_8;
            FileChannel outputFileChannel = new FileOutputStream(resultFile).getChannel();

            Files.walkFileTree(directoryPath, new SimpleFileVisitor<Path>()
            {
                @Override
                public FileVisitResult visitFile(Path path, BasicFileAttributes basicFileAttributes) throws IOException
                {
                    if(path.toString().endsWith(".txt"))
                    {
                        FileChannel inputFileChannel = new FileInputStream(new File(path.toString())).getChannel();
                        ByteBuffer byteBuffer = ByteBuffer.allocate((int)inputFileChannel.size());

                        inputFileChannel.read(byteBuffer);
                        byteBuffer.flip();

                        CharBuffer charBuffer = inCharset.decode(byteBuffer);

                        byteBuffer = outCharset.encode(charBuffer);
                        outputFileChannel.write(byteBuffer);

                        inputFileChannel.close();
                    }

                    return super.visitFile(path, basicFileAttributes);
                }
            });

            outputFileChannel.close();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }
}
