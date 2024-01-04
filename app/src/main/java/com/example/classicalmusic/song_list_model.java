package com.example.classicalmusic;

public class song_list_model {
    String pic,name, singer,song_acc;

    song_list_model(String pic,String name,String singer,String song_acc)
    {
        this.pic=pic;
        this.name=name;
        this.singer = singer;
        this.song_acc=song_acc;

    }


    public String getPic() {
        return pic;
    }

    public String getName() {
        return name;
    }

    public String getSinger() {
        return singer;
    }

    public String getSong_acc() {
        return song_acc;
    }
}
