import { type Candidate, type CandidateRequest } from '../types';
import api from './api';

export const getCandidates = async () => {
  const response = await api.get<Candidate[]>('/api/candidates');
  return response.data;
};

export const getCandidateById = async (id: number) => {
  const response = await api.get<Candidate>(`/api/candidates/${id}`);
  return response.data;
};

export const createCandidate = async (candidate: CandidateRequest) => {
  const response = await api.post<Candidate>('/api/candidates', candidate);
  return response.data;
};

export const updateCandidate = async (id: number, candidate: Candidate) => {
  const response = await api.put<Candidate>(`/api/candidates/${id}`, candidate);
  return response.data;
};

export const deleteCandidate = async (id: number) => {
  await api.delete(`/api/candidates/${id}`);
};
