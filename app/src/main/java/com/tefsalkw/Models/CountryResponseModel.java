package com.tefsalkw.Models;

import java.util.ArrayList;

/**
 * Created by Rituparna Khadka on 11/29/2017.
 */


    public class CountryResponseModel
    {
        private String message;

        private ArrayList<CountryRecordModel> record;

        private String status;

        public String getMessage ()
        {
            return message;
        }

        public void setMessage (String message)
        {
            this.message = message;
        }

        public ArrayList<CountryRecordModel> getRecord ()
        {
            return record;
        }

        public void setRecord (ArrayList<CountryRecordModel> record)
        {
            this.record = record;
        }

        public String getStatus ()
        {
            return status;
        }

        public void setStatus (String status)
        {
            this.status = status;
        }

        @Override
        public String toString()
        {
            return "ClassPojo [message = "+message+", record = "+record+", status = "+status+"]";
        }
    }

