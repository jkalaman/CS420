package sample;

public class FeedMessage {
    String title;
    String description;
    String link;
    String media;
    int id;

    public void setId( int num) {
        this.id = num;
    }
    public int getId( ) {
        return this.id ;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getMedia() {
        return media;
    }

    public void setMedia(String media) {
        this.media = media;
    }
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    @Override
    public String toString() {
        return "FeedMessage [title=" + title + ", description=" + description
                + ", link=" + link + ", media=" + media + "]";
    }

}