function AnuncioCard({ publicacion, esAdmin, onEdit, onDelete, urlDocumentos }){
    const formatearFecha = (fechaString) => {
    const opciones = { year: "numeric", month: "long", day: "numeric" };
    return new Date(fechaString).toLocaleDateString("es-ES", opciones);
  };

  return (
    <div className="card h-100 shadow-sm border-0 bg-white w-100">
        <div className="card-body d-flex flex-column">
            <div className="d-flex align-content-start mb-3">
                <div className={`p-3 rounded-3 me-3 ${publicacion.tipo === 'NOTICIA' ? 'bg-primary text-primary' : 'bg-warning text-warning'} bg-opacity-50`}>
                    <i className={`fs-3 lh-1 bi ${publicacion.tipo === 'NOTICIA' ? 'bi-megaphone-fill' : 'bi-tools'}`}></i>
                </div>

                <div>
                    <h5 className="card-title fw-bold mb-1 fs-6 text-dark text-break">
                        {publicacion.titulo}
                    </h5>
                    <small className="text-muted d-flex align-items-center gap-1">
                        <i className="bi bi-calendar3"></i>
                        Publicado: {formatearFecha(publicacion.fechaCreacion)}
                    </small>
                    {publicacion.fechaFin && (
                        <small className="text-muted d-flex align-items-center gap-1">
                            <i className="bi bi-calendar3"></i>
                            Limite: {formatearFecha(publicacion.fechaFin)}
                        </small>
                    )}
                </div>
            </div>
            <p className="card-text text-secondary small mb-3 text-wrap">
                {publicacion.descripcion}
            </p>
            {publicacion.documento && (
                <div className="mb-3">
                    <a 
                    href={`${urlDocumentos}${publicacion.documento.documento}`} 
                    target="_blank" 
                    rel="noreferrer"
                    className="badge bg-secondary text-decoration-none p-2"
                    >
                        <i className="bi bi-paperclip me-1"></i>
                        {publicacion.documento.nombre}
                    </a>
                </div>
            )}
            {esAdmin && (
                <div className="mt-auto pt-3 border-top d-flex gap-2 justify-content-end">
                    <button
                        className="btn btn-outline-secondary btn-sm d-flex align-items-center gap-1"
                        title="Editar publicación"
                        onClick={() => onEdit(publicacion)}
                    >
                        <i className="bi bi-pencil-fill"></i>
                    </button>
                    <button
                        className="btn btn-outline-danger btn-sm d-flex align-items-center gap-1"
                        title="Eliminar publicación"
                        onClick={() => onDelete(publicacion)}
                    >
                        <i className="bi bi-trash-fill"></i>
                    </button>
                </div>
            )}
        </div>
    </div>
  );

}

export default AnuncioCard;