import { Avatar, AvatarFallback } from "../ui/avatar";
import HeartIcon from "../Icon/HeartIcon"
import { useState, useEffect, useContext } from "react";
import { Textarea } from "../ui/textarea";
import { Button } from "../ui/button";
import { likeIt, fetchLikeCount, writeComment, isExistUUID, uuidSave } from "@/utils/API";
import AnonymousInputSection from "./AnonymousInputSection";
import { commentWriteReqParam } from "@/utils/Parameter";
import { GlobalContext } from "@/index";
import { getShortNickName } from "@/utils/common";

const Comment = ({commentInfoList, ownIP, docId, reload, setReload}) => {
    //전역 변수 - 로그인 여부
    const {isLogin} = useContext(GlobalContext);
    const [nickname, setNickName] = useState("");
    const [password, setPassword] = useState("");
    const [content, setContent] = useState("");

    const doRegisterComment = async () => {
        const reqParam = commentWriteReqParam(password, ownIP || "", content, nickname);
        writeComment(reqParam, docId)
        .then(() =>{
            //컴포넌트 강제 새로고침
            setReload(reload * -1);
        });
    }



    //현재는 Comment를 생성할 때 호출하도록 되어있음. 함수 호출 시점은 바뀔 수 있음.
    return (
        <div className="p-4 border-t">
            <ul className="flex flex-col gap-4">
                {
                    commentInfoList.map(commentInfo =>{
                        return (<CommentItem key={commentInfo.commentId} {...commentInfo}></CommentItem>);
                    })
                }
            </ul>
            {
                !isLogin ? 
                <AnonymousInputSection ownIP={ownIP} setNickName={setNickName} setPassword={setPassword}></AnonymousInputSection> :
                <></>
            }
            <Textarea onChange={(ev) => setContent(ev.target.value)} className="mt-4 resize-none" placeholder="댓글을 입력해주세요"></Textarea>
            <Button asChild className="mt-2 bg-black text-white w-full">
                <div onClick={doRegisterComment}>댓글 등록</div>
            </Button>
        </div>
    );
};

/**
 * 
 * @param { {nickName: String, userId: String, commentDate: Date, comment: String, likeCount: number} } userInfo 
 * @returns 
 */
const CommentItem = ({commentId, nickname, commentCreatorIp, email, commentDate, commentContent}) =>{
    const shortNickName = getShortNickName(nickname);
    const [likeCount, setLikeCount] = useState(0);
    const [likeAble, setLikeAble] = useState(!isExistUUID(commentId));
    const doLikeIt = () =>{
        if(isExistUUID(commentId)){
            alert("이미 좋아요한 댓글/게시글입니다.");
            return ;
        }
        likeIt(commentId)
        .then(() => {
            uuidSave(commentId);
            setLikeCount(likeCount + 1);
            setLikeAble(false);
        });
    }

    useEffect(() =>{
        fetchLikeCount(commentId)
        .then(data =>{
            setLikeCount(data.likeCount);
        });
    }, [likeCount]);

    return (
        <li className="space-y-4">
            <div className="flex items-start space-x-3">
                <Avatar>
                    <AvatarFallback className="font-bold">{shortNickName}</AvatarFallback>
                </Avatar>
                <div>
                    <p className="text-sm font-semibold">{`${nickname}(${email || "비회원"})`}</p>
                    <p className="text-xs text-gray-500">{commentDate}</p>
                    <p className="mt-1 text-sm">{commentContent}</p>
                    <div className="flex items-center mt-1 space-x-1 text-sm text-gray-500">
                        <HeartIcon onClick={doLikeIt} className={`h-4 w-4 ${likeAble ? "" : "text-red-500"}`} />
                        <span>{likeCount}</span>
                    </div>
                </div>
            </div>
        </li>
      );
};

export{
    Comment,
    CommentItem
}