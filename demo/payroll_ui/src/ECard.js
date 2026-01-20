export default function ECard({empId, empName, empRole, deptId}){
    return(
        <div style={{
            justifyContent:"center",
            margin:"auto",
            textAlign:"center", backgroundColor:"whitesmoke", border:"2px solid black", borderRadius:"3px", width:"250px"
        }}> 
            <p>Employee ID: {empId}</p>
            <p>Employee Name: {empName}</p>
            <p>Employee Role: {empRole}</p>
            <p>Department ID: {deptId}</p>
        </div>
    );
};