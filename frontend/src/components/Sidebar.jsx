import { NavLink, UNSAFE_useScrollRestoration } from "react-router-dom";
import logoComunidad from '../assets/icons/house-logo.svg';

function Sidebar( {usuario} ) {
  const menuItems = [
    { name: "Tablon de Anuncios", path: "/menu", roles: ["USER", "ADMIN", "SUPER_ADMIN"] },
    { name: "Mis datos", path: "/perfil", roles: ["USER", "ADMIN", "SUPER_ADMIN"] },
    { name: "Cuentas Comunidad", path: "/cuentas", roles: ["USER", "ADMIN", "SUPER_ADMIN"] },
    { name: "Incidencias", path: "/incidencias", roles: ["USER", "ADMIN", "SUPER_ADMIN"] },
    { name: "Gestión Usuarios", path: "/usuarios", roles: ["ADMIN", "SUPER_ADMIN"] }
  ];

  return (
    <aside
      className="d-flex flex-column flex-shrink-0 p-3 bg-white border-end"
      style={{ width: "280px", height: "100vh" }}
    >
      <a
        href="/"
        className="d-flex align-items-center mb-3 mb-md-0 me-md-auto link-dark text-decoration-none"
      >
        <span className="fs-4 fw-bold text-primary opacity-75"><img src={logoComunidad} alt="logo comunidad" style={{width: '30px', height: '30px' }}/> Tu Comunidad</span>
      </a>
      <hr />
      <ul className="nav nav-pills flex-column mb-auto">
        {menuItems
          .filter(item => usuario?.rol && item.roles.includes(usuario?.rol))
          .map((item) => (
          <li className="nav-item" key={item.path}>
            <NavLink
              to={item.path}
              className={({ isActive }) =>
                isActive ? "nav-link active bg-primary bg-opacity-75" : "nav-link link-dark"
              }
            >
              {item.name}
            </NavLink>
          </li>
        ))}
      </ul>
      <hr />
      <div className="dropdown">
        <button
          className="btn btn-outline-danger btn-sm w-100"
          onClick={() => {
            localStorage.clear();
            window.location.href = "/";
          }}
        >
          Cerrar Sesión
        </button>
      </div>
    </aside>
  );
}

export default Sidebar;
