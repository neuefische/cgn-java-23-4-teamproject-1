package de.neuefische.cgn234.team1.backend.service;


import de.neuefische.cgn234.team1.backend.model.User;
import de.neuefische.cgn234.team1.backend.model.dto.UserResponse;
import de.neuefische.cgn234.team1.backend.model.submodel.UserRequest;
import de.neuefische.cgn234.team1.backend.model.submodel.UserWorkout;
import de.neuefische.cgn234.team1.backend.repo.UserRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepo userRepo;

    public UserResponse createNewUser(String userName, String password) {
        if (userRepo.existsUserByUserName(userName)) {
            throw new IllegalArgumentException("User already exists");
        }
        userRepo.save(new User(userName, password, new ArrayList<>()));
        return new UserResponse(userName, new ArrayList<>());
    }

    public UserResponse getUser(String userName, String password) {
        User user = userRepo.findByUserName(userName).orElseThrow(() -> new IllegalArgumentException("User not found"));
        if (!user.password().equals(password)) {
            throw new IllegalArgumentException("Wrong password");
        }
        return new UserResponse(user.userName(), user.userWorkoutList());
    }

    public UserResponse addWorkoutToUser(UserRequest userRequest) {
        List<UserWorkout> userWorkoutList = userRepo.getWorkoutListByUserName(userRequest.userName());
        List<UserWorkout> updatedList = new ArrayList<>(userWorkoutList);
        for (UserWorkout workout : userRequest.userWorkoutList()) {
            int index = updatedList.indexOf(workout);
            if (index == -1) {
                updatedList.add(workout);
            } else {
                updatedList.add(index, workout);
            }
        }
        User user = userRepo.findByUserName(userRequest.userName()).get();
        User userToBeSafed = new User(user.userName(), user.password(), updatedList);
        userRepo.save(userToBeSafed);
        return new UserResponse(userToBeSafed.userName(), userToBeSafed.userWorkoutList());
    }

    public UserResponse deleteWorkoutFromUser(String userName, UserWorkout workout) {
        User user = userRepo.findByUserName(userName).get();
        List<UserWorkout> updatedList = new ArrayList<>(user.userWorkoutList());
        updatedList.remove(workout);
        userRepo.save(new User(user.userName(), user.password(), updatedList));
        return new UserResponse(user.userName(), updatedList);
    }

    public boolean deleteUser(String userName) {
        if (userRepo.existsUserByUserName(userName)) {
            return userRepo.deleteByUserName(userName);
        } else {
            throw new IllegalArgumentException("User not found");
        }

    }

    public boolean login(String userName, String password) {
        Optional<User> user = userRepo.findByUserName(userName);
        if (user.isPresent()) {
            return user.get().password().equals(password);
        }
        return false;
    }

    public boolean logout(String userName) {
        Optional<User> user = userRepo.findByUserName(userName);
        if (user.isPresent()) {
            return true;
        }
        return false;
    }
}
