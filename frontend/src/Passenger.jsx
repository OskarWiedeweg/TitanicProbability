export const Passenger = (props) => {
    return (<div className="passenger">
        <span>{props.sex === 'male' ? "M" : "W"}</span>
        <span>{props.name}</span>
        <span>{props.age} Jahre</span>
        <span>{props.survived ? "Ja" : "Nein"}</span>
        <span>Klasse {props.class}</span>
        <span>{props.fare}€</span>
        <span>{props.siblings === 0 ? "Keine" : props.siblings}</span>
        <span>{props.parents === 0 ? "Keine" : props.parents}</span>

    </div>)
}