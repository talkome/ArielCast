package com.example.arielcast.firebase.model.dataObject;

public class LectureObj {
    private String name,lecturerEmail, video_url, search;

    public LectureObj() {}

    public String getLecturerEmail() {
        return lecturerEmail;
    }

    public void setLecturerEmail(String lecturerEmail) {
        this.lecturerEmail = lecturerEmail;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getVideo_url() {
        return video_url;
    }

    public void setVideo_url(String video_url) {
        this.video_url = video_url;
    }

    public String getSearch() {
        return search;
    }

    public void setSearch(String search) {
        this.search = search;
    }

}
