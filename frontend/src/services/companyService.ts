import type { Company } from '../types';
import api from './api';

export const getCompanies = async () => {
  const response = await api.get<Company[]>('/api/companies');
  return response.data;
};

export const getCompanyById = async (id: number) => {
  const response = await api.get<Company>(`/api/companies/${id}`);
  return response.data;
};

export const createCompany = async (company: Company) => {
  const response = await api.post<Company>('/api/companies', company);
  return response.data;
};

export const updateCompany = async (id: number, company: Company) => {
  const response = await api.put<Company>(`/api/companies/${id}`, company);
  return response.data;
};

export const deleteCompany = async (id: number) => {
  await api.delete(`/api/companies/${id}`);
};
