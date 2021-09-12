import React, {  useState } from 'react';
import axios from 'axios';
import MemberInfo from './components/MemberInfo';


const Home = () => {

    const [member, setMember ] = useState([]);

    const token = window.sessionStorage.getItem("token")

    function f1(){
    
        alert("클릭")
        axios({
            method: "GET",
            url: "http://ec2-3-22-108-197.us-east-2.compute.amazonaws.com/api/v1/member",
            headers : {
                "Auth" : token
            }
        }).then((response)=>{
            console.log("1"+response);
            const data = JSON.data(response)
            console.log("2"+data)
        }).catch(Error=>{
            console.log(Error);
        })
    
    }
    function f2(){
    
        alert("클릭")
        axios({
            method: "GET",
            url: "http://ec2-3-22-108-197.us-east-2.compute.amazonaws.com:8080/api/v2/member",
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
            </h2>
            <div>     
                {token == null ? <a  href="http://ec2-3-22-108-197.us-east-2.compute.amazonaws.com:8080/oauth2/authorization/google">구글로그인</a> : "loginsucess"} {token == null ? null : <button onClick={() => { f1();}}>연결 테스트</button>}
            </div>
                {token == null ? null : <button onClick={() => { f2();}}>내정보 보기</button>}
                <MemberInfo MemberInfo = {member}></MemberInfo>
            </div>
    );
};

export default Home;