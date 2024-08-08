import axios from 'axios';
import mem from "mem";
import { getAccessToken, getRefreshToken, removeAccessToken, removeRefreshToken, setAccessToken, setRefreshToken } from './Cookie';
const URL = "http://13.209.187.24:8080";
const ipCheckURL = "https://geolocation-db.com/json/";

axios.interceptors.response.use(
    (res) => res,
    async (res) => {
    const { config, response } = res;

    //token 재시도 이후, 혹은 별도의 이유로 에러가 나면 reject
    if(config.url === (URL + "/refreshToken") || response.status !== 401 || config.sent){
        return Promise.reject(res);
    }

    config.sent = true;
    await getNewAccessToken();

    if(getAccessToken()){
        return axios(config);
    }

    return Promise.reject(res);
});

//Access Token을 계속 들고있는건 보안상 이슈 가능성 존재
const donkeyGet = async (URL) => {
    const accessToken = getAccessToken();
    const refreshToken = getRefreshToken();

    const headers = accessToken ?
                    { Authorization: JSON.stringify({accessToken, refreshToken}) } :
                    {};

    return axios.get(URL, {
        headers: headers
    });
}

const donkeyPost = async (URL, body) => {
    const accessToken = getAccessToken();
    const refreshToken = getRefreshToken();
    const headers = accessToken ?
                    { Authorization: JSON.stringify({accessToken, refreshToken}) } :
                    {};
    return axios.post(URL, body, {
        headers: headers
    });
}

const donkeyPut = async (URL, body) => {
    const accessToken = getAccessToken();
    const refreshToken = getRefreshToken();
    const headers = accessToken ?
                    { Authorization: JSON.stringify({accessToken, refreshToken}) } :
                    {};

    return axios.put(URL, body, {
        headers: headers
    })
}

const donkeyDelete = async (URL, body) => {
    const accessToken = getAccessToken();
    const refreshToken = getRefreshToken();
    const headers = accessToken ?
                    JSON.stringify({accessToken, refreshToken}) :
                    "";

    return axios.delete(URL, {
        headers: {
            "Authorization" : headers
        },
        data: body
    });
}

/**
 * 
 * @returns { {commentId: String, nickname: String, commentCreatorIp: String || null , email: String, commentDate: "YYYY-MM-DD HH:MM:SS.XXX", commentContent: String, likeCount: number}[] } API returns
 */
export async function fetchDocComments(docId){
    const DocCommentURL = URL + `/comment/list/${docId}`;

    return donkeyGet(DocCommentURL)
    .then((response) => response.data);
}

/**
 * @returns { {nickname: String, email: String, {industryId: String, industryName: String}[] } }
 */
export async function fetchUserInfo(){
    const userInfoURL = URL + `/member/userinfo`;

    return donkeyGet(userInfoURL)
    .then((response) => response.data);
}

/**
 * @breif 게시판id와 pageNumber로 게시글을 불러온다.
 * @param {String} boardId 
 * @param {number} docPageNumber 
 * @returns { {docId: String, docTitle: String, docContent : String, docCreateDate: YYYY-MM-DD HH:MM:SS.XXX, nickname: String || null, email: String, memberRoleName: USER, boardId: String, boardName: String, likeCount: number, viewCount: number} }
 */
export async function fetchDocumentList(boardId, docPageNumber){
    const DocumentListURL = URL +  `/document/list/${boardId}?page=${docPageNumber}`;

    return donkeyGet(DocumentListURL)
    .then((response) => response.data);
}

export async function fetchDocument(docId){
    const DocumentURL = URL + `/document/${docId}`;

    return donkeyGet(DocumentURL)
    .then((response) => response.data);
}

/**
 * @brief 게시판 생성 허가가 완료된 목록을 조회하는 API입니다.
 * @returns  { {boardId: UUID, boardName: String, industyrName: String, comName: String}[] }
 */
export async function fetchBoardList(){
    const BoardListURL = URL + "/board/true";
    return donkeyGet(BoardListURL)
    .then(response => response.data);
}

/**
 * @brief 아직 게시판 생성 허가가 되지 않은 게시판 목록을 불러온다.
 * @returns  { {boardId: UUID, boardName: String, industyrName: String, comName: String}[] }
 */
export async function fetchNotApproveBoardList(){
    const NotApproveBoardListURL = URL + "/board/false";
    return donkeyGet(NotApproveBoardListURL) 
    .then((response) => response.data);
}

/**
 * @breif 업종 목록을 조회한다. 
 * @returns { { industryId: String, industryName: String, industryDescription: String }[] }
 */
export async function fetchIndustryList(){
    const IndustryListURL = URL + "/industry";
    return donkeyGet(IndustryListURL) 
    .then((response) => response.data);
}

/**
 * @brief 비밀번호 찾기 질문을 서버로부터 받아온다.
 * @returns  { { passwordQuestionId : String, passwordQuestion: String}[] }
 */
export async function fetchPasswordQuestion(){
    const PasswordQuestionURL = URL + "/passwordquestion";
    return donkeyGet(PasswordQuestionURL)
    .then(async (response) => response.data);
}

/**
 * @brief 성별 데이터를 가져온다. 
 * @returns { {value: String , title: String }[] }
 */
export async function fetchGender(){
    return [
        {value: "M", title: "남성"},
        {value: "F", title: "여성"}
    ]
}

/**
 * @brief 내 IP 확인
 */
export async function fetchOwnIp(){
    const ipData = await fetch(ipCheckURL);
    const locationIp = await ipData.json();
    return locationIp;
}

/**
 * @brief 좋아요 갯수 조회 API
 */
export async function fetchLikeCount(uuid){
    const likeCountURL = URL + `/likeit/${uuid}`;
    return donkeyGet(likeCountURL)
    .then((response) => response.data);
}

/**
 * @param { signupReqParam }
 * @brief 회원가입 API 
 */
export async function signup(signupReqParam){
    const signupURL = URL + `/member/register`;
    return donkeyPost(signupURL, signupReqParam)
    .then((response) => response.data);
}

/**
 * 
 * @param { loginReqParam } loginReqParam 
 * @returns 로그인 결과 반환
 */
export async function login(loginReqParam){
    const loginURL = URL + "/api/login";
    //응답 데이터 : accessToken, refreshToken
    return donkeyPost(loginURL, loginReqParam)
    .then((response) => response.data) 
    .then(json => {
        setAccessToken(json.accessToken);
        setRefreshToken(json.refreshToken);
    })
    .catch(response =>{
        alert("로그인에 실패하였습니다. 이메일 혹은 비밀번호를 확인해주세요");
    });
}

export async function writeDocument(docWriteReqParam){
    const writeDocumentURL = URL + "/document/manage";
    return donkeyPost(writeDocumentURL, docWriteReqParam)
    .then((response) => response.data)
    .catch(e =>{
        removeAccessToken();
        removeRefreshToken();
        alert("로그인이 만료되었습니다. 다시 로그인해주세요");
        location.href = "/";
    });
}

export async function editDocument(documentEditReqParam, docId){
    const editDocumentURL = URL + `/document/manage/${docId}`;

    return donkeyPut(editDocumentURL, documentEditReqParam)
    .then((response) => response.data)
}

export async function removeDocument(docId){
    const removeDocumentURL = URL + `/document/manage/${docId}`;

    return donkeyDelete(removeDocumentURL)
    .then((response) => response.data);
}

export async function likeIt(uuid){
    const likeItURL = URL + `/likeit/${uuid}`;
    return donkeyPut(likeItURL)
    .then((response) => response.data);
}

export async function writeComment(commentWriteReqParam, docId){
    const writeCommentURL = URL + `/comment/${docId}`;
    return donkeyPost(writeCommentURL, commentWriteReqParam)
    .then((response) => response.data);
}

export async function appendBoard(appendBoardReqParam){
    const appendBoardURL = URL + "/board";
    return donkeyPost(appendBoardURL, appendBoardReqParam)
    .then((response) => response.data);
}

export function acceptBoardPublicing(acceptBoardPublicingReqParam){
    //Accept API 호출
    const acceptBoardPublicingURL = URL + "/admin/board";

    return donkeyPut(acceptBoardPublicingURL, acceptBoardPublicingReqParam)
    .then((response) => response.data);
}

export async function matchPasswordHint(matchPasswordHintReqParam){
    const matchPasswordHintURL = URL + `/member/findpassword`;

    return donkeyPost(matchPasswordHintURL, matchPasswordHintReqParam)
    .then((response) => response.data);
}

export async function pwdChange(pwdChangeReqParam){
    const pwdChangeURL = URL + "/member/changepassword";

    return donkeyPost(pwdChangeURL, pwdChangeReqParam)
    .then((response) => response.data);
}

export async function appendIndustry(appendIndustryReqParam){
    // 업종 추가 API
    const appendIndustryURL = URL + `/admin/industry`;

    return donkeyPost(appendIndustryURL, appendIndustryReqParam)
    .then((response) => response.data);
}

    // 비밀번호 질문 추가 API
export async function appendPasswordQuestion(passwordQuestionReqParam){
    const appendPasswordQuestionURL = URL + "/admin/passwordquestion";

    return donkeyPost(appendPasswordQuestionURL, passwordQuestionReqParam)
    .then((response) => response.data);
}

export async function editUserInfo(userEditInfo){
    const editUserInfoURL = URL + `/member/modifyInfo`;
    return donkeyPut(editUserInfoURL, userEditInfo)
    .then(response => response.data);
}

/**
 * 다수의 API가 토큰 재발급을 의존할 경우를 대비한 메모미제이션
 */
export const getNewAccessToken = mem(async () =>{
    const newAccessTokenURL = URL + "/refreshToken";
    return donkeyGet(newAccessTokenURL)
    .then(response => response.data)
    .then(json => setAccessToken(json.accessToken));
}, {maxAge: 1000})

/**
 * @breif 토큰 유무로 로그인 상태 확인하므로 토큰 삭제만 진행
 */
export const logout = () =>{
    removeAccessToken();
    removeRefreshToken();
    location.reload();
}

/**
 * @breif 현재 유저가 로그인한 상태인지 확인한다.
 * AccessToken이 있을 경우 true
 * 사유: 실제 로그인한 유저인지 확인하는 API를 호출하면 실제 로그인한 유저가 만료되었는지 확인 가능하므로
 * @returns 
 */
export function isLoginUser(){
    return getAccessToken() ? true : false;
}

export function uuidSave(uuid){
    let uuids = JSON.parse(localStorage.getItem("uuids")) || [];
    localStorage.setItem("uuids", JSON.stringify([...uuids, uuid]));
}

export function isExistUUID(uuid){
    let uuids = JSON.parse(localStorage.getItem("uuids")) || [];
    uuids = uuids.filter(savedUUID => uuid == savedUUID);

    return uuids.length > 0 ? true : false;
}