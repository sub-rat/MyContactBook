package com.subratgyawali.iii.mycontact;

/**
 * Created by Subrat Gyawali on 11/30/2016.
 */

public interface DatabaseUpdatedListener {
    void setDatabaseSuccess(String name, String phone,String email);
    void setDatabaseError(String failureMessage);
}
