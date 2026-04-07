import { useState } from "react";
import { cambioPass } from "../services/usuarioService";

function PassModal({ show, onHide, esForzado }) {
  const [passData, setPassData] = useState({
    oldPassword: "",
    newPassword: "",
    confirmPassword: "",
  });
  const [cargando, setCargando] = useState(false);
  const [mostrarOld, setMostrarOld] = useState(false);
  const [mostrarNew, setMostrarNew] = useState(false);
  const [mostrarConfirm, setMostrarConfirm] = useState(false);

  const btnCambio = (e) => {
    setPassData({ ...passData, [e.target.name]: e.target.value });
  };

  const btnSubmit = async (e) => {
    e.preventDefault();

    if (passData.newPassword !== passData.confirmPassword) {
      alert("La nueva contraseña no coincide");
      return;
    }

    if (passData.newPassword.length < 6) {
      window.alert("La contraseña debe tener al menos 6 caracteres");
      return;
    }

    try {
      const passwords = {
        oldPassword: passData.oldPassword,
        newPassword: passData.newPassword,
      };

      await cambioPass(passwords);
      window.alert("Contraseña cambiada");
      onHide();
    } catch (err) {
      console.error("Error en el cambio de contraseña:", err);
      window.alert("La contraseña actual no es correcta");
    } finally {
      setCargando(false);
    }
  };

  if (!show) return null;

  return (
    <div
      className="modal show d-block"
      style={{ backgroundColor: "rgba(0,0,0,0.6)" }}
    >
      <div className="modal-dialog modal-dialog-centered">
        <div className="modal-content shadow-lg border-0">
          <div
            className={`modal-header ${esForzado ? "bg-warning" : "bg-light"}`}
          >
            <h5 className="modal-title fw-bold">
              {esForzado
                ? "Cambio de contraseña obligatorio"
                : "Cambio de contraseña"}
            </h5>
            {!esForzado && (
              <button className="btn-close" onClick={onHide}></button>
            )}
          </div>
          <form onSubmit={btnSubmit}>
            <div className="modal-body p-4">
              {esForzado && (
                <p className="small text-muted mb-4">
                  Por seguiridad, debes actualizar la contraseña antes de
                  continuar.
                </p>
              )}
              <div className="mb-3">
                <label className="form-label small fw-bold">
                  Contraseña Actual
                </label>
                <div className="input-group">
                <input
                  type={mostrarOld ? "text" : "password"}
                  name="oldPassword"
                  className="form-control"
                  value={passData.oldPassword}
                  onChange={btnCambio}
                  required
                />
                <span
                  className="input-group-text btn btn-light"
                  type="button"
                  onClick={() => setMostrarOld(!mostrarOld)}
                  style={{ cursor:"pointer", borderLeft: "none"}}
                >
                  <i className={`bi ${mostrarOld ? 'bi-eye-slash' : 'bi-eye'}`}></i>
                </span>
                </div>
              </div>

              <div className="mb-3">
                <label className="form-label small fw-bold">
                  Nueva Contraseña
                </label>
                <div className="input-group">
                <input
                  type={mostrarNew ? "text" : "password"}
                  name="newPassword"
                  className="form-control"
                  value={passData.newPassword}
                  onChange={btnCambio}
                  required
                />
                <span
                  className="input-group-text btn btn-light"
                  type="button"
                  onClick={() => setMostrarNew(!mostrarNew)}
                  style={{ cursor:"pointer", borderLeft: "none"}}
                >
                  <i className={`bi ${mostrarNew ? 'bi-eye-slash' : 'bi-eye'}`}></i>
                </span>
                </div>
              </div>

              <div className="mb-3">
                <label className="form-label small fw-bold">
                  Confirmar Nueva Contraseña
                </label>
                <div className="input-group">
                <input
                  type={mostrarConfirm ? "text" : "password"}
                  name="confirmPassword"
                  className="form-control"
                  value={passData.confirmPassword}
                  onChange={btnCambio}
                  required
                />
                <span
                  className="input-group-text btn btn-light"
                  type="button"
                  onClick={() => setMostrarConfirm(!mostrarConfirm)}
                  style={{ cursor:"pointer", borderLeft: "none"}}
                >
                  <i className={`bi ${mostrarConfirm ? 'bi-eye-slash' : 'bi-eye'}`}></i>
                </span>
                </div>
              </div>
            </div>
            <div className="modal-footer border-0 bg-light">
              {!esForzado && (
                <button
                  type="button"
                  className="btn btn-secondary px-4"
                  onClick={onHide}
                >
                  Cancelar
                </button>
              )}
              <button
                type="submit"
                className="btn btn-primary px-4"
                disabled={cargando}
              >
                {cargando ? "Cambiando..." : "Cambiar Contraseña"}
              </button>
            </div>
          </form>
        </div>
      </div>
    </div>
  );
}

export default PassModal;
