package com.interview.pandasoft.Model;

public class DataApiResponse {
    String id,uuid,title,image,detail;
    long create;

    public DataApiResponse(String id, String uuid, long create, String title, String image, String detail) {
        this.id = id;
        this.uuid = uuid;
        this.create = create;
        this.title = title;
        this.image = image;
        this.detail = detail;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public long getCreate() {
        return create;
    }

    public void setCreate(long create) {
        this.create = create;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }
}
