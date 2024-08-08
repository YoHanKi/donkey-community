/**
 * @brief 이메일 정규식 형태가 일치하는지 확인합니다.
 * The email couldn't start or finish with a dot
 * The email shouldn't contain spaces into the string
 * The email shouldn't contain special chars (<:, *,ecc)
 * The email could contain dots in the middle of mail address before the @
 * The email could contain a double doman ( '.de.org' or similar rarity)
 * @param {String} inputString 
 * @returns boolean
 */
export function correctRegxEmail(inputString){
    const emailRegx = new RegExp(/^((?!\.)[\w\-_.]*[^.])(@\w+)(\.\w+(\.\w+)?[^.\W])$/gm);
    return emailRegx.test(inputString)
}

/**
 * @brief 비밀번호 정규식 형태가 일치하는지 확인합니다.
 * 형태는
 * password must contain 1 number (0-9)
 * password must contain 1 uppercase letters
 * password must contain 1 lowercase letters
 * password must contain 1 non-alpha numeric number
 * password is 8-16 characters with no space
 * @param {String} inputString 
 * @returns boolean
 */
export function correctRegxPwd(inputString){
    const pwdRegx = new RegExp(/^(?=.*\d)(?=.*[A-Z])(?=.*[a-z])(?=.*[^\w\d\s:])([^\s]){8,16}$/gm);
    return pwdRegx.test(inputString);
}

