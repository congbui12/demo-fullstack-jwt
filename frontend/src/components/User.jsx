import React, { useState, useEffect } from 'react';
import UserService from '../services/UserService';
import { useNavigate } from 'react-router-dom';

function User() {
    const[userMessage, setUserMesssage] = useState('Nothing');
    const nav = useNavigate();

    useEffect(() => {
        const token = window.localStorage.getItem('auth_token');
        if(token !== null) {
            UserService.getUserMessage(token)
            .then(msg => {
                console.log(msg);
                setUserMesssage(msg.message);
            }).catch(error => {
                console.error(error);
            });
        } else {
            throw Error('Authentication failed!');
        }
    }, []);

    const handleLogout = () => {
        const token = window.localStorage.getItem('auth_token');
        UserService.logout(token)
        .then(status => {
            console.log(status);
            if(status == 200) {
                window.localStorage.removeItem('auth_token');
                nav(`/`);
            }
        })
        .catch(error => {
            console.error(error);
        });
    }

  return (
    <div>
        <button onClick={handleLogout}>Logout</button>
        <p>User Message:</p>
        <p>{userMessage}</p>
    </div>
  )
}

export default User