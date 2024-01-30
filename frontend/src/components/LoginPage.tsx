import {useState} from "react";
import {useNavigate} from "react-router-dom";
import axios from "axios";
import {User} from "../model/User.tsx";

export default function LoginPage() {
    const [userName, setUserName] = useState<string>("");
    const [password, setPassword] = useState<string>("");
    const [showPassword, setShowPassword] = useState<boolean>(false);

    const navigate = useNavigate();

    const togglePasswordVisibility = () => {
        setShowPassword(!showPassword);
    };

    const user: User = {
        userName: userName,
        password: password,
        userWorkoutList: null
    }

    const login = (event: React.FormEvent<HTMLFormElement>) => {
        event.preventDefault();

        axios.post("/login", user)
            .then(() => navigate(`/users/${userName}`))
            .catch(error => {
                console.error("Error updating workout", error);
            });

        setUserName("");
        setPassword("");
    };

    const redirectToLogin = () => {
        navigate("/register");
    }

    return (
        <form onSubmit={login} className="WorkoutEdit-form">
            <h5 className="WorkoutEdit-form-h5">User name</h5>
            <input
                value={userName}
                onChange={(e) => setUserName(e.target.value)}
                className="WorkoutEdit-form-input"
            />

            <h5 className="WorkoutEdit-form-h5">Password:</h5>

            <div className={"hideButtonDiv"}>
                <input
                    type={showPassword ? "text" : "password"}
                    value={password}
                    onChange={(e) => setPassword(e.target.value)}
                    className="HidePasswordInput"
                />
                <button className={"hideButton"} type="button" onClick={togglePasswordVisibility}>
                    <img className={"hideImg"} src={"public/eye.png"}/>
                </button>
            </div>
            <button className="WorkoutEdit-save" type="submit">Login</button>
            <button className="WorkoutEdit-save" onClick={redirectToLogin}>Register</button>
        </form>
    );
}
