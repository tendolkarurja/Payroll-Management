import './App.css';
import ECard from './ECard';
import DCard from './DCard';
import { BrowserRouter, Routes, Route, Link } from 'react-router-dom';

function Employee() {
  const empDets = [
    { empId: 1, empName: "Ishaan", empRole: "HR Trainee", deptId: 101 },
    { empId: 2, empName: "Riya", empRole: "Software Engineer", deptId: 102 },
    { empId: 3, empName: "Arjun", empRole: "Senior Developer", deptId: 102 },
    { empId: 4, empName: "Neha", empRole: "Financial Analyst", deptId: 103 },
    { empId: 5, empName: "Aman", empRole: "Marketing Executive", deptId: 104 },
    { empId: 6, empName: "Kunal", empRole: "Operations Manager", deptId: 105 }
  ];

  return (
    <div>
      <h2 className="page-title">Employees</h2>
      <div style={{display:"flex", gap:"8px", margin: "auto"}}>
      {
        empDets.map(emp => (
          <ECard
            key={emp.empId}
            empId={emp.empId}
            empName={emp.empName}
            empRole={emp.empRole}
            deptId={emp.deptId}
          />
        ))
      }
      </div>
    </div>
  );
}

function Department() {
  const deptDets = [
    { deptID: 101, deptName: "Human Resources", manager: "Ananya Sharma" },
    { deptID: 102, deptName: "Engineering", manager: "Rohit Verma" },
    { deptID: 103, deptName: "Finance", manager: "Neha Gupta" },
    { deptID: 104, deptName: "Marketing", manager: "Amit Patel" },
    { deptID: 105, deptName: "Operations", manager: "Kavita Rao" }
  ];

  return (
    <div style={{margin:"auto", textAlign:"center"}}>
      <h2 className="page-title">Departments</h2>
      <div style={{display:"flex", gap:"8px", margin: "auto"}}>{
        deptDets.map(d =>
          <DCard 
            key={d.deptID}
            deptId={d.deptID}
            deptName={d.deptName}
            manager={d.manager}
          />
        )
      }
      </div>
    </div>
  );
}

function Home() {
  return (
    <div className="home">
      <h2>Welcome to Payroll Manager</h2>
      <div className="btn-group">
        <Link to="/employees">
          <button>View Employees</button>
        </Link>
        <Link to="/departments">
          <button>View Departments</button>
        </Link>
      </div>
    </div>
  );
}

function App() {
  return (
    <BrowserRouter>
      <Routes>
        <Route path="/" element={<Home />} />
        <Route path="/employees" element={<Employee />} />
        <Route path="/departments" element={<Department />} />
      </Routes>
    </BrowserRouter>
  );
}

export default App;
