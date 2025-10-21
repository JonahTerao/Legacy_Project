import React, { useState, useEffect } from 'react';
import './App.css';
import UserDashboard from './components/UserDashboard';
import NotificationSystem from './components/NotificationSystem';
import DarkModeToggle from './components/DarkModeToggle';

function App() {
  const [darkMode, setDarkMode] = useState(false);
  const [notifications, setNotifications] = useState([]);
  const [users, setUsers] = useState([]);

  useEffect(() => {
    // Simulate fetching user data
    const mockUsers = [
      { id: 1, name: 'John Doe', email: 'john@example.com', status: 'active', lastLogin: '2024-01-15' },
      { id: 2, name: 'Jane Smith', email: 'jane@example.com', status: 'inactive', lastLogin: '2024-01-10' },
      { id: 3, name: 'Bob Johnson', email: 'bob@example.com', status: 'active', lastLogin: '2024-01-14' }
    ];
    setUsers(mockUsers);
  }, []);

  const addNotification = (message, type = 'info') => {
    const newNotification = {
      id: Date.now(),
      message,
      type,
      timestamp: new Date().toLocaleTimeString()
    };
    setNotifications(prev => [newNotification, ...prev].slice(0, 5));
  };

  const removeNotification = (id) => {
    setNotifications(prev => prev.filter(notification => notification.id !== id));
  };

  const toggleDarkMode = () => {
    setDarkMode(!darkMode);
    addNotification(`Dark mode ${!darkMode ? 'enabled' : 'disabled'}`, 'success');
  };

  return (
    <div className={`App ${darkMode ? 'dark-mode' : ''}`}>
      <header className="app-header">
        <h1>User Management System</h1>
        <DarkModeToggle darkMode={darkMode} onToggle={toggleDarkMode} />
      </header>
      
      <NotificationSystem 
        notifications={notifications}
        onRemoveNotification={removeNotification}
      />
      
      <main className="app-main">
        <UserDashboard 
          users={users}
          onUserAction={addNotification}
        />
      </main>
    </div>
  );
}

export default App;
