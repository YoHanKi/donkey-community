import HeartIcon from "@/components/Icon/HeartIcon"
import MessageCircleIcon from "@/components/Icon/MessageCircleIcon";
import ShareIcon from "@/components/Icon/ShareIcon";
import BellIcon from "@/components/Icon/BellIcon";
import HomeIcon from "@/components/Icon/HomeIcon";
import UserIcon from "@/components/Icon/UserIcon";
import Menu from "@/components/Menu/Menu";

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