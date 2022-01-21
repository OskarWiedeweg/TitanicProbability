import React from "react";

export const PageControl = (props) => {
    const page = props.page;
    const pageDown = props.pageDown;
    const limit = props.limit;
    const pageUp = props.pageUp;

    return (<div className="pages"><span className={page === 0 ? "hide" : ""} onClick={pageDown}>&lt;</span><p>Seite {page + 1}</p><span className={page === limit ? "hide" : ""} onClick={pageUp}>&gt;</span></div>)
}