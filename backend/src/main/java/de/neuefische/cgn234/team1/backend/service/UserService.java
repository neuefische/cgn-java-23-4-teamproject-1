package de.neuefische.cgn234.team1.backend.service;


import de.neuefische.cgn234.team1.backend.model.User;
import de.neuefische.cgn234.team1.backend.model.dto.UserResponse;
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
        if (userRepo.existsByUserName(userName)) {
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
        List<UserWorkout> updatedList = userWorkoutList.stream().map(userWorkout -> {
            for (UserWorkout workout : userRequest.userWorkoutList()) {
                if (userWorkout.workoutName().equals(workout.workoutName())) {
                    return workout;
                }

            }
            return userWorkout;
        }).toList();
        User user = userRepo.findByUserName(userRequest.userName()).get();
        user.userWorkoutList().clear();
        user.userWorkoutList().addAll(updatedList);
        userRepo.save(user);
        return new UserResponse(user.userName(), user.userWorkoutList());
    }

    public UserResponse deleteWorkoutFromUser(String userName, UserWorkout workout) {
        User user = userRepo.findByUserName(userName).get();
        user.userWorkoutList().remove(workout);
        userRepo.save(user);
        return new UserResponse(user.userName(), user.userWorkoutList());
    }

    public boolean deleteUser(String userName) {
        if (userRepo.existsByUserName(userName)) {
            userRepo.deleteByUserName(userName);
            return true;
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
