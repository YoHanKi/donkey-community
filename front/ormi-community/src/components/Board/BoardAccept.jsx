import { acceptBoardPublicing } from "@/utils/API";
import { Button } from "../ui/button";
import { acceptBoardPublicingReqParam } from "@/utils/Parameter";

export default function BoardAccept({boardName, industryName, comName, boardId}){
    const doAcceptBoardPublicing = () =>{
        const reqParam = acceptBoardPublicingReqParam(boardId);

        acceptBoardPublicing(reqParam)
        .then(data =>{
            alert("게시판 승인이 완료되었습니다.");
            location.reload();
        })
    };

    return (
        <div className="p-4 flex justify-between items-end border rounded-sm">
            <div className="grid gap-2">
                <span>게시판명: {boardName}</span>
                <span>업종: {industryName}</span>
                <span>회사명: {comName || ""}</span>
            </div>
            <Button className="bg-violet-800 mt-4 hover:bg-violet-600">
                <div onClick={doAcceptBoardPublicing}>
                    승인
                </div>
            </Button>
        </div>
    );
}