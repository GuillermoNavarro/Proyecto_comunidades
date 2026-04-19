import MovimientoForm from "./MovimientoForm";

function ModalMovimiento({ mostrar, onCerrar, onGuardar, usuarios }) {
    if(!mostrar) return null;

    return (
        <div
            className="modal show d-block"
            tabIndex="-1"
            style={{ backgroundColor: "rgba(0,0,0,0.6)", zIndex: 1060}}
        >
            <div className="modal-dialog modal-lg modal-dialog-centered">
                <div className="modal-content shadow-lg border-0">
                    <div className="modal-header bg-light border-bottom-0 pb-3">
                        <h5 className="modal-title fw-bold text-dark">
                            <i className="bi bi-cash-coin me-2 text-primary"></i>
                            Registrar Nuevo Movimiento
                        </h5>
                        <button
                            type="button"
                            className="btn-close"
                            onClick={onCerrar}
                        ></button>
                    </div>
                    <div className="modal-body p-4 pt-0">
                        <MovimientoForm 
                            onSave={onGuardar} 
                            onCancel={onCerrar} 
                            usuarios={usuarios} 
                        />
                    </div>
                </div>
            </div>
        </div>
    );
}

export default ModalMovimiento;
