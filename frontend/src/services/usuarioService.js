import axios from "axios";
const API_URL = '/api/usuarios';

export const getUsuarios = async () => {
    const token = localStorage.getItem('token');
    const usuarios = await axios.get(`${API_URL}/comunidad`, {
        headers: {
            Authorization: `Bearer ${token}`
        }
    });

    return usuarios.data;
}

export const getUsuariosSuper = async () => {
    const token = localStorage.getItem('token');
    const usuarios = await axios.get(`${API_URL}`, {
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

export const modificarUser = async(usuario) => {
    const token = localStorage.getItem('token');
    const respuesta = await axios.put(`${API_URL}/modificar`, usuario,  {
        headers: {
            Authorization: `Bearer ${token}`
        }
    });
    return respuesta.data;
}

export const modificarUserAdmin = async(id, usuario) => {
    const token = localStorage.getItem('token');
    const respuesta = await axios.put(`${API_URL}/admin/${id}`, usuario,  {
        headers: {
            Authorization: `Bearer ${token}`
        }
    });
    return respuesta.data;
}

export const crearUsuario = async(usuario) => {
    const token = localStorage.getItem('token');
    const respuesta = await axios.post(`${API_URL}`, usuario,  {
        headers: {
            Authorization: `Bearer ${token}`
        }
    });
    return respuesta.data;
} 

export const cambioPass = async(passwords) => {
    const token = localStorage.getItem('token');
    const respuesta = await axios.patch(`${API_URL}/pass`, passwords,  {
        headers: {
            Authorization: `Bearer ${token}`
        }
    });
    return respuesta.data;
}

export const cambioPassAdmin = async(id) => {
    const token = localStorage.getItem('token');
    const respuesta = await axios.patch(`${API_URL}/admin/${id}`, {}, {
        headers: {
            Authorization: `Bearer ${token}`
        }
    });
    return respuesta.data;
}
