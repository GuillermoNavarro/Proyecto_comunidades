import { Link } from "react-router-dom";

function Header({usuario, onAbrirMenu}){
    if(!usuario){
      return (
      <header className="navbar navbar-light bg-white border-bottom px-4 shadow-sm" style={{ height: '70px' }}>
        <div className="container-fluid d-flex align-items-center">
          <div className="spinner-border spinner-border-sm text-primary me-2"></div>
          <span className="text-muted small">Cargando datos de la comunidad...</span>
        </div>
      </header>
      );
    }

    return (
    <header className="navbar navbar-light bg-white border-bottom px-4 shadow-sm" style={{ height: '70px' }}>
      <div className="container-fluid d-flex justify-content-between align-items-center p-0">

        <div className="d-flex align-items-center">
          <button
            className="btn btn-light d-md-none me-2 border-0"
            onClick={onAbrirMenu}
          >
            <i className="bi bi-list fs-3"></i>
          </button>
          <span className="navbar-brand mb-0 h1 fs-6 fw-bold text-dark text-uppercase letter-spacing-1">
            {usuario?.comunidad?.nombre || "Comunidad VecinoGest"}
          </span>
        </div>
                
        <div className="d-flex align-items-center">
          <div className="text-end me-3 d-none d-sm-block">
            <div className="fw-bold text-dark lh-1 mb-1" style={{ fontSize: '0.9rem' }}>
              {usuario?.nombre}
            </div>
            <div className="text-muted fw-medium" style={{ fontSize: '0.75rem' }}>
              Puerta: <span className="text-primary">{usuario?.puerta || "N/A"}</span>
            </div>
          </div>
          <Link to="/perfil" style={{textDecoration: "none"}}>
          <div 
            className="bg-primary bg-opacity-75 text-white rounded-circle d-flex align-items-center justify-content-center fw-bold shadow-sm" 
            style={{ 
              width: '42px', 
              height: '42px', 
              fontSize: '1.1rem',
              border: '2px solid white' 
            }}
          >
            {usuario?.nombre?.charAt(0).toUpperCase()}
          </div>
          </Link>
        </div>
      </div>
    </header>
  );
}

export default Header;