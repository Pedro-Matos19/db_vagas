import { type Application } from '../types';
import api from './api';

export const getApplications = async () => {
  const response = await api.get<Application[]>('/api/applications');
  return response.data;
};

export const getApplicationById = async (id: number) => {
  const response = await api.get<Application>(`/api/applications/${id}`);
  return response.data;
};

export const createApplication = async (application: Application) => {
  const response = await api.post<Application>('/api/applications', application);
  return response.data;
};

export const updateApplication = async (id: number, application: Application) => {
  const response = await api.put<Application>(`/api/applications/${id}`, application);
  return response.data;
};

export const deleteApplication = async (id: number) => {
  await api.delete(`/api/applications/${id}`);
};
