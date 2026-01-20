  import { BrowserRouter as Router, Routes, Route } from "react-router-dom";
  import Navbar from "./Components/Navbar";

  // import DepartmentList from "./components/departments/DepartmentList";
  // import AddDepartment from "./components/departments/AddDepartment";
  // import EditDepartment from "./components/departments/EditDepartment";
  // import DepartmentEmployees from "./components/departments/DepartmentEmployees";

  // import EmployeeList from "./components/employees/EmployeeList";
  // import AddEmployee from "./components/employees/AddEmployee";
  // import EditEmployee from "./components/employees/EditEmployee";

  function App() {
    return (
      <Router>
        <Navbar />
        {/* <div className="container mt-4"> */}
          {/* <Routes> */}
            {/* Departments */}
            {/* <Route path="/" element={<DepartmentList />} /> */}
            {/* <Route path="/departments" element={<DepartmentList />} /> */}
            {/* <Route path="/departments/add" element={<AddDepartment />} /> */}
            {/* <Route path="/departments/edit/:id" element={<EditDepartment />} /> */}
            {/* <Route path="/departments/:id/employees" element={<DepartmentEmployees />} /> */}

            {/* Employees */}
            {/* <Route path="/employees" element={<EmployeeList />} />
            <Route path="/employees/add" element={<AddEmployee />} />
            <Route path="/employees/edit/:id" element={<EditEmployee />} /> */}
          {/* </Routes> */}
        {/* </div> */}
      </Router>
    );
  }

  export default App;
