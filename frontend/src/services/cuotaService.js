import axios from "axios";
const API_URL = '/api/cuotas';

export const crearCuota = async (datos, idUsuario = null) => {
    const token = localStorage.getItem('token');
    const url = idUsuario ? `${API_URL}?idUsuario=${idUsuario}` : API_URL;
    const cuotas = await axios.post(url, datos, {
        headers: {
            Authorization: `Bearer ${token}`
        }
    });

    return cuotas.data;
}

export const getCuotaComunidad = async () => {
    const token = localStorage.getItem('token');
    const cuotas = await axios.get(`${API_URL}/comunidad`,  {
        headers: {
            Authorization: `Bearer ${token}`
        }
    });

    return cuotas.data;
}

export const getCuotaId = async (id) => {
    const token = localStorage.getItem('token');
    const cuotas = await axios.get(`${API_URL}/${id}`,  {
        headers: {
            Authorization: `Bearer ${token}`
        }
    });

    return cuotas.data;
}

export const putCuotaId = async (id, datos) => {
    const token = localStorage.getItem('token');
    const cuotas = await axios.put(`${API_URL}/${id}`, datos, {
        headers: {
            Authorization: `Bearer ${token}`
        }
    });

    return cuotas.data;
}

export const deleteCuotaId = async (id) => {
    const token = localStorage.getItem('token');
    const cuotas = await axios.delete(`${API_URL}/${id}`,  {
        headers: {
            Authorization: `Bearer ${token}`
        }
    });

    return cuotas.data;
}