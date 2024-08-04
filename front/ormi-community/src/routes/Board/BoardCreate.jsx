import LabelSection from "@/components/Layout/LabelSection";
import LogoHeader from "@/components/Layout/LogoHeader";
import { Select, SelectTrigger, SelectContent, SelectLabel, SelectItem, SelectGroup, SelectValue } from "@/components/ui/select";
import { Button } from "@/components/ui/button";
import { appendBoard, fetchIndustryList } from "@/utils/API";
import { useContext, useEffect, useState } from "react";
import { Input } from "@/components/ui/input";
import { useNavigate } from "react-router-dom";
import { appendBoardReqParam } from "@/utils/Parameter";
import { GlobalContext } from "@/index";

export default function BoardCreate(){
    const nevigate = useNavigate();
    const [selectedIndustry, selectIndustry] = useState([]);
    const [industries, setIndustries] = useState([]);
    const [tBox_boardName, setBoardName] = useState("");

    // 전역변수 - 유저 정보
    const {userInfo} = useContext(GlobalContext);

    // 전역변수 - 로그인 상태
    const {isLogin} = useContext(GlobalContext);
    
    const doRegisterBoard = () =>{
        const reqParam = appendBoardReqParam(tBox_boardName, selectedIndustry, null, userInfo.email)

        appendBoard(reqParam)
        .then(data =>{
            alert("정상적으로 처리되었습니다. 실제 추가는 관리자가 허용하면 이루어집니다.");
            nevigate("/");
        })
        .catch(e => console.log(e));
    };

    useEffect(() =>{
        if(!isLogin){
            alert("로그인이 필요합니다.");
            nevigate(-1);
            return ;
        }

        fetchIndustryList()
        .then(data => setIndustries(data));
    },[]);

    return (
        <main className="flex flex-col bg-gray-100 min-h-screen items-center justify-center">
            <section className="w-900 flex flex-col items-center bg-white p-20 rounded-lg shadow-2xl">
                <LogoHeader></LogoHeader>
                <LabelSection isRequire asChild label="게시판명" className="mt-4">
                    <Input type="text" onChange={(ev) =>setBoardName(ev.target.value)} id="tBox_doc_title" placeholder="게시글 제목을 입력해주세요."></Input>
                </LabelSection>
                <LabelSection isRequire asChild label="업종" className="mt-4">
                    <Select onValueChange={selectIndustry}>
                        <SelectTrigger>
                            <SelectValue placeholder="업종"></SelectValue>
                        </SelectTrigger>
                        <SelectContent>
                            {
                                industries.map(industry => <SelectItem value={industry.industryId}>{industry.industryName}</SelectItem>)
                            }
                        </SelectContent>
                    </Select>
                </LabelSection>
                <div className="mt-4 w-full flex justify-end items-center gap-4">
                    <Button asChild className="cursor-pointer bg-white hover:bg-gray-400 hover:text-white hover:border-white border border-gray-400 text-black font-bold">
                        <div onClick={() => nevigate(-1)}>취소</div>
                    </Button>
                    <Button asChild className="cursor-pointer bg-violet-800 hover:bg-violet-600 font-bold">
                        <div onClick={doRegisterBoard}>저장하기</div>
                    </Button>
                </div>
            </section>
        </main>
    );
}