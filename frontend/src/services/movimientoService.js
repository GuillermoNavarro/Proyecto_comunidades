import axios from "axios";
const API_URL = '/api/movimientos';

export const crearMovimiento = async (datos) => {
    const token = localStorage.getItem('token');
    const movimientos = await axios.post(`${API_URL}`, datos, {
        headers: {
            Authorization: `Bearer ${token}`
        }
    });

    return movimientos.data;
}

export const getMovimientoComunidad = async () => {
    const token = localStorage.getItem('token');
    const movimientos = await axios.get(`${API_URL}/comunidad`, {
        headers: {
            Authorization: `Bearer ${token}`
        }
    });

    return movimientos.data;
}

export const getMovimientoMe = async () => {
    const token = localStorage.getItem('token');
    const movimientos = await axios.get(`${API_URL}/me`, {
        headers: {
            Authorization: `Bearer ${token}`
        }
    });

    return movimientos.data;
}

export const getMovimientoId = async (id) => {
    const token = localStorage.getItem('token');
    const movimientos = await axios.get(`${API_URL}/${id}`, {
        headers: {
            Authorization: `Bearer ${token}`
        }
    });

    return movimientos.data;
}