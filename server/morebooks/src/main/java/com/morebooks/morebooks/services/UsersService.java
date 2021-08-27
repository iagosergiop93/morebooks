package com.morebooks.morebooks.services;

import com.morebooks.morebooks.daos.UsersDao;
import com.morebooks.morebooks.domain.dtos.Credentials;
import com.morebooks.morebooks.domain.dtos.Principal;
import com.morebooks.morebooks.entities.User;
import com.morebooks.morebooks.utils.EncryptUtil;
import com.morebooks.morebooks.utils.JwtWrapper;
import com.morebooks.morebooks.utils.UuidGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Service
public class UsersService {

    @Autowired
    private UsersDao usersDao;

    @Autowired
    private JwtWrapper jwtWrapper;

    public Principal accountLoad(String authHeader) {
        if(authHeader == null) return null;
        String jwt = authHeader.replace("Bearer ", "");
        Principal principal = null;
        try {
            jwtWrapper.verifyJwt(jwt);
            String username = jwtWrapper.getJwtUsername(jwt);
            if(username == null || username.isBlank()) return null;
            User user = usersDao.getUserByUsernameOrEmail(username);
            principal = createPrincipal(user);
        } catch (Exception ex) {
            return null;
        }

        return principal;
    }

    public Principal login(Credentials credentials) throws Exception {

        // find by username or email
        User user = usersDao.getUserByUsernameOrEmail(credentials.getUsername());

        if(user == null) {
            throw new Exception("Invalid credentials");
        }

        // validate password
        boolean matchedPasswd = EncryptUtil.checkHash(credentials.getPasswd(), user.getPasswd());

        if(!matchedPasswd) throw new Exception("Invalid credentials");

        Principal principal = createPrincipal(user);

        return principal;
    }

    public Principal createUser(User user) throws Exception {

        // hash password
        if(user.getPasswd() == null || user.getPasswd().isBlank()) throw new Exception("Invalid password");
        String hashedPasswd = EncryptUtil.createHash(user.getPasswd());
        user.setPasswd(hashedPasswd);

        user.setId(UuidGenerator.generateUuid());

        // insert user
        boolean inserted = usersDao.insertUser(user);
        if(!inserted) {
            throw new Exception("Cannot add this user");
        }

        Principal principal = createPrincipal(user);

        return principal;
    }

    private Principal createPrincipal(User user) {

        Principal principal = new Principal();
        principal.setId(user.getId());
        principal.setEmail(user.getEmail());
        principal.setUsername(user.getUsername());
        principal.setFirstName(user.getFirstName());
        principal.setLastName(user.getLastName());

        return principal;
    }

    public String createJwt(Principal principal) {
        String jwt = jwtWrapper.createJwt(principal);
        return jwt;
    }

}
