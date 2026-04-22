import { useState, useEffect } from "react";
import { crearComunidad, getComunidadesActivo, modificarComunidad } from "../services/comunidadService";
import ModalComunidad from "../components/ModalComunidad";

function ComunidadPage({usuario}){
    const [comunidades, setComunidades] = useState([]);
    const [cargando, setCargando] = useState(true);
    const [mostrarModal, setMostrarModal] = useState(false);
    const [modo, setModo] = useState("");
    const [comunidad, setComunidad] = useState(null);

    useEffect(() => {
        setCargando(true);
        getComunidadesActivo()
            .then((datos) => {
                setComunidades(datos);
                setCargando(false);
            })
            .catch(() => {
                setCargando(false);
            });
    }, []);

    const btnNuevo = () => {
        setComunidad(null);
        setModo("nuevo");
        setMostrarModal(true);
    }

    const btnModificar = (id) => {
        setComunidad(id);
        setModo("modificar");
        setMostrarModal(true);
    }

    const btnGuardar = async (datosForm) => {
        try{
            if(modo === "nuevo"){
                const nuevaComunidad = await crearComunidad(datosForm);
                setComunidades((prev) => [...prev, nuevaComunidad]);
                console.log("comunidad creada");
            }else{
                await modificarComunidad(datosForm.id, datosForm);
                const listaActualizada = comunidades.map((c) =>
                    c.id === datosForm.id ? { ...c, ...datosForm } : c,
                );
                setComunidades(listaActualizada);
            }
            setMostrarModal(false);
            alert("Operacion realizada con exito");
        }catch(err){
            console.error("Error al guardar:", err);
        }
    }

    const btnBaja = async (comunidad) => {
        if(comunidad.usuarios > 0) {
            alert("No se pueden borrar comunidades con usuarios activos");
        }else{
            alert("Pendiente de implentar para borrar la comunidad: " + comunidad.id);
        }
    }
 

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
                   <h2 className="h3 mb-1 text-dark fw-bold">Comunidades</h2>
                    <p className="text-muted small mb-0">Consulta y gestiona las comunidades</p>
                    
                </div>
                <button 
                    className="btn btn-primary d-flex align-items-center gap-2 shadow-sm"
                    onClick={() => btnNuevo()}
                    >
                    <i className="bi bi-plus-circle-fill"></i>
                    <span className="d-none d-sm-inline">Crear Comunidad</span>
                </button>
            </div>
            <div className="card border-0 shadow-sm">
                <div className="card-body p-0">
                    <table className="table table-hover align-middle mb-0">
                        <thead className="bg-light">
                            <tr>
                                <th className="ps-4">Nombre</th>
                                <th>Usuarios Activos</th>
                                <th>Codigo Postal</th>
                                <th className="text-center">Modificar</th>
                            </tr>
                        </thead>
                        <tbody>
                            {comunidades.map((comunidad) => (
                                <tr key={comunidad.id}>
                                    <td className="ps-4 fw-medium">{comunidad.nombre}</td>
                                    <td>{comunidad.usuarios}</td>
                                    <td>{comunidad.codPostal}</td>
                                    <td className="text-center">
                                        <button
                                            className="btn btn-sm btn-outline-primary me-2"
                                            tittle="Editar comunidad"
                                            onClick={() => btnModificar(comunidad.id)}
                                        >
                                            <i className="bi bi-pencil-square"></i>
                                        </button>
                                        <button
                                            className="btn btn-sm btn-outline-danger"
                                            title="Eliminar vecino"
                                            onClick={() => btnBaja(comunidad)}
                                            disabled={comunidad.id === usuario.id}
                                        >
                                            <i className="bi bi-trash3"></i>
                                        </button>
                                    </td>
                                </tr>
                            ))}
                        </tbody>
                    </table>
                </div>
            </div>
            <ModalComunidad
                mostrar={mostrarModal}
                onCerrar={() => setMostrarModal(false)}
                onGuardar={btnGuardar}
                comunidad={comunidad}
            />
        </div>
    )

}

export default ComunidadPage;