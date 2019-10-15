
package com.example.neardeal.ApiResponse;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class DealResponse {

    @SerializedName("success")
    @Expose
    private Boolean success;
    @SerializedName("deal")
    @Expose
    private List<Deal> deal = null;

    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }

    public List<Deal> getDeal() {
        return deal;
    }

    public void setDeal(List<Deal> deal) {
        this.deal = deal;
    }

}
