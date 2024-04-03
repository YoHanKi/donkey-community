import { appendPasswordQuestion, fetchPasswordQuestion } from "@/utils/API";
import { useState, useEffect } from "react";
import { Input } from "@/components/ui/input";
import { Button } from "@/components/ui/button";
import LabelSection from "@/components/Layout/LabelSection";
import PasswordQuestionArticle from "@/components/Password/PasswordQuestionArticle";

export default function AdminPasswordQuestion(){
    const [passwordQuestion, setPasswordQuestion] = useState("");
    const [passwordQuestionList, setPasswordQuestionList] = useState([]);
    useEffect(()=>{
        setPasswordQuestionList(fetchPasswordQuestion());
    },[]);

    return (
        <ul className="grid gap-2">
            <article className="p-4 flex justify-between items-end border rounded-md gap-2">
                <div className="grid gap-2 w-full">
                    <LabelSection asChild label="비밀번호 질문" className="font-bold">
                        <Input type="text" placeholder="추가할 질문을 입력해주세요" maxLength={200} value={passwordQuestion} onChange={(ev) => {setPasswordQuestion(ev.target.value);}} className="w-full"></Input>
                    </LabelSection>
                </div>
                <Button className="bg-violet-800 mt-4 hover:bg-violet-600">
                    <div onClick={()=>{appendPasswordQuestion(passwordQuestion)}}>추가하기</div>
                </Button>
            </article>

            {
                passwordQuestionList.map((passwordQuestion, idx) => <PasswordQuestionArticle key={passwordQuestion.passwordQuestionId} {...passwordQuestion} order={idx + 1} />)
            }
        </ul>
    );
}