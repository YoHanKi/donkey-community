export default function IndustryArticle({industryId ,industryName, industryDescription}){
    return (
        <li className="p-4 flex flex-col border rounded-sm">
                <span>업종: {industryName}</span>
                <span>설명: {industryDescription}</span>
        </li>
    );
}