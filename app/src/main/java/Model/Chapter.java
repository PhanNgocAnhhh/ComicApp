package Model;

import java.io.Serializable;
import java.util.List;

public class Chapter  implements Serializable {
    public String name; //name
    public List<String> link;

    public Chapter() {
    }

    public Chapter(String name, List<String> link) {
        this.name = name;
        this.link = link;
    }

    public String getName() {

        return name;
    }

    public void setName(String name) {

        this.name = name;
    }

    public List<String> getLink() {

        return link;
    }

    public void setLink(List<String> link) {

        this.link = link;
    }
}
