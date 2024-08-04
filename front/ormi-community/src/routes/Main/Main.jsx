import Header from "@/components/Layout/Header";
import Menus from "@/components/Menu/Menus";
import mainCharacter from "../../assets/image/mainCharacter.png";
import emptyBoardNotify from "../../assets/image/emptyBoardNotify.png";
import { useContext, useEffect, useState } from "react";
import { GlobalContext } from "@/index";
import { fetchDocumentList, fetchUserInfo, isLoginUser } from "@/utils/API";
import { getDocumentComponents } from "@/utils/getComponents";
import { Button } from "@/components/ui/button";

export default function Main(){
   const [documentList, setDocumentList] = useState([]);
   const [pageNumber, setPageNumber] = useState(0);
   //전역 변수 - 게시판 ID
   const {selectBoardID} = useContext(GlobalContext);
   //전역변수 - 새로고침
   const {reload} = useContext(GlobalContext);

    useEffect(() =>{
      setPageNumber(0);
    },[selectBoardID]);

    useEffect(()=>{
      if(!selectBoardID) return ;
      fetchDocumentList(selectBoardID, 0)
      .then(data => {
        setDocumentList(getDocumentComponents(data));
      });
    },[selectBoardID, reload]);

    useEffect(() =>{
      if(!selectBoardID) return ;
      fetchDocumentList(selectBoardID, pageNumber)
      .then(data => {
        setDocumentList([...documentList, getDocumentComponents(data)]);
      });
    },[pageNumber]);

    return (
    <div className="flex flex-col h-screen">
        <Header></Header>
      <main className="flex flex-1 overflow-hidden">
        <aside className="w-64 border-r dark:border-gray-800 overflow-auto">
            <Menus></Menus>
        </aside>
          {
            documentList.length > 0 ?
              (<section className="flex-1 overflow-auto">
                <div className="grid gap-4 p-4">
                  <div className="flex justify-center bg-[#7a8cd1] rounded-lg">
                      <img src={mainCharacter} className="h-[380px]"></img>
                  </div>
                  <div className="flex flex-col gap-4">
                    {...documentList}
                  </div>
                </div>
                <div className="flex justify-center items-center mb-2">
                  <Button asChild>
                    <div onClick={() =>{setPageNumber(pageNumber + 1)}}>더보기</div>
                  </Button>
                </div>
              </section>
              ):
              <div className="flex justify-center items-center w-full">
                <img src={emptyBoardNotify} className="h-[380px]"></img>
              </div>
        }
      </main>
    </div>
  );
}