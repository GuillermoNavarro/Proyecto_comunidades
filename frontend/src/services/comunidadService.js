import axios from "axios";
const API_URL = '/api/comunidades';

export const getComunidades = async () => {
    const token = localStorage.getItem('token');
    const comunidades = await axios.get(`${API_URL}`, {
        headers: {
            Authorization: `Bearer ${token}`
        }
    });

    return comunidades.data;
}

export const getComunidadId = async (id) => {
    const token = localStorage.getItem('token');
    const comunidades = await axios.get(`${API_URL}/${id}`, {
        headers: {
            Authorization: `Bearer ${token}`
        }
    });

    return comunidades.data;
}


export const getComunidadesActivo = async () => {
    const token = localStorage.getItem('token');
    const comunidades = await axios.get(`${API_URL}/activos`, {
        headers: {
            Authorization: `Bearer ${token}`
        }
    });

    return comunidades.data;
}

export const crearComunidad = async (datos) => {
    const token = localStorage.getItem('token');
    const comunidades = await axios.post(`${API_URL}`, datos, {
        headers: {
            Authorization: `Bearer ${token}`
        }
    });

    return comunidades.data;
}

export const modificarComunidad = async (id, datos) => {
    const token = localStorage.getItem('token');
    const comunidades = await axios.put(`${API_URL}/${id}`, datos, {
        headers: {
            Authorization: `Bearer ${token}`
        }
    });

    return comunidades.data;
}