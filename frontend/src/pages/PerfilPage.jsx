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
            console.erro("Error al modificar ", err)
        }
        console.log ("datos enviados");

        setUsuario(datosActualizados);
        setModo('ver');
    }
    return(
        <div className="perfil-container">
            <div className="d-flex justify-content-between align-items-center mb-3">
                <h2>Mis Datos</h2>
                <div className='d-flex gap-2'>
                <button 
                    className="btn btn-outline-secondary"
                    onClick={() => setMostrarPass(true)}
                    >
                    <i className="bi bi-key me-2"></i>Cambio Contraseña
                </button>
                {modo === 'ver' &&(
                    <button
                        className='btn btn-outline-primary'
                        onClick={() => setModo('editar')}
                        ><i className="bi bi-pencil me-2"></i>Modificar</button>
                )} 
                </div>               
            </div>
            <UserForm
                initialData={usuario}
                modo={modo}
                esAdmin = {false}
                onsave={guardar}
            />
            {modo === 'editar' && (
                <button 
                    className="btn btn-link text-muted mt-2" 
                    onClick={() => setModo('ver')}>
                    Cancelar
                </button>
            )}
            <PassModal
                show={mostrarPass}
                esForzado={false}
                onHide={() => setMostrarPass(false)}
            />


        </div>
    )
}

export default PerfilPage;