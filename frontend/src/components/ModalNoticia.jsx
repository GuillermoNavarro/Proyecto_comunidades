import { useState, useEffect } from "react";
import { crearNoticia, modificarNoticia } from "../services/publicacionService";
import { getDocumentos } from "../services/documentoService";

function ModalNoticia({ noticiaActual, modo, onSave, onClose}){
    const [formData, setFormData] = useState({
        titulo: "",
        descripcion: "",
        fechaFin: "",
        documentoId: ""
    });

    const [listaDocumentos, setListaDocumentos] = useState([]);

    useEffect(() => {
        if(modo === "editar" && noticiaActual){
            setFormData({
                titulo: noticiaActual.titulo,
                descripcion: noticiaActual.descripcion,
                fechaFin: noticiaActual.fechaFin ? noticiaActual.fechaFin.split('T')[0] : "",
                documentoId: noticiaActual.documento ? noticiaActual.documento.id : ""
            });
        }else{
            setFormData({ titulo: "", descripcion: "", fechaFin: "", documentoId: "" });
        }
    }, [noticiaActual, modo]);

    useEffect(() => {
        getDocumentos()
            .then((datos) => {
                setListaDocumentos(datos);
            })

    }, []);

    const btnCambio = (e) => {
        const {name, value} = e.target;
        setFormData((prev) => ({...prev, [name]: value}));
    };

    const btnGuardar = async (e) => {
        e.preventDefault();
        try{
            const datos = {
                titulo: formData.titulo,
                descripcion: formData.descripcion,
                fechaFin: formData.fechaFin ? `${formData.fechaFin}T23:59:59` : null
            };

            if(formData.documentoId){
                datos.documento={ id: parseInt(formData.documentoId)};
            }

            if(modo === "crear"){
                const nuevaNoticia = await crearNoticia(datos);
                onSave(nuevaNoticia, "crear");
            }else{
                const noticiaModificada = await modificarNoticia(noticiaActual.id, datos);
                onSave(noticiaModificada, "editar");
            }
            onClose();
        }catch(err){
            console.error("Error al guardar la noticia", err);
            alert("No se pudo guardar");
        }
    };

    const btnModificar = async (e) => {

    }

    return (
        <div className="modal show d-block" style={{ backgroundColor: "rgba(0,0,0,0.5)" }}>
            <div className="modal-dialog">
                <div className="modal-content border-0 shadow-lg">
                    <div className="modal-header bg-light">
                        <h5 className="modal-title fw-bold">
                            {modo === "crear" ? "Crear Nueva Noticia" : "Editar Noticia"}
                        </h5>
                        <button type="button" className="btn-close" onClick={onClose}></button>
                    </div>
                    <div className="modal-body">
                        <div className="mb-3">
                            <label className="form-label fw-medium">Título de la Noticia</label>
                            <input 
                                type="text" 
                                className="form-control" 
                                name="titulo" 
                                value={formData.titulo} 
                                onChange={btnCambio} 
                                placeholder="Ej: Reunión extraordinaria"
                                required 
                            />
                        </div>
                        <div className="mb-3">
                            <label className="form-label fw-medium">Descripción</label>
                            <textarea 
                                className="form-control" 
                                name="descripcion" 
                                value={formData.descripcion} 
                                onChange={btnCambio} 
                                rows="4" 
                                placeholder="Detalles de la noticia..."
                                required
                            ></textarea>
                        </div>
                        <div className="mb-3">
                            <label className="form-label fw-medium text-muted">Fecha Límite (Opcional)</label>
                            <input 
                                type="date" 
                                className="form-control" 
                                name="fechaFin" 
                                value={formData.fechaFin} 
                                onChange={btnCambio} 
                            />
                            <small className="form-text text-muted">Útil para convocatorias o derramas.</small>
                        </div>
                        <div className="mb-3">
                            <label className="form-label fw-medium text-muted">Adjuntar Documento (Opcional)</label>
                            <select 
                                className="form-select" 
                                name="documentoId" 
                                value={formData.documentoId} 
                                onChange={btnCambio}
                            >
                                <option value="">-- No adjuntar ningún documento --</option>
                                {listaDocumentos.map((doc) => (
                                    <option key={doc.id} value={doc.id}>{doc.nombre}</option>
                                ))}
                            </select>
                        </div>
                    </div>
                    <div className="modal-footer border-top-0 pt-0">
                        <button type="button" className="btn btn-outline-secondary" onClick={onClose}>
                        Cancelar
                        </button>
                        <button type="button" className="btn btn-primary" onClick={btnGuardar}>
                        {modo === "crear" ? "Publicar Noticia" : "Guardar Cambios"}
                        </button>
                    </div>
                </div>
            </div>
        </div>
    )
    
}

export default ModalNoticia;