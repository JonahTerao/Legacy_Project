import React from 'react';

const UserDashboard = ({ users, onUserAction }) => {
  const handleUserAction = (action, userName) => {
    onUserAction(`${action} performed for ${userName}`, 'info');
  };

  const getStatusDisplay = (status) => {
    return status === 'active' ? 'status-active' : 'status-inactive';
  };

  return (
    <div className="user-dashboard">
      <h2>User Management Dashboard</h2>
      <p>Total Users: {users.length}</p>
      
      <div className="user-grid">
        {users.map(user => (
          <div key={user.id} className="user-card">
            <h3>{user.name}</h3>
            <p><strong>Email:</strong> {user.email}</p>
            <p>
              <strong>Status:</strong> 
              <span className={`user-status ${getStatusDisplay(user.status)}`}>
                {user.status}
              </span>
            </p>
            <p><strong>Last Login:</strong> {user.lastLogin}</p>
            
            <div className="user-actions">
              <button 
                onClick={() => handleUserAction('Edit', user.name)}
                className="action-btn"
              >
                Edit
              </button>
              <button 
                onClick={() => handleUserAction('Message', user.name)}
                className="action-btn"
              >
                Send Message
              </button>
            </div>
          </div>
        ))}
      </div>
    </div>
  );
};

export default UserDashboard;
