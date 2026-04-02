import { useNavigate } from 'react-router-dom';

function MenuPage() {
  const navigate = useNavigate();

  const cerrarSesion = () => {
    localStorage.removeItem('token'); // Limpiamos el rastro
    navigate('/');
  };

  return (
    <div style={{ padding: '20px' }}>
      <h1>Panel de Control</h1>
      <p>¿Qué quieres gestionar hoy?</p>
      
      <div style={{ display: 'flex', gap: '20px', marginTop: '30px' }}>
        <button onClick={() => navigate('/comunidades')} style={{ width: '200px', height: '100px' }}>
          🏢 Gestionar Comunidades
        </button>
        <button style={{ width: '200px', height: '100px' }}>
          👤 Mi Perfil (Próximamente)
        </button>
      </div>

      <button onClick={cerrarSesion} style={{ marginTop: '50px', backgroundColor: '#ff4444', color: 'white' }}>
        Cerrar Sesión
      </button>
    </div>
  );
}

export default MenuPage;