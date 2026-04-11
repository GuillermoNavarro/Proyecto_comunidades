import { useEffect, useState } from "react";
import { borrarPublicacion, getNoticias } from "../services/publicacionService";
import AnuncioCard from "../components/AnuncioCard";
import ModalNoticia from "../components/ModalNoticia";

function NoticePage({ usuario }){
    const esAdmin = usuario?.rol === "ADMIN";
    const urlDocuemntos = import.meta.env.VITE_URL_DOCUMENTS;
    const [noticias, setNoticias] = useState([]);
    const [cargando, setCargando] = useState(true);

    const [mostrarModal, setMostrarModal] = useState(false);
    const [modo, setModo] = useState("crear");
    const [noticiaEditando, setNoticiaEditando] = useState(null);


    useEffect(() => {
        getNoticias()
            .then((datos) => {
                setNoticias(datos);
                setCargando(false);
            })
            .catch(() => {
                setCargando(false);
            });
    }, []);

    const abrilModalCrear = () => {
        setNoticiaEditando(null);
        setModo("crear");
        setMostrarModal(true);
    }

    const abrilModalEditar = (noticia) => {
        setNoticiaEditando(noticia);
        setModo("editar");
        setMostrarModal(true);
    }

    const btnGuardarModal = (noticiaGuardada, tipo) => {
        if(tipo === "crear"){
            setNoticias([noticiaGuardada, ...noticias]);
        }else if(tipo === "editar"){
            const listaActualizada = noticias.map(n => 
                n.id === noticiaGuardada.id ? noticiaGuardada : n
            );
            setNoticias(listaActualizada);   
        }
    };

    const btnBorrar = async (noticia) => {
        if(window.confirm(`¿Seguro que quiere borrar la noticia: ${noticia.titulo}?`)){
            try{
                await borrarPublicacion(noticia.id);
                const listaActualizada = noticias.filter((n) => n.id !== noticia.id);
                setNoticias(listaActualizada);
            }catch (err) {
                console.error("Error al borrar", err);
                alert("Error al eliminar la noticia");
            }
        }
    };
    
    const noticiasOrdenadas = [...noticias].sort((a,b) => {
        const fechaA = new Date(a.fechaCreacion);
        const fechaB = new Date(b.fechaCreacion);
        return fechaB-fechaA;
    });
    
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
                    <h2 className="h3 mb-1 text-dark fw-bold">Tablón de Noticias</h2>
                    <p className="text-muted small mb-0">Últimas novedades de la comunidad</p>
                </div>

                {esAdmin && (
                    <button 
                        className="btn btn-primary d-flex align-items-center gap-2 shadow-sm"
                        onClick={abrilModalCrear}
                    >
                        <i className="bi bi-plus-circle-fill"></i>
                        <span className="d-none d-sm-inline">Nueva Noticia</span>
                    </button>
                )}
            </div>

            <div className="row justify-content-center g-4">
                <div className="col-12 col-lg-8"> 
                    <div className="d-flex flex-column gap-4">
                        {noticiasOrdenadas.map((noticia) => (
                            <AnuncioCard 
                                key={noticia.id}
                                publicacion={noticia} 
                                esAdmin={esAdmin} 
                                urlDocumentos={urlDocuemntos}
                                onEdit={() => abrilModalEditar(noticia)}
                                onDelete={() => btnBorrar(noticia)}
                            />
                        ))}
                    </div>
                    {noticias.length === 0 && (
                        <div className="text-center text-muted py-5 mt-4 bg-white rounded shadow-sm">
                            <i className="bi bi-megaphone display-1 text-secondary opacity-25 mb-3"></i>
                            <h5 className="fw-bold text-dark opacity-75">No hay noticias</h5>
                            <p>El tablón de la comunidad está tranquilo ahora mismo.</p>
                        </div>
                    )}
                </div>
            </div>

            
            {mostrarModal && (
                <ModalNoticia
                    noticiaActual={noticiaEditando}
                    modo={modo}
                    onClose={() => setMostrarModal(false)}
                    onSave={btnGuardarModal}
                />
            )}
        </div>
    );
}

export default NoticePage;