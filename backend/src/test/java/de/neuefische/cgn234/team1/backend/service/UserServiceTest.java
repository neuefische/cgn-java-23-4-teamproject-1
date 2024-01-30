package de.neuefische.cgn234.team1.backend.service;

import de.neuefische.cgn234.team1.backend.model.User;
import de.neuefische.cgn234.team1.backend.model.dto.UserResponse;
import de.neuefische.cgn234.team1.backend.model.submodel.UserRequest;
import de.neuefische.cgn234.team1.backend.model.submodel.UserWorkout;
import de.neuefische.cgn234.team1.backend.repo.UserRepo;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class UserServiceTest {

    private final UserRepo userRepo = mock(UserRepo.class);

    private UserService userService = new UserService(userRepo);

    @Test
    void createUser() {
        //ARRANGE
        String userName = "Test1";
        String password = "1234";
        UserResponse expected = new UserResponse("Test1", new ArrayList<>());
        Boolean create = false;
        when(userRepo.existsByUserName(userName)).thenReturn(create);


        //ACT
        UserResponse actual = userService.createNewUser(userName, password);

        //ASSERT
        assertEquals(actual, expected);
    }

    @Test
    void getUser() {
        //ARRANGE
        String userName = "Test1";
        String password = "1234";
        UserResponse expected = new UserResponse("Test1", new ArrayList<>());
        when(userRepo.findByUserName(expected.userName()))
                .thenReturn(Optional.of(
                        new User("Test1", "1234", new ArrayList<>())));

        //ACT
        UserResponse actual = userService.getUser(userName, password);

        //ASSERT
        assertEquals(actual, expected);
    }

    @Test
    void addWorkoutToUserTest() {
        //ARRANGE
        UserWorkout toBeAdded = new UserWorkout("test2", "test2",
                1, 1, 1, 1, 15);
        UserRequest userRequest = new UserRequest("test1", "1234", List.of(toBeAdded));


        UserResponse expected = new UserResponse("test1", List.of(new UserWorkout("test1", "test1",
                        1, 1, 1, 1, 15),
                new UserWorkout("test2", "test2", 1, 1, 1,
                        1, 15)));

        when(userRepo.getWorkoutListByUserName(userRequest.userName())).thenReturn(List.of(
                (new UserWorkout("test1", "test1",
                        1, 1, 1, 1, 15))));
        when(userRepo.findByUserName(userRequest.userName())).thenReturn(Optional.of(new User(userRequest.userName(),
                userRequest.password(), List.of(new UserWorkout("test1", "test1",
                1, 1, 1, 1, 15)))));
        //ACT
        UserResponse actual = userService.addWorkoutToUser(userRequest);

        //ASSERT
        assertEquals(expected, actual);
    }

    @Test
    void deleteWorkoutFromUserTest() {
        //ARRANGE
        String userName = "Test1";
        UserWorkout toBeDeleted = new UserWorkout("test1", "test1",
                1, 1, 1, 1, 1);
        UserResponse expected = new UserResponse("test1", new ArrayList<>());
        User userDTO = new User("test1", "1234", List.of(toBeDeleted));
        when(userRepo.findByUserName(userName)).thenReturn(Optional.of(userDTO));

        //ACT
        UserResponse actual = userService.deleteWorkoutFromUser(userName, toBeDeleted);

        //ASSERT
        assertEquals(actual, expected);
    }

    @Test
    void deleteUserTest() {
        //ARRANGE
        String userName = "test1";
        User user = new User("test1", "123", new ArrayList<>());
        Boolean expected = true;
        when(userRepo.existsUserByUserName(userName)).thenReturn(expected);

        when(userRepo.deleteByUserName(userName)).thenReturn(expected);

        //ACT
        Boolean actual = userService.deleteUser(userName);

        //ASSERT
        assertEquals(expected, actual);
    }


    @Test
    void loginTest() {
        //ARRANGE
        String userName = "test1";
        String password = "1234";
        User user = new User("test1", "1234", new ArrayList<>());
        Boolean expected = true;

        when(userRepo.findByUserName(userName)).thenReturn(Optional.of(user));
        //ACT
        Boolean actual = userService.login(userName, password);

        //ASSERT
        assertEquals(expected, actual);
    }

    @Test
    void logoutTest() {
        //ARRANGE
        String userName = "test1";
        User user = new User("test1", "1234", new ArrayList<>());
        Boolean expected = true;

        when(userRepo.findByUserName(userName)).thenReturn(Optional.of(user));

        //ACT
        Boolean actual = userService.logout(userName);

        //ASSERT
        assertEquals(expected, actual);

    }

}
