package com.tefsalkw.models;

import java.util.List;

public class Records {

    public List<SentMailModel> getMails() {
        return mails;
    }

    public void setMails(List<SentMailModel> mails) {
        this.mails = mails;
    }

    private List<SentMailModel> mails;
}
