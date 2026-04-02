import { useState, useEffect } from 'react';
import { BrowserRouter as Router, Routes, Route, Navigate } from 'react-router-dom';
import LandingPage from './pages/LandingPage';
import LoginPage from './pages/LoginPage';
import MenuPage from './pages/MenuPage';
import MainLayout from './components/MainLayout';
import { getPerfil } from './services/authService';


function App() {
  const [usuario, setUsuario] = useState(null);
  const [cargando, setCargando] = useState(true);

  useEffect(() => {
    const token = localStorage.getItem('token');
    if(token){
      getPerfil().then(datos => {
        setUsuario(datos);
        setCargando(false);
      })
      .catch(() => {
        localStorage.removeItem('token');
        setCargando(false);
      });
    }else{
      setCargando(false);
    }
  }, []);

  if (cargando) return <div style={{textAlign: 'center', marginTop: '50px'}}>Conectando con el servidor... </div>;

  return (
    <Router>
      <Routes>
        {/*Parte publica*/}
        <Route path="/" element={
          usuario ? <Navigate to="/menu" /> : <LandingPage setUsuario={setUsuario} />
          } 
        />

        <Route path='/menu' element={
          usuario ? (
          <MainLayout usuario={usuario}>
            <MenuPage usuario={usuario} />
          </MainLayout>
          ) : (
            <Navigate to="/" />
          )
        } />

        
      </Routes>
    </Router>
  );
}

export default App;