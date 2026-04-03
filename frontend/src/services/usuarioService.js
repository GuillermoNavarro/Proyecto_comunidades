import axios from "axios";
const API_URL = '/api/usuarios';

export const getUsuarios = async () => {
    const token = localStorage.getItem('token');
    const usuarios = await axios.get(`${API_URL}/comunidad/1`, {
        headers: {
            Authorization: `Bearer ${token}`
        }
    });

    return usuarios.data;
}

export const bajaUsuario = async(id) => {
    const token = localStorage.getItem('token');
    const respuesta = await axios.delete(`${API_URL}/${id}`, {
         headers: {
            Authorization: `Bearer ${token}`
        }
    });

    return respuesta.data;
}

export const modificarUsuarioAdmin = async(id, usuario) => {
    const token = localStorage.getItem('token');
    const respuesta = await axios.put(`${API_URL}/admin/${id}`, usuario,  {
        headers: {
            Authorization: `Bearer ${token}`
        }
    });

    return respuesta.data;
}