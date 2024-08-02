export interface Transaction {
  uuid: string;
  amount: number;
  status: "APPROVED" | "REVERSED" | "REFUNDED" | "ERROR";
  customerEmail: string;
  customerPhone: string;
  referenceId: string;
}
