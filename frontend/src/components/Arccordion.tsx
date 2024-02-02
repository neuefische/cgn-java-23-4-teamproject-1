import Accordion from '@mui/material/Accordion';
import AccordionSummary from '@mui/material/AccordionSummary';
import AccordionDetails from '@mui/material/AccordionDetails';
import Typography from '@mui/material/Typography';
import ArrowDownwardIcon from '@mui/icons-material/ArrowDownward';
import ArrowDropDownIcon from '@mui/icons-material/ArrowDropDown';
import {UserWorkout} from "../model/UserWorkout.tsx";

type ArrowDownwardIconProps = {
    workout: UserWorkout;
}


export default function AccordionExpandIcon(props: ArrowDownwardIconProps) {
    const workout: UserWorkout = props.workout;

    return (
        <div>
            <Accordion>
                <AccordionSummary
                    expandIcon={<ArrowDownwardIcon/>}
                    aria-controls="panel1-content"
                    id="panel1-header"
                >
                    <Typography>{workout.workoutName}</Typography>
                </AccordionSummary>
                <AccordionDetails>
                    <Typography>
                        {workout.workoutDescription}
                    </Typography>
                </AccordionDetails>
            </Accordion>
            <Accordion>
                <AccordionSummary
                    expandIcon={<ArrowDropDownIcon/>}
                    aria-controls="panel2-content"
                    id="panel2-header"
                >
                    <Typography>Details</Typography>
                </AccordionSummary>
                <AccordionDetails>
                    <Typography>
                        <p>Repeats: <button value={workout.workoutRepeat}/></p>
                        <p>Sets: <button value={workout.workoutSet}/></p>
                        <p>Weight: <button value={workout.workoutWeight}/></p>
                        <p>Time: <button value={workout.workoutTime}/></p>
                        <p>Break: <button value={workout.workoutBreak}/></p>
                    </Typography>
                </AccordionDetails>
            </Accordion>
        </div>
    );
}