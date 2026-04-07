import { useNavigate } from "react-router-dom";
import { useState, useEffect } from "react";
import { crearUsuario, getUsuarios } from "../services/usuarioService";
import { bajaUsuario } from "../services/usuarioService";
import { modificarUsuarioAdmin } from "../services/usuarioService";
import { cambioPassAdmin } from "../services/usuarioService";
import UserForm from "../components/UserForm";

function GestionUsuarios({ usuario }) {
  const navigate = useNavigate();
  const [usuarios, setUsuarios] = useState([]);
  const [cargando, setCargando] = useState(true);
  const [mostrarTodos, setMostrarTodos] = useState(false);
  const [usuarioSeleccionado, setUsuarioSeleccionado] = useState(null);
  const [modo, setModo] = useState("ver");
  const [mostrarModal, setMostrarModal] = useState(false);

  useEffect(() => {
    getUsuarios()
      .then((datos) => {
        setUsuarios(datos);
        setCargando(false);
      })
      .catch(() => {
        setCargando(false);
      });
  }, []);

  if (cargando)
    return (
      <div style={{ textAlign: "center", marginTop: "50px" }}>
        Conectando con el servidor...{" "}
      </div>
    );

  const btnBaja = async (usuario) => {
    if (
      window.confirm(
        `¿Seguro que quiere dar de baja a ${usuario.nombre} ${usuario.apellidos}?`,
      )
    ) {
      try {
        await bajaUsuario(usuario.id);
        const listaActualizada = usuarios.map((u) =>
          u.id === usuario.id ? { ...u, estado: false } : u,
        );
        setUsuarios(listaActualizada);
      } catch (error) {
        console.error("Error al procesar la baja", error);
      }
    }
  };

  const btnAlta = async (usuario) => {
    if (
      window.confirm(
        `¿Seguro que quiere reactivar a ${usuario.nombre} ${usuario.apellidos}?`,
      )
    ) {
      try {
        const datosActualizados = { ...usuario, estado: true };
        await modificarUsuarioAdmin(usuario.id, datosActualizados);
        const listaActualizada = usuarios.map((u) =>
          u.id === usuario.id ? { ...u, estado: true } : u,
        );
        setUsuarios(listaActualizada);
      } catch (error) {
        console.error("Error al reactivar", error);
      }
    }
  };

  const btnVer = (vecino) => {
    setUsuarioSeleccionado(vecino);
    setModo("ver");
    setMostrarModal(true);
  };

  const btnEditar = (vecino) => {
    setUsuarioSeleccionado(vecino);
    setModo("editar");
    setMostrarModal(true);
  };

  const btnNuevo = () => {
    setUsuarioSeleccionado(null);
    setModo("nuevo");
    setMostrarModal(true);
  };

  const btnGuardar = async (datosForm) => {
    try {
      if (modo === "nuevo") {
        const nuevoUsuario = await crearUsuario(datosForm);
        setUsuarios((prevUsuarios) => [...prevUsuarios, nuevoUsuario]);
        console.log("usuario creado");
      } else {
        await modificarUsuarioAdmin(datosForm.id, datosForm);
        const listaActualizada = usuarios.map((u) =>
          u.id === usuario.id ? { ...u, ...datosForm } : u,
        );
        setUsuarios(listaActualizada);
      }
      setMostrarModal(false);
      alert("Operacion realizada con exito");
    } catch (err) {
      console.error("Error al guardar:", err);
    }
  };

  const resetPassword = async (id) => {
    try{
      await cambioPassAdmin(id);
      window.alert("Nueva contraseña enviada");
    }catch(err){
      console.error("Error al resetear", err);
    }
  }

  const usuariosMostrar = mostrarTodos
    ? usuarios
    : usuarios.filter((u) => u.estado === true);

  return (
    <>
      <div className="p-4">
        <div className="d-flex justify-content-between align-items-center mb-4">
          <h2 className="fw-bold text-dark">Gestión de Vecinos</h2>
          <button
            className="btn btn-primary shadow-sm"
            onClick={() => btnNuevo()}
          >
            <i className="bi bi-person-plus-fill me-2"></i>Nuevo Vecino
          </button>
        </div>
        <div className="d-flex justify-content-start mb-3">
          <div className="form-check form-switch">
            <input
              className="form-check-input shadow-none"
              type="checkbox"
              role="switch"
              id="flexSwitchCheckDefault"
              checked={mostrarTodos}
              onChange={() => setMostrarTodos(!mostrarTodos)} // Toggles entre true/false
            />
            <label
              className="form-check-label small text-muted"
              htmlFor="flexSwitchCheckDefault"
            >
              {mostrarTodos ? "Ocultar bajas" : "Mostrar todos"}
            </label>
          </div>
        </div>
        <div className="card border-0 shadow-sm">
          <div className="card-body p-0">
            <table className="table table-hover align-middle mb-0">
              <thead className="bg-light">
                <tr>
                  <th className="ps-4">Nombre</th>
                  <th>Apellidos</th>
                  <th>Puerta</th>
                  <th>Email</th>
                  {usuario.rol === "SUPER_ADMIN" && <th>Comunidad</th>}
                  <th className="text-center">Acciones</th>
                </tr>
              </thead>
              <tbody>
                {usuariosMostrar.map((vecino) => (
                  <tr
                    key={vecino.id}
                    className={!vecino.estado ? "table-ligth opacity-50" : ""}
                  >
                    <td className="ps-4 fw-medium">{vecino.nombre}</td>
                    <td>{vecino.apellidos}</td>
                    <td>{vecino.puerta}</td>
                    <td>{vecino.email}</td>
                    {usuario.rol === "SUPER_ADMIN" && (
                      <td>{vecino.comunidad.id}</td>
                    )}
                    <td className="text-center">
                      <button
                        className="btn btn-sm btn-outline-info me-2"
                        title="Ver vecino"
                        onClick={() => btnVer(vecino)}
                      >
                        <i className="bi bi-eye"></i>
                      </button>
                      {vecino.estado ? (
                        <>
                          <button
                            className="btn btn-sm btn-outline-primary me-2"
                            title="Editar vecino"
                            onClick={() => btnEditar(vecino)}
                            disabled={vecino.id === usuario.id}
                          >
                            <i className="bi bi-pencil-square"></i>
                          </button>
                          <button
                            className="btn btn-sm btn-outline-danger"
                            title="Eliminar vecino"
                            onClick={() => btnBaja(vecino)}
                            disabled={vecino.id === usuario.id}
                          >
                            <i className="bi bi-trash3"></i>
                          </button>
                        </>
                      ) : (
                        <button
                          className="btn btn-sm btn-outline-success"
                          title="Activar de nuevo"
                          onClick={() => btnAlta(vecino)}
                        >
                          <i className="bi bi-arrow-counterclockwise"></i>
                        </button>
                      )}
                    </td>
                  </tr>
                ))}
              </tbody>
            </table>
          </div>
        </div>
      </div>
      {mostrarModal && (
        <div
          className="modal show d-block"
          tabIndex="-1"
          style={{ backgroundColor: "rgba(0,0,0,0.6)" }}
        >
          <div className="modal-dialog modal-lg modal-dialog-centered">
            <div className="modal-content shadow-lg border-0">
              <div className="modal-header bg-light">
                <h5 className="modal-title">
                  {modo === "nuevo"
                    ? "Nuevo Vecino"
                    : `${usuarioSeleccionado?.nombre} ${usuarioSeleccionado?.apellidos}`}
                </h5>
                {modo === "ver" && (
                  <button
                    className="btn btn-outline-primary ms-3"
                    onClick={() => setModo("editar")}
                  >
                    Editar Datos
                  </button>
                )}
                <button
                  type="button"
                  className="btn-close"
                  onClick={() => setMostrarModal(false)}
                ></button>
              </div>
              <div className="modal-body">
                <UserForm
                  initialData={usuarioSeleccionado}
                  modo={modo}
                  esAdmin={true}
                  onsave={btnGuardar}
                />
              </div>
              {modo !== 'nuevo' && (
                <div className="modal-footer border-top-0 pt-0">
                  <button
                    type="button"
                    className="btn btn-sm btn-outline-secondary px-3"
                    onClick={() => resetPassword(usuarioSeleccionado.id)}
                  >
                    <i className="bi bi-key me-2"></i>Resetear Contraseña
                  </button>
                </div>
              )}
            </div>
          </div>
        </div>
      )}
    </>
  );
}

export default GestionUsuarios;
