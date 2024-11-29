import axios from "axios";
import { jwtDecode } from "jwt-decode";

class UserService {
    static BASE_URL = 'http://localhost:8080';

    static async getPublicMesage() {
        try {
            const response = await axios.get(`${this.BASE_URL}/auth/welcome`);
            return response.data;
        } catch(err) {
            throw err;
        } 
    }

    static async signup(username, password, role) {
        try {
            const response = await axios.post(`${this.BASE_URL}/auth/signup`, {username, password, role});
            return response.data;
        } catch(err) {
            throw err;
        }
    }

    static async login(username, password) {
        try {
            const response = await axios.post(`${this.BASE_URL}/auth/login`, {username, password});
            return response.data;
        } catch(err) {
            throw err;
        }
    }

    static async getAdminMessage(auth_token) {
        try {
            const response = await axios.get(`${this.BASE_URL}/admin`, {
                headers: {Authorization: `Bearer ${auth_token}`}
            });
            return response.data;
        } catch(err) {
            throw err;
        }
    }

    static async getUserMessage(auth_token) {
        try {
            const response = await axios.get(`${this.BASE_URL}/user`, {
                headers: {Authorization: `Bearer ${auth_token}`}
            });
            return response.data;
        } catch(err) {
            throw err;
        }
    }

    static async logout(auth_token) {
        try {
            const response = await axios.post(`${this.BASE_URL}/auth/logout`, null, {
                headers: {Authorization: `Bearer ${auth_token}`}
            });
            return response.status;
        } catch(err) {
            throw err;
        }
    }

    static isAuthenticated() {
        const token = window.localStorage.getItem('auth_token');
        return !!token;
    }

    static isAdmin() {
        const token = window.localStorage.getItem('auth_token');
        const decoded = jwtDecode(token);
        const roles = decoded.role;
        const role = roles[0].authority;
        return role === 'ADMIN';
    }

    static adminOnly() {
        return this.isAuthenticated() && this.isAdmin();
    }
}

export default UserService;