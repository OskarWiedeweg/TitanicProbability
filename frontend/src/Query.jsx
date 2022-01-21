import React from "react";
import {useForm} from "react-hook-form";

export const Query = (props) => {

    const { register, setValue, getValues, reset } = useForm();

    const query = () => {
        props.query(getValues());
    }

    const clear = () => {
        reset();
        query();
    }

    const handleUpdate = (event) => {
        const target = event.target;
        const value = target.type === 'checkbox' ? target.checked : target.value;
        const name = target.name;

        this.setState({
            [name]: value
        });
    }

    const handleClear = (event) => {
        setValue(event.target.name, "");
    }

        return (
            <div className={"query"}>
                <div><b>Überlebt</b> <label><input {...register("survivedIndicator")} type="radio" value={"true"}/> Ja</label> <label><input {...register("survivedIndicator")} type={"radio"} value={"false"}/> Nein</label> <button name={"survivedIndicator"} onClick={handleClear}>Löschen</button></div>
                <br/>
                <div><b>Klasse</b> <label><input {...register("passengerClass")} type="radio" value={1} /> 1</label> <label><input {...register("passengerClass")} type="radio" value={2} /> 2</label> <label><input {...register("passengerClass")} type="radio" value={3}/> 3</label> <button name={"passengerClass"} onClick={handleClear}>Löschen</button></div>
                <br/>
                <div><b>Geschlecht</b> <label><input {...register("sex")} type="radio" value={"male"}/> Männlich</label> <label><input {...register("sex")} type="radio" value={"female"}/> Weiblich</label> <button name={"sex"} onClick={handleClear}>Löschen</button></div>
                <br/>
                <input {...register("age")} type={"number"} placeholder={"Alter"}/>
                <input {...register("name")} onChange={handleUpdate} placeholder={"Name"}/>
                <input {...register("fare")} onChange={handleUpdate} type={"number"} step={0.01} placeholder={"Preis"}/>
                <input {...register("siblingsAboard")} onChange={handleUpdate} type={"number"} placeholder={"Geschwister"}/>
                <input {...register("parentsAboard")} onChange={handleUpdate} type={"number"} placeholder={"Eltern"}/>
                <br/>
                <div className={"buttons"}><span onClick={query}>Suche</span> <span className={"secondary"} onClick={clear}>Löschen</span></div>
                <br/>
            </div>
        );


}