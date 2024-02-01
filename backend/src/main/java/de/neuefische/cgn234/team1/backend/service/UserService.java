package de.neuefische.cgn234.team1.backend.service;


import de.neuefische.cgn234.team1.backend.model.User;
import de.neuefische.cgn234.team1.backend.model.dto.UserRequest;
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


    public User getUser(String userName) {
        Optional<User> user = userRepo.findByUserName(userName);
            if (user.isPresent()) {
                return user.get();
            } else {
                return createUser(userName);
            }
    }

    private User createUser(String userName) {
        User user = new User(userName, new ArrayList<>());
        userRepo.save(user);
        return user;
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
        User userToBeSafed = new User(user.userName(), updatedList);
        userRepo.save(userToBeSafed);
        return new UserResponse(userToBeSafed.userName(), userToBeSafed.userWorkoutList());
    }

    public UserResponse deleteWorkoutFromUser(String userName, UserWorkout workout) {
        User user = userRepo.findByUserName(userName).get();
        List<UserWorkout> updatedList = new ArrayList<>(user.userWorkoutList());
        updatedList.remove(workout);
        userRepo.save(new User(user.userName(), updatedList));
        return new UserResponse(user.userName(), updatedList);
    }

    public boolean deleteUser(String userName) {
        if (userRepo.existsUserByUserName(userName)) {
            userRepo.deleteByUserName(userName);
            return !userRepo.existsUserByUserName(userName);
        } else {
            throw new IllegalArgumentException("User not found");
        }

    }

    public void attachPhoto(String userName, String photoUrl, String workoutName) {
        Optional<User> user = userRepo.findByUserName(userName);
        if (user.isPresent()) {
            User presentUser = user.get();
            UserWorkout oldWorkout = presentUser.userWorkoutList().stream()
                    .filter(workout -> workout.workoutName().equals(workoutName)).findFirst().get();
            List<String> workoutPhotos = new ArrayList<>(oldWorkout.workoutPhotos());

            if (workoutPhotos == null) {
                workoutPhotos = new ArrayList<>();
            }
            workoutPhotos.addFirst(photoUrl); // Füge neues Foto der Liste hinzu hier muss getestet werden ob die Liste modifiable ist

            List<UserWorkout> userWorkoutList = presentUser.userWorkoutList(); // Hole die Liste der Workouts des Users


            UserWorkout newWorkout = oldWorkout.withWorkoutPhotos(workoutPhotos); // Erstelle ein neues Workout mit der neuen Liste der Fotos

            userWorkoutList.remove(oldWorkout); // Lösche das alte Workout aus der Liste

            userWorkoutList.add(newWorkout);// Füge das neue Workout der Liste hinzu

            User tempUser = presentUser.withUserWorkoutList(userWorkoutList);


            userRepo.save(tempUser); // Speichere den User mit der neuen Liste der Workouts ab und ersetze den Alten dabei
        } else {
            throw new IllegalArgumentException("User not found");
        }
    }
}
