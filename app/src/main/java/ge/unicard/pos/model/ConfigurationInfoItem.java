package ge.unicard.pos.model;

import java.io.Serializable;

/**
 * Created by Akaki on 11/5/18.
 */
public class ConfigurationInfoItem implements Serializable {

    private String label;
    private String value;

    public ConfigurationInfoItem(String label, String value) {
        this.label = label;
        this.value = value;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
