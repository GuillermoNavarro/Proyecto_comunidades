import { useNavigate } from "react-router-dom";
import { useState } from "react";
import { login } from "../services/authService";

function LoginModal({ isOpen, onClose, setUsuario }) {
  const navigate = useNavigate();
  const [email, setEmail] = useState("");
  const [password, setPassword] = useState("");
  const [error, setError] = useState("");

  if (!isOpen) return null;

  const manejarLogin = async (e) => {
    e.preventDefault();
    try {
      const respuesta = await login(email, password);
      setUsuario(respuesta.perfil);
      onClose();
      navigate("/menu");
    } catch (err) {
      setError("Usuario o contraseña incorrectos");
    }
  };

  return (
    <div
      className="modal d-block"
      style={{ backgroundColor: "rgba(0,0,0,0.6)" }}
    >
      <div className="modal-dialog modal-dialog-centered">
        <div className="modal-content border-0 shadow-lg">
          <div className="modal-header border-0 pb-0">
            <h5 className="modal-title fw-bold">Iniciar Sesión</h5>
            <button
              type="button"
              className="btn-close"
              onClick={() => onClose()}
            ></button>
          </div>
          <div className="modal-body p-4">
            <form onSubmit={manejarLogin}>
              <div className="mb-3">
                <label className="form-label small fw-bold text-muted">
                  Correo Electrónico
                </label>
                <input
                  type="email"
                  className="form-control form-control-lg fs-6"
                  placeholder="vecino@correo.com"
                  value={email}
                  onChange={(e) => setEmail(e.target.value)}
                  required
                />
              </div>
              <div className="mb-4">
                <label className="form-label small fw-bold text-muted">
                  Contraseña
                </label>
                <input
                  type="password"
                  className="form-control form-control-lg fs-6"
                  placeholder="••••••••"
                  value={password}
                  onChange={(e) => setPassword(e.target.value)}
                  required
                />
              </div>

              {error && (
                <div className="alert alert-danger py-2 small">{error}</div>
              )}

              <button
                type="submit"
                className="btn btn-primary bg-opacity-75 w-100 py-2 fw-bold"
              >
                Entrar al Panel
              </button>
            </form>
          </div>
        </div>
      </div>
    </div>
  );
}

export default LoginModal;
