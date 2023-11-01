package Model;

import java.io.Serializable;
import java.util.List;

public class Comic  implements Serializable {

    public String id;
    public String name;
    public String image;
    public String category;
    public String description;
    public List<Chapter> chapter; //chapter cho nay thua chu s

    public Comic() {
    }

    public Comic(String id, String name, String image, String category, String description, List<Chapter> chapters) {
        this.id = id;
        this.name = name;
        this.image = image;
        this.category = category;
        this.description = description;
        this.chapter = chapters;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<Chapter> getChapters() {
        return chapter;
    }

    public void setChapters(List<Chapter> chapters) {
        this.chapter = chapters;
    }
}
