package com.boxfox.chefriapplication.postcard;

/**
 * Created by boxfox on 2017-06-03.
 */

public class PostCardItem {
    private String name;
    private String subInfo;
    private int likes, postNumber;

    private String bgimageUrl;
    private String profileImageUrl;

    public PostCardItem(int postNumber, String name, String subInfo, int likes, String bgimageUrl, String profileImageUrl) {
        this.postNumber = postNumber;
        this.name = name;
        this.subInfo = subInfo;
        this.likes = likes;
        this.bgimageUrl = bgimageUrl;
        this.profileImageUrl = profileImageUrl;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSubInfo() {
        return subInfo;
    }

    public void setSubInfo(String subInfo) {
        this.subInfo = subInfo;
    }

    public int getLikes() {
        return likes;
    }

    public void setLikes(int likes) {
        this.likes = likes;
    }

    public String getBgimageUrl() {
        return bgimageUrl;
    }

    public void setBgimageUrl(String bgimageUrl) {
        this.bgimageUrl = bgimageUrl;
    }

    public String getProfileImageUrl() {
        return profileImageUrl;
    }

    public void setProfileImageUrl(String profileImageUrl) {
        this.profileImageUrl = profileImageUrl;
    }

    public int getPostNumber() {
        return postNumber;
    }

    public void setPostNumber(int postNumber) {
        this.postNumber = postNumber;
    }
}
