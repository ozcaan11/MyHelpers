package semiworld.org.myhelpers;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;

/**
 * Created on 17.06.2017.
 */
@Table(name = "TBL_User")
public class User extends Model {
    @Column(name = "_username")
    public String username;

    @Column(name = "_password")
    public String password;

    public User() {
    }

    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }

    @Override public String toString() {
        return String.valueOf(getId() +"-"+ username + " " + password);
    }
}
