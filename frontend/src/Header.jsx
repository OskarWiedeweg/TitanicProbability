import {useEffect, useState} from "react";
import {Login} from "./Login";

export const Header = () => {

    const [ login, setLogin ] = useState(false);
    const [ username, setUsername ] = useState(null);

    const update = () => {
        setUsername(getCookie("username"))
    }

    useEffect(() => {
        update();
    }, []);

    return (<div className={(login ? "open " : "") + "header"}>
        <div className={"navigation"}><h3>Titanic <span>Probability</span></h3>
        <span onClick={() => { setLogin(!login) }}>{ username == null || username === "" ? "Login" : username}</span></div>
        <Login show={login} username={username} onHide={() => {setLogin(false); update(); }} />
    </div>)
}

export function getCookie(cname) {
    let name = cname + "=";
    let decodedCookie = decodeURIComponent(document.cookie);
    let ca = decodedCookie.split(';');
    for(let i = 0; i <ca.length; i++) {
        let c = ca[i];
        while (c.charAt(0) === ' ') {
            c = c.substring(1);
        }
        if (c.indexOf(name) === 0) {
            return c.substring(name.length, c.length);
        }
    }
    return "";
}