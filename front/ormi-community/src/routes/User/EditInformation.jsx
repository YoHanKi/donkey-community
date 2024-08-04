import LabelSection from "@/components/Layout/LabelSection";
import LogoHeader from "@/components/Layout/LogoHeader";
import { Button } from "@/components/ui/button";
import { Checkbox } from "@/components/ui/checkbox";
import { Input } from "@/components/ui/input";
import { Select, SelectTrigger, SelectContent, SelectLabel, SelectItem, SelectGroup, SelectValue } from "@/components/ui/select";
import { GlobalContext } from "@/index";
import { editUserInfo, fetchGender, fetchIndustryList, fetchPasswordQuestion, fetchUserInfo, isLoginUser, logout} from "@/utils/API";
import { editUserInfoReqParam } from "@/utils/Parameter";
import { goToback } from "@/utils/RouteHelper";
import { useContext, useEffect, useState } from "react";
import { useNavigate } from "react-router-dom"


export default function EditInformation(){
    const nevigate = useNavigate();
    //변수 선언 지역
    const navigate = useNavigate();
    //전역변수 - 로그인 여부 확인
    const {isLogin} = useContext(GlobalContext);
    //전역변수 - 회원 정보 확인
    const {userInfo} = useContext(GlobalContext);
    //업종 목록
    const [industries, setIndustries] = useState([]);
    //비밀번호 찾기 질문
    const [passwordQuestions, setPasswordQuestions] = useState([]);

    const [tBox_nickname_val, setNickNameVal] = useState("");
    const [interestIndustry1, setInterestIndustry1] = useState("");
    const [interestIndustry2, setInterestIndustry2] = useState("");
    const [interestIndustry3, setInterestIndustry3] = useState("");
    // 패스워드 힌트 바꾸기 처리
    const [ddl_phq_val, setPHQVal] = useState("");
    // 패스워드 힌트 응답 바꾸기 처리
    const [tBox_pha_val, setPHAVal] = useState("");

    useEffect(() =>{
        if(!isLogin && !isLoginUser()){
            alert("로그인이 만료되었습니다.");
            logout();
            navigate("/");
        }else{
            Promise.all([fetchIndustryList(), fetchPasswordQuestion(), fetchUserInfo()])
            .then((result) =>{
                const industries = result[0];
                const passwordQuestions = result[1];

                setIndustries(industries);
                setPasswordQuestions(passwordQuestions);
                InitState();
            });
        }
    },[]);

    const InitState = () =>{
        // 새로고침시 전역변수 사라짐.. 리액트 특
        const {nickname, interest} = userInfo;
        if(!interest || !nickname) return ; 
        setNickNameVal(nickname || "");
        setInterestIndustry1(interest[0] ? interest[0].industryId : "");
        setInterestIndustry2(interest[1] ? interest[1].industryId : "");
        setInterestIndustry3(interest[2] ? interest[2].industryId : "");
    }

    const doEditUserInfo = () =>{
        //원래는 여기에 유효성 검증이 필요하나... 시간상 일단 스킵
        let industriesId = [interestIndustry1 || undefined, interestIndustry2 || undefined, interestIndustry3 || undefined];
        industriesId = industriesId.filter(industry => industry);
        const reqParam = editUserInfoReqParam(tBox_nickname_val, ddl_phq_val, tBox_pha_val, industriesId);

        editUserInfo(reqParam)
        .then(data =>{
            alert("성공적으로 수정되었습니다.");
            navigate("/");
        })
        .catch(e => console.log(e));
    }


    return (
        <main className="flex flex-col bg-gray-100 min-h-screen items-center overflow-y-auto pb-20">
            <section className="w-800 p-2 mt-10">
                <LogoHeader>정보수정</LogoHeader>
                <LabelSection asChild isRequire label="닉네임" className="mt-2">
                    <Input type="text" id="tBox_nickname" value={tBox_nickname_val} onChange={(ev) =>setNickNameVal(ev.target.value)} placeholder="닉네임을 입력해주세요."></Input>
                </LabelSection>
                <LabelSection asChild label="관심업종1" className="mt-2">
                    <Select value={interestIndustry1} onValueChange={setInterestIndustry1}>
                        <SelectTrigger>
                            <SelectValue placeholder="업종"></SelectValue>
                        </SelectTrigger>
                        <SelectContent>
                           {
                                industries.map((industry) => <SelectItem key={industry.industryId} value={industry.industryId}>{industry.industryName}</SelectItem>)
                           } 
                        </SelectContent>
                    </Select>
                </LabelSection>
                <LabelSection asChild label="관심업종2" className="mt-2">
                    <Select value={interestIndustry2} onValueChange={setInterestIndustry2}>
                        <SelectTrigger>
                            <SelectValue placeholder="업종"></SelectValue>
                        </SelectTrigger>
                        <SelectContent>
                            {
                                industries.map((industry) => <SelectItem key={industry.industryId} value={industry.industryId}>{industry.industryName}</SelectItem>)
                            } 
                        </SelectContent>
                    </Select>
                </LabelSection>
                <LabelSection asChild label="관심업종3" className="mt-2">
                    <Select value={interestIndustry3} onValueChange={setInterestIndustry3}>
                        <SelectTrigger>
                            <SelectValue placeholder="업종"></SelectValue>
                        </SelectTrigger>
                        <SelectContent>
                            {
                                industries.map((industry) => <SelectItem key={industry.industryId} value={industry.industryId}>{industry.industryName}</SelectItem>)
                            } 
                        </SelectContent>
                    </Select>
                </LabelSection>
                <LabelSection asChild isRequire label="비밀번호 찾기 질문" className="mt-2">
                    <Select value={ddl_phq_val} onValueChange={setPHQVal}>
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
                   <Input id="tBox_password_hint" placeholder="비밀번호 찾기 응답을 입력해주세요." onChange={(ev) => {setPHAVal(ev.target.value)}}></Input> 
                </LabelSection>
                <div className="flex justify-center gap-2 items-center mt-4">
                    <Button asChild className="mt-4">
                        <div onClick={()=>{navigate(-1)}}>취소</div>
                    </Button>
                    <Button asChild className="bg-violet-800 mt-4 hover:bg-violet-600">
                        <div onClick={doEditUserInfo}>정보수정</div>
                    </Button>
                </div>
            </section>
        </main>
    );
}