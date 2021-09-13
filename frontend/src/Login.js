import React from 'react';
import axios from 'axios';
import KakaoLogin from 'react-kakao-login';
import {GoogleLogin} from "react-google-login";
import FacebookLogin from 'react-facebook-login';

const state = {
    data : null
}
const config = {
    headers: {
        "Content-Type": "application/json; charset=utf-8"
    },
};
const Login = () => {
    const onSuccess = (response) => {
        console.log(response);
        const data = JSON.stringify(response)
        console.log(data);
        axios.post('http://localhost:8080/auth/token',
            data,
            config
        )
            .then(function (response) {
            console.log(response);
            console.log(response.data);
            sessionStorage.setItem("token", response.data)
            window.location.href = '/'
        })
        .catch(function (error) {
            console.log(error);
        });
        
        
    }
// 받아온 데이터 처리하기
const onFailure = (error) => {
    console.log(error);
    }   
    return (
        <div>
            <div id="kakao">
                <KakaoLogin 
                token="137e0846ccca1d617c49511778c8a04c"
                buttonText="Kakao"
                onSuccess={onSuccess}
                onFailure={onFailure}
                onLogout={console.info}
                getProfile="true"
                />
            </div>
            <div id="google">
                <GoogleLogin
                clientId={"418062724925-9ou23s69favr33e0tq2f6uruoa89tkit.apps.googleusercontent.com"}
                buttonText="Google"
                onSuccess={onSuccess}
                onFailure={onFailure}
                cookiePolicy={"single_host_origin"}
                />
            </div>
            <div id="facebook">
                <FacebookLogin
                appId="626075848384794"
                autoLoad={true}
                fields="name,email,picture"
                callback={onSuccess}
                />
            </div>
        </div>
    );
};

export default Login;
