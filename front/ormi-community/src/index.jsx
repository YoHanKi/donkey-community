import { useState, createContext, useEffect } from 'react';
import { fetchUserInfo, isLoginUser } from "@/utils/API";

const GlobalContext = createContext({
  selectBoardID: "",
  setSelectBoardID: () =>{},
  isLogin: false,
  setLoginUser: () =>{},
  userInfo: {},
  setUserInfo: () =>{} ,
  reload: -1 ,
  setReload: () =>{}
});

const ContextProvider = ({children}) =>{
    const [selectBoardID, setSelectBoardID] = useState("");
    const [isLogin, setLoginUser] = useState(false);
    const [userInfo, setUserInfo] = useState({});
    const [reload, setReload] = useState(-1);

    useEffect(() =>{
      setLoginUser(isLoginUser());
    },[]);

    useEffect(() =>{
      isLogin ?
      fetchUserInfo()
      .then(data => setUserInfo(data)) :
      setUserInfo(null);
    },[isLogin]);
    

    return (
        <GlobalContext.Provider value={{
            selectBoardID,
            isLogin,
            userInfo,
            reload,
            setSelectBoardID,
            setLoginUser,
            setUserInfo,
            setReload
        }}>
            {children}
        </GlobalContext.Provider>
    )
}


export {GlobalContext, ContextProvider}