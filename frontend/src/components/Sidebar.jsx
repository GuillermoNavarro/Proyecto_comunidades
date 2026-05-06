import { Link, NavLink, useNavigate, UNSAFE_useScrollRestoration } from "react-router-dom";
import logoComunidad from '../assets/icons/house-logo.svg';

function Sidebar( {usuario, setUsuario, colapsado, setColapsado, movilAbierto, setMovilAbierto} ) {
  const navigate = useNavigate();
  const menuItems = [
    { name: "Tablon de Anuncios", path: "/anuncios", icon: "bi-pin-angle", roles: ["USER", "ADMIN"] },
    { name: "Mis datos", path: "/perfil", icon: "bi-person-circle", roles: ["USER", "ADMIN", "SUPER_ADMIN"] },
    { name: "Mis Recibos", path: "/recibos", icon: "bi-cash-stack", roles: ["USER", "ADMIN"] },
    { name: "Gestión de Cuotas", path:"/cuotas", icon: "bi-receipt-cutoff",roles: ["ADMIN"] },
    { name: "Cuentas Comunidad", path: "/cuentas", icon: "bi-bar-chart-line", roles: ["USER", "ADMIN"] },
    /*{ name: "Incidencias", path: "/incidencias", icon:"bi-exclamation-triangle", roles: ["USER", "ADMIN"] },*/
    { name: "Documentación", path: "/documentos", icon:"bi-files", roles: ["USER", "ADMIN"] },
    { name: "Gestión Usuarios", path: "/usuarios", icon: "bi-people-fill", roles: ["ADMIN", "SUPER_ADMIN"] },
    { name: "Gestion Comunidades", path: "/comunidades", icon:"bi-house-gear-fill", roles: ["SUPER_ADMIN"]}
  ];

  return (
    <>
    {movilAbierto && (
      <div
        className="d-md-none position-fixed top-0 start-0 w-100 vh-100 bg-dark opacity-50"
        style={{zIndex: 1040}}
        onClick={() => setMovilAbierto(false)}
        ></div>
    )}
    <aside
      className={`d-flex flex-column flex-shrink-0 p-3 bg-white border-end transition-all
          ${colapsado ? 'sidebar-colapsado' : 'sidebar-ancho'}
          ${movilAbierto ? 'sidebar-movil-abierto' : 'sidebar-movil-cerrado'}`}
        style={{zIndex: 1050}}
    >
      <Link
        to="/menu"
        className="d-flex align-items-center mb-3 mb-md-0 me-md-auto link-dark text-decoration-none overflow-hidden"
        onClick={() => setMovilAbierto(false)}>
          <img src={logoComunidad} alt="logo" style={{ width: '30px', height: '30px' }} className="flex-shrink-0" />
          {!colapsado && <span className="fs-5 fw-bold text-primary opacity-75 ms-2 text-nowrap">Tu Comunidad</span>}
      </Link>
      <button 
          className="btn btn-sm btn-light d-none d-md-block my-2" 
          onClick={() => setColapsado(!colapsado)}
        >
          <i className={`bi ${colapsado ? 'bi-arrow-right' : 'bi-arrow-left'}`}></i>
      </button>
     
      <hr />
      <ul className="nav nav-pills flex-column mb-auto overflow-hidden">
        {menuItems
          .filter(item => usuario?.rol && item.roles.includes(usuario?.rol))
          .map((item) => (
          <li className="nav-item" key={item.path}>
            <NavLink
              to={item.path}
              className={({ isActive }) =>
                `nav-link d-flex align-items-center ${isActive ? "nav-link active bg-primary bg-opacity-75" : "nav-link link-dark"}`
              }
              title={colapsado ? item.name : ""}
              onClick={() => setMovilAbierto(false)}
            >
              <i className={`bi ${item.icon} fs-5`}></i>
                  {!colapsado && <span className="ms-2 text-nowrap">{item.name}</span>}
              
            </NavLink>
          </li>
        ))}
      </ul>
      <hr />
      <div className="dropdown">
        <button
          className="btn btn-outline-danger btn-sm w-100 d-flex align-items-center justify-content-center"
          onClick={() => {
            localStorage.clear();
            setUsuario(null);
            navigate("/");
          }}
        >
          <i className="bi bi-box-arrow-left"></i>
            {!colapsado && <span className="ms-2">Cerrar Sesión</span>}
        </button>
      </div>
    </aside>
    </>
  );
}

export default Sidebar;
