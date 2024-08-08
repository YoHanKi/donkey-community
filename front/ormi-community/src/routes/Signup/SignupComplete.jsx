import CompleteIcon from "@/components/Icon/CompleteIcon";
import LogoHeader from "@/components/Layout/LogoHeader";
import { Button } from "@/components/ui/button";
import { useNavigate } from "react-router-dom";

export default function SignupComplete(){
    //변수 선언 지역
    const navigate = useNavigate();
    const goToLogin = () =>{
        navigate("/login");
    }

    return (
            <main className="flex flex-col bg-gray-100 min-h-screen items-center justify-center">
                <section className="w-800 flex flex-col items-center bg-white p-20 rounded-lg shadow-2xl">
                    <LogoHeader></LogoHeader>
                    <CompleteIcon></CompleteIcon>
                    <h2 className="text-center text-stone-600 text-3xl font-bold">진심으로 환영합니다!</h2>
                    <p className="text-center text-gray-300 font-normal mt-10 leading-10">
                        입력하신 계정으로 가입이 완료되었습니다.<br />
                        계정으로 로그인할 수 있으며, 동키의 모든 기능을 사용하실 수 있습니다! <br/>
                        다양한 게시판에서 여러 회원과 소통해 보세요! <br />
                        저희 동키는 회원님의 취업을 진심으로 응원하고 바라고 있습니다.
                    </p>
                    <Button asChild className="mt-10 bg-violet-800 w-4/5">
                        <div onClick={goToLogin}>로그인</div>
                    </Button>
                </section>
            </main>
        );
}