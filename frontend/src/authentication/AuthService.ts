import api from "../repositories/api/Api.ts";

export class AuthService {
  static async login(username: string, password: string): Promise<string> {
    const response = await api.post(`/login`, {
      username,
      password,
    });

    const token = response.data.token;
    localStorage.setItem("token", token);
    api.defaults.headers.common["Authorization"] = `Bearer ${token}`;

    return token;
  }

  static logout(): void {
    localStorage.removeItem("token");
    delete api.defaults.headers.common["Authorization"];
  }

  static getToken(): string | null {
    return localStorage.getItem("token");
  }

  static loginWithLocalToken(): void {
    const token = this.getToken();
    api.defaults.headers.common["Authorization"] = `Bearer ${token}`;
  }

  static isAuthenticated(): boolean {
    return !!this.getToken();
  }
}
