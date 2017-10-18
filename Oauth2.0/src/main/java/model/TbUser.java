package model;

import java.io.Serializable;
import java.util.Set;

/**
 * Created by xiexw on 2017/9/5.
 */
public class TbUser implements Serializable {

    private int id;
    private String username;
    private String password;
    private String name;
    private boolean isTP;
    private Set<TbRole> roles;

    public TbUser() {

    }

    public TbUser(String username, String password, String name, boolean isTP) {
        this.username = username;
        this.password = password;
        this.name = name;
        this.isTP = isTP;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isTP() {
        return isTP;
    }

    public void setTP(boolean TP) {
        isTP = TP;
    }

    public Set<TbRole> getRoles() {
        return roles;
    }

    public void setRoles(Set<TbRole> roles) {
        this.roles = roles;
    }
}
