import React, { useEffect, useState } from "react";
import { Table, Container, Alert, Spinner, Button, ButtonGroup } from "react-bootstrap";
import { useNavigate } from "react-router-dom";

const ClientiTable = () => {
  const [clienti, setClienti] = useState([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState("");
  const navigate = useNavigate();

  useEffect(() => {
    const token = localStorage.getItem("token");
    if (!token) {
      setError("Non sei autenticato");
      setLoading(false);
      return;
    }

    fetch("http://localhost:3001/api/clienti", {
      headers: {
        "Content-Type": "application/json",
        Authorization: `Bearer ${token}`,
      },
    })
      .then((res) => {
        if (!res.ok) throw new Error("Errore caricamento Clienti");
        return res.json();
      })
      .then((data) => {
        setClienti(data);
        setLoading(false);
      })
      .catch((err) => {
        setError(err.message);
        setLoading(false);
      });
  }, []);

  if (loading) {
    return (
      <Container
        className="text-center mt-5"
        style={{
          minHeight: "100vh",
          background: "linear-gradient(135deg, #1f1c2c 0%, #928dab 100%)",
          color: "#f1f1f1",
        }}
      >
        <Spinner animation="border" variant="primary" />
      </Container>
    );
  }

  if (error) {
    return (
      <Container
        className="mt-5"
        style={{
          minHeight: "100vh",
          background: "linear-gradient(135deg, #1f1c2c 0%, #928dab 100%)",
          color: "#f1f1f1",
        }}
      >
        <Alert variant="danger">{error}</Alert>
      </Container>
    );
  }

  return (
    <div
      style={{
        minHeight: "100vh",
        background: "linear-gradient(135deg, #1f1c2c 0%, #928dab 100%)",
        color: "#f1f1f1",
        padding: "3rem 0",
        display: "flex",
        justifyContent: "center",
        overflowX: "hidden",
      }}
    >
      <Container fluid style={{ maxWidth: "1200px", paddingLeft: 0, paddingRight: 0 }}>
        <div className="text-center mb-4">
          <ButtonGroup>
            <Button
              onClick={() => navigate("/clienti")}
              style={{
                backgroundColor: "#2f3136",
                borderColor: "#7289da",
                color: "#f1f1f1",
                padding: "0.5rem 1.5rem",
              }}
            >
              Clienti
            </Button>
            <Button
              onClick={() => navigate("/fatture")}
              style={{
                backgroundColor: "#2f3136",
                borderColor: "#43b581",
                color: "#f1f1f1",
                padding: "0.5rem 1.5rem",
              }}
            >
              Fatture
            </Button>
          </ButtonGroup>
        </div>

        <h2 className="mb-4 text-center">Clienti</h2>

        <Table
          style={{
            color: "#f1f1f1",
            backgroundColor: "#40444b",
            borderColor: "#7289da",
            fontSize: "1.1rem",
            width: "100%",
            tableLayout: "auto",
            borderCollapse: "separate",
            borderSpacing: "0",
            marginBottom: 0,
          }}
          className="text-center"
        >
          <thead>
            <tr>
              {[
                "ID",
                "Ragione Sociale",
                "Partita IVA",
                "Email",
                "Data Inserimento",
                "Fatturato Annuale",
                "Telefono",
                "Nome Contatto",
                "Cognome Contatto",
                "Telefono Contatto",
              ].map((header) => (
                <th
                  key={header}
                  style={{
                    borderColor: "#7289da",
                    backgroundColor: "#2f3136",
                    color: "#f1f1f1",
                    padding: "0.75rem 1rem",
                  }}
                >
                  {header}
                </th>
              ))}
            </tr>
          </thead>
          <tbody>
            {clienti.map((c, i) => (
              <tr key={c.id}>
                {[
                  c.id,
                  c.ragioneSociale,
                  c.partitaIVA,
                  c.email,
                  new Date(c.dataInserimento).toLocaleDateString(),
                  c.fatturatoAnnuale,
                  c.telefono,
                  c.nome,
                  c.cognome,
                  c.telefonoContatto,
                ].map((cell, idx) => (
                  <td
                    key={idx}
                    style={{
                      borderColor: "#7289da",
                      backgroundColor: i % 2 === 0 ? "#40444b" : "#2f3136",
                      color: "#f1f1f1",
                      padding: "0.75rem 1rem",
                    }}
                  >
                    {cell}
                  </td>
                ))}
              </tr>
            ))}
          </tbody>
        </Table>
      </Container>
    </div>
  );
};

export default ClientiTable;
