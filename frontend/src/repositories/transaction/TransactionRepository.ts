import { Transaction } from "./Transaction.ts";
import api from "../api/Api.ts";

const API_URL = "/api/transactions";

export class TransactionRepository {
  async getAll(): Promise<Transaction[]> {
    const response = await api.get<Transaction[]>(API_URL);
    return response.data;
  }

  async getById(uuid: string): Promise<Transaction> {
    const response = await api.get<Transaction>(`${API_URL}/${uuid}`);
    return response.data;
  }

  async create(transaction: Omit<Transaction, "uuid">): Promise<Transaction> {
    const response = await api.post<Transaction>(API_URL, transaction);
    return response.data;
  }
}
