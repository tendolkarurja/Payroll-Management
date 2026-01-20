import './App.css';
import ECard from './ECard';
import DCard from './DCard';
import { BrowserRouter, Routes, Route, Link } from 'react-router-dom';
import { useState } from 'react';


function Employee() {
  const [role, setRole] = useState('');
  const [eID, setEID] = useState('');

  const empDets = [
    { empId: 401, empName: "Ishaan", empRole: "HR Trainee", deptId: 101 },
    { empId: 402, empName: "Riya", empRole: "Software Engineer", deptId: 102 },
    { empId: 403, empName: "Arjun", empRole: "Senior Developer", deptId: 102 },
    { empId: 404, empName: "Neha", empRole: "Financial Analyst", deptId: 103 },
    { empId: 405, empName: "Aman", empRole: "Marketing Executive", deptId: 104 },
    { empId: 406, empName: "Kunal", empRole: "Operations Manager", deptId: 105 }
  ];

  const filteredEmp = empDets.filter(emp => {
    const roleMatch =
      role === '' ||
      emp.empRole.toLowerCase().includes(role.toLowerCase());

    const idMatch =
      eID === '' ||
      emp.empId.toString().includes(eID);

    return roleMatch && idMatch;
  });
  
  return (
    <div>
      <h2 className="page-title">Employees</h2>
      <div style={{display:"flex", margin:"auto", justifyContent:"center"}}> 
        <label>Find by role: 
        <input type='text' onChange={(e) =>setRole(e.target.value)} style={{margin:"8px", padding:"5px"}}/>
        </label>

        <label>Find by ID: 
        <input type='text' onChange={(e) =>setEID(Number(e.target.value))} style={{margin:"8px", padding:"5px"}}/>
        </label>

      </div>
      <div style={{display:"flex", gap:"8px", margin: "auto"}}>
       
      {
        filteredEmp.map(emp => (
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
  const [manager, setManager] = useState('');
  const [dID, setDID] = useState('');

  const deptDets = [
    { deptID: 101, deptName: "Human Resources", manager: "Ananya Sharma" },
    { deptID: 102, deptName: "Engineering", manager: "Rohit Verma" },
    { deptID: 103, deptName: "Finance", manager: "Neha Gupta" },
    { deptID: 104, deptName: "Marketing", manager: "Amit Patel" },
    { deptID: 105, deptName: "Operations", manager: "Kavita Rao" }
  ];

  const filteredDept = deptDets.filter(dep => {
    const managerMatch =
      manager === '' ||
      dep.manager.toLowerCase().includes(manager.toLowerCase());

    const idMatch =
      dID === '' ||
      dep.deptID.toString().includes(dID);

    return managerMatch && idMatch;
  });


  return (
    <div style={{margin:"auto", textAlign:"center"}}>
      <h2 className="page-title">Departments</h2>
      <div style={{display:"flex", margin:"auto", justifyContent:"center"}}> 
        <label>Find by manager: 
        <input type='text' onChange={(e) =>setManager(e.target.value)} style={{margin:"8px", padding:"5px"}}/>
        </label>

        <label>Find by ID: 
        <input type='text' onChange={(e) =>setDID(Number(e.target.value))} style={{margin:"8px", padding:"5px"}}/>
        </label>

      </div>
      <div style={{display:"flex", gap:"8px", margin: "auto"}}>{
        filteredDept.map(d =>
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
