export default function DCard({deptId, deptName, manager}){
    return(
        <div style={{
            justifyContent:"center",
            margin:"auto",
            textAlign:"center", backgroundColor:"whitesmoke", border:"2px solid black", borderRadius:"3px", width:"250px"
        }}> 
            <p>Department ID: {deptId}</p>
            <p>Department Name: {deptName}</p>
            <p>Manager: {manager}</p>
        </div>
    );
}