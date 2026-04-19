import axios from "axios";
const API_URL = '/api/recibos'

export const getReciboMe = async () => {
    const token = localStorage.getItem('token');
    const recibos = await axios.get(`${API_URL}/me`, {
        headers: {
            Authorization: `Bearer ${token}`
        }
    });

    return recibos.data;
}

export const getReciboComunidad = async () => {
    const token = localStorage.getItem('token');
    const recibos = await axios.get(`${API_URL}/comunidad`, {
        headers: {
            Authorization: `Bearer ${token}`
        }
    });

    return recibos.data;
}

export const getReciboMorosos = async () => {
    const token = localStorage.getItem('token');
    const recibos = await axios.get(`${API_URL}/comunidad/morosos`, {
        headers: {
            Authorization: `Bearer ${token}`
        }
    });

    return recibos.data;
}

export const getReciboIdPendiente = async (id) => {
    const token = localStorage.getItem('token');
    const recibos = await axios.get(`${API_URL}/usuario/${id}/pendientes`, {
        headers: {
            Authorization: `Bearer ${token}`
        }
    });

    return recibos.data;
}

export const getRecibosPorCuota = async (id) => {
    const token = localStorage.getItem('token');
    const recibos = await axios.get(`${API_URL}/cuota/${id}`, {
        headers: {
            Authorization: `Bearer ${token}`
        }
    });

    return recibos.data;
}