import { useEffect, useState } from "react";
import BoardAccept from "@/components/Board/BoardAccept";
import { fetchNotApproveBoardList } from "@/utils/API";

// 게시판 관리 화면
// api 호출하여 승인 여부를 리스트로 받아오고, 이를 화면에 출력한다.
export default function AdminBoard(){
    const [boardList, setBoardList] = useState([]);
    useEffect(() =>{
        fetchNotApproveBoardList()
        .then(data =>{
            setBoardList(data);
        });
    },[]);

    return (
        <ul className="grid gap-2">
            {/* 화면 하단에는 현재 업종 리스트 */}
            {
                boardList.map((board) => <BoardAccept key={board.boardId} {...board}></BoardAccept>)
            }
        </ul>
    );
}