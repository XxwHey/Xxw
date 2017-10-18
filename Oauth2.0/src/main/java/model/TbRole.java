package model;

import java.util.Date;

/**
 * Created by xiexw on 2017/9/5.
 */
public class TbRole {

    private int id;
    private String name;
    private String code;
    private Date updateTime;
    private TbUser updateUser;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public TbUser getUpdateUser() {
        return updateUser;
    }

    public void setUpdateUser(TbUser updateUser) {
        this.updateUser = updateUser;
    }
}
