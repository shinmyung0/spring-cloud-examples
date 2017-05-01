package example.user;

import example.user.datamodel.UserEntity;
import example.user.datamodel.UserRepository;
import example.user.exception.ResourceConflictException;
import example.user.exception.ResourceNotFoundException;
import example.user.web.User;
import example.user.web.UserController;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyLong;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Created by shiny on 4/25/17.
 */
public class UserControllerTest {

    @Rule
    public MockitoRule mockitoRule = MockitoJUnit.rule();

    @Mock
    private UserRepository mockUserRepository;

    @Mock
    private EntityToDtoConverter mockConverter;

    private UserController userController;

    private final long validUserId = 1L;
    private final long nonExistantUser = 99L;

    @Before
    public void setUp() {
        userController = new UserController(mockUserRepository, mockConverter);

        when(mockUserRepository.findOne(validUserId)).thenReturn(new UserEntity());
        when(mockUserRepository.findOne(nonExistantUser)).thenReturn(null);

        when(mockUserRepository.exists(validUserId)).thenReturn(true);
        when(mockUserRepository.exists(nonExistantUser)).thenReturn(false);
        when(mockUserRepository.exists(0L)).thenReturn(false);

    }


    @Test
    public void gettingUserShouldCallRepositoryFindOneAndConvert() {

        User user = userController.getUser(validUserId);
        verify(mockUserRepository).findOne(validUserId);

        verify(mockConverter).convertToUserFromEntity(any(UserEntity.class));

    }

    @Test(expected = ResourceNotFoundException.class)
    public void gettingNonExistantUserShouldThrowException() {
        User user = userController.getUser(nonExistantUser);
    }

    @Test
    public void registeringUserWithoutIdShouldCheckIfUserIdConflictsAndCallSave() {

        User newUser = new User();
        User user = userController.registerUser(newUser);

        verify(mockUserRepository).exists(anyLong());
        verify(mockConverter).convertToUserFromEntity(any(UserEntity.class));
        verify(mockUserRepository).save(any(UserEntity.class));
    }

    @Test(expected = ResourceConflictException.class)
    public void registeringExistingUserShouldThrowResourceConflictException() {
        User newUser = new User();
        newUser.setUid(validUserId);
        User registered = userController.registerUser(newUser);

    }

    @Test
    public void updatingUserShouldCheckRepoSave() {
        User newUser = new User();
        newUser.setUid(validUserId);
        User updated = userController.updateUser(newUser);

        verify(mockUserRepository).exists(validUserId);
        verify(mockConverter).convertFromUserToEntity(any(User.class));
        verify(mockUserRepository).save(any(UserEntity.class));

    }

    @Test(expected = ResourceNotFoundException.class)
    public void updatingNonExistantUserShouldThrowException() {
        User newUser = new User();
        newUser.setUid(nonExistantUser);
        User updated = userController.updateUser(newUser);

    }

    @Test(expected = ResourceNotFoundException.class)
    public void deletingNonExistantUserShouldThrowException() {
        userController.deleteUser(nonExistantUser);
    }

    public void deletingValidUserShouldCheckRepoThenDelete() {
        userController.deleteUser(validUserId);

        verify(mockUserRepository).exists(validUserId);
        verify(mockUserRepository).delete(validUserId);
    }


}
