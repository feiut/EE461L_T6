package guestbook;

import java.util.Date;

import com.google.appengine.api.users.User;
import com.googlecode.objectify.Key;
import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;
import com.googlecode.objectify.annotation.Index;
import com.googlecode.objectify.annotation.Parent;


@Entity
public class UserMail {
	@Parent Key<UserCollection> usercollection;
	@Id Long id;
	@Index User user;
	@Index String email;
	 @Index Date date;

	private UserMail() {}
	
	public UserMail(User user, String email) {
        this.user = user;
		this.email = email;
        String userCollection = "userCollection";
        this.usercollection = Key.create(UserCollection.class, userCollection);
    }
    public User getUser() {
        return user;
    }
    public String getEmail() {
        return email;
    }
    public String getNickname() {
        return user.getNickname();
    }
    public String getId() {
        return user.getUserId();
    }

}

