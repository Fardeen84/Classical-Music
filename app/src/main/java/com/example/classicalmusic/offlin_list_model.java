package com.example.classicalmusic;

import java.io.File;

public class offlin_list_model {
    File[] files;
    int num;
    offlin_list_model(File[] files,int num)
    {
        this.files=files;
        this.num=num;
    }

    public File[] getFiles() {
        return files;
    }

    public int getNum() {
        return num;
    }
}
