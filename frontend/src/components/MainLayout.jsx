import Sidebar from "./Sidebar";
import Header from "./Header";

function MainLayout({ children, usuario }) {
  if(!usuario){
    return <div className="d-flex justify-content-center mt-5">Cargando...</div>;
  }

  return (
    <div className="container-fluid p-0">
      <div className="d-flex">
        {/* Lado Izquierdo */}
        <Sidebar usuario={usuario}/>

        {/* Lado Derecho */}
        <div className="flex-grow-1 d-flex flex-column vh-100 overflow-hidden">
          <Header usuario={usuario} />

          <main className="p-4 bg-light overflow-auto">{children}</main>
        </div>
      </div>
    </div>
  );
}

export default MainLayout;
