package de.neuefische.cgn234.team1.backend.service;

import de.neuefische.cgn234.team1.backend.model.Workout;
import de.neuefische.cgn234.team1.backend.model.dto.RequestWorkout;
import de.neuefische.cgn234.team1.backend.repo.WorkoutRepository;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

class WorkoutServiceTest {
    private final WorkoutRepository workoutRepository = mock(WorkoutRepository.class);
    private WorkoutService workoutService = new WorkoutService(workoutRepository);private final UUID idService = mock(UUID.class);

    @Test
    void getAll() {
        //ARRANGE
        List<Workout> expected = List.of(new Workout("Beinpresse", "Bewege an der Beinpresse ein paar gewichte"), new Workout("test1", "test1"));
        when(workoutRepository.findAll()).thenReturn(expected);

        //ACT
        List<Workout> actual = workoutService.getAll();

        //ASSERT
        assertEquals(expected, actual);
        verify(workoutRepository).findAll();
    }

    @Test
    void getWorkoutById_whenWorkoutExists_ReturnWorkout() {
        // ARRANGE
        Workout expected = new Workout("1", "Beinpresse", "Bewege an der Beinpresse ein paar gewichte");
        when(workoutRepository.findById("1")).thenReturn(Optional.of(expected));

        // ACT
        Workout actual = workoutService.getById("1");

        // ASSERT
        assertEquals(expected, actual);
        verify(workoutRepository).findById("1");
        verifyNoMoreInteractions(workoutRepository);
    }

    @Test
    void getWorkoutById_whenWorkoutNotExists_throwException() {
        // ACT & ASSERT
        assertThrows(NoSuchElementException.class, () ->
                workoutService.getById("1"));

        // ASSERT
        verify(workoutRepository).findById("1");
        verifyNoMoreInteractions(workoutRepository);
    }

    @Test
    void updateWorkout_whenWorkoutUpdatesReturnsUpdatedWorkout() {
        // ARRANGE
        workoutService = mock(WorkoutService.class);
        String workoutId = "1";
        Workout workoutToUpdate = new Workout(workoutId, "test", "test");

        when(workoutService.editWorkout(workoutToUpdate)).thenReturn(workoutToUpdate);

        // ACT
        Workout updatedWorkout = workoutService.editWorkout(workoutToUpdate);

        // ASSERT
        assertEquals(workoutToUpdate, updatedWorkout);
        verify(workoutService).editWorkout(workoutToUpdate);
    }


    @Test
    void createWorkoutTest() {
        //ARRANGE
        Workout workout = new Workout("1", "test1", "test1");
        when(workoutRepository.findByWorkoutName("test1")).thenReturn(Optional.ofNullable(null));

        //ACT
        Workout expect = workoutService.createWorkout(new RequestWorkout("test1", "test1"));


        //ASSERT
        verify(workoutRepository, times(1)).save(expect);
    }

    @Test
    void createWorkout_whenWorkoutAlreadyExist_ThenException() {
        //ARRANGE
        Workout workout = new Workout("1", "test1", "test1");

        //ACT
        when(workoutRepository.findByWorkoutName("test1")).thenReturn(Optional.ofNullable(workout));

        //ASSERT
        assertThrows(IllegalArgumentException.class, () ->
                workoutService.createWorkout(new RequestWorkout("test1", "test1")));
        }
}