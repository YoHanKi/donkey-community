import LogoHeader from "@/components/Layout/LogoHeader";
import { Button } from "@/components/ui/button";
import { Input } from "@/components/ui/input";
import { loginReqParam } from "@/utils/Parameter";
import { correctRegxEmail, correctRegxPwd } from "@/utils/patternMatcher";
import { Link } from "react-router-dom";
import { login } from "@/utils/API";
import { useNavigate } from "react-router-dom";
import { GlobalContext } from "..";
import { useContext } from "react";

// 로그인 화면 컴포넌트
export default function Login(){
    const navigate = useNavigate();
    const {isLogin, setLoginUser} = useContext(GlobalContext)

    const doLogin = function(){
        const emailVal = document.getElementById("tBox_email").value;
        const pwdVal = document.getElementById("tBox_pwd").value;
        let reqParam = {};

        if(!correctRegxEmail(emailVal)){
            emailVal ? alert("이메일 형식이 알맞지 않습니다.") : alert("이메일값이 입력되지 않았습니다.");
            return ;
        }

        if(!correctRegxPwd(pwdVal)){
            pwdVal ? alert("비밀번호 형식이 알맞지 않습니다.") : alert("비밀번호가 입력되지 않았습니다.");
            return ;
        }

        //fetch Login success?
        //성공하면 쿠키나 토큰을 저장 하고 메인으로
        //실패하면 로그인 실패 안내 및 오류 메세지 출력
        reqParam = loginReqParam(emailVal, pwdVal);

        login(reqParam)
        .then(response => {
            setLoginUser(true);
            //jwt 토큰 발급 확인
            navigate("/");
        });

    }

    return (
        <main id="login-wrap" className="flex flex-col bg-gray-100 min-h-screen items-center">
            <section className="w-800 p-2 mt-60">
                <LogoHeader>로그인</LogoHeader>
                <Input id="tBox_email" type="email" placeholder="Email Address" className="mt-4"></Input>
                <Input id="tBox_pwd" type="password" placeholder="password" className="mt-4"></Input>
                <div className="flex justify-between items-center mt-4 text-violet-800">
                    <Link to="/password">비밀번호를 잊으셨나요?</Link>
                    <Link to="/signup">회원가입</Link>
                </div>
                {/* 버튼은 onClick 콜백 동작 불가 */}
                <Button asChild className="bg-violet-800 mt-4 w-full hover:bg-violet-600">
                    <div onClick={doLogin}>로그인</div>
                </Button>
            </section>
        </main>
    );
}