package guestbook;

import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;

@Entity
public class UserCollection {
    @Id long id;
    String name;

    public UserCollection(String name) {
        this.name = name;
    }
}