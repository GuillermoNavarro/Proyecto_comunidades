import { useState, useEffect } from 'react';
import { BrowserRouter as Router, Routes, Route, Navigate } from 'react-router-dom';
import './App.css';
import LandingPage from './pages/LandingPage';
import LoginPage from './pages/LoginPage';
import MenuPage from './pages/MenuPage';
import PerfilPage from './pages/PerfilPage';
import MainLayout from './components/MainLayout';
import { getPerfil } from './services/authService';
import GestionUsuarios from './pages/GestionUsuarios';
import DocumentPage from './pages/DocumentPage';
import NoticePage from './pages/NoticePage';
import RecibosPage from './pages/RecibosPage';
import CuentasPage from './pages/CuentasPage';
import CuotasPage from './pages/CuotasPage';

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
          <MainLayout usuario={usuario} setUsuario={setUsuario}>
            <MenuPage usuario={usuario} />
          </MainLayout>
          ) : (
            <Navigate to="/" />
          )
        } />

        <Route path='/usuarios' element={
          usuario && (usuario.rol === 'ADMIN' || usuario.rol === 'SUPER_ADMIN') ? (
           <MainLayout usuario={usuario} setUsuario={setUsuario}>
            <GestionUsuarios usuario={usuario}/>           
           </MainLayout>
          ) : (
            <Navigate to="/" />
          )
        }/>

        <Route path='/perfil' element={
          usuario ? (
          <MainLayout usuario={usuario} setUsuario={setUsuario}>
            <PerfilPage usuario={usuario} setUsuario={setUsuario} />
          </MainLayout>
          ) : (
            <Navigate to="/" />
          )
        } />

        <Route path='/documentos' element={
          usuario ? (
          <MainLayout usuario={usuario} setUsuario={setUsuario}>
            <DocumentPage usuario={usuario} />
          </MainLayout>
          ) : (
            <Navigate to="/" />
          )
        } />

        <Route path='/anuncios' element={
          usuario ? (
          <MainLayout usuario={usuario} setUsuario={setUsuario}>
            <NoticePage usuario={usuario} />
          </MainLayout>
          ) : (
            <Navigate to="/" />
          )
        } />

        <Route path='/recibos' element={
          usuario ? (
          <MainLayout usuario={usuario} setUsuario={setUsuario}>
            <RecibosPage usuario={usuario} />
          </MainLayout>
          ) : (
            <Navigate to="/" />
          )
        } />

        <Route path='/cuentas' element={
          usuario ? (
          <MainLayout usuario={usuario} setUsuario={setUsuario}>
            <CuentasPage usuario={usuario} />
          </MainLayout>
          ) : (
            <Navigate to="/" />
          )
        } />

        <Route path='/cuotas' element={
          usuario && (usuario.rol === 'ADMIN' || usuario.rol === 'SUPER_ADMIN') ? (
          <MainLayout usuario={usuario} setUsuario={setUsuario}>
            <CuotasPage usuario={usuario} />
          </MainLayout>
          ) : (
            <Navigate to="/" />
          )
        } />

        <Route path='/comunidades' element={
          usuario && (usuario.rol === 'SUPER_ADMIN') ? (
          <MainLayout usuario={usuario} setUsuario={setUsuario}>
            <CuotasPage usuario={usuario} />
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