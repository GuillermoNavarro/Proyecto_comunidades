import { useState } from "react";
import Sidebar from "./Sidebar";
import Header from "./Header";
import PassModal from "./PassModal";

function MainLayout({ children, usuario, setUsuario }) {
  const [colapsado, setColapsado] = useState(false);
  const [movilAbierto, setMovilAbierto] = useState(false); 

  if(!usuario){
    return <div className="d-flex justify-content-center mt-5">Cargando...</div>;
  }

  const passCambiado = () => {
    console.log("1. onHide recibido en MainLayot");
    console.log("2. valor del usuario:", usuario);
    if(setUsuario){
      console.log("3.llamando a setusuartio poara cmabiar el estado");
      setUsuario({...usuario, cambiarPass: false});
    }
  };

  return (
    <div className="container-fluid p-0">
      <div className="d-flex">
        {/* Lado Izquierdo */}
        <Sidebar 
          usuario={usuario}
          colapsado={colapsado}
          setColapsado={setColapsado}
          movilAbierto={movilAbierto}
          setMovilAbierto={setMovilAbierto}
          />

        {/* Lado Derecho */}
        <div className="flex-grow-1 d-flex flex-column vh-100 overflow-hidden">
          <Header usuario={usuario} 
          onAbrirMenu={()=> setMovilAbierto(true)}
            />

          <main className="p-4 bg-light overflow-auto">{children}</main>
        </div>
      </div>
      <PassModal
        show={usuario.cambiarPass === true}
        esForzado={true}
        onHide={passCambiado}
      />
    </div>
  );
}

export default MainLayout;
