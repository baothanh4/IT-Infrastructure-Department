import React, { useEffect, useState } from 'react';
import './AdminDashboard.css';
import api from "./api"; // axios instance của bạn

export default function AdminDashboard() {
  const [users, setUsers] = useState([]);
  const [roles, setRoles] = useState([]);
  const [showAddUser, setShowAddUser] = useState(false);
  const [newUser, setNewUser] = useState({
    username: '',
    password: '',
    email: '',
    phone: '',
    full_name: '',
    roleId: ''
  });
  const [errors, setErrors] = useState({});

  // Load users
  useEffect(() => {
    api.get('/admin/users')
      .then(res => setUsers(res.data))
      .catch(err => console.error(err));
  }, []);

  // Load roles
  useEffect(() => {
    api.get('/admin/roles')
      .then(res => setRoles(res.data))
      .catch(err => console.error(err));
  }, []);

  // Handle input change
  const handleChange = (e) => {
    const { name, value } = e.target;
    setNewUser(prev => ({ ...prev, [name]: value }));
  };

  // Basic client-side validation
  const validate = () => {
    let temp = {};
    if(!newUser.username) temp.username = "Username is required";
    if(!newUser.password) temp.password = "Password is required";
    if(!newUser.email) temp.email = "Email is required";
    if(!newUser.phone) temp.phone = "Phone is required";
    if(!newUser.full_name) temp.full_name = "Full name is required";
    if(!newUser.roleId) temp.roleId = "Role is required";
    setErrors(temp);
    return Object.keys(temp).length === 0;
  };

  // Handle form submit
  const handleSubmit = () => {
    if(!validate()) return;

    const payload = { ...newUser, roleId: Number(newUser.roleId) };

    api.post('/admin/users', payload)
      .then(res => {
        setUsers(prev => [...prev, res.data]);
        setShowAddUser(false);
        setNewUser({ username:'', password:'', email:'', phone:'', full_name:'', roleId:'' });
        setErrors({});
      })
      .catch(err => {
        console.error(err);
        alert("Failed to add user. Check console for details.");
      });
  };

  return (
    <div className="admin-root">
      <aside className="sidebar">
        <div className="sidebar-header">Admin Name<br/><span className="muted">IT Department</span></div>
        <nav className="nav">
          <a className="nav-item active">Dashboard</a>
          <a className="nav-item">User Management</a>
          <a className="nav-item">Systems</a>
          <a className="nav-item">Logs</a>
          <a className="nav-item">Settings</a>
        </nav>
        <div className="sidebar-footer">Log Out</div>
      </aside>

      <section className="main-area">
        <header className="main-header">
          <div>
            <h1>User Management</h1>
            <p className="sub">Manage information for all staff including IT, Users, and Managers.</p>
          </div>
          <div className="actions">
            <input className="search" placeholder="Search by username, name, email..." />
            <button className="add-btn" onClick={() => setShowAddUser(true)}>+ Add New User</button>
          </div>
        </header>

        <div className="table-card">
          <div className="table-controls">
            <div className="filters">
              <select><option>Role: All</option></select>
              <select><option>Status: All</option></select>
            </div>
          </div>

          <table className="users-table">
            <thead>
              <tr>
                <th>USERNAME</th>
                <th>FULL NAME</th>
                <th>EMAIL</th>
                <th>ROLE</th>
                <th>STATUS</th>
                <th>ACTIONS</th>
              </tr>
            </thead>
            <tbody>
              {users.map(u => (
                <tr key={u.id}>
                  <td className="mono">{u.username}</td>
                  <td>{u.full_name}</td>
                  <td>{u.email}</td>
                  <td>{u.role_name}</td>
                  <td>
                    <span className={`status ${u.status === 'ACTIVED' ? 'active' : 'inactive'}`}>
                      {u.status}
                    </span>
                  </td>
                  <td className="actions-cell">✏️ 🔑 ⋮</td>
                </tr>
              ))}
            </tbody>
          </table>

          <div className="table-footer">
            Showing 1-{users.length} of {users.length}
            <div className="pager">Previous 1 2 Next</div>
          </div>
        </div>
      </section>

      {/* Add User Modal */}
      {showAddUser && (
        <div className="modal-backdrop">
          <div className="modal">
            <h2>Add New User</h2>
            <div className="modal-body">
              <input
                name="username"
                placeholder="Username"
                value={newUser.username}
                onChange={handleChange}
              />
              {errors.username && <small className="error">{errors.username}</small>}

              <input
                name="password"
                placeholder="Password"
                type="password"
                value={newUser.password}
                onChange={handleChange}
              />
              {errors.password && <small className="error">{errors.password}</small>}

              <input
                name="full_name"
                placeholder="Full Name"
                value={newUser.full_name}
                onChange={handleChange}
              />
              {errors.full_name && <small className="error">{errors.full_name}</small>}

              <input
                name="email"
                placeholder="Email"
                value={newUser.email}
                onChange={handleChange}
              />
              {errors.email && <small className="error">{errors.email}</small>}

              <input
                name="phone"
                placeholder="Phone"
                value={newUser.phone}
                onChange={handleChange}
              />
              {errors.phone && <small className="error">{errors.phone}</small>}

              <select
                name="roleId"
                value={newUser.roleId}
                onChange={handleChange}
              >
                <option value="">Select Role</option>
                {roles.map(role => (
                  <option key={role.id} value={role.id}>{role.name}</option>
                ))}
              </select>
              {errors.roleId && <small className="error">{errors.roleId}</small>}
            </div>
            <div className="modal-footer">
              <button onClick={handleSubmit}>Add User</button>
              <button onClick={() => { setShowAddUser(false); setErrors({}); }}>Cancel</button>
            </div>
          </div>
        </div>
      )}
    </div>
  );
} 