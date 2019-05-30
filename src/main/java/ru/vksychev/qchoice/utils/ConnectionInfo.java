package ru.vksychev.qchoice.utils;

public class ConnectionInfo {
    private String url;
    private String user;
    private String password;

    public ConnectionInfo(String url,String user,String password){
        this.password = password;
        this.url = url;
        this.user = user;
    }

    public String getUrl(){
        return url;
    }

    public String getPassword(){
        return password;
    }

    public String getUser(){
        return user;
    }

    @Override
    public String toString() {
        return url;
    }
}
