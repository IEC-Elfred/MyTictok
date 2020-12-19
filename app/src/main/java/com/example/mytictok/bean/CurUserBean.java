package com.example.mytictok.bean;

public class CurUserBean {
    private VideoBean.UserBean userBean;

    public CurUserBean(VideoBean.UserBean userBean) {
        this.userBean = userBean;
    }

    public void setUserBean(VideoBean.UserBean userBean) {
        this.userBean = userBean;
    }

    public VideoBean.UserBean getUserBean() {
        return userBean;
    }
}
