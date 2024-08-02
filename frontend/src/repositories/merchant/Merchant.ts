export interface Merchant {
  id: number;
  name: string;
  description: string;
  email: string;
  status: "ACTIVE" | "INACTIVE";
  totalTransactionSum: number;
}
