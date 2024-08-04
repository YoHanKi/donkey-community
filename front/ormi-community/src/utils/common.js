import {v4 as uuidv4 } from "uuid";

export function GenerateLiElUUID(){
    return uuidv4().replaceAll("-", "");
}

export function maskingIpAddress(ipAddress){
    if(!ipAddress || typeof ipAddress  != "string") return ;

    return ipAddress.substring(0,7) + ".xxx.xxx";
}

export function getShortNickName(nickName){
    if(!nickName || typeof nickName !== "string") return "";
    return nickName.substring(0,nickName.length > 2 ? 2 : nickName.length);
}