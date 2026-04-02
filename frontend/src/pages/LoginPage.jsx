import { useState } from "react";
import { login } from "../services/authService";
import { useNavigate } from "react-router-dom";

function LoginPage( {setUsuario} ) {
  const navigate = useNavigate();
  const [email, setEmail] = useState("");
  const [password, setPassword] = useState("");
  const [error, setError] = useState("");

  const manejarLogin = async (e) => {
    e.preventDefault(); 
    try {
      console.log("intentado loguin para: ", email);
      const respuesta = await login(email, password);
      setUsuario(respuesta.perfil); 
      console.log(respuesta); 
      navigate('/menu');
    } catch (err) {
      console.log("ERROR", err.response?.status, err.response?.data);
      setError("Usuario o contraseña incorrectos");
    }
  };

  return (
    <div className="container mt-5" style={{ maxWidth: '400px' }}>
      <div className="card shadow p-4">
        <h2 className="text-center mb-4">Iniciar Sesión</h2>
        <form onSubmit={manejarLogin}>
          <div className="mb-3">
            <input
              type="email"
              className="form-control"
              placeholder="Email"
              value={email}
              onChange={(e) => setEmail(e.target.value)}
              required
            />
          </div>
          <div className="mb-3">
            <input
              type="password"
              className="form-control"
              placeholder="Contraseña"
              value={password}
              onChange={(e) => setPassword(e.target.value)}
              required
            />
          </div>
          <button type="submit" className="btn btn-primary w-100">Entrar</button>
        </form>
        {error && <p className="text-danger mt-3 text-center">{error}</p>}
      </div>
    </div>

  );
}

export default LoginPage;
