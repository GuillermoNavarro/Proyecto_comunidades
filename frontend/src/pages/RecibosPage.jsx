import { useEffect, useState } from "react";
import { getReciboMe } from "../services/reciboService";

function RecibosPage({usuario}){
    const [recibos, setRecibos] = useState([]);
    const [cargando, setCargando] = useState(true);
    const [filtroActivo, setFiltroActivo] = useState('TODOS');

    useEffect(() => {
        getReciboMe()
            .then((datos) => {
                setRecibos(datos);
                setCargando(false);
            })
            .catch(() => {
                setCargando(false);
            });
    }, []);

    const estadoRecibo = (recibo) => {
        if(recibo.estadoRecibo === 'PAGADO'){
            return { texto: 'Pagado', claseCSS: 'text-bg-success'};
        }

        const fechaVencimiento = new Date(recibo.cuota.fechaVencimiento);
        const hoy = new Date();

        hoy.setHours(0, 0, 0, 0);

        if(fechaVencimiento < hoy){
            return {texto: 'Vencido', claseCSS: 'text-bg-danger'};
        }else{
            return {texto: 'Pendiente', claseCSS: 'text-bg-warning'};
        }
    };

    const deudaTotal = recibos
        .filter(r => r.estadoRecibo === "PENDIENTE")
        .filter(r => {
            const fechaVencimiento = new Date(r.cuota.fechaVencimiento);
            const hoy = new Date();
            hoy.setHours(0,0,0,0);
            return fechaVencimiento <= hoy;
        })
        .reduce((total, r) => total + r.importe, 0);

    const recibosMostrados = recibos.filter((recibo) => {
        const estadoVisual = estadoRecibo(recibo).texto;

        if(filtroActivo === 'TODOS') return true;
        if(filtroActivo === 'PENDIENTES') return estadoVisual === 'Pendiente' || estadoVisual === 'Vencido';
        if(filtroActivo === 'PAGADOS') return estadoVisual === 'Pagado';

        return true;
    }).sort((a,b) => new Date(b.cuota.fechaEmision) - new Date(a.cuota.fechaEmision));

    if (cargando)
    return (
      <div style={{ textAlign: "center", marginTop: "50px" }}>
        Conectando con el servidor...{" "}
      </div>
    );

    return (
        <div className="container-fluid py-2">
            <div className="d-flex justify-content-between align-items-center mb-4 border-bottom pb-3">
                <div>
                   <h2 className="h3 mb-1 text-dark fw-bold">Mis Recibos</h2>
                    <p className="text-muted small mb-0">Consulta tus pagos y deudas con la comunidad</p> 
                </div>
            </div>

            <div className="row justify-content-center g-4">
                <div className="col-12 col-lg-10">
                    <div className={`alert d-flex align-items-center mb-4 shadow-sm border-0 ${deudaTotal > 0 ? 'alert-danger' : 'alert-success'}`} role="alert">
                        <i className={`bi fs-4 me-3 ${deudaTotal > 0 ? 'bi-exclamation-triangle-fill' : 'bi-check-circle-fill'}`}></i>
                        <div>
                            <h5 className="alert-heading mb-0 fw-bold">
                                {deudaTotal > 0 ? 'Aviso de pago pendiente' : '¡Todo en orden!'}
                            </h5>
                            <p className="mb-0">
                                {deudaTotal > 0 
                                    ? `Tienes una deuda acumulada de ${deudaTotal.toFixed(2)}€ con la comunidad.` 
                                    : 'No tienes ningún recibo pendiente de pago. ¡Gracias!'}
                            </p>
                        </div>
                    </div>
                    <div className="d-flex mb-3">
                        <div className="btn-group shadow-sm" role="gruop">
                            <button 
                                type="button" 
                                className={`btn ${filtroActivo === 'TODOS' ? 'btn-dark' : 'btn-outline-dark'}`}
                                onClick={() => setFiltroActivo('TODOS')}
                            >
                                Todos
                            </button>
                            <button 
                                type="button" 
                                className={`btn ${filtroActivo === 'PENDIENTES' ? 'btn-danger' : 'btn-outline-danger'}`}
                                onClick={() => setFiltroActivo('PENDIENTES')}
                            >
                                Pendientes
                            </button>
                            <button 
                                type="button" 
                                className={`btn ${filtroActivo === 'PAGADOS' ? 'btn-success' : 'btn-outline-success'}`}
                                onClick={() => setFiltroActivo('PAGADOS')}
                            >
                                Pagados
                            </button>
                        </div>
                    </div>
                    <div className="card shadow-sm border-0">
                        <div className="card-body p-0">
                            <div className="table-responsive">
                                <table className="table table-hover align-middle mb-0">
                                    <thead className="table-light">
                                        <tr>
                                            <th className="py-3 px-4 border-bottom-0 text-muted small text-uppercase">Concepto</th>
                                            <th className="py-3 px-4 border-bottom-0 text-muted small text-uppercase">Emisión</th>
                                            <th className="py-3 px-4 border-bottom-0 text-muted small text-uppercase text-end">Importe</th>
                                            <th className="py-3 px-4 border-bottom-0 text-muted small text-uppercase text-center">Estado</th>
                                        </tr>
                                    </thead>
                                    <tbody>
                                        {recibosMostrados.length === 0 ? (
                                            <tr>
                                                <td colSpan="4" className="text-center py-5 text-muted">
                                                    <i className="bi bi-receipt display-4 opacity-25 d-block mb-3"></i>
                                                    <h5 className="fw-bold text-dark opacity-75">No hay recibos</h5>
                                                    <p className="mb-0">No se encontraron recibos para este filtro.</p>
                                                </td>
                                            </tr>
                                        ) : (
                                            recibosMostrados.map((recibo) => {
                                                const estado = estadoRecibo(recibo); // Calculamos el estado al vuelo
                                                return (
                                                    <tr key={recibo.id}>
                                                        <td className="py-3 px-4 fw-semibold text-dark">
                                                            {recibo.cuota.nombre}
                                                            <div className="small text-muted fw-normal">Vence: {recibo.cuota.fechaVencimiento}</div>
                                                        </td>
                                                        <td className="py-3 px-4 text-muted">{recibo.cuota.fechaEmision}</td>
                                                        <td className="py-3 px-4 fw-bold text-end">{recibo.importe.toFixed(2)}€</td>
                                                        <td className="py-3 px-4 text-center">
                                                            <span className={`badge rounded-pill px-3 py-2 ${estado.claseCSS}`}>
                                                                {estado.texto}
                                                            </span>
                                                        </td>
                                                    </tr>
                                                );
                                            })
                                        )}
                                    </tbody>
                                </table>
                            </div>
                        </div>
                    </div>
                </div>
            </div>


        </div>
    )

}

export default RecibosPage;