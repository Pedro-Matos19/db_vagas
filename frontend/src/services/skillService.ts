import { type Skill } from '../types';
import api from './api';

export const getSkills = async () => {
  const response = await api.get<Skill[]>('/api/skills');
  return response.data;
};

export const getSkillById = async (id: number) => {
  const response = await api.get<Skill>(`/api/skills/${id}`);
  return response.data;
};

export const createSkill = async (skill: Skill) => {
  const response = await api.post<Skill>('/api/skills', skill);
  return response.data;
};

export const updateSkill = async (id: number, skill: Skill) => {
  const response = await api.put<Skill>(`/api/skills/${id}`, skill);
  return response.data;
};

export const deleteSkill = async (id: number) => {
  await api.delete(`/api/skills/${id}`);
};
