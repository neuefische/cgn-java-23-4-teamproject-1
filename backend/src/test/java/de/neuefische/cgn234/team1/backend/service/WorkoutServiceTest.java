package de.neuefische.cgn234.team1.backend.service;

import de.neuefische.cgn234.team1.backend.model.Workout;
import de.neuefische.cgn234.team1.backend.repo.WorkoutRepository;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class WorkoutServiceTest {


    private final WorkoutRepository workoutRepository = mock(WorkoutRepository.class);
    private WorkoutService workoutService = new WorkoutService(workoutRepository);


    @Test
    void getAll() {
        //ARRANGE
        List<Workout> expected = List.of( new Workout("Beinpresse","Bewege an der Beinpresse ein paar gewichte"), new Workout("test1" ,"test1"));
        when(workoutRepository.findAll()).thenReturn(expected);

        //ACT
        List<Workout> actual = workoutService.getAll();

        //ASSERT

        assertEquals(expected,actual);
        verify(workoutRepository).findAll();


    }
}