import {UserWorkout} from "./UserWorkout.tsx";

export type User = {
    userName: string,
    password: string,
    userWorkoutList: UserWorkout[] | null
}