package vo;

import java.io.Serializable;

public class Program implements Serializable{

    // 唯一标识id,32位随机字母数字
    private String id;

    // 国家
    private String country;

    // 学校
    private String university;

    // 学院
    private String school;

    // 项目名称
    private String programName;

    // 项目主页
    private String homepage;

    // 地址
    private String location;

    // 项目申请咨询邮箱
    private String email;

    // 联系方式
    private String phoneNumber;

    // 学位
    private String degree;

    // 申请截止时间（奖学金）
    private String deadlineWithAid;

    // 申请截止时间（无奖学金）
    private String deadlineWithoutAid;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUniversity() {
        return university;
    }

    public void setUniversity(String university) {
        this.university = university;
    }

    public String getSchool() {
        return school;
    }

    public void setSchool(String school) {
        this.school = school;
    }

    public String getProgramName() {
        return programName;
    }

    public void setProgramName(String programName) {
        this.programName = programName;
    }

    public String getHomepage() {
        return homepage;
    }

    public void setHomepage(String homepage) {
        this.homepage = homepage;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getDegree() {
        return degree;
    }

    public void setDegree(String degree) {
        this.degree = degree;
    }

    public String getDeadlineWithAid() {
        return deadlineWithAid;
    }

    public void setDeadlineWithAid(String deadlineWithAid) {
        this.deadlineWithAid = deadlineWithAid;
    }

    public String getDeadlineWithoutAid() {
        return deadlineWithoutAid;
    }

    public void setDeadlineWithoutAid(String deadlineWithoutAid) {
        this.deadlineWithoutAid = deadlineWithoutAid;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }
}
