package sample;
import java.util.Iterator;
import java.util.ArrayList;
import java.util.List;

/*
 * Stores an RSS feed
 */
public class Feed implements Iterable<FeedMessage> {

    final String title;
    final String link;
    final String description;
    final String media;
    public int length;

    final ArrayList<FeedMessage> entries = new ArrayList<>();

    public Feed(String title, String link, String description, String media) {
        this.title = title;
        this.link = link;
        this.description = description;
        this.media = media;
    }

    @Override
    public Iterator<FeedMessage> iterator() {
        return entries.iterator();
    }

    public List<FeedMessage> getMessages() {
        return entries;
    }

    public void setLength( int num) {
        this.length = num;
    }

    @Override
    public String toString() {
        return "Feed [title=" + title + ", description=" + description
                + ", link=" + link + ", media=" + media + "]";
    }

}