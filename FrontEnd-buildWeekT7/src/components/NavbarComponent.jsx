import { Navbar, Nav, Container } from "react-bootstrap";

const NavbarComponent = () => {
  return (
    <Navbar
      expand="lg"
      className="py-3 shadow-sm border-0"
      style={{
        background: "linear-gradient(135deg, #1f1c2c 0%, #928dab 100%)",
      }}
    >
      <Container>
        <Navbar.Brand href="#home" className="fw-bold fs-4" style={{ color: "#ffffff" }}>
          ⚡EPIC ENERGY SERVICES
        </Navbar.Brand>
        <Navbar.Toggle aria-controls="basic-navbar-nav" style={{ backgroundColor: "#ffffff" }} />
        <Navbar.Collapse id="basic-navbar-nav">
          <Nav className="ms-auto">
            <Nav.Link href="/" className="mx-2 fw-medium" style={{ color: "#e0e0e0" }}>
              Login
            </Nav.Link>
          </Nav>
        </Navbar.Collapse>
      </Container>
    </Navbar>
  );
};

export default NavbarComponent;
