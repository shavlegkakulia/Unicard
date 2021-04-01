package ge.unicard.pos.networking.messaging.get_device_info;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class TypeFunctionBtn {

    @SerializedName("ResourceDescription")
    @Expose
    private String resourceDescription;
    @SerializedName("Resourcename")
    @Expose
    private String resourcename;

    public String getResourceDescription() {
        return resourceDescription;
    }

    public void setResourceDescription(String resourceDescription) {
        this.resourceDescription = resourceDescription;
    }

    public String getResourcename() {
        return resourcename;
    }

    public void setResourcename(String resourcename) {
        this.resourcename = resourcename;
    }

}
