export default function IndustryArticle({passwordQuestion, order}){
    return (
        <li className="p-4 flex flex-col border rounded-sm">
                <span>{order}번째 비밀번호 찾기 질문: {passwordQuestion}</span>
        </li>
    );
}