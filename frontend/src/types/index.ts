export interface User {
  id?: number;
  email: string;
  password: string;
}

export interface Company {
  id?: number;
  cnpj: string;
  corporateName: string;
  location: string;
  website: string;
  sector: string;
  description: string;
  user?: User;
}

export interface Candidate {
  id?: number;
  cpf: string;
  name: string;
  birthDate: string;
  user?: User;
}

export interface CandidateRequest {
  cpf: string;
  name: string;
  birthDate: string;
  userId?: number;
  skillsId: number[];
  coursesId: number[];
}

export interface Skill {
  id?: number;
  name: string;
}

export interface Course {
  id?: number;
  name: string;
  level: string;
}

export interface Job {
  id?: number;
  title: string;
  description: string;
  type: string;
  salary: number;
  status: string;
  company: Company;
  requiredSkills: Skill[];
}

export interface JobRequest {
  title: string;
  description: string;
  type: string;
  salary: number;
  status: string;
  companyId: number;
  skillsId: number[];
}

export interface Application {
  id?: number;
  status: string;
  candidate: Candidate;
  job: Job;
}

export interface ApplicationDetail {
  idCandidatura: number;
  nomeCandidato: string;
  tituloVaga: string;
  nomeEmpresa: string;
  dataAplicacao: string;
  statusCandidatura: string;
}

export interface InterviewScheduleDetail {
  idInterview: number;
  interviewDateTime: string;
  candidateName: string;
  jobTitle: string;
  companyName: string;
  meetingLink: string;
  interviewStatus: string;
}

export interface JobPerformanceSummaryId {
  companyName: string;
  jobTitle: string;
  jobStatus: string;
  salary: number;
}

export interface JobPerformanceSummaryItem {
  id: JobPerformanceSummaryId;
  totalCandidates: number;
  jobTitle: string;
  jobStatus: string;
  salary: number;
  companyName: string;
}
