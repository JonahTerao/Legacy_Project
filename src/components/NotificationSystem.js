import React, { useEffect } from 'react';

const NotificationSystem = ({ notifications, onRemoveNotification }) => {
  useEffect(() => {
    // Auto-remove notifications after 5 seconds
    const autoRemoveTimers = notifications.map(notification => {
      return setTimeout(() => {
        onRemoveNotification(notification.id);
      }, 5000);
    });

    return () => {
      autoRemoveTimers.forEach(timer => clearTimeout(timer));
    };
  }, [notifications, onRemoveNotification]);

  if (notifications.length === 0) {
    return null;
  }

  return (
    <div className="notification-system">
      {notifications.map(notification => (
        <div key={notification.id} className={`notification ${notification.type}`}>
          <div className="notification-header">
            <strong>{notification.type.toUpperCase()}</strong>
            <button 
              className="close-btn"
              onClick={() => onRemoveNotification(notification.id)}
            >
              ×
            </button>
          </div>
          <div className="notification-body">
            {notification.message}
          </div>
          <div className="notification-timestamp">
            {notification.timestamp}
          </div>
        </div>
      ))}
    </div>
  );
};

export default NotificationSystem;
