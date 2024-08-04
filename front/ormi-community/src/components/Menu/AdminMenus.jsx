import {
    NavigationMenu,
    NavigationMenuContent,
    NavigationMenuItem,
    NavigationMenuList,
    NavigationMenuTrigger,
  } from "@/components/ui/navigation-menu";
import { getIcons } from "@/utils/getComponents";
import Menu from "./Menu";


export function AdminMenus(){
    return (
        <nav className="flex flex-col">
            <ul className="grid gap-4 p-4">
                <NavigationMenu>
                    <NavigationMenuList>
                        <NavigationMenuItem>
                            <NavigationMenuTrigger className="p-0">관리자 메뉴 목록</NavigationMenuTrigger>
                            <NavigationMenuContent className="overflow-y-auto">
                                <ul className="flex flex-col w-[150px] gap-4 p-4 md:w-[200px] lg:w-[200px] max-h-[500px] overflow-y-auto">
                                    <Menu href="/admin/board" svg={getIcons("Plus")}>게시판 관리</Menu>
                                    <Menu href="/admin/industry" svg={getIcons("Industry")}>업종관리</Menu>
                                    <Menu href="/admin/passwordquestion" svg={getIcons("User")}>비밀번호질문관리</Menu>
                                </ul>
                            </NavigationMenuContent>
                        </NavigationMenuItem>
                    </NavigationMenuList>
                </NavigationMenu>
            </ul>
        </nav>
    );
}