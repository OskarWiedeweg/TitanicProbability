import React from "react";


export class Query extends React.Component{

    query() {
        this.props.conainer.query();
    }

    render() {
        return (
            <div className={"query"}>
                <label><input type="checkbox" /> Überlebt</label>
                <br/>
                <label><input type="radio" value={1} name="class"/>Klasse 1</label>
                <label><input type="radio" value={2} name="class"/>Klasse 2</label>
                <label><input type="radio" value={3} name="class"/>Klasse 3</label>
                <br/>
                <label><input type="radio" value={"male"} name="sex"/>Männlich</label>
                <label><input type="radio" value={"female"} name="sex"/>Weiblich</label>
                <br/>
                <input type={"number"} placeholder={"Alter"}/>
                <input placeholder={"Name"}/>
                <input type={"number"} step={0.01} placeholder={"Preis"}/>
                <input type={"number"} placeholder={"Geschwister"}/>
                <input type={"number"} placeholder={"Eltern"}/>
                <br/>
                <span onClick={this.query.bind(this)}>Suche</span>
                <br/>
            </div>
        );
    }


}