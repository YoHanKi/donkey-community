import HeartIcon from "@/components/Icon/HeartIcon"
import MessageCircleIcon from "@/components/Icon/MessageCircleIcon";
import ShareIcon from "@/components/Icon/ShareIcon";
import BellIcon from "@/components/Icon/BellIcon";
import HomeIcon from "@/components/Icon/HomeIcon";
import UserIcon from "@/components/Icon/UserIcon";
import Menu from "@/components/Menu/Menu";
import DonkeyLogo from "../components/Icon/DonkeyLogo";
import Logout from "../components/Icon/Logout";
import FileEditIcon from "@/components/Icon/FileEditIcon";
import DeleteIcon from "@/components/Icon/DeleteIcon";
import PlusIcon from "@/components/Icon/PlusIcon";
import CompanyIcon from "@/components/Icon/Company";
import IndustryIcon from "@/components/Icon/Company";
import BoardMenu from "@/components/Menu/BoardMenu";
import { Document } from "@/components/Document/Document";

export function getIcons(svgStr){
    switch(svgStr){
      case "Heart":
        return HeartIcon;
      case "MessageCircle":
        return MessageCircleIcon;
      case "Share":
        return ShareIcon;
      case "SvgBell":
        return BellIcon;
      case "SvgHome":
        return HomeIcon;
      case "User":
        return UserIcon;
      case "DonkeyLogo":
        return DonkeyLogo;
      case "Logout":
        return Logout;
      case "FileEdit":
        return FileEditIcon;
      case "Delete":
        return DeleteIcon;
      case "Plus":
        return PlusIcon;
      case "Industry":
        return IndustryIcon;
      default:
        return null;
    }
  }
  
  
  /**
   * @brief param 형태의 리스트를 Menu 컴포넌트 리스트로 반환한다.
   * @param { {href: String, svg: String, title: String}[] } menuList 
   * @returns 
   */
  export function getMenuComponents(menuList){
    //1. svg문자열 -> svg컴포넌트 오프젝트로 변환
    menuList.map(menu => menu.svg = getIcons(menu.svg));
  
    //2. svg프로퍼티가 null인게 있다? 그러면 오류가 발생한것
    // 오류가 발생한 메뉴는 제거하여 반환
    menuList = menuList.filter(menu => menu.svg != null);
  
    return menuList.map((menu) => <Menu svg={menu.svg} href={menu.href}>{menu.title}</Menu>);
  }

  /**
   * 
   * @param {{boardId: String, boardName: String, clickCallbackFn: Function | null , className:String}} boardMenuList 
   * @returns {BoardMenu[]}
   */
  export function getBoardMenuComponents(boardMenuList){
    return boardMenuList.map((boardMenu) => <BoardMenu {...boardMenu}></BoardMenu>)
  }

  export function getDocumentComponents(documentList){
    return documentList.map((document) => <Document {...document}></Document>);
  }