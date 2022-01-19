import {Query} from "./Query";
import {Passenger} from "./Passenger";
import React from "react";

export class Container extends React.Component {

    constructor(props) {
        super(props);

        this.state = {
            isLoaded: false,
            passengers: [],
            page: 0
        }


    }

    componentDidMount() {
        this.fetchData()
    }

    fetchData() {
        const { page } = this.state;
        fetch("http://localhost:8080/api/passenger?page=" + page).then(res => res.json())
            .then((result) => {
                if (result.length === 0 && this.state.page !== 0) {
                    this.setState({page: page - 1}, this.fetchData);
                    this.limit = page-1;
                    return;
                }
                this.setState({isLoaded: true, passengers: result})
            });
    }

    render() {
        const { isLoading, passengers } = this.state;
        return (<div className="container">
            <h1>Passagiere ({this.state.page + 1})</h1>
            <br/>
            <Query conainer={this} />
            <div className="passengers">
                <div className="passenger titles">
                    <span>M/W</span>
                    <span>Name</span>
                    <span>Alter</span>
                    <span>Überlebt?</span>
                    <span>Klasse</span>
                    <span>Preis</span>
                    <span>Geschwister</span>
                    <span>Eltern</span>
                </div>
                {passengers.map(passenger => (
                    <Passenger key={passenger.id} sex={passenger.sex} name={passenger.name} age={passenger.age} survived={passenger.survivedIndicator} class={passenger.passengerClass} fare={passenger.fare} siblings={passenger.siblingsAboard} parents={passenger.parentsAboard} />
                ))}
            </div>
            <div className="pages"><span className={this.state.page === 0 ? "hide" : ""} onClick={this.pageDown.bind(this)}>&lt;</span><p>Seite {this.state.page + 1}</p><span className={this.state.page === this.limit ? "hide" : ""} onClick={this.pageUp.bind(this)}>&gt;</span></div>
        </div>)
    }

    pageUp() {
        const { page } = this.state
        this.setState({page: page + 1}, this.fetchData)
    }

    pageDown() {
        const { page } = this.state
        if (page === 0) return
        this.setState({page: page - 1}, this.fetchData)
    }
}