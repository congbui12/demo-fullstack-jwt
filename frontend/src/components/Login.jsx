import React, { useState } from 'react';
import UserService from '../services/UserService';
import { useNavigate } from 'react-router-dom';

function Login() {
    const[username, setUsername] = useState('');
    const[password, setPassword] = useState('');
    const nav = useNavigate();

    const handleLogin = (e) => {
        e.preventDefault();
        UserService.login(username, password)
        .then(msg => {
            console.log(msg);
            if(msg.token) {
                window.localStorage.setItem('auth_token', msg.token);
                
            } else {
                console.error('Token not found');
            }

            setUsername('');
            setPassword('');

            const isAuthenticated = UserService.isAuthenticated();
            const isAdmin = UserService.isAdmin();

            if(isAuthenticated && isAdmin) {
                nav(`/admin`);
            } else if (isAuthenticated && !isAdmin) {
                nav(`/user`);
            }
        })
        .catch(error => {
            console.error('Login failed: ', error);
            nav(`/`);
        });
    }
  return (
    <form>
        <div>
            <label>Username</label>
            <input name='username' value={username} onChange={(e) => setUsername(e.target.value)}/>
        </div>
        <div>
            <label>Password</label>
            <input name='password' value={password} onChange={(e) => setPassword(e.target.value)}/>
        </div>
        <button type='submit' onClick={handleLogin}>Login</button>
        <p>Don't have an account? <a href='/register'>Register now!</a></p>
    </form>
  )
}

export default Login