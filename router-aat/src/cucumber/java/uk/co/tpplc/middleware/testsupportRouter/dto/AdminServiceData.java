package com.mitra.middleware.testsupportRouter.dto;

public class AdminServiceData {

    private String backEndUrl;
    private String gregPath;
    private String userName;
    private String password;
    private String host;
    private String content;

    public String getBackEndUrl() {
        return backEndUrl;
    }

    public void setBackEndUrl(String backEndUrl) {
        this.backEndUrl = backEndUrl;
    }

    public String getGregPath() {
        return gregPath;
    }

    public void setGregPath(String gregPath) {
        this.gregPath = gregPath;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

}