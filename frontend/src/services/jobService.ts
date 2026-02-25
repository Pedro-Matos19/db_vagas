import { type Job, type JobRequest } from '../types';
import api from './api';

export const getJobs = async () => {
  const response = await api.get<Job[]>('/api/jobs');
  return response.data;
};

export const getJobById = async (id: number) => {
  const response = await api.get<Job>(`/api/jobs/${id}`);
  return response.data;
};

export const createJob = async (job: JobRequest) => {
  const response = await api.post<Job>('/api/jobs', job);
  return response.data;
};

export const updateJob = async (id: number, job: Job) => {
  const response = await api.put<Job>(`/api/jobs/${id}`, job);
  return response.data;
};

export const deleteJob = async (id: number) => {
  await api.delete(`/api/jobs/${id}`);
};
