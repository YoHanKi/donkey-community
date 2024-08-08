import LabelSection from "@/components/Layout/LabelSection";
import LogoHeader from "@/components/Layout/LogoHeader";
import { Button } from "@/components/ui/button";
import { Checkbox } from "@/components/ui/checkbox";
import { Input } from "@/components/ui/input";
import { PhoneInput } from "@/components/ui/phone-input";
import { Select, SelectTrigger, SelectContent, SelectLabel, SelectItem, SelectGroup, SelectValue } from "@/components/ui/select";
import { fetchGender, fetchIndustryList, fetchPasswordQuestion, signup } from "@/utils/API";
import { GenerateLiElUUID } from "@/utils/common";
import { signupReqParam } from "@/utils/Parameter";
import { correctRegxEmail, correctRegxPwd } from "@/utils/patternMatcher";
import { useState, useEffect } from "react";
import { useNavigate } from "react-router-dom"

export default function Signup(){
    //변수 선언지역
    const navigate = useNavigate();

    const [ddl_gender_value, setGenderValue] = useState(null);
    const [ddl_pwdhint_value, setPwdHintValue] = useState(null);
    const [tBox_phoneNumber, setPhoneNumberValue] = useState("");
    const [interestIndustry1, setInterestIndustry1] = useState("");
    const [interestIndustry2, setInterestIndustry2] = useState("");
    const [interestIndustry3, setInterestIndustry3] = useState("");

    //업종 목록
    const [industries, setIndustries] = useState([]);
    //비밀번호 찾기 질문
    const [passwordQuestions, setPasswordQuestions] = useState([]);
    //성별
    const [genders, setGenders] = useState([]);

    useEffect(() => {
        Promise.all([fetchIndustryList(), fetchPasswordQuestion(), fetchGender()])
        .then((result) =>{
            const industries = result[0];
            const passwordQuestions = result[1];
            const genders = result[2];

            setIndustries(industries);
            setPasswordQuestions(passwordQuestions);
            setGenders(genders);
        })
        .catch(e => console.log(e));
    },[]);

    const doSignup = () =>{
        const tBox_name = document.getElementById('tBox_name');
        const tBox_nickname = document.getElementById('tBox_nickname');
        const tBox_email = document.getElementById('tBox_email');
        const tBox_password = document.getElementById('tBox_password');
        const tBox_password_confirm = document.getElementById('tBox_password_confirm');
        const tBox_passwordquestion_answer = document.getElementById('tBox_password_hint');

        const industries = [interestIndustry1, interestIndustry2, interestIndustry3].filter(industry => industry);

        if(!tBox_name || !tBox_name.value){
            alert("이름을 입력해주세요!");
            return ;
        }

        if(!tBox_nickname || !tBox_name.value){
            alert("닉네임을 입력해주세요!");
            return ;
        }

        if(!tBox_email || !tBox_email.value){
            alert("이메일을 입력해주세요!");
            return ;
        }

        if(!tBox_phoneNumber){
            alert("휴대전화 번호를 입력해주세요!");
            return ;
        }

        if(!ddl_gender_value){
            alert("성별을 선택해주세요!");
            return ;
        }

        if(!ddl_pwdhint_value){
            alert("비밀번호 힌트를 선택해주세요!");
            return ;
        }

        if(!tBox_password.value){
            alert("비밀번호를 입력해주세요!");
            return ;
        }

        if(!tBox_password_confirm.value){
            alert("비밀번호 확인란에 입력해주세요!");
            return ;
        }

        if(!correctRegxEmail(tBox_email.value)){
            alert("이메일 형식이 올바르지 않습니다.");
            return ;
        }

        if(!correctRegxPwd(tBox_password.value)){
            alert("비밀번호는 영어 대문자,소문자,특수기호,숫자를 반드시 포함한 8 ~ 16자리 문자여야합니다.");
            return ;
        }


        if(!tBox_passwordquestion_answer || !tBox_passwordquestion_answer.value){
            alert("비밀번호 찾기 응답이 입력되지 않았습니다.");
            return ;
        }

        if(tBox_password.value !== tBox_password_confirm.value){
            alert("비밀번호가 일치하지 않습니다.");
            return ;
        }

        const reqParam = signupReqParam(tBox_name.value, tBox_nickname.value, tBox_email.value, tBox_password.value, ddl_gender_value, tBox_phoneNumber, ddl_pwdhint_value, tBox_passwordquestion_answer.value, industries);

        signup(reqParam)
        .then((response) =>{
            //go to complete
            navigate("/signup/complete");
        })
        .catch((response) =>{
            alert("회원가입이 실패했습니다.");
        });
    };

    return ( 
        <main className="flex flex-col bg-gray-100 min-h-screen items-center overflow-y-auto pb-20">
            <section className="w-800 p-2 mt-10">
                <LogoHeader>회원가입</LogoHeader>
                <LabelSection asChild isRequire label="이름">
                    <Input type="text" id="tBox_name" placeholder="이름을 입력해주세요."></Input>
                </LabelSection>
                <LabelSection asChild isRequire label="닉네임" className="mt-2">
                    <Input type="text" id="tBox_nickname" placeholder="닉네임을 입력해주세요."></Input>
                </LabelSection>
                <LabelSection asChild isRequire label="이메일" className="mt-2">
                    <Input type="email" id="tBox_email" placeholder="이메일을 입력해주세요."></Input>
                </LabelSection>
                <LabelSection asChild isRequire label="휴대폰번호" className="mt-2">
                    <PhoneInput id="tBox_phonenumber" className="w-full" onChange={setPhoneNumberValue}></PhoneInput>
                </LabelSection>
                <LabelSection asChild isRequire label="비밀번호" className="mt-2">
                    <Input type="password" id="tBox_password" placeholder="비밀번호를 입력해주세요."></Input>
                </LabelSection>
                <LabelSection asChild isRequire label="비밀번호확인" className="mt-2">
                    <Input type="password" id="tBox_password_confirm" placeholder="비밀번호를 입력해주세요."></Input>
                </LabelSection>
                <LabelSection asChild isRequire label="성별" className="mt-2">
                    <Select onValueChange={setGenderValue}>
                        <SelectTrigger>
                            <SelectValue placeholder="성별"></SelectValue>
                        </SelectTrigger>
                        <SelectContent>
                            <SelectGroup>
                                <SelectLabel>성별</SelectLabel>
                                {
                                    genders.map(gender => <SelectItem key={GenerateLiElUUID()} value={gender.value}>{gender.title}</SelectItem>)
                                }
                            </SelectGroup>
                        </SelectContent>
                    </Select>
                </LabelSection>
                <LabelSection asChild label="관심업종1" className="mt-2">
                    <Select onValueChange={setInterestIndustry1}>
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
                    <Select onValueChange={setInterestIndustry2}>
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
                    <Select onValueChange={setInterestIndustry3}>
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
                   <Input id="tBox_password_hint" placeholder="비밀번호 찾기 응답을 입력해주세요."></Input> 
                </LabelSection>
                {/* 버튼은 onClick 콜백 동작 불가 */}
                <Button asChild className="bg-violet-800 mt-4 w-full hover:bg-violet-600">
                    <div onClick={doSignup}>회원가입</div>
                </Button>
            </section>
        </main>
    );
}