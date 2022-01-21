import {getCookie} from "./Header";

export const Passenger = (props) => {

    const deletePassenger = () => {
        const requestOptions = {
            method: 'DELETE',
            headers: { 'Content-Type': 'application/json', 'Authorization': 'Bearer ' + getCookie("jwt") }
        };
        fetch('http://localhost:8080/api/passenger?id=' + props.id, requestOptions)
            .then(response => response.json())
            .then(data => {
                props.fetchData();
            }).catch(error => {
        });
    }

    return (<div className="passenger">
        <span>{props.sex === 'male' ? "M" : "W"}</span>
        <span>{props.name}</span>
        <span>{props.age} Jahre</span>
        <span>{props.survived ? "Ja" : "Nein"}</span>
        <span>Klasse {props.class}</span>
        <span>{props.fare}€</span>
        <span>{props.siblings === 0 ? "Keine" : props.siblings}</span>
        <span>{props.parents === 0 ? "Keine" : props.parents}</span>
        <span><i onClick={deletePassenger} className="fas fa-trash-alt"/></span>

    </div>)
}