import { Merchant } from "./Merchant.ts";
import api from "../api/Api.ts";

const API_URL = "/merchants";

export class MerchantRepository {
  async getAll(): Promise<Merchant[]> {
    const response = await api.get<Merchant[]>(API_URL);
    return response.data;
  }

  async getById(id: number): Promise<Merchant> {
    const response = await api.get<Merchant>(`${API_URL}/${id}`);
    return response.data;
  }

  async create(merchant: Omit<Merchant, "id">): Promise<Merchant> {
    const response = await api.post<Merchant>(API_URL, merchant);
    return response.data;
  }

  async update(id: number, merchant: Omit<Merchant, "id">): Promise<Merchant> {
    const response = await api.put<Merchant>(`${API_URL}/${id}`, merchant);
    return response.data;
  }

  async delete(id: number): Promise<void> {
    await api.delete(`${API_URL}/${id}`);
  }
}
