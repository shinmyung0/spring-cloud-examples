package example.user.web;

import example.user.EntityToDtoConverter;
import example.user.datamodel.UserEntity;
import example.user.datamodel.UserRepository;
import example.user.exception.ResourceConflictException;
import example.user.exception.ResourceNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * Created by shiny on 4/25/17.
 */
@RestController
@RequestMapping("/user")
public class UserController {

    private UserRepository userRepository;

    private EntityToDtoConverter entityToDtoConverter;

    private static Logger logger = LoggerFactory.getLogger(UserController.class);

    @Autowired
    public UserController(UserRepository userRepository,
                          EntityToDtoConverter entityToDtoConverter) {
        this.userRepository = userRepository;
        this.entityToDtoConverter = entityToDtoConverter;
    }


    @GetMapping("/{id}")
    public User getUser(@PathVariable long id) {

        logger.info("Getting user " + id);

        UserEntity entity = userRepository.findOne(id);

        if (entity == null) {
            throw new ResourceNotFoundException();
        }


        return entityToDtoConverter.convertToUserFromEntity(entity);
    }

    @PostMapping
    public User registerUser(User user) {

        long uid = user.getUid();

        if (userRepository.exists(uid)) {
            throw new ResourceConflictException();
        }

        UserEntity entity = entityToDtoConverter.convertFromUserToEntity(user);

        UserEntity saved = userRepository.save(entity);


        return entityToDtoConverter.convertToUserFromEntity(saved);
    }

    @PutMapping
    public User updateUser(User user) {

        long uid = user.getUid();
        if (!userRepository.exists(uid)) {
            throw new ResourceNotFoundException();
        }


        UserEntity entity = entityToDtoConverter.convertFromUserToEntity(user);
        UserEntity saved = userRepository.save(entity);

        return entityToDtoConverter.convertToUserFromEntity(saved);
    }

    @DeleteMapping("/{id}")
    public void deleteUser(@PathVariable long id) {

        if (!userRepository.exists(id)) {
            throw new ResourceNotFoundException();
        }

        userRepository.delete(id);

    }


}
