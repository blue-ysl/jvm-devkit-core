package com.yslee.devkit;

import com.yslee.devkit.ioutil.FileWriter;

import java.nio.charset.StandardCharsets;

public class Main
{
    public static void main(String[] args)
    {
        FileWriter fw = new FileWriter();
        // fw.overwrite("한국어 인코딩 테스트", "path", StandardCharsets.UTF_8);

        fw.append("test test", "path", StandardCharsets.UTF_8);
        fw.append("content test", "nonexist", StandardCharsets.UTF_8);
    }
}
