import axios from "axios";
import {useState} from "react";
import {useNavigate} from "react-router-dom";

type Userinfo = {
    values: string[]
}

export default function RegisterPage() {
    const [userName, setUserName] = useState<string>("");
    const [password, setPassword] = useState<string>("");
    const [showPassword, setShowPassword] = useState<boolean>(false)

    const navigate = useNavigate();

    const togglePasswordVisibility = () => {
        setShowPassword(!showPassword);
    }

    const user: Userinfo = {values: [userName, password]}

    const register = (event: React.FormEvent<HTMLFormElement>) => {
        event.preventDefault();

        axios.post("/register", user)
            .then(() => navigate("/login"))
            .catch(error => {
                console.error("Error setting up an account", error)
            });

        setUserName("");
        setPassword("");
    }

    return (
        <>
            <form onSubmit={register} className="WorkoutEdit-form">
                <h4 className={"RegisterForm-h3"}>Sign up</h4>
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
                <button type={"submit"} className="WorkoutEdit-save">Register</button>
            </form>
        </>
    )
}