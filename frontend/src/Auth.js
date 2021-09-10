import React from 'react';
import queryString from 'query-string';
import { Redirect} from 'react-router-dom';

const Auth = ({location}) => {

    const queryData = queryString.parse(location.search, { ignoreQueryPrefix: true });
    const token = queryData.token;
    window.sessionStorage.setItem("token",token)
    return (
        <Redirect to={{
            pathname: '/', 
        }}
/>
    );
};

export default Auth;