package com.hmarka.kloop1996.transferelegance.model;

/**
 * Created by kloop1996 on 06.06.2016.
 */
public class User {
    private String name;
    private String telephone;

    public User() {
    }

    public User(String name, String telephone) {
        this.name = name;
        this.telephone = telephone;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        User user = (User) o;

        if (name != null ? !name.equals(user.name) : user.name != null) return false;
        return telephone != null ? telephone.equals(user.telephone) : user.telephone == null;

    }

    @Override
    public int hashCode() {
        int result = name != null ? name.hashCode() : 0;
        result = 31 * result + (telephone != null ? telephone.hashCode() : 0);
        return result;
    }
}
