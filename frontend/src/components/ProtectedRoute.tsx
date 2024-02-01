import {User} from "../model/User.tsx";
import {Navigate, Outlet} from "react-router-dom";

type ProtectedRouteProps = {
    user: User | undefined
    loggedInUser: string
}
export default function ProtectedRoute(props: ProtectedRouteProps) {

    const isAuth: boolean = props.user !== undefined && props.loggedInUser !== 'anonymousUser';

    return (
        isAuth ? <Outlet/> : <Navigate to={"/"}/>

    );
}