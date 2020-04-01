package com.example.realtime_gps.fragment.Model;

public class Group {
    private String groupID;
    private String name;
    private String imageURL;
    private String joinCode;
    private String admin_id;
    private String join_code;



    public Group() {
    }

    public Group(String groupID, String name, String imageURL, String joinCode, String admin_id, String join_code) {
        this.groupID = groupID;
        this.name = name;
        this.imageURL = imageURL;
        this.joinCode = imageURL;
        this.admin_id = admin_id;
        this.join_code = join_code;
    }

    public String getGroupID() {
        return groupID;
    }

    public String getAdmin_id() {
        return admin_id;
    }

    public String getJoin_code() {
        return join_code;
    }

    public void setGroupID(String groupID) {
        this.groupID = groupID;
    }

    public void setAdmin_id(String admin_id) {
        this.admin_id = admin_id;
    }

    public void setJoin_code(String join_code) {
        this.join_code = join_code;
    }

    public Group(String name, String imageURL, String joinCode) {
        this.name = name;
        this.imageURL = imageURL;
        this.joinCode = joinCode;
    }
    public String getName() {
        return name;
    }

    public String getImageURL() {
        return imageURL;
    }

    public String getJoinCode() {
        return joinCode;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }

    public void setJoinCode(String joinCode) {
        this.joinCode = joinCode;
    }
    @Override
    public String toString() {
        return "Group{" +
                "groupID='" + groupID + '\'' +
                ", name='" + name + '\'' +
                ", imageURL='" + imageURL + '\'' +
                ", joinCode='" + joinCode + '\'' +
                ", admin_id='" + admin_id + '\'' +
                ", join_code='" + join_code + '\'' +
                '}';
    }

}
