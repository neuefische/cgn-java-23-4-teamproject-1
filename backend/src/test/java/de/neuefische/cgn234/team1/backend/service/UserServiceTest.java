package de.neuefische.cgn234.team1.backend.service;

import de.neuefische.cgn234.team1.backend.model.User;
import de.neuefische.cgn234.team1.backend.model.dto.UserRequest;
import de.neuefische.cgn234.team1.backend.model.dto.UserResponse;
import de.neuefische.cgn234.team1.backend.model.submodel.UserWorkout;
import de.neuefische.cgn234.team1.backend.repo.UserRepo;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class UserServiceTest {

    private final UserRepo userRepo = mock(UserRepo.class);

    private UserService userService = new UserService(userRepo);


    @Test
    void getUser() {
        //ARRANGE
        String userName = "Test1";
        User expected = new User("Test1", new ArrayList<>());
        when(userRepo.findByUserName(expected.userName()))
                .thenReturn(Optional.of(
                        new User("Test1", new ArrayList<>())));

        //ACT
        User actual = userService.getUser(userName);

        //ASSERT
        assertEquals(actual, expected);
    }

    @Test
    void addWorkoutToUserTest() {
        //ARRANGE
        UserWorkout toBeAdded = new UserWorkout("test2", "test2",
                1, 1, 1, 1, 15);
        UserRequest userRequest = new UserRequest("test1", List.of(toBeAdded));


        UserResponse expected = new UserResponse("test1", List.of(new UserWorkout("test1", "test1",
                        1, 1, 1, 1, 15),
                new UserWorkout("test2", "test2", 1, 1, 1,
                        1, 15)));

        when(userRepo.getWorkoutListByUserName(userRequest.userName())).thenReturn(List.of(
                (new UserWorkout("test1", "test1",
                        1, 1, 1, 1, 15))));
        when(userRepo.findByUserName(userRequest.userName())).thenReturn(Optional.of(new User(userRequest.userName(),
                List.of(new UserWorkout("test1", "test1",
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
        User userDTO = new User("test1", List.of(toBeDeleted));
        when(userRepo.findByUserName(userName)).thenReturn(Optional.of(userDTO));

        //ACT
        UserResponse actual = userService.deleteWorkoutFromUser(userName, toBeDeleted);

        //ASSERT
        assertEquals(actual, expected);
    }

    @Test
    void deleteUserTest() {
        // ARRANGE
        String userName = "test1";
        User user = new User("test1", new ArrayList<>());
        Boolean userExists = true;
        when(userRepo.existsUserByUserName(userName)).thenReturn(userExists);
        doNothing().when(userRepo).deleteByUserName(userName);
        boolean expected = false;

        // ACT
        Boolean actual = userService.deleteUser(userName);

        // ASSERT
        assertEquals(expected, actual);

        verify(userRepo).deleteByUserName(userName);
        verify(userRepo, times(2)).existsUserByUserName(userName);
        verifyNoMoreInteractions(userRepo);
    }

}
