import LogoHeader from "@/components/Layout/LogoHeader";
import { Button } from "@/components/ui/button";
import { Input } from "@/components/ui/input";
import { Link } from "react-router-dom";
import { useEffect, useState } from "react";
import { Select, SelectTrigger, SelectContent, SelectLabel, SelectItem, SelectGroup, SelectValue } from "@/components/ui/select";
import LabelSection from "@/components/Layout/LabelSection";
import { fetchPasswordQuestion, matchPasswordHint } from "@/utils/API";
import { matchPasswordHintReqParam } from "@/utils/Parameter";
import { useNavigate } from "react-router-dom";

export default function FindPassword(){
    const nevigate = useNavigate();

    const [passwordQuestions, setPasswordQuestions] = useState([]);
    const [emailVal, setEmailVal] = useState("");
    const [ddl_pwdhint_value, setPwdHintValue] = useState(null);
    const [hintAnswerVal, setHintAnswerVal] = useState("");

    const doMatchPasswordHint = () =>{
        const reqParam = matchPasswordHintReqParam(emailVal, ddl_pwdhint_value, hintAnswerVal)

        return matchPasswordHint(reqParam)
        .then(data => {
            alert("정보가 일치합니다.");
            nevigate("/password/edit", {state: {email: data.email}});
        })
        .catch(e =>{
            alert("입력정보가 상이합니다.")
        });
    }
    
    useEffect(() =>{
        fetchPasswordQuestion()
        .then((data) =>{
            setPasswordQuestions(data);
        });
    },[]);

    return (<main className="flex flex-col bg-gray-100 min-h-screen items-center">
            <section className="w-800 p-2 mt-40">
                <LogoHeader>계정 비밀번호 찾기</LogoHeader>
                <LabelSection asChild isRequire label="이메일" className="mt-2">
                    <Input onChange={(ev) => setEmailVal(ev.target.value)} type="email" id="tBox_email" placeholder="이메일을 입력해주세요."></Input>
                </LabelSection>
                <LabelSection asChild isRequire label="비밀번호 찾기 질문" className="mt-2">
                        <Select onValueChange={setPwdHintValue}>
                            <SelectTrigger>
                                <SelectValue placeholder="선택"></SelectValue>
                            </SelectTrigger>
                            <SelectContent>
                                <SelectGroup>
                                    {
                                        passwordQuestions.map(passwordQuestion => <SelectItem key={passwordQuestion.passwordQuestionId} value={passwordQuestion.passwordQuestionId}>{passwordQuestion.passwordQuestion}</SelectItem>)
                                    }
                                </SelectGroup>
                            </SelectContent>
                        </Select>
                </LabelSection>
                <LabelSection asChild isRequire label="비밀번호 찾기 응답" className="mt-2">
                    <Input onChange={(ev) => {setHintAnswerVal(ev.target.value)}} id="tBox_password_hint" placeholder="비밀번호 찾기 응답을 입력해주세요."></Input> 
                </LabelSection>

            <div className="flex justify-between items-center mt-4 text-violet-800">
                    <div></div>
                    <Link to="signup">회원가입</Link>
                </div>
                {/* 버튼은 onClick 콜백 동작 불가 */}
                <Button asChild className="bg-violet-800 mt-4 w-full hover:bg-violet-600">
                    <div onClick={doMatchPasswordHint}>확인하기</div>
                </Button>
            </section>
        </main>
    );
}