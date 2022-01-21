import {useState} from "react";
import {useForm} from "react-hook-form";
import {getCookie} from "./Header";

export const Create = (props) => {

    const [open, setOpen] = useState(false);
    const [error, setError] = useState(null);

    const { register, getValues } = useForm();

    const create = () => {
        const requestOptions = {
            method: 'POST',
            headers: { 'Content-Type': 'application/json', 'Authorization':  "Bearer " + getCookie("jwt") },
            body: JSON.stringify(getValues())
        };
        fetch('http://localhost:8080/api/passenger', requestOptions)
            .then(response => response.json())
            .then(data => {
                props.fetchData();
                setOpen(false);
            }).catch(error => {
                setError("Es ist ein Fehler aufgetreten!");
        });
    }

    return (<>
        <span className={"button"} onClick={() => setOpen(!open)}>Erstellen</span>
        <div className={(open ? "open" : "" ) + " create"}>
            <label><input {...register("survivedIndicator")} type={"checkbox"}/> Hat überlebt</label>
            <span><label><input {...register("passengerClass")} type={"radio"} value={1}/> 1</label> <label><input {...register("passengerClass")} type={"radio"} value={2}/> 2</label> <label><input {...register("passengerClass")} value={3} type={"radio"}/> 3</label> Klasse</span>
            <input {...register("name")} type={"text"} placeholder={"Name"}/>
            <input {...register("age")} type={"number"} step={0.5} placeholder={"Alter"}/>
            <select {...register("sex")}>
                <option value={"male"}>Männlich</option>
                <option value={"female"}>Weiblich</option>
            </select>
            <input {...register("parentsAboard")} type={"number"} placeholder={"Eltern an Bord"}/>
            <input {...register("siblingsAboard")} type={"number"} placeholder={"Geschwister an Bord"}/>
            <input {...register("fare")} type={"number"} step={0.01} placeholder={"Gezahlter Preis in €"}/>
            <button className={"button"} onClick={create}>Erstellen</button>
            <p className={"error"}>{error}</p>
        </div>
    </>)
}