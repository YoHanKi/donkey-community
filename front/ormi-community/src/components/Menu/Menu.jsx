import HeartIcon from "../Icon/HeartIcon";
import { forwardRef, useEffect, useState } from "react";
import { GenerateLiElUUID } from "../../utils/common"
import { useNavigate } from "react-router-dom";

// 게시판 목록 메뉴를 제외한 일반적인 메뉴를 표현
const Menu = forwardRef((props, ref) => {
    const navigate = useNavigate();

    const {href, svg, children, beforeClicks, size, className} = props;
    const defaultIcon = HeartIcon(getSizeToSvgClassName(size));
    const [onclicks, setOnClick] = useState([]);
    const [svgIconEl, setSvgIcon] = useState(svg ? svg(getSizeToSvgClassName(size)) : defaultIcon);
    const moveToHref = () =>{
        href ? 
        navigate(href) :
        "";
    };

    const changeSvgIcon = (newSvg) =>{
        setSvgIcon(newSvg ? newSvg : defaultIcon);
    }

    useEffect(() => {
        setOnClick(beforeClicks?.length > 0 ? [...beforeClicks, moveToHref ] : [moveToHref]);
    },[]);
   
    return (
        (<li 
            ref={ref}
            key={GenerateLiElUUID()}
            className={`list-none flex items-center gap-2 text-sm font-medium truncate cursor-pointer ${className ? className : ""}`}
            onClick={() => {
                onclicks.forEach(onclick => onclick.call());
            }}>
            {svgIconEl}
            <span className="trunacte">
                {children}
            </span>
        </li>)
    );
})

function getSizeToSvgClassName(size){
    return size ?
    { className: `h-${size} w-${size} shrink-0` } :
    { className: `h-4 w-4 shrink-0`};
}


export default Menu;