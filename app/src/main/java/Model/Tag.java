package Model;

import java.io.Serializable;

public class Tag implements Serializable {
    private String tag;
    private int id;

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

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
