import { NavLink } from "react-router-dom";

function Navbar() {
  return (
    <nav className="navbar navbar-dark bg-dark" style={{ padding: "1.5rem 2rem" }}>
        <div className="container-fluid">
            {/* Brand: very large */}
            <NavLink className="navbar-brand" to="/" style={{ fontSize: "8rem", fontWeight: "350" }}>
              Employee-Department
            </NavLink>

            <div className="d-flex justify-content-end align-items-center"
 style={{ gap: "9rem", fontSize: "5rem", margin: "2px 2px 2px 2px", padding: "3px 3px 3px 3px" }}>
                <NavLink className="nav-link text-light" to="/employees">Employees</NavLink>
                <NavLink className="nav-link text-light" to="/departments">Departments</NavLink>
                <NavLink className="nav-link btn btn-success" to="/employees/add" style={{ fontSize: "5rem" , color: "white"}}> Add Employee</NavLink>
                <NavLink className="nav-link btn btn-primary" to="/departments/add" style={{ fontSize: "5rem", color: "white" }}> Add Department</NavLink>
            </div>
        </div>
    </nav>
  );
}

export default Navbar;