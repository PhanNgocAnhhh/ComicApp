package Model;

import java.util.List;

public class Chapter {
    public String Name;
    public List<String> link;

    public Chapter() {
    }

    public Chapter(String name, List<String> link) {
        this.Name = name;
        this.link = link;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        this.Name = name;
    }

    public List<String> getLink() {
        return link;
    }

    public void setLink(List<String> link) {
        this.link = link;
    }
}
