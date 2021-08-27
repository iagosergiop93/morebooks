package com.morebooks.morebooks.domain.dtos;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter @NoArgsConstructor
public class Credentials {

    private String username;
    private String passwd;

    public Credentials(String username, String passwd) {
        super();
        this.username = username;
        this.passwd = passwd;
    }

    @Override
    public String toString() {
        return "Credentials [username=" + username + ", passwd=" + passwd + "]";
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((passwd == null) ? 0 : passwd.hashCode());
        result = prime * result + ((username == null) ? 0 : username.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Credentials other = (Credentials) obj;
        if (passwd == null) {
            if (other.passwd != null)
                return false;
        } else if (!passwd.equals(other.passwd))
            return false;
        if (username == null) {
            if (other.username != null)
                return false;
        } else if (!username.equals(other.username))
            return false;
        return true;
    }

}
