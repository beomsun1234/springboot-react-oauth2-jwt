import React from 'react';
import KakaoLogin from 'react-kakao-login';
import {GoogleLogin} from "react-google-login";
const Login = () => {
    const onSuccess = (response) => {
        console.log(response);
        //const data = JSON.stringify(response)
        //console.log(JSON.stringify(response));
        
    }
// 받아온 데이터 처리하기
const onFailure = (error) => {
    console.log(error);
    }   
    return (
        <div>
            <KakaoLogin 
            token="137e0846ccca1d617c49511778c8a04c"
            onSuccess={onSuccess}
            onFailure={onFailure}
            onLogout={console.info}
            getProfile="true"
            />
            <GoogleLogin
            clientId={"418062724925-9ou23s69favr33e0tq2f6uruoa89tkit.apps.googleusercontent.com"}
            buttonText="Login"
            onSuccess={onSuccess}
            onFailure={onFailure}
            cookiePolicy={"single_host_origin"}
            />
        </div>
    );
};

export default Login;
