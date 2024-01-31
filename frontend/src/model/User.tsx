import {UserWorkout} from "./UserWorkout.tsx";

export type User = {
    userName: string,
    userWorkoutList: UserWorkout[] | null
}