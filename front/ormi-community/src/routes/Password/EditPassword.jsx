import LabelSection from "@/components/Layout/LabelSection";
import LogoHeader from "@/components/Layout/LogoHeader";
import { Button } from "@/components/ui/button";
import { Input } from "@/components/ui/input";
import { pwdChange } from "@/utils/API";
import { pwdChangeReqParam } from "@/utils/Parameter";
import { useEffect, useState } from "react";
import { useLocation, useNavigate } from "react-router-dom";


export default function EditPassword(){
    const location = useLocation();
    const nevigate = useNavigate();

    const [ddl_pwdhint_value, setPwdHintValue] = useState(null);
    const [pwd, setPwd] = useState("");
    const [pwdConfirm, setPwdConfirm] = useState("");

    const doPwdChange = () =>{
        const tBox_password = document.getElementById('tBox_password');
        const tBox_password_confirm = document.getElementById('tBox_password_confirm');
        
        if(!tBox_password || !tBox_password_confirm){
            alert("값을 입력하세요");
            return ;
        }

        if(tBox_password.value != tBox_password_confirm.value){
            alert("비밀번호와 비밀번호 확인값이 같지 않습니다.");
            return ;
        }

        const reqParam = pwdChangeReqParam(location.state.email, tBox_password.value);

        pwdChange(reqParam)
        .then(data =>{
            alert("정상적으로 비밀번호가 변경되었습니다. 다시 로그인해주세요");
            nevigate("/login");
            return ;
        });
    }

    useEffect(() =>{
        if(!location.state || !location.state.email){
            alert("비정상적인 접근입니다.");
            nevigate("/");
        }
    },[]);

    return (<main className="flex flex-col bg-gray-100 min-h-screen items-center">
            <section className="w-800 p-2 mt-40">
                <LogoHeader>계정 비밀번호 재설정</LogoHeader>
                <LabelSection asChild isRequire label="새로운 비밀번호" className="mt-10">
                    <Input type="password" id="tBox_password" placeholder="새로운 비밀번호를 입력해주세요."></Input>
                </LabelSection>
                <LabelSection asChild isRequire label="새로운 비밀번호 확인">
                    <Input type="password" id="tBox_password_confirm" placeholder="새로운 비밀번호를 한번 더 입력해주세요."></Input>
                </LabelSection>

            
                {/* 버튼은 onClick 콜백 동작 불가 */}
                <Button asChild className="bg-violet-800 mt-10 w-full hover:bg-violet-600">
                    <div onClick={doPwdChange}>비밀번호 변경</div>
                </Button>
            </section>
        </main>
    );
}