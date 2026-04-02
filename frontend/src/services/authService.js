import axios from "axios";
const API_URL = '/api/login';

export const getPerfil = async () => {
    const token = localStorage.getItem('token');
    const response = await axios.get('/api/usuarios/me', {
        headers: {
            Authorization: `Bearer ${token}`
        }
    });

    return response.data;
}

export const login = async(email, password) => {
    const response = await axios.post(API_URL, {email, password});
    const token = response.data;

    if(token){
        localStorage.setItem('token', token);
        const perfil = await getPerfil();
        return {token, perfil};
    }
    

    return null;
}