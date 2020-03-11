package zad1;

import java.io.IOException;
import static java.nio.file.StandardOpenOption.CREATE;
import static java.nio.file.StandardOpenOption.WRITE;
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
            Path directoryPath = Paths.get(dirName);
            Path outputPath = Paths.get(resultFileName);
            Charset inCharset = Charset.forName("Cp1250"), outCharset = StandardCharsets.UTF_8;
            FileChannel outputFileChannel = FileChannel.open(outputPath, CREATE, WRITE);

            Files.walkFileTree(directoryPath, new SimpleFileVisitor<Path>()
            {
                @Override
                public FileVisitResult visitFile(Path path, BasicFileAttributes basicFileAttributes) throws IOException
                {
                    if(path.toString().endsWith(".txt"))
                    {
                        FileChannel inputFileChannel = FileChannel.open(path);
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
