import {Query} from "./Query";
import {Passenger} from "./Passenger";
import React, {useEffect, useState} from "react";
import {PageControl} from "./PageControl";
import {Create} from "./Create";

export const Container = () => {

    const [ page, setPage ] = useState(0);
    const [ query, setQuery ] = useState({});
    const [ loaded, setLoading ] = useState(false);
    const [ passengers, setPassenger ] = useState({});
    const [ limit, setLimit ] = useState(null);

    const fetchData = () => {

        let propertyString = "?page=" + page;

        if (query != null) {
            for (const [key, value] of Object.entries(query)) {
                if (value !== "" && value !== null)
                    propertyString += "&" + key + "=" + value;
            }
        }

        fetch("http://localhost:8080/api/passenger/all" + propertyString).then(res => res.json())
            .then((result) => {
                if (result.length === 0 && page !== 0) {
                    setPage(page - 1);
                    setLimit(page-1);
                    return;
                }
                setPassenger(result);
                setLoading(true);
            });
    }

    const pageUp = () => {
        setPage(page + 1);
    }

    const pageDown = () => {
        if (page === 0) return;
        setPage(page - 1);
    }

    const performQuery = (query) => {
        setQuery(query);
        setPage(0);
    }

    useEffect(()=>{
        fetchData();
    },[])

    useEffect(() => {
        fetchData();
    }, [page, query]);


    return (<div className="container">
            <h1>Passagiere ({page + 1})</h1>
            <br/>
            <Query query={performQuery} />
                <Create fetchData={fetchData}/>
            <PageControl page={page} pageUp={pageUp} pageDown={pageDown} limit={limit} />
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
                {loaded && passengers.map(passenger => {
                    return (<Passenger key={passenger.id} id={passenger.id} sex={passenger.sex} name={passenger.name} age={passenger.age}
                                       survived={passenger.survivedIndicator} class={passenger.passengerClass}
                                       fare={passenger.fare} siblings={passenger.siblingsAboard}
                                       parents={passenger.parentsAboard} fetchData={fetchData}/>)
                })}
            </div>
        <PageControl page={page} pageUp={pageUp} pageDown={pageDown} limit={limit} />
        </div>);
}