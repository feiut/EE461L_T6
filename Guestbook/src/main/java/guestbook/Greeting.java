package guestbook;
import java.util.Date;

import com.google.appengine.api.users.User;
import com.googlecode.objectify.Key;
import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;
import com.googlecode.objectify.annotation.Index;
import com.googlecode.objectify.annotation.Parent;


@Entity
public class Greeting implements Comparable<Greeting> {

	@Parent Key<Guestbook> guestbookName;
    @Id Long id;
    @Index User user;
    @Index String content;
    @Index String nickname;
    @Index String title;
    @Index Date date;
    private Greeting() {}
    public Greeting(User user, String nickname,String content, String title, String guestbookName) {
        this.user = user;
        this.nickname = nickname;
        this.content = content;
        this.title = title;
        this.guestbookName = Key.create(Guestbook.class, guestbookName);
        date = new Date();
    }
    public User getUser() {
        return user;
    }

    public String getContent() {
        return content;
    }
    public String getTitle() {
        return title;
    }

    @Override
    public int compareTo(Greeting other) {
        if (date.after(other.date)) {
            return 1;
        } else if (date.before(other.date)) {
            return -1;
        }
        return 0;
     }
}