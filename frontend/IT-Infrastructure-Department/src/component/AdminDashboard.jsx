import React, { useEffect, useState } from 'react';
import './AdminDashboard.css';
import api from "./api"; // axios instance của bạn

export default function AdminDashboard() {
  const [users, setUsers] = useState([]);

useEffect(() => {
  api.get('/admin/users')
    .then(res => {
      console.log("Users from API:", res.data);
      setUsers(res.data);
    })
    .catch(err => console.error(err));
}, []);

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
            <button className="add-btn">+ Add New User</button>
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
    </div>
  );
}
