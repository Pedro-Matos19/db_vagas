import type { InterviewRequest, InterviewScheduleDetail } from '../types';
import api from './api';

export const createInterview = async (interview: InterviewRequest) => {
  const response = await api.post('/api/interviews', interview);
  return response.data;
};

export const getInterviewSchedule = async () => {
  const response = await api.get<InterviewScheduleDetail[]>('/api/interviews/schedule');
  return response.data;
};
