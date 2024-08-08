import { useContext } from "react";
import { Link } from "react-router-dom";
import { AvatarImage, AvatarFallback, Avatar} from "../ui/avatar"
import DonkeyLogo from "../Icon/DonkeyLogo";
import { Popover, PopoverContent, PopoverTrigger } from "../ui/popover";
import Menu from "../Menu/Menu";
import { getIcons } from "../../utils/getComponents";
import { NavigationMenuLink, NavigationMenuList, NavigationMenu, NavigationMenuItem } from "../ui/navigation-menu";
import { useEffect, useState } from "react";
import { fetchUserInfo, isLoginUser, logout } from "@/utils/API";
import { GlobalContext } from "@/index";
import { getShortNickName } from "@/utils/common";
function Header(){
    //전역변수 - 로그인 여부
    const {isLogin} = useContext(GlobalContext);
    //전역변수 - 닉네임
    const {userInfo} = useContext(GlobalContext);

    return <header className="flex items-center justify-between px-4 py-2 border-b dark:border-gray-800">
        <Link className="flex items-center gap-2 text-lg font-semibold" to="/">
          <DonkeyLogo className="w-52 h-16"/>
        </Link>
        <div className="flex items-center gap-4">
          {
            isLogin ?
            <Popover>
              <PopoverTrigger asChild>
                <Avatar className="h-10 w-10">
                  <AvatarImage alt="@shadcn" src="/placeholder-avatar.jpg" />
                  <AvatarFallback>{getShortNickName(userInfo?.nickname)}</AvatarFallback>
                </Avatar>
              </PopoverTrigger>
              <PopoverContent className="w-80 grid gap-2">
                <Menu href={"/user"} svg={getIcons("User")}>정보수정</Menu>               
                {/* 토큰 없앤 후 리다이렉션 */}
                <Menu href={"/"} beforeClicks={[logout]} svg={getIcons("Logout")}>로그아웃</Menu>               
              </PopoverContent>
            </Popover> :
            <NavigationMenu>
              <NavigationMenuList className="flex gap-2">
                <NavigationMenuItem>
                    <NavigationMenuLink href="/login" className="cursor-pointer">로그인</NavigationMenuLink>
                </NavigationMenuItem>
                <NavigationMenuItem>
                  <NavigationMenuLink href="/signup" className="cursor-pointer">회원가입</NavigationMenuLink>
                </NavigationMenuItem>
              </NavigationMenuList> 
            </NavigationMenu>
          }
        </div>
      </header>
}

export default Header;