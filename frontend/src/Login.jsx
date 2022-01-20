import {useForm} from "react-hook-form";
import {useEffect, useState} from "react";

export const Login = (props) => {

    const { register, getValues, reset } = useForm();
    const [ error, setError] = useState(null);

    const submit = () => {
        login(getValues())
    }

    const login = (values) => {
        const requestOptions = {
            method: 'POST',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify(values)
        };
        setError(null)
        fetch('http://localhost:8080/api/auth/login', requestOptions)
            .then(response => response.json())
            .then(data => {
                document.cookie = "jwt=" + data.jwt;
                document.cookie = "username=" + data.username;
                props.onHide();
            }).catch(error => {
                setError("Benutzername oder Passwort falsch.")
            });
    }

    const logout = () =>{
        document.cookie = "username=; expires=" + new Date(Date.now() -9999999);
        document.cookie = "jwt=; expires=" + new Date(Date.now() -9999999);
        props.onHide()
    }

    useEffect(() => {
        if (props.show === false) {
            reset();
            setError(null)
        }
    }, [props.show]);

    if (props.username != null && props.username !== "") {
        return <div className={"login"}><button onClick={logout}>Logout</button></div>
    }else {
        return (
            <div className={"login"}>
                <p className={"error"}>{error}</p>
                <input {...register("username")} placeholder={"Benutzername"}/>
                <input {...register("password")} placeholder={"Passwort"} type={"password"}/>
                <button onClick={submit}>Login</button>
            </div>
        );
    }
}