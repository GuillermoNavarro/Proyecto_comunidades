import { useState, useEffect } from "react";
import { getReciboIdPendiente } from "../services/reciboService";

function MovimientoForm ({ onSave, onCancel, usuarios = []}) {
    const fechaHoy = new Date().toISOString().split('T')[0];

    const [formData, setFormData] = useState({
        tipo: 'GASTO',
        nombre: '',
        importe: '',
        fecha: fechaHoy,
        usuarioId: '',
        reciboId: ''
    });

    const [recibosPendientes, setRecibosPendientes] = useState([]);

    useEffect(() => {
        if(formData.usuarioId){
            getReciboIdPendiente(formData.usuarioId)
                .then((datos) => {
                    setRecibosPendientes(datos);
                })
            .catch((error) => {
                setRecibosPendientes([]);
            });
        }else{
            setRecibosPendientes([]);
            setFormData(prev => ({ ...prev, reciboId: '', importe: '', nombre: '' }));
        }
    }, [formData.usuarioId]);

    const btnCambio = (e) => {
        const {name, value} = e.target;
        if(name === 'reciboId' && value !== ''){
            const reciboElegido = recibosPendientes.find(r => r.id === parseInt(value));
            if (reciboElegido) {
                setFormData({
                    ...formData,
                    [name]: value,
                    nombre: `Cobro ${reciboElegido.cuota.nombre}`,
                    importe: reciboElegido.importe
                });
                return
            }    
        }
        setFormData({ ...formData, [name]: value});
    };

    const btnSubmit = (e) => {
        e.preventDefault();
        let conceptoDefinitivo = formData.nombre;
        if(formData.usuarioId){
            const vecino = usuarios.find(u => u.id === parseInt(formData.usuarioId));
            if( vecino && vecino.puerta){
                conceptoDefinitivo = `${formData.nombre} (Pta: ${vecino.puerta})`;
            }
        }
        const datosAEnviar = {
            tipo: formData.tipo,
            nombre: conceptoDefinitivo,
            importe: parseFloat(formData.importe),
            fecha: formData.fecha,
            usuario: formData.usuarioId ? {id: parseInt(formData.usuarioId)} : null,
            recibo: formData.reciboId ? { id: parseInt(formData.reciboId)} : null
        };
        onSave(datosAEnviar);
    };

    return (
        <form onSubmit={btnSubmit}>
            <div className="row g-3">
                <div className="col-12 col-md-6">
                    <label className="form-label text-muted small fw-bold">Tipo de Movimiento</label>
                    <select 
                        className="form-select fw-bold"
                        name="tipo" 
                        value={formData.tipo} 
                        onChange={btnCambio}
                        required
                    >
                        <option value="GASTO">Gasto / Pago</option>
                        <option value="INGRESO">Ingreso / Cobro</option>    
                    </select>
                </div>
                <div className="col-12 col-md-6">
                    <label className="form-label text-muted small fw-bold">Fecha del Movimiento</label>
                    <input 
                        type="date" 
                        className="form-control" 
                        name="fecha" 
                        value={formData.fecha} 
                        onChange={btnCambio}
                        required 
                    />
                </div>
                {formData.tipo === 'INGRESO' && (
                    <div className="col-12 p-3 bg-light border rounded mt-3">
                        <h6 className="text-primary small fw-bold mb-3">
                            <i className="bi bi-link-45deg me-1"></i>Vincular a un Recibo (Opcional)
                        </h6>
                        <div className="row g-3">
                            <div className="col-12 col-md-6">
                                <label className="form-label text-muted small">Vecino</label>
                                    <select 
                                    className="form-select"
                                    name="usuarioId"
                                    value={formData.usuarioId}
                                    onChange={btnCambio}
                                >
                                    <option value="">-- Seleccionar Vecino --</option>
                                    
                                    {usuarios
                                        .filter(u => u.estado === true)
                                        .map(u => (
                                        <option key={u.id} value={u.id}>{u.nombre} {u.apellidos} (Pta: {u.puerta})</option>
                                    ))}
                                </select>
                            </div>
                            <div className="col-12 col-md-6">
                                <label className="form-label text-muted small">Recibo a liquidar</label>
                                <select 
                                    className="form-select"
                                    name="reciboId"
                                    value={formData.reciboId}
                                    onChange={btnCambio}
                                    disabled={!formData.usuarioId} 
                                >
                                    <option value="">-- Seleccionar Recibo --</option>
                                    {recibosPendientes.map(r => (
                                        <option key={r.id} value={r.id}>{r.cuota.nombre} ({r.importe})</option>
                                    ))}
                                </select>
                            </div>
                        </div>
                    </div>
                )}
                <div className="col-12 mt-3">
                    <label className="form-label text-muted small fw-bold">Concepto (Descripción)</label>
                    <input 
                        type="text" 
                        className="form-control" 
                        name="nombre" 
                        placeholder={formData.tipo === 'GASTO' ? "Ej: Factura luz pasillos" : "Ej: Ingreso cuota manual"} 
                        value={formData.nombre} 
                        onChange={btnCambio}
                        required 
                    />
                </div>
                <div className="col-12 col-md-6 mt-3">
                    <label className="form-label text-muted small fw-bold">Importe (€)</label>
                    <div className="input-group">
                        <input 
                            type="number" 
                            step="0.01" 
                            min="0.01"
                            className="form-control" 
                            name="importe" 
                            placeholder="0.00" 
                            value={formData.importe} 
                            onChange={btnCambio}
                            required 
                        />
                        <span className="input-group-text">€</span>
                    </div>
                </div>   
            </div>
            <div className="d-flex justify-content-end gap-2 mt-4 pt-3 border-top">
                <button type="button" className="btn btn-light" onClick={onCancel}>
                    Cancelar
                </button>
                <button type="submit" className="btn btn-primary">
                    <i className="bi bi-save me-2"></i>Guardar Movimiento
                </button>
            </div>
        </form>
    );
}

export default MovimientoForm;