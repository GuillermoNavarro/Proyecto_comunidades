import { useEffect, useState } from "react";
import { getDocumentos, borrarDocumento } from "../services/documentoService";
import ModalDocumentos from "../components/ModalDocumentos";

function DocumentPage({ usuario }) {
  const esAdmin = usuario?.rol === "ADMIN" || usuario?.rol === "SUPER_ADMIN";
  const urlDocuemntos = import.meta.env.VITE_URL_DOCUMENTS;
  const [documentos, setDocumentos] = useState([]);
  const [cargando, setCargando] = useState(true);
  const [mostrarModal, setMostrarModal] = useState(false);
  const [docEditando, setDocEditando] = useState(null);

  useEffect(() => {
    getDocumentos()
      .then((datos) => {
        setDocumentos(datos);
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

  const formatearFecha = (fechaString) => {
    const opciones = { year: "numeric", month: "long", day: "numeric" };
    return new Date(fechaString).toLocaleDateString("es-ES", opciones);
  };

  const btnDescargar = async (nombreArchivo) => {
    try{
      const urlCompleta = `${urlDocuemntos}${nombreArchivo}`;
      const respuesta = await fetch(urlCompleta);
      if(!respuesta.ok){
        throw new Error("Archivo no encontrado");
      }
      const archivo = await respuesta.blob();
      const urlArchivo = window.URL.createObjectURL(archivo);
      const link = document.createElement('a');
      link.href = urlArchivo;
      link.download = nombreArchivo;
      document.body.appendChild(link);
      link.click();

      link.remove();
      window.URL.revokeObjectURL(urlArchivo);    
    }catch (err){
      alert("El documento no esta disponible");
    }
  }

  const btnBorrar = async (doc) => {
    if(
      window.confirm(`¿Seguro que quiere borrar ${doc.nombre}?`,)
    ){
      try{
        await borrarDocumento(doc.id);
        const listaActualizada = documentos.filter((d) => d.id !== doc.id);
        setDocumentos(listaActualizada);
      }catch(err){
        console.error("Error al borrar", err);
      }
    }
  }
  
  const btnGuardarModal = (documentoGuardado, modo) => {
    if (modo === 'crear') {
      setDocumentos ([ ...documentos, documentoGuardado ]);
    }else if(modo === 'editar'){
      const listaActualizada = documentos.map(doc => 
        doc.id === documentoGuardado.id ? documentoGuardado : doc
      );
      setDocumentos(listaActualizada);
    }
  };

  const abrirModalCrear = () =>{
    setDocEditando(null);
    setMostrarModal(true);
  }

  const abrirModalEditar = (doc) =>{
    setDocEditando(doc);
    setMostrarModal(true);
  }

  return (
    <div className="container-fluid py-2">
      <div className="d-flex justify-content-between align-items-center mb-4 border-bottom pb-3">
        <div>
          <h2 className="h3 mb-1 text-dark fw-bold">Documentos</h2>
          <p className="text-muted small mb-0">
            Archivos y actas de tu comunidad
          </p>
        </div>

        {esAdmin && (
          <button 
            className="btn btn-primary d-flex align-items-center gap-2 shadow-sm"
            onClick={abrirModalCrear}
            >
            <i className="bi bi-cloud-arrow-up-fill"></i>
            <span className="d-none d-sm-inline">Subir Documento</span>
          </button>
        )}
      </div>

      <div className="row g-4">
        {documentos.map((doc) => (
          <div className="col-12 col-md-6 col-xl-4" key={doc.id}>
            <div className="card h-100 shadow-sm border-0 bg-white w-100">
              <div className="card-body d-flex flex-column">
                <div className="d-flex align-items-start mb-3">
                  <div className="bg-danger bg-opacity-10 p-3 rounded-3 me-3 text-danger">
                    <i className="bi bi-file-earmark-pdf-fill fs-3 lh-1"></i>
                  </div>
                  <div>
                    <h5
                      className="card-title fw-bold mb-1 fs-6 text-dark text-break"
                      title={doc.nombre}
                    >
                      {doc.nombre}
                    </h5>
                    <small className="text-muted d-flex align-items-center gap-1">
                      <i className="bi bi-calendar3"></i>
                      {formatearFecha(doc.fecha)}
                    </small>
                  </div>
                </div>

                <p className="card-text text-secondary small mb-3 text-wrap">
                  {doc.descripcion}
                </p>

                <div className="mt-3 pt-3 border-top d-flex gap-2 justify-content-end">
                   <button
                    className="btn btn-outline-primary btn-sm d-flex align-items-center gap-1"
                    title="Ver PDF"
                    onClick={() => window.open(`${urlDocuemntos}${doc.documento}`, '_blank')}
                  >
                    <i className="bi bi-eye"></i>
                  </button>
                  <button
                    className="btn btn-outline-primary btn-sm d-flex align-items-center gap-1"
                    title="Descargar PDF"
                    onClick={() => btnDescargar(doc.documento)}
                  >
                    <i className="bi bi-download"></i>
                  </button>

                  {esAdmin && (
                    <>
                      <button
                        className="btn btn-outline-secondary btn-sm d-flex align-items-center gap-1"
                        title="Editar detalles"
                        onClick={()=> abrirModalEditar(doc)}
                      >
                        <i className="bi bi-pencil-fill"></i>
                      </button>
                      <button
                        className="btn btn-outline-danger btn-sm d-flex align-items-center gap-1"
                        title="Eliminar documento"
                        onClick={() => btnBorrar(doc)}
                      >
                        <i className="bi bi-trash-fill"></i>
                      </button>
                    </>
                  )}
                </div>
              </div>
            </div>
          </div>
        ))}
      </div>
      {documentos.length === 0 && (
        <div className="text-center text-muted py-5 mt-4 bg-white rounded shadow-sm">
          <i className="bi bi-folder2-open display-1 text-secondary opacity-25 mb-3"></i>
          <h5 className="fw-bold text-dark opacity-75">No hay documentos</h5>
          <p>Aún no se ha subido ningún archivo a la comunidad.</p>
        </div>
      )}
      {mostrarModal && (
        <ModalDocumentos
          docActual={docEditando}
          modo={docEditando ? 'editar' : 'crear'}
          onClose={() => setMostrarModal(false)}
          onSave={btnGuardarModal}
          />
      )}
    </div>
  );
}

export default DocumentPage;
