import { useNavigate } from 'react-router-dom';

function MenuPage() {
  const navigate = useNavigate();

  const cerrarSesion = () => {
    localStorage.removeItem('token'); // Limpiamos el rastro
    navigate('/');
  };

  return (
    <div style={{ padding: '20px' }}>
      <h1>Tu comunidad de vecinos</h1>
      
    </div>
  );
}

export default MenuPage;