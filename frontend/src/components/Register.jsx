import React, { useState } from 'react';
import UserService from '../services/UserService';

function Register() {
    const[username, setUsername] = useState('');
    const[password, setPassword] = useState('');
    const[role, setRole] = useState('USER');
    const[msg, setMsg] = useState(
        <p>Already have an account? <a href='/login'>Login now!</a></p>
    );

    const handleRegister = (e) => {
        e.preventDefault();
        UserService.signup(username, password, role)
        .then(msg => {
            console.log(msg);
            setUsername('');
            setPassword('');
            setRole('USER');
            setMsg(
                <p>Register successfully. Click <a href='/login'>here</a> to login.</p>
            );
        })
        .catch(error => {
            console.error('Register failed: ', error);
        });
    }

  return (
    <form>
        <div>
            <label>Username</label>
            <input name='username' value={username} onChange={e => setUsername(e.target.value)}/>
        </div>

        <div>
            <label>Password</label>
            <input name='password' value={password} onChange={e => setPassword(e.target.value)}/>
        </div>

        <div>
            <label>Choose a role:</label>
            <select value={role} onChange={e => setRole(e.target.value)}>
                <option value='USER'>User</option>
                <option value='ADMIN'>Admin</option>
            </select>
        </div>

        <button type='submit' onClick={handleRegister}>Register</button>
        {msg}
    </form>
  )
}

export default Register