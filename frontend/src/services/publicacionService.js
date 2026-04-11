import axios from "axios";
const API_URL = '/api/publicaciones'

export const getNoticias = async () => {
    const token = localStorage.getItem('token');
    const noticias = await axios.get(`${API_URL}/noticias`, {
        headers: {
            Authorization: `Bearer ${token}`
        }
    });

    return noticias.data;
}

export const borrarPublicacion = async (id) => {
    const token = localStorage.getItem('token');
    const publicacion = await axios.delete(`${API_URL}/${id}`, {
        headers: {
            Authorization: `Bearer ${token}`
        }
    });

    return publicacion.data;
}

export const crearNoticia = async (noticia) => {
    const token = localStorage.getItem('token');
    const noticias = await axios.post(`${API_URL}/noticias`, noticia, {
        headers: {
            Authorization: `Bearer ${token}`
        }
    });

    return noticias.data;
}

export const modificarNoticia = async (id, noticia) => {
    const token = localStorage.getItem('token');
    const noticias = await axios.put(`${API_URL}/noticias/${id}`, noticia, {
        headers: {
            Authorization: `Bearer ${token}`
        }
    });

    return noticias.data;
}

