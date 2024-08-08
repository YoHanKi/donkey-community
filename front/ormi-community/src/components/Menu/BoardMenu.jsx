import { GlobalContext } from "@/index";
import { GenerateLiElUUID } from "@/utils/common";
import { forwardRef, useContext } from "react";

const BoardMenu = forwardRef((props, ref) =>{
    const {boardId, boardName, className}  = props;
    const {setSelectBoardID} = useContext(GlobalContext);
    
    return (
        <li
            ref={ref}
            key={GenerateLiElUUID()}
            className={`list-none flex items-center gap-2 text-sm font-medium truncate cursor-pointer ${className ? className : ""}`}
            onClick={() =>{setSelectBoardID(boardId)}}
        >
            <span className="truncate">
                {boardName}
            </span>
        </li>
    );

},[]);

export default BoardMenu;