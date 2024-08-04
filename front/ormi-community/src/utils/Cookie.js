import { Cookies } from "react-cookie";


const cookies = new Cookies();

/**
 * 
 * @param {String} name 
 * @param {String} value 
 * @param {any} options 
 */
export const setCookie = (name, value, options) =>{
    return cookies.set(name, value, {...options});
}

/**
 * 
 * @param {String} name 
 */
export const getCookie = (name) =>{
    return cookies.get(name);
}

/**
 * @brief 접근 토큰 반환 없을 경우 null
 */
export const getAccessToken = () => {
    return getCookie('access_token') || null;
}

/**
 * @breif 리프레쉬 토큰 반환 없을 경우 null
 */
export const getRefreshToken = () => {
    return getCookie('refresh_token') || null;
}

/**
 * 
 * @param {String} tokenString 
 */
export const setAccessToken = (tokenString) =>{
    if(!tokenString) return ;
    setCookie('access_token', tokenString, { path: '/', secure: false, sameSite: 'lax'})
}

/**
 * 
 * @param {String} tokenString 
 */
export const setRefreshToken = (tokenString) => {
    if(!tokenString) return ;
    setCookie('refresh_token', tokenString, { path: '/', secure: false, sameSite: 'lax'})
}


export const removeAccessToken = () =>{
    cookies.remove('access_token');
}

export const removeRefreshToken = () =>{
    cookies.remove('refresh_token');
}