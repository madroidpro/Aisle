
package com.madroid.aisle.profiles.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Invites {

    @SerializedName("profiles")
    @Expose
    private List<MainProfile> profiles = null;
    @SerializedName("totalPages")
    @Expose
    private Integer totalPages;
    @SerializedName("pending_invitations_count")
    @Expose
    private Integer pendingInvitationsCount;

    public List<MainProfile> getProfiles() {
        return profiles;
    }

    public void setProfiles(List<MainProfile> profiles) {
        this.profiles = profiles;
    }

    public Integer getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(Integer totalPages) {
        this.totalPages = totalPages;
    }

    public Integer getPendingInvitationsCount() {
        return pendingInvitationsCount;
    }

    public void setPendingInvitationsCount(Integer pendingInvitationsCount) {
        this.pendingInvitationsCount = pendingInvitationsCount;
    }

}
