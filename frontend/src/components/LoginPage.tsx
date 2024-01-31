import {useState} from "react";
import {useNavigate} from "react-router-dom";
import {User} from "../model/User.tsx";

type LoginPageProps = {
    getUser: () => void
}

export default function LoginPage({getUser}: LoginPageProps) {
    const [userName, setUserName] = useState<string>("");
    const [password, setPassword] = useState<string>("");
    const [showPassword, setShowPassword] = useState<boolean>(false);

    const navigate = useNavigate();

    const togglePasswordVisibility = () => {
        setShowPassword(!showPassword);
    };

    const user: User = {
        userName: userName,
        userWorkoutList: null
    }

    const login = (event: React.FormEvent<HTMLFormElement>) => {
        event.preventDefault();

        const host = window.location.host === "localhost:5173" ? "http://localhost:8080" : window.location.origin;

        window.open(host + '/oauth2/authorization/github', '_self');

        /*
        axios.post("/login", user)
            .then(() => navigate(`/users/${userName}`))
            .catch(() => {
                showLoginError()
            });

        setUserName("");
        setPassword("");*/
    };

    /* const showLoginError = () => {
         <p>We don't recognize that username or password. Try again!</p>
     }*/


    const redirectToLogin = () => {
        navigate("/register");
    }

    return (
        <form onSubmit={login} className="WorkoutEdit-form">
            <h5 className="WorkoutEdit-form-h5">User name</h5>
            <input
                value={user.userName}
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
