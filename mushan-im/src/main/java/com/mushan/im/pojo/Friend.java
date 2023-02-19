package com.mushan.im.pojo;

public class Friend {

    private Long id;
    private Long uid;
    private Long fid;

    @Override
    public String toString() {
        return "Friend{" +
                "id=" + id +
                ", uid=" + uid +
                ", fid=" + fid +
                '}';
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUid() {
        return uid;
    }

    public void setUid(Long uid) {
        this.uid = uid;
    }

    public Long getFid() {
        return fid;
    }

    public void setFid(Long fid) {
        this.fid = fid;
    }
}
