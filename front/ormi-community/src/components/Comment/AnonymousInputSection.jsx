import { maskingIpAddress } from "@/utils/common";
import { Input } from "../ui/input";


export default function AnonymousInputSection({ownIP, setNickName, setPassword}){
    return (
        <section className="flex justify-between items-center mt-4">
            <div className="flex gap-4">
                <Input type="text" placeholder="닉네임" onChange={(ev) => setNickName(ev.target.value)}></Input>
                <Input type="password" placeholder="비밀번호" onChange={(ev) => setPassword(ev.target.value)}></Input>
            </div>
            <span className="text-gray-400">ip: {maskingIpAddress(ownIP) || ""}</span>
        </section>
    );
}