import { getMenuComponents } from "../../utils/getComponents";
import BoardMenus from "./BoardMenus";
import navImage from "../../assets/image/navImage.png";

export default function Menus(){
    //fetch MenuList by Server
    //가데이터
    const fetchMenus = [
        {href: "/document", svg: "FileEdit", title: "게시글작성"},
        {href: "/board", svg: "Plus", title: "게시판생성"},
    ];

    const MenuList = getMenuComponents(fetchMenus);

    return (
        <nav className="flex flex-col">
            <ul className="grid gap-4 p-4">
                <BoardMenus></BoardMenus>
                {...MenuList}
            </ul>
            <div className="flex-grow flex-col flex gap-10 p-2">
                <img src={navImage} className="h-[450px] w-full"></img>
                <img src={navImage} className="h-[450px] w-full"></img>
            </div>
        </nav>
    );
}