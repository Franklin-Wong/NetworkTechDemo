package com.integration.networktechdemo.bean;

import java.util.ArrayList;

/**
 * Created by Wongerfeng on 2019/9/4.
 */
public class MailBox {
    public int box_icon;
    public String box_title;
    public ArrayList<MailItem> mail_list;

    public MailBox() {
        this.box_icon = 0;
        this.box_title = "";
        this.mail_list = new ArrayList<MailItem>();
    }

    public MailBox(int box_icon, String box_title, ArrayList<MailItem> mail_list) {
        this.box_icon = box_icon;
        this.box_title = box_title;
        this.mail_list = mail_list;
    }

}
