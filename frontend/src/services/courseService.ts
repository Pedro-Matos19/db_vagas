import { type Course } from '../types';
import api from './api';

export const getCourses = async () => {
  const response = await api.get<Course[]>('/api/courses');
  return response.data;
};

export const getCourseById = async (id: number) => {
  const response = await api.get<Course>(`/api/courses/${id}`);
  return response.data;
};

export const createCourse = async (course: Course) => {
  const response = await api.post<Course>('/api/courses', course);
  return response.data;
};

export const updateCourse = async (id: number, course: Course) => {
  const response = await api.put<Course>(`/api/courses/${id}`, course);
  return response.data;
};

export const deleteCourse = async (id: number) => {
  await api.delete(`/api/courses/${id}`);
};
