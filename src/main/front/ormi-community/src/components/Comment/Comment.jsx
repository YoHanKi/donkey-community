import { Avatar, AvatarFallback } from "../ui/avatar";
import HeartIcon from "../Icon/HeartIcon"
import { useState, useEffect } from "react";
import { Textarea } from "../ui/textarea";
import { Button } from "../ui/button";
import { fetchOwnIp, isLoginUser, writeComment } from "@/utils/API";
import AnonymousInputSection from "./AnonymousInputSection";
import { commentWriteReqParam } from "@/utils/Parameter";

const Comment = ({commentInfoList, ownIP, isAnonymous}) => {
    const [nickname, setNickName] = useState("");
    const [password, setPassword] = useState("");
    const [content, setContent] = useState("");

    const docId = commentInfoList? commentInfoList[0].docId : "";
    const doRegisterComment = async () => {
        const reqParam = commentWriteReqParam(password, nickname, content);
        writeComment(reqParam, docId)
        .then(data => console.log(data));

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
                isAnonymous ? 
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
 * @param { {nickName: String, userId: String, commentDate: Date, comment: String, likeCount: number, likeAble: boolean} } userInfo 
 * @returns 
 */
const CommentItem = ({commentId, nickname, commentCreatorIp, email, commentDate, commentContent, likeCount, likeAble = true}) =>{
    const shortNickName = getShortNickName(nickname);

    return (
        <li className="space-y-4">
            <div className="flex items-start space-x-3">
                <Avatar>
                    <AvatarFallback className="font-bold">{shortNickName}</AvatarFallback>
                </Avatar>
                <div>
                    <p className="text-sm font-semibold">{`${nickname}(${email})`}</p>
                    <p className="text-xs text-gray-500">{commentDate}</p>
                    <p className="mt-1 text-sm">{commentContent}</p>
                    <div className="flex items-center mt-1 space-x-1 text-sm text-gray-500">
                        <HeartIcon className={`h-4 w-4 ${likeAble ? "" : "text-red-500"}`} />
                        <span>{likeCount}</span>
                    </div>
                </div>
            </div>
        </li>
      );
};


const getShortNickName = (nickName) =>{
    if(!nickName || typeof nickName !== "string") return "";
    return nickName.substring(0,nickName.length > 2 ? 2 : nickName.length);
}

export{
    Comment,
    CommentItem
}