package com.morebooks.morebooks.daos;

import com.morebooks.morebooks.daos.mappers.UserRowMapper;
import com.morebooks.morebooks.entities.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Component;

@Component
public class UsersDao {

    private static final Logger LOG = LoggerFactory.getLogger(UsersDao.class);

    @Value("${daos.usersdao.getuserbyid}")
    private String getuserbyid;

    @Value("${daos.usersdao.getuserbyusernameoremail}")
    private String getuserbyusernameoremail;

    @Value("${daos.usersdao.insertuser}")
    private String insertuser;

    @Autowired
    NamedParameterJdbcTemplate namedJdbcTemplate;

    public User getUserById(String userId) {
        User user = null;
        try {
            SqlParameterSource namedParameters = new MapSqlParameterSource().addValue("id", userId);
            user = this.namedJdbcTemplate.queryForObject(getuserbyid, namedParameters, new UserRowMapper());
        } catch (Exception ex) {
            LOG.error(ex.getMessage());
        }

        return user;
    }

    public User getUserByUsernameOrEmail(String usernameOrEmail) {
        User user = null;

        SqlParameterSource namedParameters = new MapSqlParameterSource().addValue("usernameoremail", usernameOrEmail);
        user = this.namedJdbcTemplate.queryForObject(getuserbyusernameoremail, namedParameters, new UserRowMapper());

        return user;
    }

    public boolean insertUser(User user) {
        try {
            SqlParameterSource namedParameters = mapUserToNamedParameters(user);
            int rowsUpdated = this.namedJdbcTemplate.update(insertuser, namedParameters);
            if(rowsUpdated > 0) {
                return true;
            }
        } catch (Exception ex) {
            LOG.error(ex.getMessage());
        }

        return false;
    }

    public MapSqlParameterSource mapUserToNamedParameters(User user) {
        MapSqlParameterSource map = new MapSqlParameterSource();
        map.addValue("id", user.getId());
        map.addValue("username", user.getUsername());
        map.addValue("email", user.getEmail());
        map.addValue("passwd", user.getPasswd());
        map.addValue("firstname", user.getFirstName());
        map.addValue("lastname", user.getLastName());

        return map;
    }

}
