import { useState, useEffect } from "react";
import { getComunidadId } from "../services/comunidadService";

function ComunidadForm({idComunidad, onCancel, onSave}){
    const [formData, setFormData] = useState({
        nombre: '',
        direccion: '',
        ciudad: '',
        codPostal: ''
    });

    useEffect(() => {
        if(idComunidad){
            getComunidadId(idComunidad)
                .then((datos) => {
                    setFormData(datos);
                })
                .catch();
        }else{
            setFormData({
                nombre: '',
                direccion: '',
                ciudad: '',
                codPostal: ''
            });
        }
    }, [idComunidad]);

    const btnCambio = (e) => {
        const {name, value} = e.target;
        setFormData(prev => ({...prev, [name]: value}));
    };


    return (
        <form onSubmit={(e) => {e.preventDefault(); onSave(formData); }}>
            <div className="row g-3">
                <div className="col-md-6">
                    <label className="form-label small fw-bold">Nombre</label>
                    <input type="text" name="nombre" className="form-control"
                        value={formData.nombre || ''} onChange={btnCambio}
                        required />
                </div>
                <div className="col-md-6">
                    <label className="form-label small fw-bold">Direccion</label>
                    <input type="text" name="direccion" className="form-control"
                        value={formData.direccion || ''} onChange={btnCambio}
                        required />
                </div>
                <div className="col-md-6">
                    <label className="form-label small fw-bold">Ciudad</label>
                    <input type="text" name="ciudad" className="form-control"
                        value={formData.ciudad || ''} onChange={btnCambio}
                        required />
                </div>
                <div className="col-md-6">
                    <label className="form-label small fw-bold">Codigo Postal</label>
                    <input type="text" name="codPostal" className="form-control"
                        value={formData.codPostal || ''} onChange={btnCambio}
                        required />
                </div>
            </div>
            <div className="mt-4 d-flex justify-content-end">
                <button type="submit" className="btn btn-primary px-4 shadow-sm">
                    <i className="bi-check-lg me-2"></i>
                    Guardar cambios
                </button>
            </div>
        </form>

    ) 

}

export default ComunidadForm;