package example.user;

import example.user.datamodel.UserEntity;
import example.user.web.User;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.Matchers.samePropertyValuesAs;
import static org.junit.Assert.assertThat;

/**
 * Created by shiny on 4/25/17.
 */
public class EntityToDtoConverterTest {

    private EntityToDtoConverter entityToDtoConverter;

    private long uid = 1L;
    private String firstName = "first";
    private String lastName = "last";
    private String email = "test@test.com";
    private String address1 = "address1";
    private String address2 = "address2";


    private User testUser;
    private UserEntity testEntity;

    @Before
    public void setUp() {

        entityToDtoConverter = new EntityToDtoConverter();

        testUser = new User();
        testUser.setUid(uid);
        testUser.setFirstName(firstName);
        testUser.setLastName(lastName);
        testUser.setAddress1(address1);
        testUser.setAddress2(address2);
        testUser.setEmail(email);

        testEntity = new UserEntity();
        testEntity.setUid(uid);
        testEntity.setFirstName(firstName);
        testEntity.setLastName(lastName);
        testEntity.setAddress1(address1);
        testEntity.setAddress2(address2);
        testEntity.setEmail(email);

    }

    @Test
    public void userToEntity() {
        UserEntity result = entityToDtoConverter.convertFromUserToEntity(testUser);
        assertThat(result, samePropertyValuesAs(testEntity));
    }

    @Test
    public void entityToUser() {
        User result = entityToDtoConverter.convertToUserFromEntity(testEntity);
        assertThat(result, samePropertyValuesAs(testUser));
    }
}
