package example.user;

import example.user.datamodel.UserEntity;
import example.user.web.User;
import org.springframework.stereotype.Component;

/**
 * Created by shiny on 4/25/17.
 */
@Component
public class EntityToDtoConverter {

    public User convertToUserFromEntity(UserEntity entity) {

        User user = new User();
        user.setUid(entity.getUid());
        user.setFirstName(entity.getFirstName());
        user.setLastName(entity.getLastName());
        user.setEmail(entity.getEmail());
        user.setAddress1(entity.getAddress1());
        user.setAddress2(entity.getAddress2());
        return user;
    }

    public UserEntity convertFromUserToEntity(User user) {

        UserEntity entity = new UserEntity();
        entity.setUid(user.getUid());
        entity.setFirstName(user.getFirstName());
        entity.setLastName(user.getLastName());
        entity.setEmail(user.getEmail());
        entity.setAddress1(user.getAddress1());
        entity.setAddress2(user.getAddress2());
        return entity;
    }

}
