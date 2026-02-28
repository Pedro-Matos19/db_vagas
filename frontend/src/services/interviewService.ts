import type { InterviewScheduleDetail } from '../types';
import api from './api';

export const getInterviewSchedule = async () => {
  const response = await api.get<InterviewScheduleDetail[]>('/api/interviews/schedule');
  return response.data;
};
