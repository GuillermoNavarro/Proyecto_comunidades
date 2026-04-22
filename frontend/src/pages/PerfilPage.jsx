import UserForm from '../components/UserForm';
import { useState } from 'react';
import { modificarUser } from '../services/usuarioService';
import PassModal from '../components/PassModal';

function PerfilPage({ usuario, setUsuario }){
    const [modo, setModo] = useState('ver');
    const [mostrarPass, setMostrarPass] = useState(false);
    
    const guardar = async (datosActualizados) => {
        try{
            await modificarUser(datosActualizados);
        }catch(err){
            console.error("Error al modificar ", err)
        }
        console.log ("datos enviados");

        setUsuario(datosActualizados);
        setModo('ver');
    }
    return(
        <div className="container-fluid py-2">
            <div className="d-flex justify-content-between align-items-center mb-4 border-bottom pb-3">
                <div>
                    <h2 className="h3 mb-1 text-dark fw-bold">Mi Perfil</h2>
                    <p className="text-muted small mb-0">Consulta y actualiza tus datos personales</p>
                </div>
                <div className='d-flex gap-2'>
                    <button 
                        className="btn btn-outline-secondary d-flex align-items-center shadow-sm"
                        onClick={() => setMostrarPass(true)}
                        >
                        <i className="bi bi-key me-2"></i>
                        <span className="d-none d-sm-inline">Contraseña</span>
                    </button>
                    {modo === 'ver' &&(
                        <button
                            className='btn btn-primary d-flex align-items-center shadow-sm'
                            onClick={() => setModo('editar')}
                            ><i className="bi bi-pencil me-2"></i>
                            <span className="d-none d-sm-inline">Modificar</span>
                        </button>
                    )} 
                </div>               
            </div>
            <div className="row justify-content-center g-4">
                <div className="col-12 col-lg-12">
                    <div className="card shadow-sm border-0">
                        <div className="card-body p-4 p-md-5">    
                            <UserForm
                                initialData={usuario}
                                modo={modo}
                                esAdmin = {false}
                                onsave={guardar}
                            />
                            {modo === 'editar' && (
                                <div className="text-left mt-4 pt-3 border-top">
                                    <button 
                                        className="btn btn-link text-muted mt-2" 
                                        onClick={() => setModo('ver')}>
                                        <i className="bi bi-x-circle me-1"></i> Cancelar edición
                                    </button>
                                </div>
                            )}
                        </div>
                    </div>
                </div>
            </div>
            <PassModal
                show={mostrarPass}
                esForzado={false}
                onHide={() => setMostrarPass(false)}
            />
        </div>
    );
}

export default PerfilPage;