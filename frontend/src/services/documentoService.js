import axios from "axios";
const API_URL = '/api/documentos'

export const getDocumentos = async () => {
    const token = localStorage.getItem('token');
    const documentos = await axios.get(`${API_URL}`, {
        headers: {
            Authorization: `Bearer ${token}`
        }
    });

    return documentos.data;
}

export const borrarDocumento = async (id) => {
    const token = localStorage.getItem('token');
    const documentos = await axios.delete(`${API_URL}/${id}`, {
        headers: {
            Authorization: `Bearer ${token}`
        }
    });

    return documentos.data;
}

export const crearDocumento = async (formData) =>{
    const token = localStorage.getItem('token');
    const documentos = await axios.post(`${API_URL}`, formData, {
        headers: {
            Authorization: `Bearer ${token}`
        }
    });

    return documentos.data;
}

export const modificarDocumento = async (id, datos) =>{
    const token = localStorage.getItem('token');
    const documentos = await axios.patch(`${API_URL}/${id}`, datos, {
        headers: {
            Authorization: `Bearer ${token}`
        }
    });

    return documentos.data;
}