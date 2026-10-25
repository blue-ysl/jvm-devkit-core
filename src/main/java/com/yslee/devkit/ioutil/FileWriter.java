package com.yslee.devkit.ioutil;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousByteChannel;
import java.nio.channels.AsynchronousFileChannel;
import java.nio.channels.CompletionHandler;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.concurrent.Future;

import static java.nio.file.StandardOpenOption.*;

public class FileWriter
{
    public boolean overwrite(String content, String filePath)
    {
        return overwrite(content, filePath, StandardCharsets.UTF_8);
    }

    public boolean overwrite(String content, String filePath, Charset charset)
    {
        try
        {
            Files.writeString(Path.of(filePath), content, charset);
            return true;
        }
        catch (IOException exception)
        {
            System.out.println(exception.getMessage());
        }

        return false;
    }

    public boolean overWriteAsync(String content, String filePath)
    {
        return overwriteAsync(content, filePath, StandardCharsets.UTF_8);
    }

    public boolean overwriteAsync(String content, String filePath, Charset charset)
    {
        try
        {
            AsynchronousFileChannel channel = AsynchronousFileChannel.open(Path.of(filePath), TRUNCATE_EXISTING, WRITE);
            byte[] b = content.getBytes(charset);
            ByteBuffer bb = ByteBuffer.wrap(b);
            bb.limit(3);
            int lsm = 3;
            CompletionHandler<Integer, Long> ch = new CompletionHandler<>()
            {
                @Override
                public void completed(Integer written, Long position)
                {
                    long nextPosition = position + written;

                    System.out.println("PRINT DONE! = " + nextPosition);
                    int originLimit = bb.limit();
                    int newLimit = Math.min(originLimit + 3, bb.capacity());
                    bb.limit(newLimit);
                    if (bb.hasRemaining())
                    {

                        channel.write(bb, nextPosition, nextPosition, this);
                    }
                    else
                    {
                        try
                        {
                            channel.close();
                        }
                        catch (IOException e)
                        {
                            throw new RuntimeException(e);
                        }
                    }
                }

                @Override
                public void failed(Throwable exc, Long position)
                {
                    System.out.println("PRINT FAILED! " + Arrays.toString(exc.getStackTrace()));
                }
            };

            channel.write(bb, 0, 0L, ch);
        }
        catch (IOException exception)
        {
            System.out.println(exception.getMessage());
            exception.printStackTrace();
        }


        return true;
    }

    public boolean append(String content, String filePath)
    {
        return append(content, filePath, StandardCharsets.UTF_8);
    }

    public boolean append(String content, String filePath, Charset charset)
    {
        try
        {
            Files.writeString(Path.of(filePath), content, charset, CREATE, WRITE, APPEND);
            return true;
        }
        catch (IOException exception)
        {
            System.out.println("Exception: " + exception.getMessage());
        }
        return false;
    }
}
