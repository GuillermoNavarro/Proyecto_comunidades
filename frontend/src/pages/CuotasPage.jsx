import { useState, useEffect } from "react";
import { getCuotaComunidad, crearCuota, putCuotaId, deleteCuotaId } from "../services/cuotaService";
import { getRecibosPorCuota } from "../services/reciboService"
import ModalCuota from "../components/ModalCuota";


function CuotasPage({usuario}){
    const [cuotas, setCuotas] = useState([]);
    const [cargando, setCargando] = useState(false);

    const [recibosPorCuota, setRecibosPorCuota] = useState({}); 
    const [cargandoRecibos, setCargandoRecibos] = useState({});

    const [mostrarModal, setMostrarModal] = useState(false);
    const [cuotaAEditar, setCuotaAEditar] = useState(null);

    const cargarDatos = async () => {
        setCargando(true);
        try{
            const datos = await getCuotaComunidad();
            setCuotas(datos);
        }catch (error){
            console.error("Error al cargar cuotas:", error);
        }finally{
            setCargando(false);
        }
    };

    useEffect(() => {
        if(usuario){
            cargarDatos();
        }
    }, [usuario]);

    const abrirAcordeon = async (idCuota) => {
        if(recibosPorCuota[idCuota]) return;
        setCargandoRecibos(prev => ({...prev, [idCuota]: true}));
        try {
            const datosRecibos = await getRecibosPorCuota(idCuota);
            setRecibosPorCuota(prev => ({...prev, [idCuota]:datosRecibos}));
        }catch (error){
            console.error("Error al cargar recibos", error);
        }finally{
            setCargandoRecibos(prev => ({ ...prev, [idCuota]:false}));
        }
    }

    const btnAbrirCerrar= () => {
        setCuotaAEditar(null);
        setMostrarModal(true);
    };

    const btnAbrirEditar = (e, cuota) => {
        e.stopPropagation();
        setCuotaAEditar(cuota);
        setMostrarModal(true);
    };

    const btnEliminar = async (e, id) => {
        e.stopPropagation();
        if(window.confirm("¿Estas seguro de eliminar esta cuota?")) {
            try{
                await deleteCuotaId(id);
                cargarDatos();
            } catch(err){
                alert("Error al eliminar");
            }
        }
    };

    const btnGuardar = async (datosCuota, idUsuario) => {
        try{
            if (cuotaAEditar){
                await putCuotaId(cuotaAEditar.id, datosCuota);
            }else{
                await crearCuota(datosCuota, idUsuario);
            }
            setMostrarModal(false);
            cargarDatos();
        }catch (error){
            alert("Error al procesar la cuota");
        }
    };

    if (cargando) return (
        <div className="container py-5 text-center">
            <div className="spinner-border text-primary" role="status"></div>
            <p className="mt-2">Cargando gestión de cuotas...</p>
        </div>
    );

    return (
        <div className="container-fluid py-2">
            <div className="d-flex justify-content-between align-items-center mb-4 border-bottom pb-3">
                <div>
                    <h2 className="h3 mb-1 text-dark fw-bold">Gestión de Cuotas</h2>
                    <p className="text-muted small mb-0">Creacion y control de cuotas de la comunidad</p>
                </div>
                <button className="btn btn-primary shadow-sm" onClick={btnAbrirCerrar}>
                    <i className="bi bi-plus-circle me-2"></i>Nueva Cuota
                </button>
            </div>
            <div className="row justify-content-center">
                <div className="col-12 col-lg-10">
                    {cuotas.length === 0 ? (
                        <div className="text-center py-5 bg-white rounded shadow-sm border-0">
                            <i className="bi bi-receipt display-4 text-muted opacity-25"></i>
                            <p className="text-muted mt-3 fw-bold">No hay cuotas registradas aún.</p>
                        </div>
                    ) : (
                        <div className="accordion shadow-sm" id="accordionCuotas">
                            {cuotas.map((c) => {
                                const listaRecibos = recibosPorCuota[c.id] || [];
                                const totalRecibos = listaRecibos.length;
                                const pagados = listaRecibos.filter(r => r.estadoRecibo === 'PAGADO').length;

                                return (
                                    <div className="accordion-item border-0 border-bottom" key={c.id}>
                                        <h2 className="accordion-header d-flex bg-white pe-3">
                                            <button 
                                                className="accordion-button collapsed fw-bold text-primary flex-grow-1" 
                                                type="button" 
                                                data-bs-toggle="collapse" 
                                                data-bs-target={`#collapse${c.id}`}
                                                onClick={() => abrirAcordeon(c.id)} // Enganchamos el Lazy Loading
                                            >
                                                <div className="d-flex flex-column w-100 pe-3">
                                                    <div className="d-flex justify-content-between align-items-center mb-1">
                                                        <span className="fs-6 text-dark">{c.nombre} - {c.importe}€</span>
                                                        <span className={`badge ${
                                                            c.tipo === 'ORDINARIA' ? 'bg-info' : 
                                                            c.tipo === 'EXTRAORDINARIA' ? 'bg-warning text-dark' : 
                                                            'bg-secondary'
                                                        }`}>
                                                            {c.tipo}
                                                        </span>
                                                    </div>
                                                    <span className="text-muted small fw-normal">
                                                        Emitida: {c.fechaEmision} | Vence: <span className="text-danger fw-bold">{c.fechaVencimiento}</span>
                                                    </span>
                                                </div>
                                            </button>
                                            
                                            <div className="d-flex align-items-center gap-2 py-3 ms-2">
                                                <button className="btn btn-sm btn-light border" onClick={(e) => btnAbrirEditar(e, c)}>
                                                    <i className="bi bi-pencil-square text-secondary"></i>
                                                </button>
                                                <button className="btn btn-sm btn-light border" onClick={(e) => btnEliminar(e, c.id)}>
                                                    <i className="bi bi-trash text-danger"></i>
                                                </button>
                                            </div>
                                        </h2>
                                        
                                        <div id={`collapse${c.id}`} className="accordion-collapse collapse" data-bs-parent="#accordionCuotas">
                                            <div className="accordion-body p-0 bg-light border-top">
                                                {cargandoRecibos[c.id] ? (
                                                    <div className="p-4 text-center">
                                                        <div className="spinner-border spinner-border-sm text-primary me-2" role="status"></div>
                                                        <span className="text-muted small">Cargando recibos...</span>
                                                    </div>
                                                ) : totalRecibos === 0 ? (
                                                    <div className="p-4 text-center text-muted small">
                                                        <i className="bi bi-info-circle me-1"></i>No hay recibos asociados.
                                                    </div>
                                                ) : (
                                                    <div className="table-responsive">
                                                        <div className="bg-white px-4 py-2 border-bottom d-flex justify-content-between align-items-center">
                                                            <span className="small text-muted fw-bold">Estado de Recaudación:</span>
                                                            <span className={`badge ${pagados === totalRecibos ? 'bg-success' : 'bg-warning text-dark'} rounded-pill`}>
                                                                {pagados} / {totalRecibos} Pagados
                                                            </span>
                                                        </div>
                                                        <table className="table table-sm table-hover align-middle mb-0 m-0">
                                                            <thead className="table-light">
                                                                <tr>
                                                                    <th className="ps-4 py-2 text-muted small text-uppercase">Vecino</th>
                                                                    <th className="py-2 text-muted small text-uppercase text-end pe-4">Estado</th>
                                                                </tr>
                                                            </thead>
                                                            <tbody>
                                                                {listaRecibos.map((recibo) => (
                                                                    <tr key={recibo.id}>
                                                                        <td className="ps-4 py-2 fw-medium">{recibo.usuario?.nombre || "Vecino"}</td>
                                                                        <td className="py-2 text-end pe-4">
                                                                            {recibo.estadoRecibo === 'PAGADO' ? (
                                                                                <span className="text-success small fw-bold">PAGADO</span>
                                                                            ) : (
                                                                                <span className="text-danger small fw-bold">PENDIENTE</span>
                                                                            )}
                                                                        </td>
                                                                    </tr>
                                                                ))}
                                                            </tbody>
                                                        </table>
                                                    </div>
                                                )}
                                            </div>
                                        </div>
                                    </div>
                                );
                            })}
                        </div>
                    )}
                </div>
            </div>
            
            <ModalCuota 
                mostrar={mostrarModal} 
                onCerrar={() => setMostrarModal(false)} 
                onGuardar={btnGuardar} 
                cuotaSeleccionada={cuotaAEditar}
            />
        </div>
    );
}

export default CuotasPage;