import React, { useEffect, useState } from "react";
import { AuthService } from "./authentication/AuthService.ts";
import { Login } from "./Login.tsx";

const App: React.FC = () => {
  const [isAuthenticated, setIsAuthenticated] = useState(
    AuthService.isAuthenticated(),
  );

  useEffect(() => {
    const token = AuthService.getToken();
    if (token != null) {
      AuthService.loginWithLocalToken();
    }
  }, []);

  const handleLoginSuccess = () => {
    setIsAuthenticated(true);
  };

  const handleLogout = () => {
    AuthService.logout();
    setIsAuthenticated(false);
  };

  if (!isAuthenticated) {
    return <Login onLoginSuccess={handleLoginSuccess} />;
  }

  return (
    <div>
      <h1>Welcome to the Dashboard</h1>
      <button onClick={handleLogout}>Logout</button>
    </div>
  );
};

export default App;
