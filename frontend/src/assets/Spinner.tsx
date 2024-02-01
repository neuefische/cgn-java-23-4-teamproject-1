import {MagnifyingGlass, ProgressBarProps} from "react-loader-spinner";
import React from "react";


export const LoadingSpinnerComponent: React.FunctionComponent<ProgressBarProps> = () => {


    return (
        <div className="sweet-loading">
            <MagnifyingGlass
                width={150}
                height={150}
                glassColor={"transparent"}
                color={"lightblue"}
                wrapperClass={"wrapper"}
                aria-label="progress Spinner"
                data-testid="loader"
            />
        </div>
    );

};