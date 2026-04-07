import { useState, useEffect } from "react";
import {
  crearDocumento,
  modificarDocumento,
} from "../services/documentoService";

function ModalDocumentos({ docActual, modo, onSave, onClose }) {
  const [formData, setFormData] = useState({
    nombre: "",
    descripcion: "",
    archivo: null,
  });

  useEffect(() => {
    if (modo === "editar" && docActual) {
      setFormData({
        nombre: docActual.nombre,
        descripcion: docActual.descripcion,
        archivo: null,
      });
    } else {
      setFormData({ nombre: "", descripcion: "", archivo: null });
    }
  }, [docActual, modo]);

  const btnCambio = (e) => {
    const { name, value, files } = e.target;
    if (name === "archivo") {
      setFormData((prev) => ({ ...prev, archivo: files[0] }));
    } else {
      setFormData((prev) => ({ ...prev, [name]: value }));
    }
  };

  const btnGuardar = async (e) => {
    e.preventDefault();
    try {
      if (modo === "crear") {
        const data = new FormData();
        data.append("nombre", formData.nombre);
        data.append("descripcion", formData.descripcion);
        data.append("archivo", formData.archivo);
        const nuevoDoc = await crearDocumento(data);
        onSave(nuevoDoc, "crear");
      } else if (modo === "editar") {
        const datosModif = {
          nombre: formData.nombre,
          descripcion: formData.descripcion,
        };
        const docModificado = await modificarDocumento(
          docActual.id,
          datosModif,
        );
        onSave(docModificado, "editar");
      }
      onClose();
    } catch (err) {
      console.error("Error al guardar", err);
      const mensajeError = err.response?.data || "Ocurrió un error al subir el documento.";
      alert(mensajeError);
    }
  };

  return (
    <div
      className="modal show d-block"
      style={{ backgroundColor: "rgba(0,0,0,0.5)" }}
    >
      <div className="modal-dialog">
        <div className="modal-content">
          <div className="modal-header">
            <h5 className="modal-title">
              {modo === "crear" ? "Subir Documento" : "Editar Documento"}
            </h5>
            <button
              type="button"
              className="btn-close"
              onClick={onClose}
            ></button>
          </div>

          <div className="modal-body">
            <div className="mb-3">
              <label className="form-label">Nombre del Documento</label>
              <input
                type="text"
                className="form-control"
                name="nombre"
                value={formData.nombre}
                onChange={btnCambio}
              />
            </div>

            <div className="mb-3">
              <label className="form-label">Descripción</label>
              <textarea
                className="form-control"
                name="descripcion"
                value={formData.descripcion}
                onChange={btnCambio}
                rows="3"
              ></textarea>
            </div>

            {modo === "crear" && (
              <div className="mb-3">
                <label className="form-label">Archivo PDF</label>
                <input
                  type="file"
                  className="form-control"
                  name="archivo"
                  accept="application/pdf"
                  onChange={btnCambio}
                />
              </div>
            )}
          </div>

          <div className="modal-footer">
            <button
              type="button"
              className="btn btn-secondary"
              onClick={onClose}
            >
              Cancelar
            </button>
            <button
              type="button"
              className="btn btn-primary"
              onClick={btnGuardar}
            >
              {modo === "crear" ? "Subir" : "Guardar Cambios"}
            </button>
          </div>
        </div>
      </div>
    </div>
  );
}

export default ModalDocumentos;
