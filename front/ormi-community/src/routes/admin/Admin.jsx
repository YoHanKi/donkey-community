import LogoHeader from "@/components/Layout/LogoHeader";
import { AdminMenus } from "@/components/Menu/AdminMenus";
import { GlobalContext } from "@/index";
import { GenerateLiElUUID } from "@/utils/common";
import { useContext, useEffect } from "react";
import { Outlet } from "react-router-dom"

export default function Admin(){
    return (
        <div className="flex flex-col h-screen">
            <LogoHeader>관리자 페이지</LogoHeader>
        <main className="flex flex-1 overflow-hidden">
            <aside className="w-64 border-r dark:border-gray-800 overflow-auto">
                <AdminMenus></AdminMenus>
            </aside>
            <section className="flex-1 overflow-auto">
            <div className="grid gap-4 p-4">
                <Outlet key={GenerateLiElUUID()} />
            </div>
            </section>
        </main>
        </div>

  );
}