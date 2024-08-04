import DonkeyLogo from "../Icon/DonkeyLogo";


// 로그인, 회원가입, 비밀번호찾기 등 로고와 함께 Title이 입력되야하는 경우 사용하는 컴포넌트
export default function LogoHeader({children}){
    return (
        <div className="w-full space-y-2">
            <DonkeyLogo className="w-full h-32"></DonkeyLogo>
            <p className="text-center text-stone-600 text-3xl font-bold">
                {children}
            </p>
        </div>
    );
}