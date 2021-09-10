import React from 'react';
import axios from 'axios';


const Home = () => {

    const token = window.sessionStorage.getItem("token")

    function f1(){
    
        alert("클릭")
        axios({
            method: "GET",
            url: "http://localhost:8080/api/v1/member",
            headers : {
                "Auth" : token
            }
        }).then((response)=>{
            console.log(response);
            const data = JSON.stringify(response)
            let test = data.map(as=>(as.data))
            console.log(test)
            alert(test)
        }).catch(Error=>{
            console.log(Error);
        })
    
    }
    function f2(){
    
        alert("클릭")
        axios({
            method: "GET",
            url: "http://localhost:8080/api/v2/member",
            headers : {
                "Auth" : token
            }
        }).then((response)=>{
            console.log(response);
            let data = JSON.stringify(response)
            console.log(response.data("data"))
            console.log(data)
            
        }).catch(Error=>{
            console.log(Error);
        })
    
    }
    
    return (
        <div>
            <h2>
            Home
            </h2>
            {token == null ? <a  href="http://localhost:8080/oauth2/authorization/google">구글로그인</a> : "login sucess"}
            {token == null ? null : <button onClick={() => { f1();}}>Click Me1</button>}
            {token == null ? null : <button onClick={() => { f2();}}>Click Me2</button>}
        </div>
        
    );
};

export default Home;