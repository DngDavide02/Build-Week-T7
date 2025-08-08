import { BrowserRouter as Router, Routes, Route } from "react-router-dom";
import LoginForm from "./components/LoginForm";
import ClientiTable from "./components/ClientiTable";
import NavbarComponent from "./components/NavbarComponent";
import FattureTable from "./components/FattureTable";

function App() {
  return (
    <Router>
      <NavbarComponent />
      <Routes>
        <Route path="/" element={<LoginForm />} />
        <Route path="/clienti" element={<ClientiTable />} />
        <Route path="/fatture" element={<FattureTable />} />
      </Routes>
    </Router>
  );
}

export default App;
