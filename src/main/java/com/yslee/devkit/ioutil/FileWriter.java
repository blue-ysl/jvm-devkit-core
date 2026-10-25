package com.yslee.devkit.ioutil;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;

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
