import React, { useState, useEffect } from 'react';
import UserService from '../services/UserService';
import { useNavigate } from 'react-router-dom';


function Home() {
    const[publicMessage, setPublicMessage] = useState('Nothing');
    const nav = useNavigate();
    
    useEffect(() => {
        UserService.getPublicMesage().then(msg => {
            console.log(msg);
            setPublicMessage(msg.message);
        }).catch(error => {
            console.error(error);
        }) 
    }, [])
  return (
    <>
        <div>
            <p>Public messsage:</p>
            <p>{publicMessage}</p>
        </div>
        <div>
            <button onClick={() => nav(`/login`)}>Login</button>
            <button onClick={() => nav(`register`)}>Register</button>
        </div>
    </>
  )
}

export default Home