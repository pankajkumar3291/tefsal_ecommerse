package com.tefsalkw.Models;

/**
 * Created by AC 101 on 25-10-2017.
 */

public class AddCartRecord {

        private String[] items;

        public String[] getItems ()
        {
            return items;
        }

        public void setItems (String[] items)
        {
            this.items = items;
        }

        @Override
        public String toString()
        {
            return "ClassPojo [items = "+items+"]";
        }
    }

