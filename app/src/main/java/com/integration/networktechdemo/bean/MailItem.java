package com.integration.networktechdemo.bean;

/**
 * Created by Wongerfeng on 2019/9/4.
 */
public class MailItem {
    public String mail_title;
    public String mail_date;

    public MailItem() {
        this.mail_title = "";
        this.mail_date = "";
    }

    public MailItem(String mail_title, String mail_date) {
        this.mail_title = mail_title;
        this.mail_date = mail_date;
    }

}