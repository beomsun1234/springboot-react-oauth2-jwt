import React, {  useState } from 'react';
import axios from 'axios';
import MemberInfo from './components/MemberInfo';
//import kakao from './img/kakao_login_medium_narrow'

const Home = () => {

    const [member, setMember ] = useState([]);
    
    const token = window.sessionStorage.getItem("token")

    function f1(){
        sessionStorage.removeItem("token")
        window.location.replace("/")
    }
    function f2(){
        axios({
            method: "GET",
            url: "/api/v2/member",
            headers : {
                "Auth" : token
            }
        }).then((response)=>{
            response= response.data
            console.log(response)
            setMember(response)
        }).catch(Error=>{
            console.log(Error);
        })
    }
    return (
        <div>
            <h2>
            Home
            {token == null ? null : <button onClick={() => { f1();}}>로그아웃</button>}
            </h2>
            <div id="google" class="g-signin2"> 
            {token == null ? <a  href="/api/oauth2/authorization/google">구글로그인</a> : null} 
            </div>
            <div id="naver">
            {token == null ? <a  href="/api/oauth2/authorization/naver">네이버로그인</a> : null}
            </div>
            <div id="kakao">
            {token == null ? <a  href="/api/oauth2/authorization/kakao">카카오로그인</a> : null}
            </div>
                {token == null ? null : <button onClick={() => { f2();}}>내정보 보기</button>}
                <MemberInfo MemberInfo = {member}></MemberInfo>        
            </div>
    );
}

export default Home;