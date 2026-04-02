import { useState } from 'react';
import LoginModal from '../components/LoginModal';
import logoComunidad from '../assets/icons/house-logo.svg';
import heroImg from '../assets/hero.webp'

function LandingPage( {setUsuario} ) {
  const [showModal, setShowModal] = useState(false);
  

  return (
    <div className="landing-page min-vh-100 d-flex flex-column bg-light">
      <header className="p-3 mb-4 border-bottom bg-white shadow-sm">
        <div className="container">
          <div className="d-flex flex-wrap align-items-center justify-content-center justify-content-md-between">
            <div className="col-md-3 mb-2 mb-md-0 fs-4 fw-bold text-primary opacity-75">
              <span className="me-2"><img src={logoComunidad} alt="logo comunidad" style={{width: '50px', height: '50px' }}/></span> Micro Comunidades
            </div>
            <div className="col-md-3 text-end">
              <button className="btn btn-primary bg-opacity-75 px-4 fw-bold shadow-sm" onClick={() => setShowModal(true)} >
                Acceder al Portal
              </button>
            </div>
          </div>
        </div>
      </header>

      <main className="flex-grow-1">
        <section
          className="text-center d-flex align-items-center justify-content-center text-white"
          style={{
            backgroundImage: `linear-gradient(rgba(0,0,0,0.7), rgba(0,0,0,0.7)), url(${heroImg})`,
            backgroundSize: 'cover',
            backgroundPosition: 'center',
            minHeight: '80vh'
          }}>
            <div className="container px-4">
              <h1 className="display-2 fw-bold mb-4 lh-sm">
                Gestiona tu comunidad <br className="d-none d-md-block" /> 
                <span className="text-primary opacity-100">sin complicaciones</span>
              </h1>
              <p className="lead mb-5 mx-auto" style={{maxWidth: '800px'}}>
                Consulta tus recibos, reporta incidencias y mantente informado de las noticias oficiales. Diseñado específicamente para comunidades que se gestionan de forma autónoma.
              </p>
              <div className="d-flex justify-content-center">
                <a 
                  href="#solicitar" 
                  className="btn btn-primary btn-lg px-5 py-3 fw-bold shadow-lg border-0"
                  style={{ transition: 'transform 0.2s' }}
                  onMouseOver={(e) => e.target.style.transform = 'scale(1.05)'}
                  onMouseOut={(e) => e.target.style.transform = 'scale(1)'}
                >
                  Solicita tu prueba gratuita
                </a>
              </div>

            </div>
          </section>

          <section id="solicitar" className="py-5 bg-white">
            <div className='container py-5'>
              <div className='row justify-content-center'>
                <h2 className='fw-bold mb-3'>Solicita tu prueba gratuita</h2>
                <p className='text-muted mb-4'>Dejanos tus datos y nos pondremos en contacto para configurar tu comunidad</p>
                <div className='card border-0 shadow-sm p-4 bg-light'>
                  <form onSubmit={(e) => {e.preventDefault(); alert('¡Pronto contactaremos contigo!');}}>
                    <div className='mb-3 text-start'>
                      <label className='form-label small fw-bold'>Nombre</label>
                      <input type="text" className='form-control' placeholder='Tu nombre' required />  
                    </div>
                    <div className='mb-3 text-start'>
                      <label className='form-label small fw-bold'>Telefono</label>
                      <input type="tel" className='form-control' placeholder='600 123 456' required />
                    </div>
                    <div className='mb-4 text-start'>
                      <label className='form-label small fw-bold'>Email</label>
                      <input type="email" className='form-control' placeholder='vecino@correo.com' required />
                    </div>
                    <button type='submit' className='btn btn-primary btn-lg w-100 fw-bold'>
                      Enviar solicitud
                    </button>
                  </form>
                </div>
              </div>
            </div>

          </section>

      </main>

    

      <footer className="py-3 my-4 border-top text-center text-muted">
        <p>© 2026 Micro Comunidades - Proyecto Final DAW</p>
      </footer>

      <LoginModal
        isOpen={showModal}
        onClose={() => setShowModal(false)}
        setUsuario={setUsuario}
      />
    </div>
  );
}

export default LandingPage;