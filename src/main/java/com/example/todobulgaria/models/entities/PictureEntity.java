package com.example.todobulgaria.models.entities;

import com.example.todobulgaria.models.BaseEntity;

import javax.persistence.*;

@Entity
@Table(name = "pictures")
public class PictureEntity extends BaseEntity {

    @Column
    private String title;
    @Column
    private String url;
    @Column
    private String publicId;

    public PictureEntity() {
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPublicId() {
        return publicId;
    }

    public void setPublicId(String publicId) {
        this.publicId = publicId;
    }
}
