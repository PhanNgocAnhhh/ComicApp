package Model;

import java.io.Serializable;

public class Tag implements Serializable {
    private String tag;

    public Tag() {
    }

    public Tag(String tag) {

        this.tag = tag;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {

        this.tag = tag;
    }
}
