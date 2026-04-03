import React, {userState, useEffect} from 'react';

function UserForm({initialData, modo, esAdmin, onsave}){
    const [formData, setFormData] = userState({
        nombre: '',
        apellidos: '',
        telefono: '',
        dni: '',
        email: '',
        puerta: '',
        coeficiente: '',
        rol: 'USER'
    });

    useEffect(() => {
        if(initialData) setFormData(initialData);
    }, [initialData]);

    const btnCambio = (e) => {
        const {name, value} = e.target;
        setFormData(prev => ({...prev, [name]: value}));
    };

    const isReadOnly = modo === 'ver';
    const puedeEditarTodo = esAdmin || modo === "nuevo";

    return (
        <form onSubmit={(e) => {e.preventDefault(); onsave(formData); }}>
            <div className="row g-3">
                <div className="col-md-6">
                    <label className="form-label small fw-bold">Nombre</label>
                    <input type="text" name="nombre" className="form-control"
                        value={formData.nombre || ''} onChange={handleChange}
                        readOnly={isReadOnly} required />
                </div>
                <div className="col-md-6">
                    <label className="form-label small fw-bold">Apellidos</label>
                    <input type="text" name="apellidos" className="form-control"
                        value={formData.apellidos || ''} onChange={handleChange}
                        readOnly={isReadOnly} required />
                </div>
                <div className="col-md-6">
                    <label className="form-label small fw-bold">Teléfono</label>
                    <input type="text" name="telefono" className="form-control"
                        value={formData.telefono || ''} onChange={handleChange}
                        readOnly={isReadOnly} />
                </div>
                <div className="col-md-6">
                    <label className="form-label small fw-bold">DNI</label>
                    <input type="text" name="dni" className="form-control"
                        value={formData.dni || ''} onChange={handleChange}
                        readOnly={isReadOnly || !puedeEditarTodo} required />
                </div>
                <div className="col-md-6">
                    <label className="form-label small fw-bold">Email (Login)</label>
                    <input type="email" name="email" className="form-control"
                        value={formData.email || ''} onChange={handleChange}
                        readOnly={isReadOnly || !puedeEditarTodo} required />
                </div>
                <div className="col-md-3">
                    <label className="form-label small fw-bold">Puerta</label>
                    <input type="text" name="puerta" className="form-control"
                        value={formData.puerta || ''} onChange={handleChange}
                        readOnly={isReadOnly || !puedeEditarTodo} />
                </div>
                <div className="col-md-3">
                    <label className="form-label small fw-bold">Coeficiente</label>
                    <input type="number" step="0.01" name="coeficiente" className="form-control"
                        value={formData.coeficiente || ''} onChange={handleChange}
                        readOnly={isReadOnly || !puedeEditarTodo} />
                </div>
                {esAdmin && modo !== 'ver' && (
                    <div className="col-md-6">
                        <label className="form-label small fw-bold">Rol</label>
                        <select name="rol" className="form-select" 
                            value={formData.rol} onChange={handleChange}>
                            <option value="USER">Vecino</option>
                            <option value="ADMIN">Administrador</option>
                        </select>
                    </div>
                )}
            </div>

            {!isReadOnly && (
                <div className="mt-4 d-flex justify-content-end">
                    <button type="submit" className="btn btn-primary px-4 shadow-sm">
                        <i className={`bi ${modo === 'nuevo' ? 'bi-person-plus' : 'bi-check-lg'} me-2`}></i>
                        {modo === 'nuevo' ? 'Crear y enviar email' : 'Guardar cambios'}
                    </button>
                </div>
            )}
            

        </form>
    )

}