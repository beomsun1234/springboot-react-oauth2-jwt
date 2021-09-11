import React from 'react';

const MemberInfo = (props) => {
    const {email, name} = props.MemberInfo
    return (
        <div>
            <div>성함 : {name}</div>
            <div>이메일 : {email}</div>
        </div>
    );
};

export default MemberInfo;