import { useState, useEffect } from "react";
import { getUsuarios } from "../services/usuarioService";

function CuotaForm({ onSave, onCancel, cuotaExistente = null}){
    const fechaHoy = new Date().toISOString().split('T')[0];

    const [formData, setFormData] = useState({
        nombre: '',
        importe: '',
        tipo: 'ORDINARIA',
        fechaEmision: fechaHoy,
        fechaVencimiento: '',
        idUsuarioSeleccionado: ''
    });

    const [usuarios, setUsuarios] = useState([]);
    const [cargandoVecinos, setCargandoVecinos] = useState(false);

    useEffect(() => {
        if(formData.tipo === 'INDIVIDUAL' && usuario.length === 0){
            setCargandoVecinos(true);
            getUsuarios()
                .then(datos => {
                    setUsuarios(datos);
                    setCargandoVecinos(false);
                })
                .catch(err => {
                    console.error("Error al cargar vecinos:", err);
                    setCargandoVecinos(false);
                });
        }
    }, [formData.tipo]);

    useEffect(() => {
        if(cuotaExistente){
            setFormData({
                ...cuotaExistente,
                idUsuarioSeleccionado: ''
            });
        }
    }, [cuotaExistente]);

    const btnCambio = (e) => {
        const { name, value } = e.target;
        setFormData({...formData, [name]: value});
    };

    const btnSubmit = (e) => {
        e.preventDefault();
        const { idUsuarioSeleccionado, ...datosCuota } = formData;
        onSave(
            { ...datosCuota, importe: parseFloat(datosCuota.importe)},
            idUsuarioSeleccionado || null
        );
    };

    return (
        <form onSubmit={btnSubmit}>
            <div className="row g-3">
                <div className="col-12 col-md-8">
                    <label className="form-label small fw-bold text-muted">Nombre/Concepto</label>
                    <input type="text" name="nombre" className="form-control" value={formData.nombre} onChange={btnCambio} required />
                </div>
                <div className="col-12 col-md-4">
                    <label className="form-label small fw-bold text-muted">Importe (€)</label>
                    <input type="number" step="0.01" name="importe" className="form-control fw-bold" value={formData.importe} onChange={btnCambio} required />
                </div>
                <div className="col-12 col-md-6">
                    <label className="form-label small fw-bold text-muted">Tipo de Cuota</label>
                    <select name="tipo" className="form-select" value={formData.tipo} onChange={btnCambio} required>
                        <option value="ORDINARIA">Ordinaria (Todos)</option>
                        <option value="EXTRAORDINARIA">Extraordinaria (Todos)</option>
                        <option value="INDIVIDUAL">Individual (Solo un vecino)</option>
                    </select>
                </div>
                {formData.tipo === 'INDIVIDUAL' && (
                    <div className="col-12 col-md-6 animate__animated animate__fadeIn">
                        <label className="form-label small fw-bold text-primary">Asignar a Vecino</label>
                        <select 
                            name="idUsuarioSeleccionado" 
                            className="form-select border-primary" 
                            value={formData.idUsuarioSeleccionado} 
                            onChange={btnCambio} 
                            required
                        >
                            <option value="">-- Elegir Vecino --</option>
                            {usuarios.map(u => (
                                <option key={u.id} value={u.id}>{u.nombre} {u.apellidos} ({u.puerta})</option>
                            ))}
                        </select>    
                    </div>
                )}
                <div className="col-6">
                    <label className="form-label small fw-bold text-muted">Fecha Emisión</label>
                    <input type="date" name="fechaEmision" className="form-control" value={formData.fechaEmision} onChange={btnCambio} required />
                </div>
                <div className="col-6">
                    <label className="form-label small fw-bold text-muted">Vencimiento</label>
                    <input type="date" name="fechaVencimiento" className="form-control" value={formData.fechaVencimiento} onChange={btnCambio} required />
                </div>
            </div>  
            <div className="d-flex justify-content-end gap-2 mt-4 pt-3 border-top">
                <button type="button" className="btn btn-light" onClick={onCancel}>Cancelar</button>
                <button type="submit" className="btn btn-primary shadow-sm">
                    {cuotaExistente ? 'Actualizar' : 'Generar Recibos'}
                </button>
            </div>  
        </form>
    );
}

export default CuotaForm;