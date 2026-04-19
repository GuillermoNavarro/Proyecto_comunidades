import { useState, useEffect } from "react";
import ModalMovimiento from "../components/ModalMovimientos";
import { getUsuarios } from "../services/usuarioService";
import { crearMovimiento, getMovimientoComunidad } from "../services/movimientoService";

function CuentasPage({usuario}){
    const [movimientos, setMovimentos] = useState([]);
    const [cargando, setCargando] = useState(true);
    const [mostrarModal, setMostrarModal] = useState(false);
    const [usuariosComunidad, setUsuariosComunidad] = useState([]);

    useEffect(() => {
        getMovimientoComunidad()
            .then((datos) => {
                setMovimentos(datos);
                setCargando(false);
            })
            .catch(() => {
                setCargando(false);
            });
    }, []);

    useEffect(() => {
        if (['ADMIN'].includes(usuario?.rol)) {
            getUsuarios()
                .then(datos => setUsuariosComunidad(datos))
                .catch(err => console.error("Error al cargar vecinos:", err));
        }
    }, [usuario?.rol]);

    const btnGuardarMovimiento = async (datos) => {
        try{
            await crearMovimiento(datos);
            setMostrarModal(false);
            alert("¡Movimiento registrado con exito!");
        }catch (error){
            console.error("Error al guardar", error);
            alert("Hubo un error al guardar el movimiento");
        }
    };

    const agruparMovimientos = (lista) => {
        return lista.reduce((acc, mov) => {
            const fecha = new Date(mov.fecha);
            const año = fecha.getFullYear();
            const nombreMes = fecha.toLocaleDateString('es-ES', {month: 'long'});
            const mes = nombreMes.charAt(0).toLocaleUpperCase() + nombreMes.slice(1);

            if(!acc[año]) acc[año] = {};
            if(!acc[año][mes]) acc[año][mes] = {ingresos: 0, gastos: 0, detalle: []};

            acc[año][mes].detalle.push(mov);

            if(mov.tipo === 'INGRESO'){
                acc[año][mes].ingresos += mov.importe;
            }else{
                acc[año][mes].gastos += mov.importe;
            }
            return acc;
        }, {});
    };

    const datosAgrupados = agruparMovimientos(movimientos);

    const totalIngresos = movimientos.filter(m => m.tipo === 'INGRESO').reduce((sum, m) => sum + m.importe, 0);
    const totalGastos = movimientos.filter(m => m.tipo === 'GASTO').reduce((sum,m) => sum + m.importe, 0);
    const saldoActual = totalIngresos - totalGastos;


    if (cargando) return (
        <div className="container-fluid py-5 text-center text-muted">
            <div className="spinner-border text-primary mb-3" role="status"></div>
            <br />Cargando libros de cuentas...
        </div>
    );

    return (
        <div className="container-fluid py-2">
            <div className="d-flex justify-content-between align-items-center mb-4 border-bottom pb-3">
                <div>
                    <h2 className="h3 mb-1 text-dark fw-bold">Cuentas de la Comunidad</h2>
                    <p className="text-muted small mb-0">Balance de ingresos y gastos por meses</p>
                </div>
                {['ADMIN', 'SUPER_ADMIN'].includes(usuario?.rol) && (
                    <button 
                        className="btn btn-primary d-flex align-items-center shadow-sm"
                        onClick={() => setMostrarModal(true)}
                    >
                        <i className="bi bi-plus-circle-fill me-2"></i>
                        <span className="d-none d-sm-inline">Nuevo Movimiento</span>
                    </button>
                )}
            </div>
            <div className="row justify-content-center g-4">
                <div className="col-12 col-lg-10">
                    <div className="row g-3 mb-5">
                        <div className="col-12 col-md-4">
                            <div className="card border-0 shadow-sm bg-primary text-white h-100">
                                <div className="card-body">
                                    <h6 className="text-uppercase fw-semibold mb-2 opacity-75">Saldo Actual</h6>
                                    <h3 className="mb-0">{saldoActual.toFixed(2)}€</h3>
                                </div> 
                            </div>
                        </div>
                        <div className="col-6 col-md-4">
                            <div className="card border-0 shadow-sm h-100">
                                <div className="card-body">
                                    <h6 className="text-muted text-uppercase small fw-semibold mb-2">Total Ingresado</h6>
                                    <h4 className="text-success mb-0">+{totalIngresos.toFixed(2)}€</h4>
                                </div>
                            </div>
                        </div>
                        <div className="col-6 col-md-4">
                            <div className="card border-0 shadow-sm h-100">
                                <div className="card-body">
                                    <h6 className="text-muted text-uppercase small fw-semibold mb-2">Total Gastado</h6>
                                    <h4 className="text-danger mb-0">-{totalGastos.toFixed(2)}€</h4>
                                </div>
                            </div>
                        </div>
                    </div>



                    {Object.keys(datosAgrupados).sort((a, b) => b - a).map((año) => (
                        <div key={año} className="mb-5">
                            <h3 className="h5 border-bottom pb-2 mb-3 text-primary fw-bold">
                                <i className="bi bi-calendar3 me-2"></i>Ejercicio {año}
                            </h3>
                            <div className="accordion shadow-sm" id={`accordion-${año}`}>
                                {Object.keys(datosAgrupados[año]).map((mes, index) => {
                                    const datosMes = datosAgrupados[año][mes];
                                    const balance = datosMes.ingresos - datosMes.gastos;
                                    const collapseId = `collapse-${año}-${mes}`;

                                    return (
                                        <div className="accordion-item border-0 border-bottom" key={mes}>
                                            <h2 className="accordion-header">
                                                <button 
                                                    className="accordion-button collapsed bg-white text-dark" 
                                                    type="button" 
                                                    data-bs-toggle="collapse" 
                                                    data-bs-target={`#${collapseId}`}
                                                >
                                                    <div className="d-flex justify-content-between w-100 pe-3">
                                                        <span className="fw-bold fs-6">{mes}</span>
                                                        <div className="d-flex gap-4 small">
                                                            <span className="text-success"><i className="bi bi-arrow-up-circle me-1"></i>{datosMes.ingresos.toFixed(2)}€</span>
                                                            <span className="text-danger"><i className="bi bi-arrow-down-circle me-1"></i>{datosMes.gastos.toFixed(2)}€</span>
                                                            <span className={`fw-bold ${balance >= 0 ? 'text-success' : 'text-danger'}`}>
                                                                Total: {balance.toFixed(2)}€
                                                            </span>
                                                        </div>
                                                    </div>
                                                </button>
                                            </h2>
                                            <div id={collapseId} className="accordion-collapse collapse" data-bs-parent={`#accordion-${año}`}>
                                                <div className="accordion-body p-0">
                                                    <div className="table-responsive">
                                                        <table className="table table-hover align-middle mb-0 m-0">
                                                            <thead className="table-light">
                                                                <tr>
                                                                    <th className="ps-4 py-3 text-muted small text-uppercase">Fecha</th>
                                                                    <th className="py-3 text-muted small text-uppercase">Concepto</th>
                                                                    <th className="py-3 text-muted small text-uppercase text-end pe-4">Importe</th>
                                                                </tr>
                                                            </thead>
                                                            <tbody>
                                                                {datosMes.detalle.map((mov) => (
                                                                    <tr key={mov.id}>
                                                                        <td className="ps-4 py-3 text-muted small">{mov.fecha}</td>
                                                                        <td className="py-3 fw-medium">{mov.nombre}</td>
                                                                        <td className={`py-3 fw-bold text-end pe-4 ${mov.tipo === 'INGRESO' ? 'text-success' : 'text-danger'}`}>
                                                                            {mov.tipo === 'INGRESO' ? '+' : '-'}{mov.importe.toFixed(2)}€
                                                                        </td>
                                                                    </tr>
                                                                ))}
                                                            </tbody>
                                                        </table>
                                                    </div>
                                                </div>
                                            </div>
                                        </div>
                                    );
                                })}
                            </div>
                        </div>
                    ))}
                    {Object.keys(datosAgrupados).length === 0 && (
                        <div className="text-center text-muted py-5 bg-white rounded shadow-sm border-0">
                            <i className="bi bi-bank display-1 text-secondary opacity-25 mb-3"></i>
                            <h5 className="fw-bold text-dark opacity-75">Sin movimientos</h5>
                            <p>Aún no hay registros financieros en esta comunidad.</p>
                        </div>
                    )} 
                </div>
            </div>
            <ModalMovimiento
                mostrar={mostrarModal}
                onCerrar={() => setMostrarModal(false)}
                onGuardar={btnGuardarMovimiento}
                usuarios={usuariosComunidad}
            />
        </div>
    );

}

export default CuentasPage;