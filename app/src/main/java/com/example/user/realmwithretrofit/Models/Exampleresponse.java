package com.example.user.realmwithretrofit.Models;

/**
 * Created by user on 2/7/2018.
 */

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import io.realm.RealmList;
import io.realm.RealmObject;

public class Exampleresponse extends RealmObject {

    @SerializedName("user_details_list")
    @Expose
    private RealmList<UserDetailsList> userDetailsList = null;

    @SerializedName("remarks")
    @Expose
    private String remarks;

    @SerializedName("status")
    @Expose
    private Boolean status;

    public RealmList<UserDetailsList> getUserDetailsList() {
        return userDetailsList;
    }

    public void setUserDetailsList(RealmList<UserDetailsList> userDetailsList) {
        this.userDetailsList = userDetailsList;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

}
